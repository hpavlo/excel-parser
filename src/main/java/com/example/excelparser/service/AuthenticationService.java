package com.example.excelparser.service;

import com.example.excelparser.model.User;

public interface AuthenticationService {
    User register(String login, String password);

    String login(String login, String password);

    boolean usernameAvailabilityCheck(String login);
}
