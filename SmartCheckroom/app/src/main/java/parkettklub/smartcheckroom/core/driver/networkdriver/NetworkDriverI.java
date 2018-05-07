package parkettklub.smartcheckroom.core.driver.networkdriver;

import java.io.IOException;

public interface NetworkDriverI {
    public void runClient() throws IOException;
    public void runServer() throws IOException;
    public boolean sendData(Request request);
}
