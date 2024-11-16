package ru.otus.course.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.otus.course.crm.service.DBServiceClient;
import ru.otus.course.processor.TemplateProcessor;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class ClientServlet extends HttpServlet {

  public static final String PATH_CLIENTS = "/clients";

  private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";

  private final TemplateProcessor templateProcessor;

  private final DBServiceClient serviceClient;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    var clients = serviceClient.findAll();
    var page = templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, Map.of("clients", clients));

    resp.setContentType("text/html");
    resp.getWriter().println(page);
  }

}
