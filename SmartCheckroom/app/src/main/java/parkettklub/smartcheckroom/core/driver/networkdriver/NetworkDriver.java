package parkettklub.smartcheckroom.core.driver.networkdriver;

import android.content.Context;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

import parkettklub.smartcheckroom.core.Core;

public class NetworkDriver implements NetworkDriverI {

    private static final int UDP_PORT = 9000;
    private static final int TIMEOUT = 5000;
    private static final int TCP_PORT = 5000;
    private static NetworkDriver networkDriver;

    private NetworkServer server;
    private NetworkClient client;

    public static NetworkDriver getInstance(){
        if (networkDriver == null){
            networkDriver = new NetworkDriver();
        }
        return networkDriver;
    }

    private NetworkDriver(){

    }

    @Override
    public boolean findServer(final Context aContext) throws IOException {

        client = new NetworkClient(UDP_PORT, TIMEOUT, TCP_PORT);
        if(client.findServer(UDP_PORT, TIMEOUT, TCP_PORT)) {
            client.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if (object instanceof Response) {
                        client.setReceived(true);
                        client.setResponse((Response) object);
                    }
                    if (object instanceof Request) {
                        Core.freshItem(((Request) object).getItem());
                    }
                }
            });

            return true;
        }
        else
        {
            client = null;
            server = new NetworkServer(TCP_PORT, UDP_PORT);
            server.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if (object instanceof Request) {
                        Request request = (Request) object;
                        Response response = new Response();
                        response.setOK(Core.freshItem(request.getItem()));
                        connection.sendTCP(response);
                        server.sendToAllTCP(request);
                    }
                }
            });

            return false;
        }

    }

    @Override
    public void runClient() throws IOException {
        client = new NetworkClient(UDP_PORT, TIMEOUT, TCP_PORT);
        if(client.findServer(UDP_PORT, TIMEOUT, TCP_PORT)) {
            client.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if (object instanceof Response) {
                        client.setReceived(true);
                        client.setResponse((Response) object);
                    }
                    if (object instanceof Request) {
                        Core.freshItem(((Request) object).getItem());
                    }
                }
            });
        }
        else
        {
            client = null;
            runServer();
        }
    }

    @Override
    public boolean sendData(Request request) {
        if(client == null){
            server.sendToAllTCP(request);
            return true;
        }
        return client.sendData(request).isOK();
    }

    @Override
    public void runServer() throws IOException {
        server = new NetworkServer(TCP_PORT, UDP_PORT);
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Request) {
                    Request request = (Request) object;
                    Response response = new Response();
                    response.setOK(Core.freshItem(request.getItem()));
                    connection.sendTCP(response);
                    server.sendToAllTCP(request);
                }
            }
        });
    }
}
