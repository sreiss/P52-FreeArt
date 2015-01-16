package app.filter;

import app.model.Author;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by Simon on 16/01/2015.
 */
@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (((HttpServletRequest) request).getSession().getAttribute("currentAuthor") != null) {
            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ((HttpServletResponse) response).sendRedirect(MessageFormat.format("{0}/Login?action=login", ((HttpServletRequest) request).getContextPath()));
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
