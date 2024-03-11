package com.spiderdiary.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showMyLoginPage() {

        return "login";
    }

    @GetMapping("/user_view")
    public String showUserView() {
        return "/user/user_view";
    }


    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";
    }
}
