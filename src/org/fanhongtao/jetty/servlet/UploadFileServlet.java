/*
 * This file is in PUBLIC DOMAIN. You can use it freely. No guarantee.
 */
package org.fanhongtao.jetty.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import android.util.Log;

/**
 * @author Fan Hongtao &ltfanhongtao@gmail.com&gt
 */
public class UploadFileServlet extends HttpServlet {

    private static final long serialVersionUID = -5992264585339747068L;

    private static final String TAG = "UploadFileServlet";

    /** Default path to save uploaded files */
    private String uploadPath;

    @Override
    public void init() throws ServletException {
        super.init();
        uploadPath = getServletContext().getRealPath("/upload");
        Log.i(TAG, "Upload dir: " + uploadPath);
        try {
            FileUtils.forceMkdir(new File(uploadPath));
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        if (fileName == null) {
            resp.setContentType("text/html");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Missing parameter [fileName]");
            return;
        }

        File file = new File(uploadPath + "/" + fileName);
        Log.i(TAG, "File: " + file.getAbsolutePath());
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
            InputStream input = req.getInputStream();
            IOUtils.copy(input, output);
        } finally {
            IOUtils.closeQuietly(output);
        }

        // Send result page
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
