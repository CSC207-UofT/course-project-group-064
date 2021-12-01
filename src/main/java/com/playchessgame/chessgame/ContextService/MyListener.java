package com.playchessgame.chessgame.ContextService;

import com.playchessgame.chessgame.Entities.PlayerUser;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class MyListener implements HttpSessionListener {

    public static int online = 0;
    public static List<PlayerUser> onlineUsers = new ArrayList<PlayerUser>();

    @Override
    public synchronized void sessionCreated(HttpSessionEvent event){
        System.out.println("我是session监听器，我被创建啦");

        online ++;

        //event.getSession().getServletContext().setAttribute("online", online);

    }

    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("我是session监听器，我被销毁啦");
        online --;
        //event.getSession().getServletContext().setAttribute("online", online);
    }


}
