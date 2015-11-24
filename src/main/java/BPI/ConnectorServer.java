package BPI;

import com.vtence.molecule.Body;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.lib.BinaryBody;
import com.vtence.molecule.routing.DynamicRoutes;
import electre.RestClient;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.logging.Logger;

public class ConnectorServer {
    Logger log = Logger.getLogger(ConnectorServer.class.getName());

    public void run(WebServer server) throws IOException {
        server.start(new DynamicRoutes() {{

            map("/").to((request, response) -> response.done("Welcome!"));

            get("/imagette/:id").to((request, response) -> {
                Optional<InputStream> thumbnail = new RestClient().fetchthumbnail(extractISBN(request));
                sendResponse(response, "image/jpeg", getBinaryBody(thumbnail));
            });

            get("/couverture/:id").to((request, response) -> {
                Optional<InputStream> cover = new RestClient().fetchCover(extractISBN(request));
                sendResponse(response, "image/jpeg", getBinaryBody(cover));
            });

        }});
    }

    private BinaryBody getBinaryBody(Optional<InputStream> thumbnail) throws IOException {
        return thumbnail.isPresent() ? new BinaryBody(IOUtils.toByteArray(thumbnail.get())) : new BinaryBody(new byte[0]);
    }

    private String extractISBN(Request request) {
        return request.parameter("id");
    }

    private void sendResponse(Response response, String contentType, Body body) {
        response.contentType(contentType);
        response.done(body);
    }

    public static void main(String[] args) throws IOException {
        ConnectorServer server = new ConnectorServer();
        WebServer webServer = WebServer.create();
        server.run(webServer);
        System.out.println("Access at " + webServer.uri());
    }
}
