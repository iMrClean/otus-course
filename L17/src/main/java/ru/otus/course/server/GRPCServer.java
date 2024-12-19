package ru.otus.course.server;

import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.otus.course.server.service.RemoteServiceImpl;

import java.io.IOException;

@Slf4j
public class GRPCServer {

  public static final int SERVER_PORT = 8080;
  public static final String SERVER_HOST = "localhost";

  public static void main(String[] args) throws IOException, InterruptedException {
    var remoteService = new RemoteServiceImpl();

    var server = ServerBuilder.forPort(SERVER_PORT).addService(remoteService).build();
    server.start();
    log.info("server waiting for client connections...");
    server.awaitTermination();
  }

}
