package scalecube.examples.main;
    
import io.scalecube.services.Microservices;
import scalecube.example.services.helloworld.SimpleGreetingService;
import scalecube.example.services.helloworld.api.GreetingService;

/**
 *    ------------                  ------------
 *    -          -                  -          -
 *    - consumer -                  - service  -
 *    - (node1)  -                  - (node2)  -
 *    ------------     network      ------------
 *         |                             |
 *         |-------request-------------->|
 *         |<-----------response(1)------|
 *         |                             |                            
 */
public class RequestResponseMain {

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
