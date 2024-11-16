package ru.otus.course.server;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.ee10.servlet.security.ConstraintMapping;
import org.eclipse.jetty.ee10.servlet.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.Constraint;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import ru.otus.course.crm.service.DBServiceClient;
import ru.otus.course.helpers.FileSystemHelper;
import ru.otus.course.processor.TemplateProcessor;
import ru.otus.course.servlet.ClientServlet;
import ru.otus.course.servlet.CreateClientServlet;

import java.util.Arrays;
import java.util.List;

import static ru.otus.course.service.LoginServiceImpl.ROLE_NAME_ADMIN;
import static ru.otus.course.service.LoginServiceImpl.ROLE_NAME_USER;
import static ru.otus.course.servlet.ClientServlet.PATH_CLIENTS;
import static ru.otus.course.servlet.CreateClientServlet.PATH_CREATE_CLIENT;

@Slf4j
public class DefaultUserWebServer implements UserWebServer {

  private static final String START_PAGE_NAME = "index.html";

  private static final String RESOURCES_DIR = "static";

  private static final int DEFAULT_SERVER_PORT = 8080;

  private final Server server;

  private final TemplateProcessor templateProcessor;

  private final LoginService loginService;

  private final DBServiceClient serviceClient;

  public DefaultUserWebServer(TemplateProcessor templateProcessor, LoginService loginService, DBServiceClient serviceClient) {
    this.server = new Server(DEFAULT_SERVER_PORT);
    this.templateProcessor = templateProcessor;
    this.loginService = loginService;
    this.serviceClient = serviceClient;
  }

  @Override
  public void start() throws Exception {
    if (server.getHandlers().isEmpty()) {
      initContext();
    }
    server.start();
  }

  @Override
  public void join() throws Exception {
    server.join();
  }

  @Override
  public void stop() throws Exception {
    server.stop();
  }

  private void initContext() {
    var resourceHandler = createResourceHandler();
    var servletContextHandler = createServletContextHandler();

    var sequence = new Handler.Sequence();
    sequence.addHandler(resourceHandler);
    sequence.addHandler(applySecurity(servletContextHandler, PATH_CLIENTS, PATH_CREATE_CLIENT));

    server.setHandler(sequence);
  }

  protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
    Constraint constraint = Constraint.from(ROLE_NAME_USER, ROLE_NAME_ADMIN);

    return createSecurityHandler(servletContextHandler, constraint, paths);
  }

  private Handler createSecurityHandler(ServletContextHandler servletContextHandler, Constraint constraint, String... paths) {
    var security = new ConstraintSecurityHandler();

    security.setAuthenticator(new BasicAuthenticator());
    security.setLoginService(loginService);
    security.setConstraintMappings(createConstraintMappings(constraint, paths));
    security.setHandler(new Handler.Wrapper(servletContextHandler));

    return security;
  }

  private List<ConstraintMapping> createConstraintMappings(Constraint constraint, String[] paths) {
    return Arrays.stream(paths).map(path -> {
      var mapping = new ConstraintMapping();
      mapping.setPathSpec(path);
      mapping.setConstraint(constraint);
      return mapping;
    }).toList();
  }

  private ResourceHandler createResourceHandler() {
    var resourceHandler = new ResourceHandler();

    resourceHandler.setDirAllowed(Boolean.FALSE);
    resourceHandler.setWelcomeFiles(START_PAGE_NAME);
    resourceHandler.setBaseResourceAsString(FileSystemHelper.localFileNameOrResourceNameToFullPath(RESOURCES_DIR));

    return resourceHandler;
  }

  private ServletContextHandler createServletContextHandler() {
    var servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

    servletContextHandler.addServlet(new ServletHolder(new ClientServlet(templateProcessor, serviceClient)), PATH_CLIENTS);
    servletContextHandler.addServlet(new ServletHolder(new CreateClientServlet(templateProcessor, serviceClient)), PATH_CREATE_CLIENT);

    return servletContextHandler;
  }

}
