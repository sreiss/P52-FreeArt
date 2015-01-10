package controller;

import ejb.CategoryFacadeBean;
import model.Category;

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
@WebServlet("/Category")
public class CategoryServlet extends HttpServlet {
    @EJB
    private CategoryFacadeBean categoryFacade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageTitle = "Category";
        RequestDispatcher requestDispatcher = null;

        String by = request.getParameter("by");
        if (by == null)
            by = "";


        requestDispatcher = list(request, response, requestDispatcher);

        if (requestDispatcher != null) {
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(404);
        }
    }

    protected RequestDispatcher list(HttpServletRequest request, HttpServletResponse response, RequestDispatcher requestDispatcher) {
        //List<Category> works = categoryFacade.findAll();
        List<Category> works = null;

        request.setAttribute("works", works);
        requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/category/list.jsp");

        return requestDispatcher;
    }
}
