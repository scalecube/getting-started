package scalecube.examples.main;

import io.scalecube.services.Microservices;
import io.scalecube.services.gateway.GatewayConfig;
import io.scalecube.services.gateway.rsocket.RSocketGateway;
import scalecube.example.helloworld.SimpleGreetingService;
import scalecube.example.helloworld.api.GreetingService;

/**
 * 
 *    ------------                  ------------
 *    -          -                  -          -
 *    - consumer -     network      - service  -
 *    - (node1)  -                  - (node2)  -
 *    ------------                  ------------
 *         |                             |
 *         |-------request-------------->|
 *         |                             |
 *         |<-----------response(1)------|
 *         |<-----------response(2)------|
 *         |<-----------response(3)------|
 *         |                             |   
 */
public class RequestStreamMain {

  private static Microservices setup() {
    
    Microservices node1 = Microservices.builder()
        .startAwait();
    
    Microservices node2 =Microservices.builder()
            .discovery(options -> options.seeds(node1.discovery().address()))
            .services(new SimpleGreetingService())
            .startAwait();
    
    return node1;
  }
  
  public static void main(String[] args) throws InterruptedException {

    Microservices ms = setup();
  
    GreetingService proxy = ms.call().create().api(GreetingService.class);

    proxy
        .sayHellos("ronen")
        .subscribe(
            response -> {
              System.out.println(response);
            });

    Thread.currentThread().join();
  
  }
}
