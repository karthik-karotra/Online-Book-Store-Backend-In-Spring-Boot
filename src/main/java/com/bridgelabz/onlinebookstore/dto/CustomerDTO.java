package com.bridgelabz.onlinebookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @NotNull
    public String address;

    @NotNull
    @Pattern(regexp = "^[a-zA-z]+$", message = "Enter Valid City")
    public String city;

    @NotNull
    @Pattern(regexp = "^[1-9]{1}[0-9]{5}$", message = "Enter Valid Pincode")
    public String pincode;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Enter Valid LandMark")
    public String landmark;

    @NotNull
    @Pattern(regexp = "^[A-Za-z]{3,}$", message = "Enter Valid Locality")
    public String locality;

    public String type;

}
