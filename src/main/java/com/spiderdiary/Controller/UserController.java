package com.spiderdiary.Controller;

import com.spiderdiary.Entity.Spider;
import com.spiderdiary.Entity.User;
import com.spiderdiary.Services.SpiderService;
import com.spiderdiary.Services.UserService;
import com.spiderdiary.TempForms.WebSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user_view")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SpiderService spiderService;

    @GetMapping("/addSpiderForm")
    public String showAddSpiderForm(Model model) {
        model.addAttribute("spiderForm", new WebSpider());
        return "user/addSpiderForm";
    }

    @PostMapping("/addSpiderForm")
    public String addSpider(@ModelAttribute("spiderForm") WebSpider spiderForm, Principal principal) {
        // Pobierz zalogowanego użytkownika
        String username = principal.getName();

        // Utwórz obiekt Spider i dodaj go do użytkownika
        Spider spider = new Spider();
        spider.setSpiderName(spiderForm.getSpiderName());
        // Ustaw użytkownika dla pająka
        User user = userService.getUserByUsername(username);
        spider.setUser(user);

        // Zapisz pająka
        userService.addSpiderToUser(spider, user);

        // Przekieruj użytkownika na widok displaySpiders
        return "redirect:/user_view/displaySpiders";
    }

    @GetMapping("/")
    public String displaySpiders(Model model, Principal principal) {
        String username = principal.getName();
        List<Spider> spiders = spiderService.getSpidersByUser(username);

        model.addAttribute("spiders", spiders);

        return "user/user_view";
    }
}
