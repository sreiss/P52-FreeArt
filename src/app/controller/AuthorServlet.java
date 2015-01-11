package app.controller;

import app.ejb.AuthorFacadeBean;
import app.ejb.WorkFacadeBean;
import app.model.Author;
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
 * Created by Simon on 09/01/2015.
 */
@WebServlet("/Author")
public class AuthorServlet extends HttpServlet {
    @EJB
    private AuthorFacadeBean authorFacade;

    @EJB
    private WorkFacadeBean workFacade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String errorMessage = "";
        int errorCode = -1;

        String login = request.getParameter("login");
        if (login != null && !login.equals("")) {
            request.setAttribute("login", login);
            Author author = authorFacade.findByLogin(login);
            List<Work> works = null;
            if (author != null) {
                works = workFacade.findByAuthorId(author.getId());
                request.setAttribute("pageTitle", author.getLogin());
            } else {
                request.setAttribute("pageTitle", "No author found");
            }

            request.setAttribute("author", author);
            request.setAttribute("works", works);
            requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/author/get.jsp");
        } else {
            List<Author> authors = authorFacade.findAll();
            request.setAttribute("pageTitle", "Authors");
            request.setAttribute("authors", authors);
            requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/author/list.jsp");
        }

        if (requestDispatcher != null && errorCode == -1) {
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(errorCode, errorMessage);
        }
    }
}
