package org.zoobie.remotecontrol.core.actions;

import org.zoobie.remotecontrol.server.Server;

import java.io.IOException;
import java.net.DatagramPacket;

public class ConnectionAction implements Action {
    private final byte[] command;
    private final Server server;
    private final DatagramPacket senderPacket;
    public ConnectionAction(Server server, DatagramPacket senderPacket, byte... command){
        this.senderPacket = senderPacket;
        this.server = server;
        this.command = command;
    }


    @Override
    public void performAction() {
        switch(command[1]){
            case Actions.CONNECTION_CHECK_ACTION:
                reply((byte)1);
                break;
            default:
                break;
        }
    }

    private void reply(byte... reply) {
        try {
            System.out.println("Sending confirmation");
            server.reply(senderPacket, reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}