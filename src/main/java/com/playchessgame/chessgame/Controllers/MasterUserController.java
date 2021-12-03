package com.playchessgame.chessgame.Controllers;

import com.playchessgame.chessgame.ContextService.MyListener;
import com.playchessgame.chessgame.Entities.MasterUser;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.UserService.MasterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Masteruser Controller is reponsible for maintaining the database for onlineUsers
 */
@Controller
public class MasterUserController {

    private String masterUserEmail = "maryzhou0218@gmail.com";

    @Autowired
    private MasterUserService masterUserService;


    /**
     * Check the total number of online players
     * @return
     */
    @RequestMapping("/online")
    public String checkOnlineUsersNumber(Model model) {

        model.addAttribute("number", MyListener.online);

        return "onlineusernumber";
    }

    @GetMapping("/resetPW")
    public String toResetPassword(@ModelAttribute(value="user") PlayerUser user, @ModelAttribute(value="email") String email, Model model, HttpServletRequest request){

//        Map<String, String> message = new HashMap<>();
//
//        message.put("name", user.getName());
//        message.put("password", user.getPassword());
//        message.put("email", email);

//        StringBuilder message = new StringBuilder();
//        message.append(user.getName()).append(user.getPassword()).append(email);
//
//        masterUserService.resetPassword(message);
//
//        model.addAttribute("message", this.masterUserService.resetPassword(message));
//        return "resetpassword";

//        model.addAttribute("message", message.toString());

        //return "masterUserPage";

//        HttpSession httpSession = request.getSession(true);
//
//        httpSession.setAttribute("userToResetPW", user);
        model.addAttribute("user", new PlayerUser());
        //model.addAttribute("message", "");

        return "resetpasswordmaster";

    }

    @PostMapping("/resetpasswordmaster")
    public String resetPassword(@ModelAttribute(value="user") PlayerUser user, Model model){
        String result = this.masterUserService.resetPassword(user);
        model.addAttribute("message", result);
        return "resetpasswordmaster";
    }

    @GetMapping("/deleteUser")
    public String toDeleteUser(Model model){
        model.addAttribute("user", new PlayerUser());
        return "deleteusermaster";
    }

    @PostMapping("/deleteusermaster")
    public String deleteUser(@ModelAttribute(value="user") PlayerUser user, Model model){
        String result = this.masterUserService.deleteUser(user);
        model.addAttribute("message", result);
        return "deleteusermaster";
    }



}




