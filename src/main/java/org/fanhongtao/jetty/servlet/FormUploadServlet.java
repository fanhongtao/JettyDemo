/*
 * This file is in PUBLIC DOMAIN. You can use it freely. No guarantee.
 */
package org.fanhongtao.jetty.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Fan Hongtao &ltfanhongtao@gmail.com&gt
 */
public class FormUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Default path to save uploaded files */
    private String uploadPath;

    @Override
    public void init() throws ServletException {
        super.init();
        uploadPath = getServletContext().getRealPath("/upload");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // use req.getAttribute() to get uploaded file
        File uploadedFile = (File) req.getAttribute("uploadFile");
        if (uploadedFile == null) {
            resp.setContentType("text/html");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Uploaded file is null");
            return;
        }

        // use req.getParameter() to get uploaded file's name
        File file = new File(uploadPath, req.getParameter("uploadFile"));
        uploadedFile.renameTo(file);

        // use req.getParameter() to get simple form field value.
        String name = req.getParameter("name");
        String age = req.getParameter("age");

        // Send result page
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(name + "," + age);
    }
}
