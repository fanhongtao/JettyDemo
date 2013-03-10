/*
 * This file is in PUBLIC DOMAIN. You can use it freely. No guarantee.
 */
package org.fanhongtao.jetty;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.fanhongtao.jetty.servlet.HelloServlet;
import org.fanhongtao.jetty.servlet.NotImplementedServlet;
import org.fanhongtao.jetty.servlet.ServletInfoServlet;
import org.fanhongtao.jetty.servlet.UploadFileServlet;

import android.util.Log;

/**
 * @author Fan Hongtao &ltfanhongtao@gmail.com&gt
 */
public class JettyServer {
    private static final String TAG = "JettyServer";

    private Server server;

    /** HTTP server's listener port */
    private int mPort;

    /** HTTP server's resource base directory */
    private String mResourceBase;

    public JettyServer(int port, String resourceBase) {
        this.mPort = port;
        this.mResourceBase = resourceBase;
    }

    public void start() throws Exception {
        // server = new Server(mPort);
        FileUtils.forceMkdir(new File(mResourceBase));

        server = new Server();
        Connector connector = new SocketConnector();
        connector.setPort(mPort);
        server.addConnector(connector);

        ResourceHandler resHandler = new ResourceHandler();
        resHandler.setWelcomeFiles(new String[] { "index.html" });
        resHandler.setResourceBase(mResourceBase);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setResourceBase(mResourceBase);
        servletContextHandler.addServlet(HelloServlet.class, "/hello");
        servletContextHandler.addServlet(UploadFileServlet.class, "/upload");
        servletContextHandler.addServlet(ServletInfoServlet.class, "/servlet/*");
        servletContextHandler.addServlet(NotImplementedServlet.class, "/*");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[] { resHandler, servletContextHandler });
        server.setHandler(handlerList);
        server.setStopAtShutdown(true);
        server.start();
        Log.i(TAG, "Server base: " + resHandler.getBaseResource());
    }

    public void stop() throws Exception {
        server.stop();
    }

    public static void main(String[] args) throws Exception {
        new JettyServer(8080, "web").start();
    }
}
