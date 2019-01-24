package scalecube.examples.main;
    
import io.scalecube.services.Microservices;
import io.scalecube.services.gateway.GatewayConfig;
import io.scalecube.services.gateway.rsocket.RSocketGateway;
import scalecube.example.helloworld.SimpleGreetingService;

/**
 * run ServiceGatewayMain
 * run GreetingServiceMain
 * 
 * 1. Open browser url: http://scalecube.io/api-sandbox/app/index.html
 * 2. In settings change url to ws://localhost:9090 and connect
 * 3. Send { "metadata": { "q": "/greeting/once" }, "data": "hello" }
 * 
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

  public static void main(String[] args) throws InterruptedException {

    Microservices node1 =
        Microservices.builder()
            .discovery(op->op.port(4800))
            .gateway(GatewayConfig.builder("rsocket", RSocketGateway.class).port(9090).build())
            .startAwait();

    System.out.println(node1.discovery().address()); 
    
    Thread.currentThread().join();
  
  }
}
