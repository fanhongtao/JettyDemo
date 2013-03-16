/*
 * This file is in PUBLIC DOMAIN. You can use it freely. No guarantee.
 */
package org.fanhongtao.jetty.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Test with:<br>
 *     http://localhost:8080/servlet/a/b?no=1&name=fan
 * @author Fan Hongtao &ltfanhongtao@gmail.com&gt
 */
public class ServletInfoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        PrintWriter writer = resp.getWriter();
        writer.println("<html><head>");
        addTableCSS(writer);
        writer.println("</head><body>");

        writer.println("<section class=\"entry\">");

        writer.println("<h1>Request</h1>");
        writer.println("<table ><thead><tr><th>method</th><th>value</th></tr></thead><tbody>");
        show(writer, "req.getServletPath()", req.getServletPath());
        show(writer, "req.getPathInfo()", req.getPathInfo());
        show(writer, "req.getQueryString()", req.getQueryString());
        show(writer, "req.getParameter(\"no\")", req.getParameter("no"));
        show(writer, "req.getParameter(\"name\")", req.getParameter("name"));

        writer.println("</tbody></table>");
        writer.println("</section>");

        writer.println("</body></html>");
    }

    private void show(PrintWriter writer, String name, String value) {
        // writer.println(name + ": " + value + "<br>");
        writer.println("<tr><td>" + name + "</td><td>" + value + "</td></tr>");
    }

    private void addTableCSS(PrintWriter writer) {
        writer.println("<style type=\"text/css\">");
        writer.println(".entry table                { border-collapse: collapse; margin: 10px 0 10px; padding: 0; border-top: 2px solid #303030; border-bottom: 2px solid #303030; } ");
        writer.println(".entry * th                 { background: #c3c3c3; } ");
        writer.println(".entry * tr                 { background-color: #fff; border-top: 1px solid #d8d8d8; margin: 0; padding: 0; }  ");
        writer.println(" .entry * tr:nth-child(2n)   { background: #f8f8f8; } ");
        writer.println(".entry * tr th, .entry * tr td  ");
        writer.println("        { border: 1px solid #d8d8d8; margin: 0; padding: 5px 13px; text-align: left; color: #555; } ");
        writer.println(".entry * tr th:first-child, .entry * tr td:first-child  ");
        writer.println("        { border-left: none; } ");
        writer.println(".entry * tr th:last-child, .entry * tr td:last-child  ");
        writer.println("        { border-right: none; } ");
        writer.println("</style>");
    }
}
