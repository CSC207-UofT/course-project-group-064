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

/**
 * This class is for handling requests from playerusers
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * returns the "addUser" webpage when the get request "/register" is sent
     * @param model
     * @return the "addUser" webpage for players to register into the system
     */
    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("user", new PlayerUser());
        return "addUser";
    }

    /**
     * register a playeruser into the database
     * @param user
     * @param model
     * @return the "addUser" webpage which varies by registration result
     */
    @PostMapping("/register")
    public String register(@ModelAttribute(value="user") PlayerUser user, Model model){

        try {
            userService.addUser(user);
            return "ok";
        } catch (UserAlreadyExistsException e){
            model.addAttribute("message", e.getMessage() + "\nPlease try another username~~~");
            return "addUser";

        } catch(Exception e){
            model.addAttribute("message", "Something running wrong with the database, please try again");
            return "addUser";
        }

    }

    /**
     * returns the loginPlayer webpage when the get request /login1 is sent
     * @param model
     * @return the loginPlayer webpage for playeruser to login
     */
    @GetMapping("/login1")
    public String getLoginPlayer(Model model){
        model.addAttribute("user", new PlayerUser());
        return "loginPlayer";
    }

    /**
     * login the playeruser if the information provided matches it in the database and add the player into
     * MyListerner.onlineUsers map if the player successfully login
     * @param user: PlayerUser
     * @param model
     * @param request
     * @return the loginPlayer webpage which varies by the login result
     */
    @PostMapping("/login1")
    public String loginPlayer(@ModelAttribute(value="user") PlayerUser user, Model model, HttpServletRequest request) {

        if (userService.checkUserExistence(user)) {

            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("user", user);

            MyListener.onlineUsers.put(user.getName(), user);

            return "userinfo";
        }

        model.addAttribute("user", new PlayerUser());
        model.addAttribute("message", "Username or password is not correct, please try again!");
        return "loginPlayer";

    }

    /**
     * returns the resetpassword webpage when the get request /resetpassword is sent
     * @param model
     * @return the resetpassword webpage for player to reset their passwords
     */
    @GetMapping("/resetpassword")
    public String getResetPassword(Model model){
        model.addAttribute("user", new PlayerUser());
        model.addAttribute("email", "");

        return "resetpassword";

    }

    /**
     * send the resetpassword request to masteruser who is monitoring this issue
     * @param user: PlayerUser who is applying to reset password
     * @param email: the playeruser's email address
     * @param model:
     * @return the "resetpassword" webpage
     */
    @PostMapping("/resetpassword")
    public String resetPassword(@ModelAttribute(value="user") PlayerUser user, @ModelAttribute(value="email") String email, Model model){
        MyListener.usersToResetPW.put(user, email);
        model.addAttribute("message", "Please wait for administrator to contact you!");
        return "resetpassword";
    }

    /**
     * remove the playeruser from the HttpSessions and from the MyListener.onlineUsers
     * @param request
     * @param model
     * @return the "logout" webpage
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

    /**
     * returns the "playertochoose" webpage when the get request "/play" is sent from playeruser
     * @param model
     * @return the "playertochoose" webpage
     */
    @GetMapping("/play")
    public String toChoosePlayer(Model model){

        Map<String, PlayerUser> onlineUsers = MyListener.onlineUsers;

        Set usernames = onlineUsers.keySet();

        model.addAttribute("users", usernames);
        model.addAttribute("userToPlay", null);
        model.addAttribute("roleToPlay", null);

        return "playertochoose";

    }

    /**
     * a game is created based on the parameters entered by the playeruser from the wegpage and prompts the player with
     * the GameGui.
     * @param userName: the player's username the current playeruser wants to play wih
     * @param role: the role the current playeruser wants to play: white or black
     * @param request
     * @return the "userinfo" webpage once the game is over
     */
    @PostMapping("/play")
    public String playGame(@ModelAttribute(value="userToPlay") String userName, @ModelAttribute(value="roleToPlay") String role, HttpServletRequest request){

        HttpSession httpSession = request.getSession(true);
        PlayerUser user1 = (PlayerUser) httpSession.getAttribute("user");

        PlayerUser user2 = MyListener.onlineUsers.get(userName);

        switch (role.toLowerCase()){
            case "white":
                // user1 is white and user2 is black
                GameGui.run(user1, user2);
                break;
            case "black":
                // user1 is black and user2 is white
                GameGui.run(user2, user1);
                break;
        }

        return "userinfo";
    }

}
