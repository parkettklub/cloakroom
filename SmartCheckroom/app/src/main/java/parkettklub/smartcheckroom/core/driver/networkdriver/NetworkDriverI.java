package parkettklub.smartcheckroom.core.driver.networkdriver;

import android.content.Context;

import java.io.IOException;

public interface NetworkDriverI {

    public boolean findServer(Context aContext) throws IOException;
    public void runClient() throws IOException;
    public void runServer() throws IOException;
    public boolean sendData(Request request);

    public String whoAmI();

    public void close();
}
