package com.playchessgame.chessgame.Controllers;

import com.mongodb.client.MongoClient;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.UserService.impl.UserServiceImpl1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/showMasterUserPage")
    public String showMasterUserPage(){
        return "masterUserPage";
    }


}
