package app.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Simon on 08/01/2015.
 */
@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String errorMessage = "";
        int errorCode = -1;

        requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/index.jsp");

        if (requestDispatcher != null && errorCode == -1) {
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(errorCode, errorMessage);
        }
    }
}