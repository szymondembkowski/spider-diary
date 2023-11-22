package com.spiderdiary.Controller;

import com.spiderdiary.DAO.SpiderRepository;
import com.spiderdiary.DAO.UserRepository;
import com.spiderdiary.Entity.Spider;
import com.spiderdiary.Entity.User;
import com.spiderdiary.Services.SpiderService;
import com.spiderdiary.Services.UserService;
import com.spiderdiary.TempForms.WebSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user_view/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SpiderService spiderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpiderRepository spiderRepository;

    @GetMapping("/addSpiderForm")
    public String showAddSpiderForm(Model model) {
        model.addAttribute("spiderForm", new WebSpider());
        return "user/addSpiderForm";
    }

    @PostMapping("/addSpiderForm")
    public String addSpider(WebSpider webSpider, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());

        Spider spider = new Spider();
        spider.setName(webSpider.getName());
        spider.setSpecies(webSpider.getSpecies());
        spider.setMoltDate(webSpider.getLatestMolt());
        spider.setUser(user);

        spiderService.save(spider);

        return "redirect:/user_view/";
    }

    @GetMapping("/")
    public String getSpiders(Model model) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Spider> spiders = spiderRepository.findByUser(user);
        model.addAttribute("spiders", spiders);
        return "user/user_view";
    }

    @PostMapping("/deleteSpider")
    public String deleteSpider(@RequestParam("spiderId") Long id) {
        spiderRepository.deleteById(id);
        return "redirect:/user_view/";
    }

}

