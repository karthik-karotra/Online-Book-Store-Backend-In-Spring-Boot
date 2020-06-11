package com.bridgelabz.onlinebookstore.utils;

public interface ITokenGenerator {
    String generateToken(int id,int expirationTime);
    int getId(String token);
}
