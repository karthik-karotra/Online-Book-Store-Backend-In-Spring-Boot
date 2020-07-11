package com.bridgelabz.onlinebookstore.service.implementations;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.exceptions.AdminException;
import com.bridgelabz.onlinebookstore.filterenums.OrderStatus;
import com.bridgelabz.onlinebookstore.filterenums.UserRole;
import com.bridgelabz.onlinebookstore.models.BookDetails;
import com.bridgelabz.onlinebookstore.models.OrderBookDetails;
import com.bridgelabz.onlinebookstore.models.UserDetails;
import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import com.bridgelabz.onlinebookstore.repository.AdminRepository;
import com.bridgelabz.onlinebookstore.repository.OrderBookRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.service.IAdminBookStoreService;
import com.bridgelabz.onlinebookstore.utils.ITokenGenerator;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminBookStoreService implements IAdminBookStoreService {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderBookRepository orderBookRepository;

    @Autowired
    CartService cartService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ITokenGenerator tokenGenerator;

    @Override
    public String saveBook(String token, BookDTO bookDTO) {
        cartService.isUserPresent(token);
        BookDetails bookDetails = new BookDetails(bookDTO);
        Optional<BookDetails> optionalBookDetails = adminRepository.findByIsbn(bookDTO.isbn);
        Optional<BookDetails> optionalBookDetails1 = adminRepository.findByBookNameAndAuthorName(bookDTO.bookName, bookDTO.authorName);
        if (optionalBookDetails.isPresent()) {
            throw new AdminException("ISBN No Already Exists", AdminException.ExceptionType.ISBN_NO_ALREADY_EXISTS);
        }
        if (optionalBookDetails1.isPresent()) {
            throw new AdminException("Book And Author Name Already Exists", AdminException.ExceptionType.BOOK_AND_AUTHOR_NAME_ALREADY_EXISTS);
        }
        adminRepository.save(bookDetails);
        return "ADDED SUCCESSFULLY";
    }

    @Override
    public String uploadImage(MultipartFile file, String token) {
        cartService.isUserPresent(token);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileBasePath = System.getProperty("user.dir") + applicationProperties.getUploadDirectory();
        if (!(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png"))) {
            throw new AdminException("Only Image Files Can Be Uploaded", AdminException.ExceptionType.INCOMPATIBLE_TYPE);
        }
        Path path = Paths.get(fileBasePath + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageResponse = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("book/")
                .path(fileName)
                .toUriString();
        return imageResponse;
    }

    @Override
    public List<OrderBookDetails> getOrders(Integer pageNo, Integer pageSize, String token) {
        cartService.isUserPresent(token);
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<OrderBookDetails> orders = orderBookRepository.findAll(paging);
        if (!orders.hasContent())
            throw new AdminException("No Orders", AdminException.ExceptionType.NO_ORDER_FOUND);
        List<OrderBookDetails> orderBookDetailsList = orders.getContent();
        return new ArrayList<>(Lists.reverse(orderBookDetailsList));
    }

    @Override
    public String updateOrderStatus(Integer orderId, OrderStatus orderStatus, String token) {
        cartService.isUserPresent(token);
        Optional<OrderBookDetails> optionalOrderDetails = orderBookRepository.findById(orderId);
        if (!optionalOrderDetails.isPresent()) {
            throw new AdminException("No Order Of Given Id Found", AdminException.ExceptionType.NO_ORDER_FOUND);
        }
        OrderBookDetails orderBookDetails = optionalOrderDetails.get();
        orderBookDetails.setOrderStatus(orderStatus);
        orderBookRepository.save(orderBookDetails);
        return "Order Status Updated Successfully";
    }

    @Override
    public String adminLogin(UserLoginDTO userLoginDTO) {
        Optional<UserDetails> userDetailsByEmail = userRepository.findByEmail(userLoginDTO.email);
        if (userDetailsByEmail.get().getUserRole() == UserRole.ADMIN) {
            boolean isPasswordMatched = bCryptPasswordEncoder.matches(userLoginDTO.password, userDetailsByEmail.get().getPassword());
            if (isPasswordMatched) {
                String token = tokenGenerator.generateToken(userDetailsByEmail.get().id, applicationProperties.getJwtLoginExpiration());
                return token;
            }
            throw new AdminException("Invalid Password!!!Please Enter Correct Password", AdminException.ExceptionType.PASSWORD_INVALID);
        }
        throw new AdminException("You Have Not Been Given Admin Previlege", AdminException.ExceptionType.EMAIL_NOT_FOUND);
    }
}
