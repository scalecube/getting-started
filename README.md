# Getting started

1. define java service dsl using interface.

```java
@Service("greeting")
public interface GreetingService {
  
  @ServiceMethod("once")
  Mono<String> sayHello(String name);
  
  @ServiceMethod("many")
  Flux<String> sayHellos(String name);

  @ServiceMethod("timer")
  Flux<String> timerHello(String name);
  
}
```

implement the service dsl.

```java
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
```


