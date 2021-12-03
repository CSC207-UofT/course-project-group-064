package com.playchessgame.chessgame.Controllers;

import com.playchessgame.chessgame.ContextService.MyListener;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.GameService.GameGui;
import com.playchessgame.chessgame.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private GameGui gameGui;

    private MasterUserController masterUserController;

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
            httpSession.setAttribute("user", user);

            MyListener.onlineUsers.put(user.getName(), user);

            Set userNames = MyListener.onlineUsers.keySet();

            for (Object username: userNames){
                System.out.println(username);
            }

            return "userinfo";
        }

        model.addAttribute("user", new PlayerUser());
        model.addAttribute("message", "Username or password is not correct, please try again!");
        return "loginPlayer";

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
        PlayerUser user = (PlayerUser) httpSession.getAttribute("user");

        model.addAttribute("userToLogout", user);

        httpSession.removeAttribute("user");
        httpSession.invalidate();

        MyListener.onlineUsers.remove(user.getName());
//
        return "logout";

    }

    @GetMapping("/play")
    public String toChoosePlayer(Model model){

        Map<String, PlayerUser> onlineUsers = MyListener.onlineUsers;

        Set usernames = onlineUsers.keySet();

        model.addAttribute("users", usernames);
        model.addAttribute("userToPlay", null);

        return "playertochoose";

    }

    @PostMapping("/play")
    public String playGame(@ModelAttribute(value="userToPlay") String userName, HttpServletRequest request){

        HttpSession httpSession = request.getSession(true);
        PlayerUser white = (PlayerUser) httpSession.getAttribute("user");

        PlayerUser black = MyListener.onlineUsers.get(userName);
        GameGui gameGui = new GameGui(white, black);

        System.out.println(white.getName());
        System.out.println(black.getName());

        gameGui.run();

        return "userinfo";
    }

}
