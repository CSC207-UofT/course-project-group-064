package com.playchessgame.chessgame.Controllers;

import com.playchessgame.chessgame.ContextService.MyListener;
import com.playchessgame.chessgame.Entities.MasterUser;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

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
            model.addAttribute("message", e.getMessage() + "\nPlease try another username~~~");
            return "addUser";

        } catch(Exception e){
            model.addAttribute("message", "Something running wrong with the database, please try again");
            return "addUser";
        }

    }

    @GetMapping("/login1")
    public String getLoginPlayer(Model model){
        model.addAttribute("user", new PlayerUser());
        return "loginPlayer.html";
    }

    @GetMapping("/login2")
    public String getLoginMaster(Model model){
        model.addAttribute("user", new MasterUser());
        return "loginMaster";
    }

    @PostMapping("/login1")
    public String loginPlayer(@ModelAttribute(value="user") PlayerUser user, Model model, HttpServletRequest request) {

        if (userService.checkUserExistence(user)) {
            model.addAttribute("student", user);
            model.addAttribute("message", "success");

            HttpSession httpSession = request.getSession(true);
//
//            List<PlayerUser> loginUsers = (List<PlayerUser>) httpSession.getServletContext().getAttribute("loginUsers");
//
////            if (loginUsers == null) {
////                httpSession.setAttribute("loginUsers", new ArrayList<PlayerUser>().add(user));
////            }else {
////                httpSession.setAttribute("loginUsers", loginUsers.add(user));
////            }
//
//            if (loginUsers == null) {
//                loginUsers = new ArrayList<PlayerUser>();
//            }
//
//            loginUsers.add(user);
//
            httpSession.setAttribute("username", user);

            MyListener.onlineUsers.add(user);

            return "userinfo";
        }

        model.addAttribute("user", new PlayerUser());
        model.addAttribute("message", "Username or password is not correct, please try again!");
        return "loginPlayer";

    }

    @PostMapping("/login2")
    public String loginMaster(@ModelAttribute(value="user") MasterUser user, Model model) {

        if (user.getName().equals("masterusername") && user.getPassword().equals("masteruserpassword")){
            return "masterUserPage";
        }

        model.addAttribute("user", new PlayerUser());
        model.addAttribute("message", "Username or password is not correct, please try again!");
        return "loginMaster";

    }

    @GetMapping("/resetpassword")
    public String getResetPassword(Model model){
        model.addAttribute("user", new PlayerUser());
        model.addAttribute("email", "");
        return "resetpassword";

    }

//    @PostMapping("/resetpassword")
//    public String resetPassword(@ModelAttribute(value="user") PlayerUser user, Model model){
//        model.addAttribute("message", this.userService.resetPassword(user));
//        return "resetpassword";
//
//    }

    /**
     * PlayerUser logout
     */
    @PostMapping("/logout")
    public String Logout(HttpServletRequest request, Model model) {
        HttpSession httpSession = request.getSession(true);
//
        PlayerUser user = (PlayerUser) httpSession.getAttribute("username");

        model.addAttribute("userToLogout", user);

        httpSession.removeAttribute("username");
        httpSession.invalidate();

//        MyListener.onlineUsers.remove(user);
//
        return "logout";

    }


}
