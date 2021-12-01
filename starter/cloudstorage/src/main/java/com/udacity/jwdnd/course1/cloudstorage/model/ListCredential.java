package com.udacity.jwdnd.course1.cloudstorage.model;

public class ListCredential extends Credential{
    private String encryptedPassword;
    public ListCredential(Integer id, String url, String username, String key, String decryptedPassword, String encryptedPassword, Integer userId) {
        super(id, url, username, key, decryptedPassword, userId);
        this.encryptedPassword = encryptedPassword;
    }

    public ListCredential(Credential credential, String decryptedPassword) {
        super(credential.getCredentialId(), credential.getUrl(), credential.getUsername(), credential.getKey(), decryptedPassword, credential.getUserId());
        this.encryptedPassword = credential.getPassword();
    }

    public ListCredential () {
        super();
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
