package com.playchessgame.chessgame.Controllers;

import com.playchessgame.chessgame.ContextService.MyListener;
import com.playchessgame.chessgame.Entities.MasterUser;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.UserService.MasterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * A Masteruser Controller is reponsible for handling requests from playerusers and maintaining the database for onlineUsers
 */
@Controller
public class MasterUserController {

    @Autowired
    private MasterUserService masterUserService;

    /**
     * returns the loginMaster webpage when the get request "/login2" is sent
     *
     * @param model: Model from web
     * @return loginMaster webpage
     */
    @GetMapping("/login2")
    public String getLoginMaster(Model model) {
        model.addAttribute("user", new MasterUser());
        return "loginMaster";
    }

    /**
     * returns the loginMaster webpage when the post request "/login2" is sent
     *
     * @param user:    MasterUser who is going to login
     * @param model:   Model from web
     * @param request: HttpServletRequest from web
     * @return loginMaster webpage
     */
    @PostMapping("/login2")
    public String loginMaster(@ModelAttribute(value = "user") MasterUser user, Model model, HttpServletRequest request) {

        if (user.getName().equals("masterusername") && user.getPassword().equals("masteruserpassword")) {
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("masterUser", user);
            model.addAttribute("online", MyListener.online);
            return "masterUserPage";
        }

        model.addAttribute("user", new PlayerUser());
        model.addAttribute("message", "Username or password is not correct, please try again!");
        return "loginMaster";

    }

    /**
     * returns the resetpasswordmaster webpage when a get request "/resetPW" is sent
     *
     * @param model from web
     * @return the resetpasswordmaster webpage
     */
    @GetMapping("/resetPW")
    public String toResetPassword(Model model) {

        model.addAttribute("user", new PlayerUser());

        Map<PlayerUser, String> usersToResetPW = MyListener.usersToResetPW;
        Set<PlayerUser> users = usersToResetPW.keySet();

        List<List<String>> res = new ArrayList<>();
        for (PlayerUser user : users) {
            List<String> component = new ArrayList<>();
            component.add(user.getName());
            component.add(user.getPassword());
            component.add(MyListener.usersToResetPW.get(user));
            res.add(component);
        }

        model.addAttribute("usersToResetPW", res);

        return "resetpasswordmaster";

    }

    /**
     * return the resetpasswordmaster webpage when a post request "/resetPW" is sent
     *
     * @param user: a PlayerUser
     * @param model from web
     * @return the resetpasswordmaster webpage
     */
    @PostMapping("/resetpasswordmaster")
    public String resetPassword(@ModelAttribute(value = "user") PlayerUser user, Model model) {
        String result = this.masterUserService.resetPassword(user);
        model.addAttribute("message", result);
        return "resetpasswordmaster";
    }

    /**
     * return the deleteusermaster webpage when the get request /deleteUser is sent
     *
     * @param model from web
     * @return the deleteusermaster webpage
     */
    @GetMapping("/deleteUser")
    public String toDeleteUser(Model model) {
        model.addAttribute("user", new PlayerUser());
        return "deleteusermaster";
    }

    /**
     * deleted the provided user(PlayerUser) when the post request /deleteUser is sent
     *
     * @param user:  PlayerUser
     * @param model: Model form web
     * @return the deleteusermaster webpage
     */
    @PostMapping("/deleteusermaster")
    public String deleteUser(@ModelAttribute(value = "user") PlayerUser user, Model model) {
        String result = this.masterUserService.deleteUser(user);
        model.addAttribute("message", result);
        return "deleteusermaster";
    }

}




