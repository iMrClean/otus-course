package ru.otus.course.client;

import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.otus.course.NumberRequest;
import ru.otus.course.RemoteServiceGrpc;
import ru.otus.course.client.service.NumberResponseStreamObserver;
import ru.otus.course.server.GRPCServer;

@Slf4j
public class GRPCClient {

    private static final int SERVER_PORT = GRPCServer.SERVER_PORT;
    private static final String SERVER_HOST = GRPCServer.SERVER_HOST;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT).usePlaintext().build();

        var stub = RemoteServiceGrpc.newStub(channel);
        var request = NumberRequest.newBuilder().setFirstValue(1).setLastValue(30).build();
        var numberResponseStreamObserver = new NumberResponseStreamObserver();

        stub.getNumbers(request, numberResponseStreamObserver);
        process(numberResponseStreamObserver);
    }

    private static void process(NumberResponseStreamObserver responseStream) throws InterruptedException {
        int currentValue = 0;
        for (int i = 0; i < 50; i++) {
            currentValue = currentValue + responseStream.getAndResetLastNumberFromServer() + 1;
            log.info("currentValue = {}", currentValue);
        }
        responseStream.getLatch().await();
    }

}
