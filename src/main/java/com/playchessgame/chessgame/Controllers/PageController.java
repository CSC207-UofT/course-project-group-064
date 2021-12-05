package com.playchessgame.chessgame.Controllers;

import org.springframework.stereotype.Controller;
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
    public String showMasterUserPage(){
        return "masterUserPage";
    }

}
