package com.bridgelabz.onlinebookstore.utils.implementation;

import com.bridgelabz.onlinebookstore.models.BookCart;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OrderSuccessfulEmailTemplateGenerator {

    public String getEmailTemplate(String fullName, List<BookCart> bookCartList, String address, Double totalPrice, Integer orderId) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Registration Form</title>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "<style>\n" +
                ".order-details {\n" +
                "  font-family: arial, sans-serif;\n" +
                "  border-collapse: collapse;\n" +
                "  width: 100%;\n" +
                "}\n" +
                "\n" +
                ".details {\n" +
                "  border: 1px solid #dddddd;\n" +
                "  text-align: left;\n" +
                "  padding: 8px;\n" +
                "  font-family: 'calibri', Garamond, 'Comic Sans MS';\n" +
                "}\n" +
                "\n" +
                ".book-details:nth-child(even) {\n" +
                "  background-color: #dddddd;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div style='width:100%;text-align:center'>\t\n" +
                "\t\t\t<table style='width:100%;height:37px'>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<th style='width:15%;font-family:courier,arial,helvetica;'>The Country BookShop</th>\n" +
                "\t\t\t\t\t<th><hr/></th>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t\t<table style='width:100%;height:37px;font-family: calibri,Garamond,Comic Sans MS;font-weight:bold'>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>Hello " + fullName + "</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>Congratulations, you have selected a perfect book !!</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>Thank You For The Order !</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t\t<table style='width:100%;height:35px;text-align:right'>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td style='font-weight:bold;font-family:Candara;'>Order Confirmation</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td style='font-weight:bold;font-family:Candara;'>Order ID: <strong style='color:#00e6e6'>" + orderId + "</strong></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td style='font-weight:bold;font-family:Candara;text-align:left;font-size:20px'>Shipping To<hr/></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t\t<table style='width:250px;height:35px;text-align:left'>\n" +
                "\t\t\t\t<tr style='font-family:Comic Sans MS'>\n" +
                "\t\t\t\t\t<td>" + address + "</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t\t<table style='width:100%;min-height:35px'>\n" +
                "\t\t\t\t<tr style='text-align:left;height:35px;font-family:Candara;font-size:20px;font-weight:bold;'>\n" +
                "\t\t\t\t\t<td>Order Summary</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t\t<table class='order-details'>\n" +
                "  \t\t\t\t<tr class='book-details'>\n" +
                "    \t\t\t\t\t<th class='details'>Book Name</th>\n" +
                "    \t\t\t\t\t<th class='details'>Price</th>\n" +
                "    \t\t\t\t\t<th class='details'>Quantity</th>\n" +
                "    \t\t\t\t\t<th class='details'>Total</th>\n" +
                "  \t\t\t\t</tr>\n" +
                generateTable(bookCartList) +
                "  \t\t\t\t<tr class='book-details'>\n" +
                "    \t\t\t\t\t<td class='details' colspan='3'>Order Total</td>\n" +
                "\t\t\t\t\t<td class='details'>Rs. " + totalPrice + "</td>\n" +
                "  \t\t\t\t</tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t\t\n" +
                "\t<div>\n" +
                "</body>\n" +
                "</html>";
    }

    private String generateTable(List<BookCart> bookCartList) {
        StringBuilder table = new StringBuilder();
        for (BookCart cartItems : bookCartList) {
            table.append("<tr class='book-details'><td class='details'>").append(cartItems.getBook().bookName).append("</td>\n").append("<td class='details'>Rs. ").append(cartItems.getBook().bookPrice).append("</td>\n").append("<td class='details'>").append(cartItems.getQuantity()).append("</td>\n").append("<td class='details'>Rs. ").append(cartItems.getQuantity() * cartItems.getBook().bookPrice).append("</td></tr>\n");
        }
        return table.toString();
    }
}
