package scalecube.example.cluster;

import io.scalecube.cluster.Cluster;
import io.scalecube.cluster.ClusterConfig;
import io.scalecube.transport.Address;
import io.scalecube.transport.Message;

/**
 * Demo 3 cluster members joining.
 *
 * <p>For convenience we will alias them with names: (Linus Torvalds, Bill Gates,Steve Jobs).
 *
 * <p>Linus Torvalds will act as seed. Bill Gates and Steve Jobs will join the cluster as peers.
 * 
 * <p>Bill sends steve a message.
 * <p>steve sends bill message
 */
public class ClusterTalkMain {

  static Address ofSeed = Address.create("localhost", 4800);

  public static void main(String[] args) throws InterruptedException {
 
    joinAsSeed("Linus Torvalds", 4800);
    Cluster bill = joinAsFollower(ofSeed, "Bill Gates");
    Cluster steve = joinAsFollower(ofSeed, "Steve Jobs");
    
    steve.listen().subscribe(System.out::println);
    bill.listen().subscribe(System.out::println);
    
    
    bill.send(steve.member(), Message.builder().sender(steve.address()).data("I am bill").build())
        .subscribe();

    steve.send(bill.member(), Message.builder().sender(steve.address()).data("I am steve").build())
    .subscribe();
    
    Thread.currentThread().join();
  }

  /**
   * join the cluster as seed member.
   *
   * <p>please note that what makes a seed a "seed" is the fact its following no one. 
   *     for example:
   *        https://github.com/torvalds
   *
   * @return a first member of a cluster.
   */
  private static Cluster joinAsSeed(String name, int port) {
    return Cluster.joinAwait(ClusterConfig.builder().port(port).addMetadata("name", name).build());
  }

  /**
   * join the cluster as follower member.
   *
   * <p>please note that what makes a follower a follower is the fact its following at least one
   * member (any member).
   *
   * @return a first member of a cluster.
   */
  private static Cluster joinAsFollower(Address seed, String name) {
    return Cluster.joinAwait(
        ClusterConfig.builder().seedMembers(seed).addMetadata("name", name).build());
  }
}
