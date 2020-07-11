package com.bridgelabz.onlinebookstore.dto;

import com.bridgelabz.onlinebookstore.filterenums.UserRole;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
public class UserRegistrationDTO {

    @NotNull
    @Pattern(regexp = "^.{3,50}$")
    public String fullName;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z]{3,}([.|_|+|-]?[a-zA-Z0-9]+)?[@][a-zA-Z0-9]+[.][a-zA-Z]{2,3}([.]?[a-zA-Z]{2,3})?)$")
    public String email;

    @NotNull
    @Pattern(regexp = "^((?=[^@|#|&|%|$]*[@|&|#|%|$][^@|#|&|%|$]*$)*(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9#@$?]{8,})$")
    public String password;

    @NotNull
    @Pattern(regexp = "^([6-9]{1}[0-9]{9})$")
    public String phoneNo;

    public boolean status;
    public UserRole userRole;

}
