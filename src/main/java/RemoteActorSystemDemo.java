import akka.actor.*;
import com.typesafe.config.ConfigFactory;

public class RemoteActorSystemDemo {

    static class SimpleActor extends AbstractActor {
        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                System.out.println(msg);
            }).build();
        }
    }

    public static void main(String[] args) {
        //从配置文件中修改akka到2552端口
        ActorSystem sys = ActorSystem.create("sys", ConfigFactory.load("remote.conf"));
        ActorRef ref = sys.actorOf(Props.create(SimpleActor.class), "simpleActor");
        //按照path注册到akka 2552端口
        ActorSelection actorSelection = sys.actorSelection("akka.tcp://sys@127.0.0.1:2552/user/simpleActor");
        //简单的本地测试
        actorSelection.tell("tttt", ActorRef.noSender());
    }
}
