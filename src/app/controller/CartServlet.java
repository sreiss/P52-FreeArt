package app.controller;

import app.ejb.WorkFacadeBean;
import app.model.Work;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andesite on 1/11/2015.
 */
@WebServlet("/Cart")
public class CartServlet extends HttpServlet {
    @EJB
    private WorkFacadeBean workFacade;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String errorMessage = "";
        int errorCode = -1;

        Cookie cart = retrieveCartCookie(request);

        List<Work> works = list(cart);
        request.setAttribute("pageTitle", "My cart");
        request.setAttribute("works", works);
        requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/cart/list.jsp");

        if (requestDispatcher != null && errorCode == -1) {
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(errorCode, errorMessage);
        }
    }

    // GET /Cart
    private List<Work> list(Cookie cart) {
        ArrayList<Work> works = new ArrayList<Work>();

        if (cart != null) {
            String sItems = cart.getValue();
            String[] sItemIds = sItems.split(",");
            ArrayList<Integer> itemIds = new ArrayList<Integer>();
            for (String sItem : sItemIds) {
                try {
                    itemIds.add(Integer.parseInt(sItem));
                } catch (NumberFormatException e) {

                }
            }
            for (int id : itemIds) {
                works.add((Work) workFacade.find(id));
            }
        }

        return works;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String errorMessage = "";
        int errorCode = -1;

        String action = request.getParameter("action");
        if (action == null)
            action = "";

        if (!action.equals("")) {
            int id;
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
                id = -1;
            }

            if (action.equals("add")) {
                Cookie cart = retrieveCartCookie(request);

                List<Work> works = list(cart);
                boolean cartContainsWork = false;
                for (Work inCartWork : works) {
                    if (inCartWork.getId() == id) {
                        cartContainsWork = true;
                        break;
                    }
                }

                Work work = null;
                if (!cartContainsWork) {
                    work = add(id);
                    works.add(work);
                    String cartString = "";
                    if (cart != null) {
                        cartString = buildCartString(works);
                        cart.setValue(cartString);
                    } else {
                        cart = new Cookie("cart", "" + id);
                    }
                    response.addCookie(cart);
                }

                request.setAttribute("pageTitle", "Adding work");
                request.setAttribute("work", work);
                request.setAttribute("works", works);
                requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/cart/add.jsp");
            } else if (action.equals("delete")) {
                Cookie cart = retrieveCartCookie(request);
                ArrayList<Work> initialWorks = (ArrayList<Work>) list(cart);
                ArrayList<Work> works = (ArrayList<Work>) initialWorks.clone();
                works = (ArrayList<Work>) delete(id, works);
                if (initialWorks.size() == works.size()) {
                    request.setAttribute("pageTitle", "Error while deleting");
                    request.setAttribute("deleted", false);
                } else {
                    request.setAttribute("pageTitle", "Successfully deleted");
                    request.setAttribute("deleted", true);
                    if (cart != null) {
                        String cartString = buildCartString(works);
                        cart.setValue(cartString);
                    } else {
                        cart = new Cookie("cart", "");
                    }
                    response.addCookie(cart);
                }
                request.setAttribute("works", works);
                requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/cart/delete.jsp");
            } else {
                errorCode = 401;
                errorMessage = "This action is not authorized!";
            }
        } else {
            errorCode = 401;
            errorMessage = "You can't browse your cart with POST method!";
        }

        if (requestDispatcher != null && errorCode == -1) {
            requestDispatcher.forward(request, response);
        } else {
            response.sendError(errorCode, errorMessage);
        }
    }

    // POST /Cart {id}
    private Work add(int id) {
        return (Work) workFacade.find(id);
    }

    // DELETE /Cart?id={id}
    private List<Work> delete(int id, List<Work> works) {
        for (Work work: works) {
            if (work.getId() == id) {
                works.remove(work);
                break;
            }
        }

        return works;
    }

    private Cookie retrieveCartCookie (HttpServletRequest request) {
        Cookie cart = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies) {
            if (cookie.getName().equals("cart")) {
                cart = cookie;
            }
        }

        return  cart;
    }

    private String buildCartString (List<Work> works) {
        String cartString = "";
        for (Work inCartWork: works) {
            if (cartString.equals(""))
                cartString += "" + inCartWork.getId();
            else
                cartString += "," + inCartWork.getId();
        }

        return cartString;
    }
}
