package BPI;

import com.vtence.molecule.Body;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.lib.BinaryBody;
import com.vtence.molecule.lib.TextBody;
import com.vtence.molecule.routing.DynamicRoutes;
import electre.ISBN;
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


            get("/imagette/:id").to((request, response) -> {
                Optional<InputStream> thumbnail = new RestClient().fetchthumbnail(extractISBN(request));
                sendResponse(
                    response, "image/jpeg", getBinaryBody(thumbnail)
                );
            });

            get("/couverture/:id").to((request, response) -> {
                Optional<InputStream> cover = new RestClient().fetchCover(extractISBN(request));
                sendResponse(
                    response, "image/jpeg", getBinaryBody(cover)
                );
            });

            get("/quatrieme/:id").to((request, response) -> {
                Optional<InputStream> backCover = new RestClient().fetchBackCover(extractISBN(request));
                sendResponse(
                    response, "text/xml", getTextBody(backCover)
                );
            });

            get("/tabledesmatieres/:id").to((request, response) -> {
                Optional<InputStream> tableOfContent = new RestClient().fetchTableOfContent(extractISBN(request));
                sendResponse(
                    response, "text/xml", getTextBody(tableOfContent)
                );
            });

        }});
    }

    private BinaryBody getBinaryBody(Optional<InputStream> image) throws IOException {
        try {
            return image.isPresent() ? new BinaryBody(IOUtils.toByteArray(image.get())) : new BinaryBody(new byte[0]);
        } finally {
            if(image.isPresent()) image.get().close();
        }
    }

    private TextBody getTextBody(Optional<InputStream> content) throws IOException {
        try {
            TextBody responseBody = new TextBody();
            return content.isPresent() ? responseBody.append(IOUtils.toString(content.get(), "latin1")) : responseBody;
        } finally {
            if(content.isPresent()) content.get().close();
        }
    }

    private ISBN extractISBN(Request request) {
        return new ISBN(request.parameter("id"));
    }

    private void sendResponse(Response response, String contentType, Body body) {
        response.contentType(contentType);
        response.done(body);
    }
}
