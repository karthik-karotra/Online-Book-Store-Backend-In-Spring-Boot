package com.bridgelabz.onlinebookstore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserLoginDTO {

    @NotNull
    @Pattern(regexp = "^([a-zA-Z]{3,}([.|_|+|-]?[a-zA-Z0-9]+)?[@][a-zA-Z0-9]+[.][a-zA-Z]{2,3}([.]?[a-zA-Z]{2,3})?)$")
    public String email;

    @NotNull
    @Pattern(regexp = "^((?=[^@|#|&|%|$]*[@|&|#|%|$][^@|#|&|%|$]*$)*(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9#@$?]{8,})$")
    public String password;

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
