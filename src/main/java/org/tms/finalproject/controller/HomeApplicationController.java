package org.tms.finalproject.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.service.database.UserService;


@Controller
@RequestMapping(path = "/home")
public class HomeApplicationController {
    private static String role;
    private UserService userService;

    public HomeApplicationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getHomePage(ModelAndView modelAndView) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.getAuthentication().getAuthorities().forEach(auth -> role = auth.getAuthority());

        modelAndView.addObject("role", role);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping(path = "/reg")
    public ModelAndView getRegistrationForm(ModelAndView modelAndView) {
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("reg");
        return modelAndView;
    }

    @PostMapping(path = "reg")
    public ModelAndView completeRegistration(ModelAndView modelAndView,
                                             User user) {
        userService.createUser(user);
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }
}
