package Controllers;

import Entities.PlayerUser;
import UserService.impl.UserServiceImpl1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

//    @Autowired
//    private MongoClient mongoClient;

    @Autowired
    private UserServiceImpl1 userServiceImpl1;

    @RequestMapping("/index")
    public String getIndex(){
        return "index";
    }

    @RequestMapping("/showuserinfo")
    public String getUserInfo(){
        return "userinfo";
    }

    @RequestMapping("/register")
    public String getRegister(Model model){
        //model.addAttribute("isuserexist", "no");
        model.addAttribute("user", new PlayerUser());
        return "addUser";
    }

//    @GetMapping("/login")
//    public String getLogin(Model model){
//        //model.addAttribute("isuserexist", "no");
//        model.addAttribute("user", new PlayerUser());
//        return "login";
//    }

    @RequestMapping("/ok")
    public String getRegisterOK(){
        return "ok";
    }

}
