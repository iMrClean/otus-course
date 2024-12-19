package ru.otus.course.config;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.annotation.NonNull;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class ApplicationConfiguration {

  private static final int THREAD_POOL_SIZE = 2;

  @Bean(destroyMethod = "close")
  public NioEventLoopGroup eventLoopGroup() {
    return new NioEventLoopGroup(THREAD_POOL_SIZE, new ThreadFactory() {

      private final AtomicLong threadIdGenerator = new AtomicLong(0);

      @Override
      public Thread newThread(@NonNull Runnable r) {
        return new Thread(r, "server-thread-" + threadIdGenerator.incrementAndGet());
      }
    });
  }

  @Bean
  public ReactiveWebServerFactory reactiveWebServerFactory(NioEventLoopGroup eventLoopGroup) {
    var factory = new NettyReactiveWebServerFactory();

    factory.addServerCustomizers(builder -> builder.runOn(eventLoopGroup));

    return factory;
  }

  @Bean
  public Scheduler workerPool() {
    return Schedulers.newParallel("processor-thread", THREAD_POOL_SIZE);
  }

}
