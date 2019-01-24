package scalecube.examples.main;
    
import io.scalecube.services.Microservices;
import io.scalecube.services.gateway.GatewayConfig;
import io.scalecube.services.gateway.rsocket.RSocketGateway;
import scalecube.example.helloworld.SimpleGreetingService;

/**
 *    ------------       ------------          ------------
 *    -          -       -          -          -          -
 *    - consumer -       - Gateway  -          - service  -
 *    - (browser)-       - (node1)  -          - (node2)  -
 *    ------------       ------------ network  ------------
 *         |----request------>|                     |
 *         |                  |----request--------->|
 *         |                  |<------response(1)---|
 *         |<---response------|                     |                            
 */
public class ServiceGatewayMain {

  private static Microservices setup() {

    Microservices node1 =
        Microservices.builder()
            .gateway(GatewayConfig.builder("rsocket", RSocketGateway.class).port(9090).build())
            .startAwait();

    Microservices node2 =
        Microservices.builder()
            .discovery(options -> options.seeds(node1.discovery().address()))
            .services(new SimpleGreetingService())
            .startAwait();

    return node1;
  }

  public static void main(String[] args) throws InterruptedException {

    setup();
    
    //1. open browser url: http://scalecube.io/api-sandbox/app/index.html
    //2. { "metadata": { "q": "/greeting/once" }, "data": "hello" }
    Thread.currentThread().join();
  
  }
}
