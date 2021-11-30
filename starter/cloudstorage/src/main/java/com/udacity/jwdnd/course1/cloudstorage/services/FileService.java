package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer createFile(FileForm fileForm) throws IOException {
        return fileMapper.createFile(new UserFile(null, fileForm.getFile().getOriginalFilename(), fileForm.getFile().getContentType(), Long.toString(fileForm.getFile().getSize()), fileForm.getUserId(), fileForm.getFile().getBytes()));
    }

    public boolean isFileNameAvailable(String fileName, Integer userId) {
        return getFileByName(fileName, userId) == null;
    }

    public UserFile getFileByName(String fileName, Integer userId) {
        return fileMapper.getFileByFileName(fileName, userId);
    }

    public UserFile getFileByFileId(Integer fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    public List<UserFile> getAllFilesOfUser(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    public void updateFile(UserFile file) {
        fileMapper.updateFile(new UserFile(file));
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFileByFileId(fileId);
    }
}
