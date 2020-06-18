package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.filterenums.OrderStatus;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface IAdminBookStoreService {
    String saveBook(String token, BookDTO bookDTO);
    String uploadImage(MultipartFile file, String token);
    List<OrderBookDetails> getOrders(Integer pageNo, Integer pageSize, String token);
    String updateOrderStatus(Integer orderId, OrderStatus orderStatus, String token);
    String adminLogin(UserLoginDTO userLoginDTO);

}
