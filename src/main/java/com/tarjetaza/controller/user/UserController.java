package com.tarjetaza.controller.user;

import com.tarjetaza.domain.User;
import com.tarjetaza.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/signin")
    public String signin() {

        return "signin";
    }

    /*
    @GetMapping("/signup")
    public String signup(ModelMap model,
                         Principal principal) {

        // If user is in session
        if(principal != null) {

            return "redirect:/home";
        }

        model.put("user", new User());

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid User user,
                         BindingResult result) {

        if(result.hasErrors()) {

            return "signup";

        } else if(userService.existsByUsername(user.getUsername())) {

            result.addError(new FieldError(
                    "user",
                    "username",
                    "El usuario ya se encuentra registrado"
            ));

            return "signup";

        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.save(user);

        return "signin";
    }
    */

}
