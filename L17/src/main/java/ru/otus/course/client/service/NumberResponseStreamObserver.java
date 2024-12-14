package ru.otus.course.client.service;

import io.grpc.stub.StreamObserver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.otus.course.NumberResponse;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class NumberResponseStreamObserver implements StreamObserver<NumberResponse> {

  private final AtomicInteger lastNumberFromServer = new AtomicInteger(0);

  @Getter
  private final CountDownLatch latch = new CountDownLatch(1);

  public int getAndResetLastNumberFromServer() {
    return lastNumberFromServer.getAndSet(0);
  }

  @Override
  public void onNext(NumberResponse numberResponse) {
    log.info("received from server {}", numberResponse.getNumber());
    lastNumberFromServer.set(numberResponse.getNumber());
  }

  @Override
  public void onError(Throwable throwable) {
    log.error(throwable.getMessage());
    latch.countDown();
  }

  @Override
  public void onCompleted() {
    log.info("server response has been processed");
    latch.countDown();
  }

}
