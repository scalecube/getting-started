package scalecube.example.cluster;

import io.scalecube.cluster.Cluster;
import io.scalecube.cluster.ClusterConfig;
import io.scalecube.transport.Address;

/**
 * Demo 3 cluster members joining.
 *
 * <p>For convenience we will alias them with names: (Linus Torvalds, Bill Gates,Steve Jobs).
 *
 * <p>Linus Torvalds will act as seed. Bill Gates and Steve Jobs will join the cluster as peers.
 */
public class ClusterMemberMain {

  static Address ofSeed = Address.create("localhost", 4800);

  public static void main(String[] args) throws InterruptedException {
 
    print(
        joinAsSeed("Linus Torvalds", 4800)
        );

    print(
        joinAsFollower(ofSeed, "Bill Gates")
        );
    
    print(
        joinAsFollower(ofSeed, "Steve Jobs")
        );

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

  /**
   * just printing to sysout some info of a node.
   * 
   * @param node in subject.
   */
  private static void print(Cluster node) {
    System.out.println("i am " + node.metadata().get("name") + " id: " + node.member().id());
    System.out.println("⚛ My address:  " + node.member().address());
    System.out.println("⚛ My metadata: " + node.metadata());

    if (!node.otherMembers().isEmpty()) {
      System.out.println("⚛ My peers: ");

      node.otherMembers()
          .stream()
          .forEach(
              m -> {
                String s = node.metadata(m).get("name");
                System.out.println("  ⚛ " + s + " - " + m.id());
              });

    } else {
      System.out.println("i am all alone. ");
    }
    System.out.println("--------------------");
  }
}
