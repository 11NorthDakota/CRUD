package by.northdakota.Servlet;

import by.northdakota.Dto.CreateUserDto;
import by.northdakota.Entity.Gender;
import by.northdakota.Entity.Role;
import by.northdakota.Exception.ValidationException;
import by.northdakota.Service.UserService;
import by.northdakota.Utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = CreateUserDto.builder()
                .name(req.getParameter("name"))
                .birthday(req.getParameter("birthday"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();
        try{
            userService.create(userDto);
            resp.sendRedirect(JspHelper.getPath("login"));
        }catch(ValidationException e){
            req.setAttribute("error", e.getErrors());
            doGet(req, resp);
        }
    }
}
