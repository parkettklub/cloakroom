package parkettklub.smartcheckroom.core.driver.networkdriver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

import parkettklub.smartcheckroom.core.Core;

public class NetworkDriver implements NetworkDriverI {

    private static final int UDP_PORT = 9883;
    private static final int TIMEOUT = 5000;
    private static final int TCP_PORT = 5788;
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
    public void runClient() throws IOException {
        client = new NetworkClient(UDP_PORT, TIMEOUT, TCP_PORT);
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Response) {
                    client.setReceived(true);
                    client.setResponse((Response) object);
                }
                if (object instanceof Request) {
                    Core.freshItem(((Request)object).getItem());
                }
            }
        });
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
