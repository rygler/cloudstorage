package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.ListCredential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credential credential) {
        String encodedKey = generateEncodedKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialMapper.insert(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserId()));
    }

    public int createCredential(CredentialForm credentialForm, Integer userId) {
        String encodedKey = generateEncodedKey();
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        return credentialMapper.insert(new Credential(null, credentialForm.getUrl(), credentialForm.getUsername(), encodedKey, encryptedPassword, userId));
    }

    public Credential getCredential(Integer credentialId) {
        Credential credential = credentialMapper.getCredential(credentialId);
        if (credential == null) {
            return null;
        }
        return decryptCredential(credential);
    }

    public List<ListCredential> getAllCredentialsOfUser(Integer userId) {
        List<Credential> credentials = credentialMapper.getAllCredentialsOfUser(userId);
        List<ListCredential> decryptedCredentials = new ArrayList<>(credentials.size());

        for (Credential credential : credentials) { // pass over the credentials and add decrypted version to new list
            ListCredential listCredential = new ListCredential(credential, encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
            decryptedCredentials.add(listCredential);
        }

        return decryptedCredentials;
    }

    public void updateCredential(Credential credential) {
        String encodedKey = generateEncodedKey();
        String decryptedPassword = credential.getPassword();
        String encryptedPassword = encryptionService.encryptValue(decryptedPassword, encodedKey);
        credentialMapper.update(new Credential(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, credential.getUserId()));
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }

    private Credential decryptCredential(Credential credential) {
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        return new Credential(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), credential.getKey(), decryptedPassword, credential.getUserId());
    }

    private String generateEncodedKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
