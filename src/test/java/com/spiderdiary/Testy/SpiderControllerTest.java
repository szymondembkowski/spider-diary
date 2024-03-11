package com.spiderdiary.Testy;

import com.spiderdiary.Controller.UserController;
import com.spiderdiary.DAO.FoodTypeRepository;
import com.spiderdiary.DAO.RozmiarRepository;
import com.spiderdiary.DAO.SpiderRepository;
import com.spiderdiary.Entity.Extras.Rozmiar;
import com.spiderdiary.Entity.Extras.FoodType;
import com.spiderdiary.Entity.Spider;
import com.spiderdiary.Entity.User;
import com.spiderdiary.Services.UserService;
import com.spiderdiary.TempForms.Gender;
import com.spiderdiary.TempForms.WebSpider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SpiderControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RozmiarRepository rozmiarRepository;

    @Mock
    private FoodTypeRepository foodTypeRepository;

    @Mock
    private SpiderRepository spiderRepository;

    @InjectMocks
    private UserController spiderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddSpider() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        WebSpider webSpider = new WebSpider();
        webSpider.setName("SpiderName");
        webSpider.setSpecies("Species");
        webSpider.setMoltDate(new Date());
        webSpider.setGender(Gender.MALE);

        Rozmiar rozmiar = new Rozmiar();
        rozmiar.setWylinki("Wylinki");
        rozmiar.setDlugoscCiala("DlugoscCiala");

        when(userService.findByUserName(any())).thenReturn(new User());
        when(rozmiarRepository.save(any())).thenReturn(rozmiar);
        when(foodTypeRepository.findById(any())).thenReturn(Optional.of(new FoodType()));

        String result = spiderController.addSpider(webSpider, mock(Model.class), 1L, 2);

        verify(spiderRepository, times(1)).save(any(Spider.class));
        verify(rozmiarRepository, times(1)).save(any(Rozmiar.class));
        verify(foodTypeRepository, times(1)).findById(eq(1L));
    }
}
