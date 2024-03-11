package com.spiderdiary.Controller;

import com.spiderdiary.Entity.User;
import com.spiderdiary.Services.UserService;
import com.spiderdiary.TempForms.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class RegistrationController {

    private Logger logger = Logger.getLogger(getClass().getName());

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/")
    public String showLandingPage(Model theModel) {

        theModel.addAttribute("webUser", new WebUser());

        return "landing";
    }

    @PostMapping("/")
    public String processRegistrationForm(
            @Valid @ModelAttribute("webUser") WebUser theWebUser,
            BindingResult theBindingResult,
            HttpSession session, Model theModel) {

        String userName = theWebUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        if (theBindingResult.hasErrors()) {
            return "regError";
        }

        User existing = userService.findByUserName(userName);
        if (existing != null) {
            theModel.addAttribute("registrationError", "User name already exists.");
            return "regError";
        }

        userService.save(theWebUser);
        logger.info("Successfully created user: " + userName);

        session.setAttribute("user", theWebUser);
        return "/registration-confirmation";
    }
}