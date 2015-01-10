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

    // region GET

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String errorMessage = "";
        int errorCode = -1;

        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            id = -1;
        }

        if (id > 0) {
            Category category = get(id);
            if (category != null) {
                request.setAttribute("pageTitle", category.getName());
                request.setAttribute("category", category);
                requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/category/get.jsp");
            } else {
                errorMessage = "This category doesn't exist!";
                errorCode = 404;
            }
        } else {
            List<Category> categories = list();
            request.setAttribute("pageTitle", "Categories");
            request.setAttribute("categories", categories);
            requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/category/list.jsp");
        }

        if (requestDispatcher != null && errorCode == -1) {
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(errorCode, errorMessage);
        }
    }

    // GET /Category?id={id}
    private Category get(int id) {
        return (Category) categoryFacade.find(id);
    }

    // GET /Category
    private List<Category> list() {
        return categoryFacade.findAll();
    }

    // endregion

    // region POST

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;

        try {
            Category category = new Category();
            category.setName(request.getParameter("name"));
            categoryFacade.create(category);

            request.setAttribute("pageTitle", MessageFormat.format("Category {0} created", category));
            request.setAttribute("category", category);
            requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/category/create.jsp");
        } catch (IllegalArgumentException e) {

        }

        if (requestDispatcher != null) {
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(404);
        }
    }

    // POST /Category
    private Category create(Category category) {
        categoryFacade.create(category);
        return category;
    }

    // endregion

    // PUT /Category
    private Category update(Category category) {
        categoryFacade.update(category);
        return category;
    }

    // DELETE /Category
    private Category delete(Category category) {
        categoryFacade.delete(category);
        return category;
    }
}
