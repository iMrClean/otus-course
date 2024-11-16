package ru.otus.course.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.otus.course.crm.model.Address;
import ru.otus.course.crm.model.Client;
import ru.otus.course.crm.model.Phone;
import ru.otus.course.crm.service.DBServiceClient;
import ru.otus.course.processor.TemplateProcessor;

import java.io.IOException;
import java.util.Arrays;

import static ru.otus.course.servlet.ClientServlet.*;

@RequiredArgsConstructor
public class CreateClientServlet extends HttpServlet {

    public static final String PATH_CREATE_CLIENT = "/create-client";

    private static final String CREATE_CLIENT_PAGE_TEMPLATE = "create-client.html";

    private final TemplateProcessor templateProcessor;

    private final DBServiceClient serviceClient;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(CREATE_CLIENT_PAGE_TEMPLATE, null));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var name = req.getParameter("name");
        var address = req.getParameter("address");
        var phones = req.getParameter("phones");

        var client = new Client(name);
        client.setAddress(new Address(address));
        client.setPhones(Arrays.stream(phones.split(",")).map(Phone::new).toList());
        serviceClient.saveClient(client);

        resp.sendRedirect(PATH_CLIENTS);
    }

}
