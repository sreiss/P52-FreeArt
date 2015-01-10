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

/**
 * Created by Simon on 09/01/2015.
 */
@WebServlet("/Work")
public class WorkServlet extends HttpServlet {
    @EJB
    private WorkFacadeBean workFacade;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String errorMessage = "";
        int errorCode = -1;

        String category = request.getParameter("category");

        if (category != null) {
            requestDispatcher = request.getRequestDispatcher("/Category");
        } else {
            int id;
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
                id = -1;
            }

            if (id > 0) {
                Work work = (Work) workFacade.find(id);
                if (work != null) {
                    request.setAttribute("pageTitle", work.getTitle());
                    request.setAttribute("work", work);
                    requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/work/get.jsp");
                } else {
                    errorCode = 404;
                    errorMessage = "No work under this name where found";
                }
            } else {
                List<Work> works = list();
                request.setAttribute("pageTitle", "All works!");
                request.setAttribute("works", works);
                requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/work/list.jsp");
            }
        }

        if (requestDispatcher != null && errorCode == -1) {
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(errorCode, errorMessage);
        }
    }

    // GET /Work
    private List<Work> list() {
        return workFacade.findAll();
    }
}
