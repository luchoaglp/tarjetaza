package com.tarjetaza.controller.user;

import com.tarjetaza.domain.security.Role;
import com.tarjetaza.domain.security.User;
import com.tarjetaza.service.RoleService;
import com.tarjetaza.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
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

    @GetMapping("/users")
    public String users(Model model) {

        model.addAttribute("users", userService.findAllByOrderByIdAsc());

        return "users/index";
    }

    @GetMapping("/users/add")
    public String add(Model model) {

        //User user = new User();

        model.addAttribute("user", new User());
        model.addAttribute("rolesIds", Arrays.asList(new Integer[0]));
        model.addAttribute("roles", roleService.findAll());

        return "users/add";
    }

    @PostMapping("/users/save")
    public String save(@Valid User user,
                       Integer[] roles,
                       Model model,
                       BindingResult result) {

        if(roles == null) {
            roles = new Integer[0];
        }

        if(result.hasErrors()) {

            model.addAttribute("rolesIds", Arrays.asList(roles));
            model.addAttribute("roles", roleService.findAll());

            return "users/add";

        } else if(userService.existsByUsername(user.getUsername())) {

            model.addAttribute("rolesIds", Arrays.asList(roles));
            model.addAttribute("roles", roleService.findAll());

            result.addError(new FieldError(
                    "user",
                    "username",
                    "El usuario ya se encuentra registrado"
            ));

            return "users/add";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.save(user);

        return "redirect:/users/detail/" + user.getId();
    }

    @PostMapping("/users/update")
    public String update(@Valid User user,
                         BindingResult result) {

        if(result.hasErrors()) {

            return "users/update";

        } else if(userService.existsByUsername(user.getUsername())) {

            result.addError(new FieldError(
                    "user",
                    "username",
                    "El usuario ya se encuentra registrado"
            ));

            return "users/update";
        }

        userService.save(user);

        return "redirect:/users/detail/" + user.getId();
    }

    @GetMapping("/users/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {

        model.addAttribute("user", userService.findById(id));

        return "users/detail";
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
*/

}
