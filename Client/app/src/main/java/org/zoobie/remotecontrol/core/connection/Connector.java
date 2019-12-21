package org.zoobie.remotecontrol.core.connection;


import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Connector class storing all possible connections
 * Performs sending actions
 */
public class Connector {
    private Server server;
    private UdpClient udpClient;
    private BluetoothClient bluetoothClient;
    private boolean prioritiseInetConnection;

    public Connector(Server server) throws ConnectionException {
        if(!server.isValid()) throw new ConnectionException("Server is not valid");
        this.server = server;
        udpClient = new UdpClient(server);
        prioritiseInetConnection = true;
        bluetoothClient = new BluetoothClient();
    }

    public int checkConnection() throws ExecutionException, InterruptedException {
        int connections = 0;
        if(checkUdpConnection()) connections++;
        if(checkBluetoothConnection()) connections++;
        return connections;
    }

    public boolean checkUdpConnection() throws ExecutionException, InterruptedException {
        return udpClient.checkConnection();
    }


    public boolean checkBluetoothConnection() {
        return bluetoothClient.checkConnection();
    }

    public void sendUdp(byte[] data){
        if(udpClient == null)
            udpClient = new UdpClient(server);
        udpClient.send(data);
    }

    public String getServerName() throws ExecutionException, InterruptedException {
        return udpClient.askServerName();
    }

    public void setInetOrBluetooth(boolean inetOrBluetooth){
        prioritiseInetConnection = inetOrBluetooth;
    }

    public void send(byte... data){
        if(prioritiseInetConnection) sendUdp(data);
        else sendBluetooth(data);
    }

    private void sendBluetooth(byte[] data) {
    }
}

