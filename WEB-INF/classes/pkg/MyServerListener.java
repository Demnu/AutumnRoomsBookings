/*
Name: MyServerListener.java
Author: c3282352
Type: Controller
Description: Saves the current game after it's session times out
*/
package pkg;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServerListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se){
        HttpSession session = se.getSession();

    }

    public void sessionDestroyed(HttpSessionEvent se){
        HttpSession session = se.getSession();
        session.invalidate();




    }
}
