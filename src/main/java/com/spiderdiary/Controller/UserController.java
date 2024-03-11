package com.spiderdiary.Controller;

import com.spiderdiary.DAO.*;
import com.spiderdiary.Entity.Extras.Feeding;
import com.spiderdiary.Entity.Extras.FoodType;
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

import java.util.ArrayList;
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
    @Autowired
    private FoodTypeRepository foodTypeRepository;
    @Autowired
    private FeedingRepository feedingRepository;

    @GetMapping("/addSpiderForm")
    public String showAddSpiderForm(Model model) {
        WebSpider webSpider = new WebSpider();
        List<FoodType> foodTypes = foodTypeRepository.findAll();
        model.addAttribute("spiderForm", webSpider);
        model.addAttribute("foodTypes", foodTypes);

        return "user/addSpiderForm";
    }

    @GetMapping("/editSpiderForm")
    public String showEditSpiderForm(@RequestParam("spiderId") Long id, Model model) {
        Spider spider = spiderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid spider Id:" + id));

        List<FoodType> foodTypes = foodTypeRepository.findAll();

        WebSpider webSpider = new WebSpider();
        webSpider.setId(spider.getId());
        webSpider.setName(spider.getName());
        webSpider.setSpecies(spider.getSpecies());
        webSpider.setMoltDate(spider.getMoltDate());
        webSpider.setGender(spider.getGender());

        Rozmiar rozmiar = spider.getRozmiar();
        if (rozmiar != null) {
            webSpider.setWylinki(rozmiar.getWylinki());
            webSpider.setDlugoscCiala(rozmiar.getDlugoscCiala());
        }

        List<Feeding> feedings = spider.getFeedings();
        if (feedings == null || feedings.isEmpty()) {
            feedings = new ArrayList<>();
            feedings.add(new Feeding());
        }
        webSpider.setFeedings(feedings);

        model.addAttribute("spiderForm", webSpider);
        model.addAttribute("foodTypes", foodTypes);

        return "user/addSpiderForm";
    }

    @PostMapping("/saveEditedSpider")
    public String saveEditedSpider(@ModelAttribute("spiderForm") WebSpider webSpider, Model model) {
        System.out.println("Received WebSpider data: " + webSpider);

        Spider existingSpider = spiderRepository.findById(webSpider.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid spider Id:" + webSpider.getId()));

        System.out.println("Existing Spider before update: " + existingSpider);
        System.out.println("Existing Spider Feedings before update: " + existingSpider.getFeedings());

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

        List<Feeding> feedings = existingSpider.getFeedings();
        if (feedings == null || feedings.isEmpty()) {
            feedings = new ArrayList<>();
            existingSpider.setFeedings(feedings);
        }

        if (webSpider.getFeedings() != null && !webSpider.getFeedings().isEmpty()) {
            Feeding feeding = feedings.isEmpty() ? new Feeding() : feedings.get(0);
            feeding.setFeedingsPerWeek(webSpider.getFeedings().get(0).getFeedingsPerWeek());
            FoodType foodType = foodTypeRepository.findById(webSpider.getFoodTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid foodType Id:" + webSpider.getFoodTypeId()));
            feeding.setFoodType(foodType);
        }

        spiderRepository.save(existingSpider);

        System.out.println("Existing Spider after update: " + existingSpider);
        System.out.println("Existing Spider Feedings after update: " + existingSpider.getFeedings());

        return "redirect:/user_view/";
    }

    @Transactional
    @PostMapping("/addSpiderForm")
    public String addSpider(@ModelAttribute("spiderForm") WebSpider webSpider,
                            Model model,
                            @RequestParam("foodTypeId") Long foodTypeId,
                            @RequestParam("feedings[0].feedingsPerWeek") int feedingsPerWeek) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());

        Spider spider = new Spider();
        spider.setName(webSpider.getName());
        spider.setSpecies(webSpider.getSpecies());
        spider.setMoltDate(webSpider.getMoltDate());
        spider.setUser(user);
        spider.setGender(webSpider.getGender());

        Rozmiar rozmiar = new Rozmiar();
        rozmiar.setWylinki(webSpider.getRozmiar().getWylinki());
        rozmiar.setDlugoscCiala(webSpider.getRozmiar().getDlugoscCiala());
        rozmiar = rozmiarRepository.save(rozmiar);
        spider.setRozmiar(rozmiar);

        FoodType foodType = foodTypeRepository.findById(foodTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid food type Id:" + foodTypeId));

        Feeding feeding = new Feeding();
        feeding.setFeedingsPerWeek(feedingsPerWeek);
        feeding.setFoodType(foodType);
        feeding.setRozmiar(spider.getRozmiar());

        spider.addFeeding(feeding);

        try {
            spiderRepository.save(spider);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

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

    @GetMapping("/feedings")
    public String getFeedings(Model model) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Spider> spiders = spiderRepository.findByUser(user);
        List<Feeding> feedings = feedingRepository.findAll();
        model.addAttribute("spiders", spiders);
        model.addAttribute("feedings", feedings);
        return "user/feeding";
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

    // KARMÓWKA //


//    @Transactional
//    @PostMapping("/addSpiderForm")
//    public String addFeeding(@ModelAttribute("spiderForm") WebSpider webSpider,
//                            Model model,
//                            @RequestParam("foodTypeId") Long foodTypeId,
//                            @RequestParam("feedings[0].feedingsPerWeek") int feedingsPerWeek) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findByUserName(authentication.getName());
//
//        Spider spider = new Spider();
//        spider.setName(webSpider.getName());
//        spider.setSpecies(webSpider.getSpecies());
//        spider.setMoltDate(webSpider.getMoltDate());
//        spider.setUser(user);
//        spider.setGender(webSpider.getGender());
//
//        Rozmiar rozmiar = new Rozmiar();
//        rozmiar.setWylinki(webSpider.getRozmiar().getWylinki());
//        rozmiar.setDlugoscCiala(webSpider.getRozmiar().getDlugoscCiala());
//        rozmiar = rozmiarRepository.save(rozmiar);
//        spider.setRozmiar(rozmiar);
//
//        FoodType foodType = foodTypeRepository.findById(foodTypeId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid food type Id:" + foodTypeId));
//
//        Feeding feeding = new Feeding();
//        feeding.setFeedingsPerWeek(feedingsPerWeek);
//        feeding.setFoodType(foodType);
//        feeding.setRozmiar(spider.getRozmiar());
//
//        spider.addFeeding(feeding);
//
//        try {
//            spiderRepository.save(spider);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "error";
//        }
//
//        return "redirect:/user_view/";
//    }
//
//    @GetMapping("/editSpiderForm")
//    public String showEditFeedingForm(@RequestParam("spiderId") Long id, Model model) {
//        Spider spider = spiderRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid spider Id:" + id));
//
//        List<FoodType> foodTypes = foodTypeRepository.findAll();
//
//        WebSpider webSpider = new WebSpider();
//        webSpider.setId(spider.getId());
//        webSpider.setName(spider.getName());
//        webSpider.setSpecies(spider.getSpecies());
//        webSpider.setMoltDate(spider.getMoltDate());
//        webSpider.setGender(spider.getGender());
//
//        Rozmiar rozmiar = spider.getRozmiar();
//        if (rozmiar != null) {
//            webSpider.setWylinki(rozmiar.getWylinki());
//            webSpider.setDlugoscCiala(rozmiar.getDlugoscCiala());
//        }
//
//        List<Feeding> feedings = spider.getFeedings();
//        if (feedings == null || feedings.isEmpty()) {
//            feedings = new ArrayList<>();
//            feedings.add(new Feeding());
//        }
//        webSpider.setFeedings(feedings);
//
//        model.addAttribute("spiderForm", webSpider);
//        model.addAttribute("foodTypes", foodTypes);
//
//        return "user/addSpiderForm";
//    }
//
//
//    @PostMapping("/saveEditedFeeding")
//    public String saveEditedFeeding(@ModelAttribute("spiderForm") WebSpider webSpider, Model model,
//                                   @RequestParam(value = "feedingsPerWeek", defaultValue = "0") int feedingsPerWeek) {
//        System.out.println("Received WebSpider data: " + webSpider);
//
//        Spider existingSpider = spiderRepository.findById(webSpider.getId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid spider Id:" + webSpider.getId()));
//
//        System.out.println("Existing Spider before update: " + existingSpider);
//        System.out.println("Existing Spider Feedings before update: " + existingSpider.getFeedings());
//
//        existingSpider.setName(webSpider.getName());
//        existingSpider.setSpecies(webSpider.getSpecies());
//        existingSpider.setMoltDate(webSpider.getMoltDate());
//        existingSpider.setGender(webSpider.getGender());
//
//        Rozmiar existingRozmiar = existingSpider.getRozmiar();
//        if (existingRozmiar == null) {
//            existingRozmiar = new Rozmiar();
//            existingSpider.setRozmiar(existingRozmiar);
//        }
//        existingRozmiar.setDlugoscCiala(webSpider.getRozmiar().getDlugoscCiala());
//
//        List<Feeding> feedings = existingSpider.getFeedings();
//        if (feedings == null || feedings.isEmpty()) {
//            feedings = new ArrayList<>();
//            feedings.add(new Feeding());
//            existingSpider.setFeedings(feedings);
//        }
//
//        Feeding feeding = feedings.get(0);
//        System.out.println("Before setting feedingsPerWeek: " + feeding.getFeedingsPerWeek());
//        feeding.setFeedingsPerWeek(webSpider.getFeedingsPerWeek());
//        System.out.println("After setting feedingsPerWeek: " + feeding.getFeedingsPerWeek());
//        feedingRepository.saveAndFlush(feeding);
//
//        spiderRepository.save(existingSpider);
//
//        System.out.println("Existing Spider after update: " + existingSpider);
//        System.out.println("Existing Spider Feedings after update: " + existingSpider.getFeedings());
//
//        return "redirect:/feeding/";
//    }

    @GetMapping("/sortedByFoodName")
    public String getAllSpidersSortedByFoodName(Model model) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Spider> spiders = spiderRepository.findAllSortedByFoodName(user);
        model.addAttribute("spiders", spiders);
        return "user/feeding";
    }


    @GetMapping("/sortedByFeedingsPerWeek")
    public String getAllSpidersSortedByFeedingsPerWeek(Model model) {
        User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Spider> spiders = spiderRepository.findAllSortedByFeedingsPerWeek(user);
        model.addAttribute("spiders", spiders);
        return "user/feeding";
    }
}