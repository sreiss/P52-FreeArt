package controller;

import ejb.WorkFacadeBean;
import model.Work;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Simon on 09/01/2015.
 */
@WebServlet("/Work")
public class WorkServlet extends HttpServlet {
    @EJB
    private WorkFacadeBean workFacade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageTitle = "Works";
        RequestDispatcher requestDispatcher = null;

        String by = request.getParameter("by");
        if (by == null)
            by = "";

        if (by.equals("category")) {
            requestDispatcher = request.getRequestDispatcher("/Category");
        } else  if (by.equals("author")) {
            requestDispatcher = request.getRequestDispatcher("/Author");
        } else {
            requestDispatcher = list(request, response, requestDispatcher);
        }

        if (requestDispatcher != null) {
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(404);
        }
    }

    private RequestDispatcher list(HttpServletRequest request, HttpServletResponse response, RequestDispatcher requestDispatcher) throws ServletException, IOException {
        List<Work> works = workFacade.findAll();

        request.setAttribute("pageTitle", "All our amazing pieces of art!");
        request.setAttribute("works", works);
        requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/work/list.jsp");

        return requestDispatcher;
    }
}
