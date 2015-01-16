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
import javax.servlet.*;
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
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;

import static app.util.PasswordHasher.hashPassword;

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
                    String path = MessageFormat.format("{0}/uploads/{1}/{2}-{3}-{4}", "/", category.getName(), author.getName(), currentTimestamp, fileName);

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
                        writer.println("New file " + fileName + " created at " + path);
                    } catch (FileNotFoundException fne) {
                        writer.println("You either did not specify a file to upload or are "
                                + "trying to upload a file to a protected or nonexistent "
                                + "location.");
                        writer.println("<br/> ERROR: " + fne.getMessage());
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

    /*private void postUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    }*/



    private void getUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
