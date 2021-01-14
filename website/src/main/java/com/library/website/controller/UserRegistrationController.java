package com.library.website.controller;

import com.library.website.beans.UserBean;
import com.library.website.dto.UserRegistrationDto;
import com.library.website.proxies.MicroServiceLibraryProxy;
import com.library.website.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Controller
@RequestMapping("/creation-compte")
public class UserRegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationController.class);

    @Autowired
    MicroServiceLibraryProxy msLibraryProxy;

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "creation-compte";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result){
        UserBean isEmailExist = msLibraryProxy.getUserByEmail(userDto.getEmail());

        if (isEmailExist != null){
            LOGGER.warn("Cet email existe déjà en BDD");
            result.rejectValue("email", null, "Le compte "+userDto.getEmail()+" existe déjà. Identifiez-vous ou réinitialisez votre mot de passe");
        }

        if (result.hasErrors()){
            LOGGER.debug("Account-creation form has {} error(s) - First {}", result.getErrorCount(), result.getFieldError());
            return "creation-compte";
        }

        UserBean user = new UserBean();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(webSecurityConfig.passwordEncoder().encode(userDto.getPassword()));
        user.setRole(userDto.getRole() == null ? "user" : userDto.getRole());
        user.setAddress(null);
        user.setCreationDate(new Date());

        msLibraryProxy.saveUser(user);
        LOGGER.info("AccountCreation for {}", userDto.getEmail());
        return "redirect:/creation-compte?success";
    }
}
