package app.controller;

import app.ejb.AuthorFacadeBean;
import app.ejb.CategoryFacadeBean;
import app.error.ErrorManager;
import app.model.Author;
import app.model.Category;
import app.model.Work;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.omg.CORBA.Request;

import javax.ejb.EJB;
import javax.persistence.EntityNotFoundException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Simon on 11/01/2015.
 */
@WebServlet("/Account")
public class AccountServlet extends HttpServlet {
    @EJB
    private AuthorFacadeBean authorFacade;

    @EJB
    private CategoryFacadeBean categoryFacade;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        if (action.equals("login")) {
            getLogin(request, response);
        } else if (action.equals("logout")) {
            logout(request, response);
        } else if (action.equals("signup")) {
            getSignup(request, response);
        } else if (action.equals("upload")) {
            getUpload(request, response);
        } else {
            getAccount(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        if (action.equals("login")) {
            postLogin(request, response);
        } else if (action.equals("update")) {
            updateInfos(request, response);
        } else if (action.equals("signup")) {
            postSignup(request, response);
        } else if (action.equals("upload")) {
            postUpload(request, response);
        }
    }

    private void postUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        int categoryId;
        try {
            categoryId = Integer.parseInt(request.getParameter("categoryId"));
        } catch (NumberFormatException e) {
            categoryId = -1;
            response.sendRedirect(
                    MessageFormat.format("{0}/Account?action=upload&error=invalidcategory", request.getContextPath())
            );
        }

        if (categoryId > 0) {
            if (title.equals("")) {
                response.sendRedirect(
                        MessageFormat.format("{0}/Account?action=upload&error=notitle", request.getContextPath())
                );
            } else {
                Category category = (Category) categoryFacade.find(categoryId);
                Author author = (Author) request.getSession().getAttribute("currentAuthor");
                Date date = new Date();
                Timestamp currentTimestamp = new Timestamp(date.getTime());
                if (category != null) {
//                    try {
//                        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
//                        boolean hasFile = false;
//                        String finalFileName = "";
//                        String finalThumbnailName = "";
//                        for (FileItem item : items) {
//                            ServletContext servletContext = getServletConfig().getServletContext();
//                            String path = servletContext.getRealPath("/");
//
//                            String fieldName = item.getFieldName();
//                            String fileName = FilenameUtils.getName(item.getName());
//                            InputStream file = item.getInputStream();
//
//                            if (fieldName.equals("thumbnail")) {
//                                FileOutputStream fileOutputStream = new FileOutputStream(
//                                        MessageFormat.format("{0}/thumbnail/{1}/{2}-{3}-{4}", path, category.getName(), author.getName(), currentTimestamp, fileName)
//                                );
//                                int read = 0;
//                                byte[] buffer = new byte[2048];
//                                while ((read = file.read()) != -1) {
//                                    fileOutputStream.write(buffer, 0, read);
//                                }
//                                fileOutputStream.close();
//                            } else if (fieldName.equals("file")) {
//                                hasFile = true;
//                                FileOutputStream fileOutputStream = new FileOutputStream(
//                                        MessageFormat.format("{0}/uploads/{1}/{2}-{3}-{4}", path, category.getName(), author.getName(), currentTimestamp, fileName)
//                                );
//                                int read = 0;
//                                byte[] buffer = new byte[2048];
//                                while ((read = file.read()) != -1) {
//                                    fileOutputStream.write(buffer, 0, read);
//                                }
//                                fileOutputStream.close();
//                            }
//                        }
//
//                        if (hasFile) {
//                            Work work = new Work();
//                            work.setTitle(title);
//                            work.setDescription(description);
//                            work.setLocation(location);
//                            work.setCategory(category);
//                            work.setAuthor(author);
//                            work.setCreationDate(currentTimestamp);
//                            work.setFile(finalFileName);
//                            work.setFile(finalThumbnailName);
//                        } else {
//                            response.sendRedirect(
//                                    MessageFormat.format("{0}/Account?action=upload&error=nofile", request.getContextPath())
//                            );
//                        }
//                    } catch (FileUploadException e) {
//                        response.sendRedirect(
//                                MessageFormat.format("{0}/Account?action=upload&error=couldnotupload", request.getContextPath())
//                        );
//                    }
                } else {
                    response.sendRedirect(
                            MessageFormat.format("{0}/Account?action=upload&error=nocategory", request.getContextPath())
                    );
                }
            }
        }
    }

