package com.bridgelabz.onlinebookstore.utils;

public interface IEmailService {
    void notifyThroughEmail(String email, String subject, String message);
}
