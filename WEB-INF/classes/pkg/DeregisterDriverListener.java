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
import java.sql.*;
public class DeregisterDriverListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Your webapp classloader
        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
        final Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            final Driver driver = drivers.nextElement();
            // We deregister only the classes loaded by this application's classloader
            if (driver.getClass().getClassLoader() == cl) {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    event.getServletContext().log("JDBC Driver deregistration problem.", e);
                }
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // NOP
    }
}