    // POST /Account {action=login}
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

    private void getUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryFacade.findAll();

        request.setAttribute("categories", categories);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/account/getUpload.jsp");
        requestDispatcher.forward(request, response);
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

    private void getAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message");
        String error = request.getParameter("error");

        if (error != null) {
            ErrorManager errorManager = ErrorManager.getInstance();
            String errorMessage = errorManager.findError("account", error);
            request.setAttribute("errorMessage", errorMessage);
        } else if (message != null) {
            if (message.equals("updatesuccess")) {
                request.setAttribute("message", "Your data was successfully updated.");
            } else if (message.equals("passwordupdatesuccess")) {
                request.setAttribute("message", "Ahhhhh, you son of a gun! you have a new password! gg, bro ;)");
            } else if (message.equals("accountcreated")) {
                request.setAttribute("message", "You successfully created an account!");
            }
        }

        if (request.getSession().getAttribute("currentAuthor") != null) {
            request.setAttribute("pageTitle", "Account");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/account/getAccount.jsp");
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect(
                    MessageFormat.format("{0}/Account?action=login&error=authneeded", request.getContextPath())
            );
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("currentAuthor", null);
        response.sendRedirect(
                MessageFormat.format("{0}/Home", request.getContextPath())
        );
    }

    private void updateInfos(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        String updateType = request.getParameter("updateType");
        if (updateType == null) {
            updateType = "";
        }

        if (updateType.equals("general")) {
            String name = request.getParameter("name");
            String firstName = request.getParameter("firstName");
            String email = request.getParameter("email");

            Author currentAuthor = (Author) request.getSession().getAttribute("currentAuthor");
            currentAuthor.setName(name);
            currentAuthor.setFirstName(firstName);
            currentAuthor.setEmail(email);

            try {
                authorFacade.update(currentAuthor);
            } catch (EntityNotFoundException e) {
                response.sendRedirect(
                        MessageFormat.format("{0}/Account?error=updatefailure", request.getContextPath())
                );
            }

            response.sendRedirect(
                    MessageFormat.format("{0}/Account?message=updatesuccess", request.getContextPath())
            );
        } else if (updateType.equals("password")) {
            String password = request.getParameter("password");
            String passwordRepeat = request.getParameter("passwordRepeat");

            if (!password.equals(passwordRepeat)) {
                response.sendRedirect(
                        MessageFormat.format("{0}/Account?error=updatefailure", request.getContextPath())
                );
            } else {
                Author currentAuthor = (Author) request.getSession().getAttribute("currentAuthor");
                currentAuthor.setPassword(hashPassword(password));

                try {
                    authorFacade.update(currentAuthor);
                } catch (EntityNotFoundException e) {
                    response.sendRedirect(
                            MessageFormat.format("{0}/Account?error=passwordupdatefailure", request.getContextPath())
                    );
                }

                response.sendRedirect(
                        MessageFormat.format("{0}/Account?message=passwordupdatesuccess", request.getContextPath())
                );
            }
        }
    }

    private String hashPassword(String password) {
        String hashedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] hashBytes = messageDigest.digest();
            messageDigest.update(hashBytes);
            hashBytes = messageDigest.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0; i< hashBytes.length ;i++)
            {
                stringBuilder.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            hashedPassword = "";
        }
        return hashedPassword;
    }
}
