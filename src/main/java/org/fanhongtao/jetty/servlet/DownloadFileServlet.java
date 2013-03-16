/*
 * This file is in PUBLIC DOMAIN. You can use it freely. No guarantee.
 */
package org.fanhongtao.jetty.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import android.util.Log;

/**
 * @author Fan Hongtao &ltfanhongtao@gmail.com&gt
 */
public class DownloadFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String TAG = "DownloadFileServlet";

    /** Default path for downloading files */
    private String downloadPath;

    @Override
    public void init() throws ServletException {
        super.init();
        downloadPath = getServletContext().getRealPath("/download");
        Log.i(TAG, "Download dir: " + downloadPath);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        if (fileName == null) {
            resp.setContentType("text/html");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Missing parameter [fileName]");
            return;
        }

        File file = new File(downloadPath, fileName);
        Log.i(TAG, "File: " + file.getAbsolutePath());
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentLength((int) file.length());

        InputStream input = null;
        try {
            input = new FileInputStream(file);
            OutputStream output = resp.getOutputStream();
            IOUtils.copy(input, output);
        } finally {
            IOUtils.closeQuietly(input);
        }

    }

}
