package com.bridgelabz.onlinebookstore.dto;

public class OrderBookDTO {

    public Integer bookId;
    public Integer quantity;
    public Double orderPrice;
    public String customerName;
    public String mobileNo;
    public String pincode;
    public String locality;
    public String address;
    public String city;
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
