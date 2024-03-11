package com.spiderdiary.Testy;

import com.spiderdiary.Controller.RegistrationController;
import com.spiderdiary.Entity.User;
import com.spiderdiary.Services.UserService;
import com.spiderdiary.TempForms.WebUser;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;

public class RegistrationControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcessRegistrationForm_Success() {

        WebUser webUser = new WebUser();
        webUser.setUserName("testUser");
        webUser.setPassword("testPassword");
        webUser.setEmail("test@example.com");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        when(userService.findByUserName(anyString())).thenReturn(null);

        String viewName = registrationController.processRegistrationForm(webUser, bindingResult, session, model);

        verify(userService, times(1)).save(webUser);
        verify(session, times(1)).setAttribute(eq("user"), eq(webUser));
        assert viewName != null;
        assert viewName.equals("/registration-confirmation");
    }

    @Test
    public void testProcessRegistrationForm_WithErrors() {

        WebUser webUser = new WebUser();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = registrationController.processRegistrationForm(webUser, bindingResult, session, model);

        assert viewName != null;
        assert viewName.equals("regError");
    }

    @Test
    public void testProcessRegistrationForm_UserAlreadyExists() {

        WebUser webUser = new WebUser();
        webUser.setUserName("existingUser");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.findByUserName("existingUser")).thenReturn(new User());

        String viewName = registrationController.processRegistrationForm(webUser, bindingResult, session, model);

        assert viewName != null;
        assert viewName.equals("regError");
        verify(model, times(1)).addAttribute(eq("registrationError"), anyString());
    }
}
