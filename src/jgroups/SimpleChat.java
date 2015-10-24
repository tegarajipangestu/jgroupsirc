package jgroups;

/**
 * Created by tegar on 24/10/15.
 */

import org.jgroups.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SimpleChat extends ReceiverAdapter {
    JChannel channel;
    String user_name=System.getProperty("user.name", "n/a");


    public static void main(String[] args) throws Exception {
        new SimpleChat().start();
    }

    private void start() throws Exception {
        channel=new JChannel();
        channel.setReceiver(this);
        channel.connect("ChatCluster1");
        this.eventLoop();
        channel.close();
    }

    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": " + msg.getObject());
    }

    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        while(true) try {
            System.out.print("> ");
            System.out.flush();
            String line = in.readLine().toLowerCase();
            if (line.startsWith("quit") || line.startsWith("exit"))
                break;
            line = "[" + user_name + "] " + line;
            Message msg = new Message(null, null, line);
            channel.send(msg);
        } catch (Exception e) {
        }
    }


}
