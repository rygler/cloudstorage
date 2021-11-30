package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public final static int USER_NOT_FOUND = -1;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int createUser(User user) {
        String encodedSalt = getNewSalt();
        String hashedPassword = hashPassword(user.getPassword(), encodedSalt);
        return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    public Integer getUserId(String username) {
        return userMapper.getUser(username).getUserId();
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public void updateUser(User user) {
        String encodedSalt = getNewSalt();
        String hashedPassword = hashPassword(user.getPassword(), getNewSalt());
        userMapper.update(new User(user.getUserId(), user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    public void deleteUser(Integer userId) {
        userMapper.delete(userId);
    }

    private String getNewSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String encodedSalt) {
        return hashService.getHashedValue(password, encodedSalt);
    }

    public String findCurrentUsername(Authentication authentication) {
        return authentication.getName();
    }
}