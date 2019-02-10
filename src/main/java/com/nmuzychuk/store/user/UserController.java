package com.nmuzychuk.store.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserRepository userRepository;

    @Autowired
    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("register")
    public String register(ModelMap modelMap) {
        modelMap.put("user", new User());
        return "register";
    }

    @PostMapping("register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap modelMap,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            modelMap.put("user", user);
            return "register";
        } else {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setRoles("ROLE_ADMIN"); // TODO: Set for the first user only or seed admin user
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("message", "You have registered successfully");
            return "redirect:/login";
        }
    }
}
