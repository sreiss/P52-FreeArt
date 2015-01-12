package app.controller;

import app.ejb.AuthorFacadeBean;
import app.ejb.CategoryFacadeBean;
import app.ejb.WorkFacadeBean;
import app.model.Author;
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

    @EJB
    private AuthorFacadeBean authorFacade;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");

        if (search != null && !search.equals("")) {
            search(request, response, search);
        } else {
            home(request, response);
        }
    }

    private void search(HttpServletRequest request, HttpServletResponse response, String search) throws ServletException, IOException {
        List<Work> works = workFacade.findAny(search);
        List<Category> categories = categoryFacade.findAny(search);
        List<Author> authors = authorFacade.findAny(search);

        request.setAttribute("pageTitle", MessageFormat.format("Search results for: {0}", search));
        request.setAttribute("works", works);
        request.setAttribute("categories", categories);
        request.setAttribute("authors", authors);
        request.setAttribute("search", search);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/search/result.jsp");
        requestDispatcher.forward(request, response);
    }

    private void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Home");
        requestDispatcher.forward(request, response);
    }
}
