package scalecube.examples.main;
    
import io.scalecube.services.Microservices;
import io.scalecube.services.transport.api.Address;
import scalecube.example.helloworld.SimpleGreetingService;

/**
 * Run this GreetingServiceMain and then run ServiceGatewayMain.
 */
public class GreetingServiceMain {

  public static void main(String[] args) throws InterruptedException {

    Microservices ms = Microservices.builder()
        .discovery(options -> options.seeds(Address.create("127.0.1.1",4800)))
        .services(new SimpleGreetingService())
        .startAwait();
    
    System.out.println(  ms.discovery().endpoint() );;
    
    Thread.currentThread().join();
  
  }
}
