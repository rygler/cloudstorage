package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/home/note")
public class NoteController {
    private UserService userService;
    private NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping
    public String createNote(Authentication authentication, NoteForm noteForm, Model model) {
        if (noteForm.getNoteTitle().length() > 20) {
            addTitleLengthErrorToModel(model);
            return "result";
        }

        if (noteForm.getNoteDescription().length() > 1000) {
            addDescriptionLengthErrorToModel(model);
            return "result";
        }

        int userId = userService.getUserId(authentication.getName());
        Note existingNote = noteService.getNoteByNoteId(noteForm.getNoteId());
        if (existingNote == null && noteService.isNoteTitleAvailable(noteForm.getNoteTitle(), userId)) {
            noteForm.setUserId(userId);
            noteService.createNote(noteForm);
            addSuccessToModel(model);
        } else if (existingNote != null) {
            if (noteService.getNoteByNoteTitle(noteForm.getNoteTitle(), userId) != null && Objects.equals(noteService.getNoteByNoteTitle(noteForm.getNoteTitle(), userId).getNoteId(), existingNote.getNoteId())) {
                addNoteExistsErrorToModel(model);
                return "result";
            }
            existingNote.setNoteTitle(noteForm.getNoteTitle());
            existingNote.setNoteDescription(noteForm.getNoteDescription());
            noteService.updateNote(existingNote);
            addSuccessToModel(model);
        } else {
            addTryLaterErrorToModel(model);
        }

        return "result";
    }

    @GetMapping("/delete")
    public String deleteNote(int noteId, Model model) {
        noteService.deleteNote(noteId);
        model.addAttribute("success", true);
        return "result";
    }

    private void addTitleLengthErrorToModel(Model model) {
        model.addAttribute("error", true);
        model.addAttribute("message", "The note title cannot exceed 20 characters!");
    }

    private void addDescriptionLengthErrorToModel(Model model) {
        model.addAttribute("error", true);
        model.addAttribute("message", "The note description cannot exceed 1000 characters!");
    }

    private void addNoteExistsErrorToModel(Model model) {
        model.addAttribute("error", true);
        model.addAttribute("message", "The note title already exists.");
    }

    private void addSuccessToModel(Model model) {
        model.addAttribute("success", true);
    }

    private void addTryLaterErrorToModel(Model model) {
        model.addAttribute("error", true);
        model.addAttribute("message", "Something went wrong. Try again later. ");
    }
}
