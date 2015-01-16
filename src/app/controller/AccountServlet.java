package app.controller;

import app.ejb.AuthorFacadeBean;
import app.ejb.CategoryFacadeBean;
import app.ejb.WorkFacadeBean;
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
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;

import static app.util.PasswordHasher.hashPassword;

/**
 * Created by Simon on 11/01/2015.
 */
@WebServlet("/Account")
@MultipartConfig(
        location="/tmp",
        fileSizeThreshold=1024*1024,
        maxFileSize=1024*1024*5,
        maxRequestSize=1024*1024*5*5
)
public class AccountServlet extends HttpServlet {
    @EJB
    private AuthorFacadeBean authorFacade;

    @EJB
    private CategoryFacadeBean categoryFacade;

    @EJB
    private WorkFacadeBean workFacade;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        if (action.equals("upload")) {
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

        if (action.equals("update")) {
            updateInfos(request, response);
        } else if (action.equals("upload")) {
            postUpload(request, response);
        }
    }

    private void postUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");

        if (title != null) {
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
                        response.setContentType("text/html;charset=UTF-8");

                        // Create path components to save the file
                        Part filePart = request.getPart("file");
                        String fileName = getFileName(filePart);
                        String uploadPath = getServletContext().getRealPath("uploads");
                        String finalFileName = MessageFormat.format("{0}-{1}-{2}", author.getLogin(), currentTimestamp.toString(), fileName);
                        char[] forbiddenChars = {'/', '_', ' ', ':', '?'};
                        for (char forbiddenChar : forbiddenChars) {
                            finalFileName = finalFileName.replace(forbiddenChar, '-');
                        }
                        String path = MessageFormat.format("{0}/{1}/{2}", uploadPath, category.getName(), finalFileName);

                        OutputStream out = null;
                        InputStream filecontent = null;
                        final PrintWriter writer = response.getWriter();

                        try {
                            out = new FileOutputStream(new File(path));
                            filecontent = filePart.getInputStream();

                            int read = 0;
                            final byte[] bytes = new byte[1024];

                            while ((read = filecontent.read(bytes)) != -1) {
                                out.write(bytes, 0, read);
                            }

                            Work work = new Work();
                            work.setAuthor((Author) request.getSession().getAttribute("currentAuthor"));
                            work.setFile(finalFileName);
                            work.setCreationDate(currentTimestamp);
                            work.setLocation(location);
                            work.setDescription(description);
                            work.setCategory(category);
                            work.setTitle(title);
                            workFacade.create(work);
                            response.sendRedirect(
                                    MessageFormat.format("{0}/Account?action=upload&message=uploadsuccess", request.getContextPath())
                            );
                        } catch (FileNotFoundException fne) {
                            response.sendRedirect(
                                    MessageFormat.format("{0}/Account?action=upload&error=filecreation", request.getContextPath())
                            );
                        } finally {
                            if (out != null) {
                                out.close();
                            }
                            if (filecontent != null) {
                                filecontent.close();
                            }
                            if (writer != null) {
                                writer.close();
                            }
                        }
                    }
                }
            }
        } else {
            response.sendRedirect(
                    MessageFormat.format("{0}/Account?action=upload&error=notitle", request.getContextPath())
            );
        }
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private void getUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = request.getParameter("error");
        String message = request.getParameter("message");
        if (error != null) {
            ErrorManager errorManager = ErrorManager.getInstance();
            String errorMessage = errorManager.findError("account", error);
            request.setAttribute("errorMessage", errorMessage);
        } else if (message != null) {
            if (message.equals("uploadsuccess")) {
                request.setAttribute("message", "Your upload succeded!");
            }
        }

        List<Category> categories = categoryFacade.findAll();

        request.setAttribute("categories", categories);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/account/getUpload.jsp");
        requestDispatcher.forward(request, response);
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

        request.setAttribute("pageTitle", "Account");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/app/servlet/account/getAccount.jsp");
        requestDispatcher.forward(request, response);
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
}
