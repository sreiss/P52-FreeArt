package app.controller;

import app.ejb.AuthorFacadeBean;
import app.model.Author;
import org.omg.CORBA.Request;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by Simon on 11/01/2015.
 */
@WebServlet("/Authentication")
public class AuthenticationServlet extends HttpServlet {
    @EJB
    private AuthorFacadeBean authorFacade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(request, response);
    }

    // POST /Authentication
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String errorMessage = "";
        int errorCode = -1;

        request.setAttribute("pageTitle", "Login");
        String login = request.getParameter("login");
        Author author = authorFacade.findByLogin(login);

        if (author != null) {
            String password = request.getParameter("password");
            if (password.equals(author.getPassword())) {
                request.getSession()
                        .setAttribute("currentAuthor", author);

                requestDispatcher = request.getRequestDispatcher("/Account");
                requestDispatcher.forward(request, response);
            } else {
                request.setAttribute("loginErrorMessage", "Incorrect password");
                response.sendRedirect(
                        MessageFormat.format("{0}/Authentication", request.getContextPath())
                );
            }
        } else {
            request.setAttribute("loginErrorMessage", "User not found");
            response.sendRedirect(
                    MessageFormat.format("{0}/Authentication", request.getContextPath())
            );
        }

        if (errorCode != -1) {
            response.sendError(errorCode, errorMessage);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        get(request, response);
    }

    private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("pageTitle", "Login");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/authentication/get.jsp");
        requestDispatcher.forward(request, response);
    }
}
