package com.spiderdiary.Controller;

import com.spiderdiary.DAO.RozmiarRepository;
import com.spiderdiary.DAO.SpiderRepository;
import com.spiderdiary.DAO.UserRepository;
import com.spiderdiary.Entity.Extras.Rozmiar;
import com.spiderdiary.Entity.Spider;
import com.spiderdiary.Entity.User;
import com.spiderdiary.Services.SpiderService;
import com.spiderdiary.Services.UserService;
import com.spiderdiary.TempForms.Gender;
import com.spiderdiary.TempForms.WebSpider;
import jakarta.transaction.Transactional;
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
    @Autowired
    private RozmiarRepository rozmiarRepository;


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

        Rozmiar existingRozmiar = existingSpider.getRozmiar();
        if (existingRozmiar == null) {
            existingRozmiar = new Rozmiar();
            existingSpider.setRozmiar(existingRozmiar);
        }
        existingRozmiar.setDlugoscCiala(webSpider.getRozmiar().getDlugoscCiala());

        // Save Spider and its associated Rozmiar
        spiderRepository.save(existingSpider);

        return "redirect:/user_view/";
    }



    @Transactional
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
        spider.setTags(webSpider.getTags());

        Rozmiar rozmiar = new Rozmiar();
        rozmiar.setWylinki(webSpider.getRozmiar().getWylinki());
        rozmiar.setDlugoscCiala(webSpider.getRozmiar().getDlugoscCiala());
        rozmiar = rozmiarRepository.save(rozmiar); // Save the Rozmiar entity to the database
        spider.setRozmiar(rozmiar);

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
    public String search(@RequestParam(required = false) String query, @RequestParam(required = false) Gender gender, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName());

        List<Spider> spiders;

        if (gender != null) {
            spiders = spiderService.searchSpidersByGenderAndUser(gender, user);
        } else {
            spiders = spiderService.searchSpidersForUser(query, user);
        }

        model.addAttribute("spiders", spiders);

        return "user/user_view";
    }


}

