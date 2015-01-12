package app.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Simon on 12/01/2015.
 */
@WebFilter(filterName = "CryptographyFilter")
public class CryptographyFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(true);

        String secret = httpServletRequest.getParameter("secret");
        secret = decode(secret);
        session.setAttribute("secret", secret);

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        secret = (String) session.getAttribute("secret");
        session.setAttribute("secret", code(secret));
    }

    public static String code(String toCode) {
        return "";
    }

    public static String decode(String toDecode) {
        return "";
    }
}
