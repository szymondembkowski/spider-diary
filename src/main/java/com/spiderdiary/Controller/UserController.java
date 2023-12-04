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
import org.springframework.web.bind.annotation.*;

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
        WebSpider webSpider = new WebSpider();
        model.addAttribute("spiderForm", webSpider);
        return "user/addSpiderForm";
    }


    @GetMapping("/editSpiderForm")
    public String showEditSpiderForm(@RequestParam("spiderId") Long id, Model model) {
        Spider spider = spiderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid spider Id:" + id));
        model.addAttribute("spiderForm", spider);
        return "user/addSpiderForm";
    }

    @PostMapping("/saveEditedSpider")
    public String saveEditedSpider(@ModelAttribute("spiderForm") WebSpider webSpider) {
        Spider existingSpider = spiderRepository.findById(webSpider.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid spider Id:" + webSpider.getId()));

        existingSpider.setName(webSpider.getName());
        existingSpider.setSpecies(webSpider.getSpecies());
        existingSpider.setMoltDate(webSpider.getMoltDate());
        existingSpider.setGender(webSpider.getGender());

        spiderRepository.save(existingSpider);

        return "redirect:/user_view/";
    }


    @PostMapping("/addSpiderForm")
    public String addSpider(@ModelAttribute("spiderForm") WebSpider webSpider, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());

        Spider spider = new Spider();
        spider.setName(webSpider.getName());
        spider.setSpecies(webSpider.getSpecies());
        spider.setMoltDate(webSpider.getMoltDate());
        spider.setUser(user);
        spider.setGender(webSpider.getGender());
        spider.setTags(webSpider.getTags()); // Use the tags property

        spiderService.save(spider);

        return "redirect:/user_view/";
    }


    @GetMapping("/deleteSpider")
    public String deleteSpider(@RequestParam("spiderId") Long id) {
        spiderRepository.deleteById(id);
        return "redirect:/user_view/";
    }

    @GetMapping("/")
    public String getSpiders(Model model) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Spider> spiders = spiderRepository.findByUser(user);
        model.addAttribute("spiders", spiders);
        return "user/user_view";
    }

    @GetMapping("/sortedByMoltDate")
    public String getAllSpidersSortedByMoltDate(Model model) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Spider> spiders = spiderRepository.findAllSortedByMoltDate(user);
        model.addAttribute("spiders", spiders);
        return "user/user_view";
    }

    @GetMapping("/sortedByName")
    public String getAllSpidersSortedByName(Model model) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Spider> spiders = spiderRepository.findAllSortedByName(user);
        model.addAttribute("spiders", spiders);
        return "user/user_view";
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Spider> spiders = spiderRepository.searchSpidersForUser(query, user);
        model.addAttribute("spiders", spiders);
        return "user/user_view";
    }

}

