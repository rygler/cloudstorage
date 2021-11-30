package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(NoteForm noteForm) {
        return noteMapper.insert(new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), noteForm.getUserId()));
    }

    public boolean isNoteTitleAvailable(String noteTitle, Integer userId) {
        return noteMapper.getNoteByTitle(noteTitle, userId) == null;
    }

    public Note getNoteByNoteTitle(String noteTitle, Integer noteId) {
        return noteMapper.getNoteByTitle(noteTitle, noteId);
    }

    public Note getNoteByNoteId(Integer noteId) {
        return noteMapper.getNoteByUserId(noteId);
    }

    public List<Note> getAllNotesOfUser(Integer userId) {
        return noteMapper.getAllNotesOfUser(userId);
    }

    public void updateNote(Note note) {
        noteMapper.update(note);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }
}
