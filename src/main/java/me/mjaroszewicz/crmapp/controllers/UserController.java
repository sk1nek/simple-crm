package me.mjaroszewicz.crmapp.controllers;

import me.mjaroszewicz.crmapp.dto.UserRegistrationDto;
import me.mjaroszewicz.crmapp.entities.User;
import me.mjaroszewicz.crmapp.exceptions.RegistrationException;
import me.mjaroszewicz.crmapp.services.SecurityService;
import me.mjaroszewicz.crmapp.services.UserService;
import me.mjaroszewicz.crmapp.validators.EmailValidator;
import me.mjaroszewicz.crmapp.validators.PhoneNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/register")
    public ModelAndView getRegistrationForm(ModelAndView mv){

        mv.setViewName("register");

        mv.addObject("user", new UserRegistrationDto());

        return mv;
    }

    @PostMapping("/register")
    public ModelAndView handleRegister(ModelAndView mv, @Valid UserRegistrationDto user, Errors err){

        mv.setViewName("register");
        List<String> errors = new ArrayList<>();

        if(err.hasErrors()){
            err.getAllErrors().forEach(e -> errors.add(e.getDefaultMessage()));
            mv.addObject("errors", errors);
            errors.forEach(e-> System.out.println(e));
            return getRegistrationForm(mv);
        }

        try{
            userService.registerNewUser(user);
        }catch (RegistrationException rex){
            mv.setViewName("/register");
            errors.add(rex.getMessage());
            mv.addObject("errors", errors);
            return mv;
        }

        mv.setViewName("redirect:/login");
        return getRegistrationForm(mv);
    }

    @GetMapping("/account")
    public ModelAndView getUserAccountDetails(ModelAndView mv){

        mv.setViewName("accountdetails");

        User user = securityService.getCurrentUser();
        mv.addObject("user", user);

        return mv;
    }

    @PostMapping("/user/changemail")
    public ModelAndView handleMailChange(ModelAndView mv, @RequestParam String email){

        EmailValidator ev = new EmailValidator();

        if (!ev.isValid(email, null)) {
            mv.addObject("errors", Collections.singletonList("Invalid email."));
            return getUserAccountDetails(mv);
        }

        userService.changeEmail(email);

        mv.addObject("messages", Collections.singletonList("Success!"));

        return getUserAccountDetails(mv);
    }

    @PostMapping("/user/changephone")
    public ModelAndView handlePhoneChange(ModelAndView mv, @RequestParam String phone){

        PhoneNumberValidator pv = new PhoneNumberValidator();

        if(!pv.isValid(phone, null)){
            mv.addObject("errors", Collections.singletonList("Invalid phone number. "));
            return getUserAccountDetails(mv);
        }

        userService.changePhoneNumber(phone);

        mv.addObject("messages", Collections.singletonList("Success!"));

        return getUserAccountDetails(mv);
    }

}
