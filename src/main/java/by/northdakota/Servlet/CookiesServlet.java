package by.northdakota.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;


@WebServlet("/cookies")
public class CookiesServlet extends HttpServlet {
    private final static String UNIQUE_USER = "userId";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       var cookies = req.getCookies();
       if(cookies == null ||
          Arrays.stream(cookies).anyMatch(cookie ->
                  UNIQUE_USER.equals(cookie.getName()))){
           var cookie = new Cookie(UNIQUE_USER,"1");
           cookie.setMaxAge(3600);
           cookie.setPath("/cookies");
           resp.addCookie(cookie);

        }
    }
}
