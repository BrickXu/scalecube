package io.scalecube.examples;

import io.scalecube.cluster.Cluster;
import io.scalecube.cluster.ICluster;
import io.scalecube.transport.Message;

import rx.functions.Action1;

/**
 * Basic example for member gossiping between cluster members. to run the example Start ClusterNodeA and cluster
 * ClusterNodeB A listen on gossip B spread gossip
 * 
 * @author ronen hamias, Anton Kharenko
 *
 */
public class GossipExample {

  /**
   * Main method.
   */
  public static void main(String[] args) throws Exception  {
    // Start cluster nodes and subscribe on listening gossips
    ICluster alice = Cluster.joinAwait();
    alice.listenGossips().subscribe(gossip -> System.out.println("Alice heard: " + gossip.data()));

    ICluster bob = Cluster.joinAwait(alice.address());
    bob.listenGossips().subscribe(gossip -> System.out.println("Bob heard: " + gossip.data()));

    ICluster carol = Cluster.joinAwait(alice.address());
    carol.listenGossips().subscribe(gossip -> System.out.println("Carol heard: " + gossip.data()));

    ICluster dan = Cluster.joinAwait(alice.address());
    dan.listenGossips().subscribe(gossip -> System.out.println("Dan heard: " + gossip.data()));

    // Start cluster node Eve that joins cluster and spreads gossip
    ICluster eve = Cluster.joinAwait(alice.address());
    eve.spreadGossip(Message.fromData("Gossip from Eve"));

    // Avoid exit main thread immediately ]:->
    Thread.sleep(1000);
  }
}
