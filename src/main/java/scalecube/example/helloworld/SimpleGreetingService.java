package scalecube.example.helloworld;

import java.time.Duration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import scalecube.example.helloworld.api.GreetingService;

public class SimpleGreetingService implements GreetingService {

  @Override
  public Mono<String> sayHello(String name) {
    return Mono.just("greeting to: "+ name);
  }

  @Override
  public Flux<String> sayHellos(String name) {
    return Flux.range(0, 3).map(i -> "greeting to: " + name + "-" + i);
  }

  @Override
  public Flux<String> timerHello(String name) {
    return Flux.interval(Duration.ofSeconds(1)).map(i -> "greeting to: " + name + "-" + i);
  }
  
}
