package com.playchessgame.chessgame.Controllers;

import com.playchessgame.chessgame.Entities.MasterUser;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.UserService.MasterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * A Masteruser Controller is reponsible for maintaining the database for users
 */
@Controller
public class MasterUserController {

    private String masterUserEmail = "maryzhou0218@gmail.com";

    @Autowired
    private MasterUserService masterUserService;

    @PostMapping("/resetpassword")
    public String resetPassword(@ModelAttribute(value="user") PlayerUser user, @ModelAttribute(value="email") String email, Model model){

        Map<String, String> message = new HashMap<>();

        message.put("name", user.getName());
        message.put("password", user.getPassword());
        message.put("email", email);

        masterUserService.resetPassword(message);

        model.addAttribute("message", this.masterUserService.resetPassword(message));
        return "resetpassword";

    }

}




