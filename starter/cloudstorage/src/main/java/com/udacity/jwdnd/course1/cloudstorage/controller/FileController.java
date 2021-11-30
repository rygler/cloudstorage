package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;

@Controller
@RequestMapping("/home/file")
public class FileController {
    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping
    public String postFile(Authentication authentication, FileForm fileForm, Model model) {
        if (fileForm.getFile().isEmpty()) {
            //todo: add description
            model.addAttribute("error", true);
            model.addAttribute("message", "No file selected!");
            return "result";
        }

        int userId = userService.getUserId(authentication.getName());

        if (!fileService.isFileNameAvailable(fileForm.getFile().getOriginalFilename(), userId)) {
            //todo: add description
            model.addAttribute("error", true);
            model.addAttribute("message", "A file with this name already exists!");
            return "result";
        }

        fileForm.setUserId(userId);

        try {
            fileService.createFile(fileForm);
            System.out.println("file created");
            model.addAttribute("success", true);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", true);
            model.addAttribute("message", "Oops. Something went wrong on our end. Try again later.");
        }

        return "result";
    }

    @GetMapping("/delete")
    public String deleteFile(int fileId, Model model) {
        fileService.deleteFile(fileId);
        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping("/view")
    public ResponseEntity<Resource> getFile(int fileId) {
        UserFile file = fileService.getFileByFileId(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + file.getFileName());
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(Integer.parseInt(file.getFileSize()))
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(resource);
    }
}
