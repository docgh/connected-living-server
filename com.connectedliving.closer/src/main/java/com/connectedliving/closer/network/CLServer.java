package com.connectedliving.closer.network;

import javax.servlet.MultipartConfigElement;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;

public class CLServer {

	private Server server;

	public void start() throws Exception {
		StdErrLog logger = new StdErrLog();
		logger.setDebugEnabled(false);
		Log.setLog(logger);
		server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(8080);
		server.setConnectors(new Connector[] { connector });

		ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);
		root.setContextPath("/");
		root.setCompactPath(true);
		server.setHandler(root);

		SessionHandler manager = new SessionHandler();
		manager.setSessionCookie("JSESSIONID");
		manager.setHttpOnly(true);
		root.setSessionHandler(manager);

		ServletHolder registerServlet = new ServletHolder(new RegistryHandler());
		root.addServlet(registerServlet, "/register/*");

		ServletHolder queryServlet = new ServletHolder(new QueryHandler());
		root.addServlet(queryServlet, "/query/*");

		ServletHolder commandServlet = new ServletHolder(new CommandHandler());
		root.addServlet(commandServlet, "/command/*");

		ServletHolder statusServlet = new ServletHolder(new StatusHandler());
		root.addServlet(statusServlet, "/status/*");

		ServletHolder pictureServlet = new ServletHolder(new PictureHandler());
		pictureServlet.getRegistration().setMultipartConfig(new MultipartConfigElement("./tmp"));
		root.addServlet(pictureServlet, "/picture/*");

		ResourceHandler webPages = new ResourceHandler();
		webPages.setResourceBase("./src/main/resources/html");
		webPages.setWelcomeFiles(new String[] { "index.html" });
		ContextHandler webContext = new ContextHandler();
		webContext.setContextPath("/user");
		webContext.setHandler(webPages);

		HandlerCollection handlers = new HandlerCollection();
		handlers.setHandlers(new Handler[] { webContext, root });
		server.setHandler(handlers);
		server.start();
	}

}
