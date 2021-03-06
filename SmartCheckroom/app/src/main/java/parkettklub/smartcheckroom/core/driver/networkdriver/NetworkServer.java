package parkettklub.smartcheckroom.core.driver.networkdriver;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.Date;
import java.sql.Time;

import parkettklub.smartcheckroom.core.Item;
import parkettklub.smartcheckroom.core.Transaction;

public class NetworkServer extends Server{
    public NetworkServer(int tcpPort, int udpPort) throws IOException {
        Kryo kryo = getKryo();
        kryo.register(Request.class);
        kryo.register(Response.class);
        kryo.register(Item.class);
        kryo.register(Transaction.class);
        kryo.register(Date.class);
        kryo.register(Time.class);
        start();
        bind(tcpPort, udpPort);
    }
}
