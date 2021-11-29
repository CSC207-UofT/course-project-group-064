package com.playchessgame.chessgame.Controllers;

import com.playchessgame.chessgame.Entities.MasterUser;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.GameService.GameGui;
import com.playchessgame.chessgame.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("user", new PlayerUser());
        return "addUser";
    }

//    @GetMapping("/register?type=1")
//    public String getRegisterMaster(Model model){
//        //model.addAttribute("isuserexist", "no");
//        model.addAttribute("user", new MasterUser());
//        return "addUser";
//    }

    @PostMapping("/register")
    public String register(@ModelAttribute(value="user") PlayerUser user, Model model){

        try {
            userService.addUser(user);
            return "redirect:/ok";
        } catch (UserAlreadyExistsException e){
            model.addAttribute("message", "The username has been registered, please try another one");
            return "addUser";

        } catch(Exception e){
            model.addAttribute("message", "Something running wrong with the database, please try again");
            return "addUser";
        }

    }

    @GetMapping("/login")
    public String getLogin(Model model){
        model.addAttribute("user", new PlayerUser());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute(value="user") PlayerUser user, Model model) {

        if (userService.checkUserExistence(user)) {
            model.addAttribute("student", user);
            model.addAttribute("message", "success");
            return "userinfo";
        }

        model.addAttribute("user", new PlayerUser());
        model.addAttribute("message", "Username or password is not correct, please try again!");
        return "login";

    }

}
