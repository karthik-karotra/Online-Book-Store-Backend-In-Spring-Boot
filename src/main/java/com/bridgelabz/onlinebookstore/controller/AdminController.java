package com.bridgelabz.onlinebookstore.controller;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.exceptions.AdminException;
import com.bridgelabz.onlinebookstore.exceptions.UserException;
import com.bridgelabz.onlinebookstore.filterenums.OrderStatus;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.service.IAdminBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminBookStoreService adminBookStoreService;

    @PostMapping("/book")
    public ResponseEntity<ResponseDTO> saveBook(@RequestHeader(value = "token") String token, @Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new AdminException("Invalid Data!!!!! Please Enter Valid Data", AdminException.ExceptionType.INVALID_DATA);
        }
        String message = adminBookStoreService.saveBook(token, bookDTO);
        ResponseDTO responseDTO = new ResponseDTO(message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/book/image")
    public String uploadBookImageToLocalFileSystem(@RequestParam("file") MultipartFile file, @RequestHeader(value = "token") String token) {
        String message = adminBookStoreService.uploadImage(file, token);
        return message;
    }

    @GetMapping("/orders/{pageNo}")
    public ResponseEntity<ResponseDTO> getOrderDetails(@PathVariable Integer pageNo, @RequestHeader(value = "token") String token) {
        List<OrderBookDetails> orderBookDetailsList = adminBookStoreService.getOrders(pageNo, 12, token);
        ResponseDTO responseDTO = new ResponseDTO(orderBookDetailsList, "Response Successful");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/order/status/{orderId}/{orderStatus}")
    public ResponseEntity updateOrderStatus(@PathVariable Integer orderId, @PathVariable OrderStatus orderStatus, @RequestHeader(value = "token") String token) {
        String message = adminBookStoreService.updateOrderStatus(orderId, orderStatus, token);
        ResponseDTO responseDto = new ResponseDTO(message);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
