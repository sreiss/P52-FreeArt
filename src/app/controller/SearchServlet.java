package app.controller;

import app.ejb.CategoryFacadeBean;
import app.ejb.WorkFacadeBean;
import app.model.Category;
import app.model.Work;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by Andesite on 1/10/2015.
 */
@WebServlet("/Search")
public class SearchServlet extends HttpServlet {
    @EJB
    private CategoryFacadeBean categoryFacade;

    @EJB
    private WorkFacadeBean workFacade;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String errorMessage = "";
        int errorCode = -1;

        String search = request.getParameter("search");
        if (search != null && !search.equals("")) {
            List<Work> works = workFacade.findAny(search);
            List<Category> categories = categoryFacade.findAny(search);
            request.setAttribute("pageTitle", MessageFormat.format("Search results for: {0}", search));
            request.setAttribute("works", works);
            request.setAttribute("categories", categories);
            request.setAttribute("search", search);
            requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/search/result.jsp");
        } else {
            requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/index.jsp");
        }

        if (requestDispatcher != null && errorCode == -1) {
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(errorCode, errorMessage);
        }
    }
}
