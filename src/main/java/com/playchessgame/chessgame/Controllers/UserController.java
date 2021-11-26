package com.playchessgame.chessgame.Controllers;

import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.GameService.GameGui;
import com.playchessgame.chessgame.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute(value="user") PlayerUser user, Model model){

        try {
            userService.addUser(user);
            return "redirect:/ok";
        } catch (UserAlreadyExistsException e){
            return "useralreadyexist";

        } catch(Exception e){
            return "error";
        }

    }

    @PostMapping("/login")
    public String login(@ModelAttribute(value="user") PlayerUser user, Model model) {

        if (userService.checkUserExistence(user)) {
            model.addAttribute("student", user);
            model.addAttribute("message", "sss");
            return "userinfo";
        }

        model.addAttribute("user", new PlayerUser());
        model.addAttribute("message", "Username or password is not correct, please try again!");
        return "login";

    }


}
