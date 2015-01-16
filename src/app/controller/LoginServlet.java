package app.controller;

import app.ejb.AuthorFacadeBean;
import app.error.ErrorManager;
import app.model.Author;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

import static app.util.PasswordHasher.hashPassword;

/**
 * Created by Simon on 16/01/2015.
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    @EJB
    private AuthorFacadeBean authorFacade;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        if (action.equals("login")) {
            postLogin(request, response);
        } else if (action.equals("signup")) {
            postSignup(request, response);
        }
    }

    private void postLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("pageTitle", "Login");
        String login = request.getParameter("login");
        Author author = authorFacade.findByLogin(login);
        response.getWriter().append(author.getFirstName());

        if (author != null) {
            String password = hashPassword(request.getParameter("password"));
            String authorPassword = author.getPassword();

            if (password == null) {
                password = "";
            }
            if (authorPassword == null) {
                authorPassword = "";
            }

            if (password.equals(authorPassword)) {
                request.getSession()
                        .setAttribute("currentAuthor", author);

                response.sendRedirect(
                        MessageFormat.format("{0}/Account", request.getContextPath())
                );
            } else {
                response.sendRedirect(
                        MessageFormat.format("{0}/Account?action=login&error=invalidpassword", request.getContextPath())
                );
            }
        } else {
            response.sendRedirect(
                    MessageFormat.format("{0}/Account?action=login&error=invaliduser", request.getContextPath())
            );
        }
    }

    private void postSignup(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        String login = request.getParameter("login");
        String name = request.getParameter("name");
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");

        if (!password.equals(passwordRepeat)) {
            response.sendRedirect(
                    MessageFormat.format("{0}/Account?action=signup&error=invalidpassword", request.getContextPath())
            );
        } else if (authorFacade.count(login) > 0) {
            response.sendRedirect(
                    MessageFormat.format("{0}/Account?action=signup&error=existingusername", request.getContextPath())
            );
        } else {
            Author author = new Author();
            author.setLogin(login);
            author.setName(name);
            author.setFirstName(firstName);
            author.setEmail(email);
            author.setPassword(hashPassword(password));

            try {
                authorFacade.create(author);
            } catch (Exception e) {
                response.sendRedirect(
                        MessageFormat.format("{0}/Account?action=signup&error=errorwhilesaving", request.getContextPath())
                );
            }

            request.getSession().setAttribute("currentAuthor", author);

            response.sendRedirect(
                    MessageFormat.format("{0}/Account?message=accountcreated", request.getContextPath())
            );
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("currentAuthor", null);
        response.sendRedirect(
                MessageFormat.format("{0}/Home", request.getContextPath())
        );
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        if (action.equals("logout")) {
            logout(request, response);
        } else if (action.equals("signup")) {
            getSignup(request, response);
        } else {
            getLogin(request, response);
        }
    }

    private void getSignup(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        if (request.getSession().getAttribute("currentAuthor") != null) {
            response.sendRedirect(
                    MessageFormat.format("{0}/Account", request.getContextPath())
            );
        } else {
            String error = request.getParameter("error");
            if (error != null) {
                ErrorManager errorManager = ErrorManager.getInstance();
                String errorMessage = errorManager.findError("signup", error);
                request.setAttribute("errorMessage", errorMessage);
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/account/getSignup.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private void getLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("currentAuthor") != null) {
            response.sendRedirect(
                    MessageFormat.format("{0}/Account", request.getContextPath())
            );
        } else {
            String error = request.getParameter("error");
            if (error != null) {
                ErrorManager errorManager = ErrorManager.getInstance();
                String errorMessage = errorManager.findError("login", error);
                request.setAttribute("errorMessage", errorMessage);
            }

            request.setAttribute("pageTitle", "Login");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/account/getLogin.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
