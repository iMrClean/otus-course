package ru.otus.course.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.course.domain.Client;
import ru.otus.course.service.ClientService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClientController {

  private final ClientService clientService;

  @GetMapping("/")
  public ModelAndView index() {
    var modelAndView = new ModelAndView("index");

    var clients = clientService.findAll();
    modelAndView.addObject("clients", clients);

    return modelAndView;
  }

  @GetMapping("/add")
  public ModelAndView add() {
    var modelAndView = new ModelAndView("add-or-edit");

    modelAndView.addObject("client", new Client());

    return modelAndView;
  }

  @PostMapping("/save")
  public RedirectView save(@ModelAttribute Client client) {
    try {
      clientService.save(client);
      log.info("Client saved successfully with id: {}", client.getId());
    } catch (Exception e) {
      log.error("Error saving client: ", e);
    }

    return new RedirectView("/");
  }

  @GetMapping("/edit/{id}")
  public ModelAndView edit(@PathVariable("id") Long id) {
    var modelAndView = new ModelAndView("add-or-edit");

    var client = clientService.findById(id);
    modelAndView.addObject("client", client);

    return modelAndView;
  }

  @GetMapping("/delete/{id}")
  public RedirectView delete(@PathVariable("id") Long id) {
    try {
      clientService.deleteById(id);
      log.info("Client deleted with id: {}", id);
    } catch (Exception e) {
      log.error("Error deleting client with id: {}", id, e);
    }

    return new RedirectView("/");
  }

}
