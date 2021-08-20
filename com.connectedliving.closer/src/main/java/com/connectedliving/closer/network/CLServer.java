package com.connectedliving.closer.network;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
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
		ServletHandler handler = new ServletHandler();

		handler.addServletWithMapping(RegistryHandler.class, "/register");
		handler.addServletWithMapping(QueryHandler.class, "/query");
		handler.addServletWithMapping(CommandHandler.class, "/command");

		ResourceHandler webPages = new ResourceHandler();
		webPages.setResourceBase("./src/main/resources/html");
		webPages.setWelcomeFiles(new String[] { "index.html" });
		ContextHandler webContext = new ContextHandler();
		webContext.setContextPath("/user");
		webContext.setHandler(webPages);

		HandlerCollection handlers = new HandlerCollection();
		handlers.setHandlers(new Handler[] { webContext, handler });
		server.setHandler(handlers);
		server.start();
	}

}
