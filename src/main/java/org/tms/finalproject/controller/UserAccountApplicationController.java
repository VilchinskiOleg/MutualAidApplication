package org.tms.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.tms.finalproject.entity.User;
import org.tms.finalproject.service.database.UserService;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/account")
public class UserAccountApplicationController {

    private UserService userService;

    public UserAccountApplicationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/get-profile")
    public ModelAndView getUserProfile(ModelAndView modelAndView) {
        User actualUser = userService.getActualUser();
        modelAndView.addObject("profile", actualUser);
        modelAndView.setViewName("account");
        return modelAndView;
    }

    @PostMapping(path = "/update-field")
    public ModelAndView updateAccountField(@RequestParam("value") String value,
                                           @RequestParam("field") String field,
                                           HttpSession session,
                                           ModelAndView modelAndView) {
        userService.updateByFieldNameAndValue(field, value);
        session.invalidate();
        modelAndView.setViewName("redirect:/account/get-profile");
        return modelAndView;
    }

    @GetMapping(path = "/delete-profile")
    public ModelAndView deleteProfile(@RequestParam("user-id") long userId,
                                      HttpSession session,
                                      ModelAndView modelAndView) {
        userService.deleteUserById(userId);
        session.invalidate();
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }
}
