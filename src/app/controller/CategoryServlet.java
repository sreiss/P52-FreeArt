package app.controller;

import app.ejb.CategoryFacadeBean;
import app.model.Category;

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
 * Created by Simon on 09/01/2015.
 */
@WebServlet("/Category")
public class CategoryServlet extends HttpServlet {
    @EJB
    private CategoryFacadeBean categoryFacade;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            id = -1;
        }

        if (id > 0) {
            get(request, response, id);
        } else {
            list(request, response);
        }
    }

    // GET /Category
    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryFacade.findAll();
        request.setAttribute("pageTitle", "Categories");
        request.setAttribute("categories", categories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/category/list.jsp");
        requestDispatcher.forward(request, response);
    }

    // GET /Category?id={id}
    private void get(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        Category category = (Category) categoryFacade.find(id);
        if (category != null) {
            request.setAttribute("pageTitle", category.getName());
            request.setAttribute("category", category);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/category/get.jsp");
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(404, "This category doesn't exist!");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("create")) {
                create(request, response);
            }
        }
    }

    // POST /Category
    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = new Category();
        category.setName(request.getParameter("name"));
        categoryFacade.create(category);

        request.setAttribute("pageTitle", MessageFormat.format("Category {0} created", category));
        request.setAttribute("category", category);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/category/create.jsp");
        requestDispatcher.forward(request, response);
    }
}
