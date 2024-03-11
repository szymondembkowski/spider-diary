package com.spiderdiary.Controller;

import com.spiderdiary.DAO.SpiderRepository;
import com.spiderdiary.Entity.User;
import com.spiderdiary.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin_view/")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @DeleteMapping("/deleteUser/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUserWithRoles(username);
        return "redirect:/admin_view/users/";
    }
}
