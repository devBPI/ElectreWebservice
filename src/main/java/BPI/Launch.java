package BPI;

import com.vtence.molecule.WebServer;
import electre.oauth.Credential;

import java.io.IOException;

public class Launch {

    public static void main(String[] args) throws IOException {
        ConnectorServer server = new ConnectorServer();
        WebServer webServer = WebServer.create();
        server.run(webServer);
        System.out.println("Access at " + webServer.uri());
    }
}
