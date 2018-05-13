package parkettklub.smartcheckroom.core.driver.networkdriver;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.sql.Time;

import parkettklub.smartcheckroom.core.Item;
import parkettklub.smartcheckroom.core.Transaction;

public class NetworkClient extends Client {
    private boolean received;
    private Response response;

    public NetworkClient(int udpPort, int timeout, int tcpPort){
        Kryo kryo = getKryo();
        kryo.register(Request.class);
        kryo.register(Response.class);
        kryo.register(Item.class);
        kryo.register(Transaction.class);
        kryo.register(Date.class);
        kryo.register(Time.class);
        start();
        /*
        InetAddress address = discoverHost(udpPort, timeout);
        connect(timeout, address.getHostAddress(), tcpPort, udpPort);
        */
    }

    public boolean findServer(int udpPort, int timeout, int tcpPort) throws IOException {
        InetAddress address = discoverHost(udpPort, timeout);
        if(null != address) {
            connect(timeout, address.getHostAddress(), tcpPort, udpPort);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public boolean isReceived() {
        return received;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


    public Response sendData(Request request){
        setReceived(false);
        setResponse(null);
        sendTCP(request);
        waitForResponse(55);
        return getResponse();
    }

    public void waitForResponse(int maxSec) {
        int i = 0;
        while (!isReceived()) {
            try {
                Thread.sleep(100);
                i++;
                if (i * 10 > maxSec) {
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
