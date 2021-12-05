package com.playchessgame.chessgame.ContextService;

import com.playchessgame.chessgame.Entities.PlayerUser;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * The class is for monitoring and listening HttpSessions
 */
@WebListener
public class MyListener implements HttpSessionListener {

    public static int online = 0;
    public static Map<String, PlayerUser> onlineUsers = new HashMap<String, PlayerUser>();
    public static Map<PlayerUser, String> usersToResetPW = new HashMap<>();

    /**
     * Notify when a session is created and increase the online player by one
     * @param event
     */
    @Override
    public synchronized void sessionCreated(HttpSessionEvent event){
        System.out.println("This session listener，I am just created.");

        online ++;

        //event.getSession().getServletContext().setAttribute("online", online);

    }

    /**
     * Notify when a session is destroyed and decrease the online player by one
     * @param event
     */
    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("This is session listener，I am just be destroyed");
        online --;
        //event.getSession().getServletContext().setAttribute("online", online);
    }


}
