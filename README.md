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

2. implement the service dsl.

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

3. running the service.

- RequestResponseMain 
- RequestStreamMain
- GreetingServiceMain and ServiceGatewayMain

4. Api-Sandbox:
4.1. open in browser: http://scalecube.io/api-sandbox/app/index.html

4.2. click settings and choose Rsocket and set url ws://localhost:9090

![image](https://user-images.githubusercontent.com/1706296/51670553-929d8980-1fcf-11e9-8c25-581381120844.png)

4.3. input json request and click send:

select:

![image](https://user-images.githubusercontent.com/1706296/51670893-5dde0200-1fd0-11e9-97f5-da18392f805e.png)

send:
![image](https://user-images.githubusercontent.com/1706296/51670736-f4f68a00-1fcf-11e9-9155-9573d86f069c.png)





