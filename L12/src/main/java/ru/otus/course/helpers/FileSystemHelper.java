package ru.otus.course.helpers;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@UtilityClass
public final class FileSystemHelper {

  public String localFileNameOrResourceNameToFullPath(String fileOrResourceName) {
    var file = new File(fileOrResourceName);

    if (file.exists()) {
      return URLDecoder.decode(file.toURI().getPath(), StandardCharsets.UTF_8);
    }

    var classLoader = FileSystemHelper.class.getClassLoader();
    var resource = classLoader.getResource(fileOrResourceName);

    return Optional.ofNullable(resource)
      .map(URL::toExternalForm)
      .orElseThrow(() -> new RuntimeException(String.format("File \"%s\" not found", fileOrResourceName)));
  }

}
