import akka.actor.*;
import akka.remote.RemoteScope;
import com.typesafe.config.ConfigFactory;

public class RemoteCreateActorDemo {

    static class RmtCreateActor extends AbstractActor {
        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                System.out.println("remote msg====" + msg);
            }).build();
        }
    }

    public static void main(String[] args) {


        ActorSystem system = ActorSystem.create("sys", ConfigFactory.load("remoteactor.conf"));
        Address address = new Address("akka.tcp", "sys", "127.0.0.1", 2552);
        //将RmtCreateActor发布到远程127.0.0.1:2552上
        //ActorRef ref = system.actorOf(Props.create(RemoteActorSystemDemo.SimpleActor.class).withDeploy(new Deploy(new RemoteScope(address))), "simpleActor");
        ActorSelection actorSelection = system.actorSelection("akka.tcp://sys@127.0.0.1:2552/user/simpleActor");
        actorSelection.tell("hello rmt", ActorRef.noSender());
    }
}
