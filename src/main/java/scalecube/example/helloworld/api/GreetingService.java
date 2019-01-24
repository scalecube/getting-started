package scalecube.example.helloworld.api;
import io.scalecube.services.annotations.Service;
import io.scalecube.services.annotations.ServiceMethod;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("greeting")
public interface GreetingService {
  
  @ServiceMethod("once")
  Mono<String> sayHello(String name);
  
  @ServiceMethod("many")
  Flux<String> sayHellos(String name);

  @ServiceMethod("timer")
  Flux<String> timerHello(String name);
  
}
