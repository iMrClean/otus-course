package ru.otus.course.server.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.otus.course.NumberRequest;
import ru.otus.course.NumberResponse;
import ru.otus.course.RemoteServiceGrpc;

import java.util.stream.IntStream;

@Slf4j
public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {

  @Override
  public void getNumbers(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {
    int firstValue = request.getFirstValue();
    int lastValue = request.getLastValue();

    IntStream.rangeClosed(firstValue + 1, lastValue)
      .forEach(i -> {
        log.info("Generate the number: {}", i);
        var response = NumberResponse.newBuilder().setNumber(i).build();

        responseObserver.onNext(response);
        sleep();
      });

    responseObserver.onCompleted();
  }

  private void sleep() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      log.error(e.getMessage());
    }
  }

}
