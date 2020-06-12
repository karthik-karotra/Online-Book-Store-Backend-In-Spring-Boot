package com.bridgelabz.onlinebookstore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ResetPasswordDTO {

    @NotNull
    @Pattern(regexp = "^((?=[^@|#|&|%|$]*[@|&|#|%|$][^@|#|&|%|$]*$)*(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9#@$?]{8,})$")
    public String password;

    public ResetPasswordDTO(String password) {
        this.password=password;
    }

    public ResetPasswordDTO() {
    }
}
