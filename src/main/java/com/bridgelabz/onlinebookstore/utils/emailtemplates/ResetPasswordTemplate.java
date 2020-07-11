package com.bridgelabz.onlinebookstore.utils.emailtemplates;

import org.springframework.stereotype.Component;

@Component
public class ResetPasswordTemplate {
    public String getResetPasswordString(String token, String fullName) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Registration Form</title>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "<style>\n" +
                ".container{\n" +
                "\twidth:360px;\n" +
                "\theight:300px;\n" +
                "\tmargin:0;\n" +
                "\tposition:absolute;\n" +
                "\ttop:50%;\n" +
                "\tleft:50%;\n" +
                "\tmargin-right:-50%;\n" +
                "\ttransform:translate(-50%, -50%);\n" +
                "\tborder: 1px solid black;\n" +
                "\tborder-radius:4px;\n" +
                "}\n" +
                ".header{\n" +
                "\twidth:100%;\n" +
                "\ttext-align:center;\n" +
                "\tfont-family:Courier New;\n" +
                "\tbackground-color:rgb(145,10,10);\n" +
                "\tcolor: white;\n" +
                "\tfont-size:20px;\n" +
                "\theight:10%;\n" +
                "\tpadding-top:6px;\n" +
                "\tfont-weight:bold;\n" +
                "}\n" +
                ".heading{\n" +
                "\ttext-align:center;\n" +
                "\tfont-family:Candara;\n" +
                "\tfont-weight:bold;\n" +
                "\tfont-size:25px;\n" +
                "\tmargin-top:4%;\n" +
                "\tmargin-bottom:4%;\n" +
                "}\n" +
                ".text{\n" +
                "\ttext-align:center;\n" +
                "\tfont-family:Comic Sans MS;\n" +
                "}\n" +
                ".verify-button{\n" +
                "\twidth:100%;\n" +
                "\tmargin-top:7%;\n" +
                "\tmargin-bottom:7%;\n" +
                "\ttext-align:center;\n" +
                "\theight:10%;\n" +
                "}\n" +
                ".button{\n" +
                "\theight:100%;\n" +
                "\twidth:50%;\n" +
                "\tbackground:#3371B5;\n" +
                "\tborder:none;\n" +
                "\tborder-radius:3px;\n" +
                "\tcolor:white;\n" +
                "}\n" +
                ".feedback{\n" +
                "\ttext-align:center;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\t\n" +
                "     <table style='width:100%;height:97vh'>\n" +
                "           <tr>\n" +
                "               <th style='width:33.3%'></th>\n" +
                "               <th>\n" +
                "\t\t               <div class='container'>\n" +
                "\t\t\t                 <div class='header'>The Country Bookshop</div>\n" +
                "\t\t\t                 <div class='heading'>Reset Your Password</div>\n" +
                "\t\t\t                 <div class='text'>Hi " + fullName + " !</div>\n" +
                "\t\t\t                 <div class='text'>Use the link below to reset your password</div>\n" +
                "\t\t\t                 <div class='verify-button'><a href='" + token + "'><button class='button'>Reset Password</button></a></div>\n" +
                "\t\t\t                 <div class='feedback'>Question ? Email us at <a href='#'>bookstore@gmail.com</a></div>\n" +
                "\t\t               </div>\t\t\n" +
                "               </th>" +
                "           </tr>" +
                "       </table>" +
                "\t\n" +
                "</body>\n" +
                "</html>";
    }
}
