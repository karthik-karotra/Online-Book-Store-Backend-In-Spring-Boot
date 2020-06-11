package com.bridgelabz.onlinebookstore.dto;

import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class OrderBookDTO {

    @NotNull
    public Integer bookId;

    @NotNull
    @Range(min = 1, message = "Quantity Shoud Be Greater Than Zero")
    public Integer quantity;

    @NotNull
    @Range(min = 1, message = "Book Price Should Be Greater Than Zero")
    public Double orderPrice;

    @NotNull
    @Pattern(regexp = "^.{3,30}$", message = "Enter Valid Name")
    public String customerName;

    @NotNull
    @Pattern(regexp = "^[6-9]{1}[0-9]{9}$", message = "Enter Valid Mobile Number")
    public String mobileNo;

    @NotNull
    @Pattern(regexp = "^[1-9]{1}[0-9]{5}$", message = "Enter Valid Pincode")
    public String pincode;

    @NotNull
    @Pattern(regexp = "^^.{3,30}$", message = "Enter Valid Locality")
    public String locality;

    @NotNull
    public String address;

    @NotNull
    @Pattern(regexp = "^.{3,30}$", message = "Enter Valid City")
    public String city;

    @NotNull
    @Pattern(regexp = "^.{3,30}$", message = "Enter Valid LandMark")
    public String landmark;

    public String type;

    public OrderBookDTO(Integer bookId, Integer quantity, Double orderPrice, String customerName, String mobileNo, String pincode, String locality, String address, String city, String landmark, String type) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.customerName = customerName;
        this.mobileNo = mobileNo;
        this.pincode = pincode;
        this.locality = locality;
        this.address = address;
        this.city = city;
        this.landmark = landmark;
        this.type = type;
    }
}
