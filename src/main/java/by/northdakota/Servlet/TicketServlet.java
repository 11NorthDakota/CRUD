package by.northdakota.Servlet;

import by.northdakota.Service.TicketService;
import by.northdakota.Utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {
    private final TicketService ticketService = TicketService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        Long flightId = Long.valueOf(req.getParameter("flightId"));
        req.setAttribute("tickets",TicketService.getInstance().findAllByTicketId(flightId));
        req.getRequestDispatcher(JspHelper.getPath("tickets")).forward(req, resp);
    }
}
