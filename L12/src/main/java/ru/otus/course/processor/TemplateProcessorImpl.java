package ru.otus.course.processor;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TemplateProcessorImpl implements TemplateProcessor {

  private static final String DEFAULT_TEMPLATES_RESOURCES = "/templates/";

  private final Configuration configuration;

  public TemplateProcessorImpl() {
    configuration = new Configuration(Configuration.VERSION_2_3_30);
    configuration.setClassForTemplateLoading(this.getClass(), DEFAULT_TEMPLATES_RESOURCES);
    configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
  }

  @Override
  public String getPage(String filename, Map<String, Object> data) throws IOException {
    try (var stream = new StringWriter()) {
      var template = configuration.getTemplate(filename);

      template.process(data, stream);

      return stream.toString();
    } catch (TemplateException e) {
      throw new IOException(e);
    }
  }

}
