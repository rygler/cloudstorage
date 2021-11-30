package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/credential")
public class CredentialController {
    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping
    public String createCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        int userId = userService.getUserId(authentication.getName());
        Credential existingCredential = credentialService.getCredential(credentialForm.getCredentialId());
        if (existingCredential == null) {
            credentialService.createCredential(credentialForm, userId);
        } else {
            existingCredential.setPassword(credentialForm.getPassword());
            existingCredential.setUrl(credentialForm.getUrl());
            existingCredential.setUsername(credentialForm.getUsername());
            credentialService.updateCredential(existingCredential);
        }

        model.addAttribute("success", true);

        return "result";
    }

    @GetMapping("/delete")
    public String deleteCredential(int credentialId, Model model) {
        credentialService.deleteCredential(credentialId);
        model.addAttribute("success", true);
        return "result";
    }

}
