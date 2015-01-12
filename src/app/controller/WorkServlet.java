package app.controller;

import app.ejb.WorkFacadeBean;
import app.model.Work;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Simon on 09/01/2015.
 */
@WebServlet("/Work")
public class WorkServlet extends HttpServlet {
    @EJB
    private WorkFacadeBean workFacade;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            id = -1;
        }
        String category = request.getParameter("category");
        String author = request.getParameter("author");

        if (category != null) {
            category(request, response, id);
        } else if (author != null) {
            author(request, response, id);
        } else if (id > 0) {
            get(request, response, id);
        } else {
            list(request, response);
        }
    }

    private void get(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        Work work = (Work) workFacade.find(id);
        if (work != null) {
            request.setAttribute("pageTitle", work.getTitle());
            request.setAttribute("work", work);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/work/get.jsp");
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(404, "No work with this identifier was found!");
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Work> works = workFacade.findAll();
        request.setAttribute("pageTitle", "All works!");
        request.setAttribute("works", works);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/work/list.jsp");
        requestDispatcher.forward(request, response);
    }

    private void category(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Category");
        requestDispatcher.forward(request, response);
    }

    private void author(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Author");
        requestDispatcher.forward(request, response);
    }
}
