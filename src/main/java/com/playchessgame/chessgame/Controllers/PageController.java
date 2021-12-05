package com.playchessgame.chessgame.Controllers;

import com.playchessgame.chessgame.ContextService.MyListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class is directing webpages
 */
@Controller
public class PageController {

    /**
     * shows the "masterUserPage" webpage when the request "/showMasterUserPage" is sent
     * @return the "masterUserPage" webpage
     */
    @RequestMapping("/showMasterUserPage")
    public String showMasterUserPage(Model model){
        model.addAttribute("online", MyListener.online);
        return "masterUserPage";
    }

}
