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
import java.net.HttpCookie;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");

        if (login != null) {
            get(request, response, login);
        } else {
            list(request, response);
        }
    }

    private void get(HttpServletRequest request, HttpServletResponse response, String login) throws ServletException, IOException {
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
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/author/get.jsp");
        requestDispatcher.forward(request, response);
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        List<Author> authors = authorFacade.findAll();
        request.setAttribute("pageTitle", "Authors");
        request.setAttribute("authors", authors);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/author/list.jsp");
        requestDispatcher.forward(request, response);
    }
}
