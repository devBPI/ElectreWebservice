package electre;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import electre.oauth.OAuthRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;

public class RestClient {

    Logger log = Logger.getLogger(RestClient.class.getName());

    private OAuthRequest requestExecutor;

    String electreBaseUrl = "http://restapi.electre.com/Service/";
    String thumbnailPath = "imagette/%s.jpg";
    String bookCoverPath = "couverture/%s.jpg";

    public RestClient() {
        requestExecutor = new OAuthRequest();
    }

    public Optional<InputStream> fetchthumbnail(String isbn) {
        GenericUrl resourceUri = buildUri(thumbnailPath, new ISBN(isbn).toString());
        log.info(resourceUri.toString());
        return execute(resourceUri);
    }

    public Optional<InputStream> fetchCover(String isbn) {
        GenericUrl resourceUri = buildUri(bookCoverPath, new ISBN(isbn).toString());
        log.info(resourceUri.toString());
        return execute(resourceUri);
    }

    public Optional<InputStream> execute(GenericUrl resourceUri) {
        try {
            HttpResponse resource = requestExecutor.execute(resourceUri);
            return Optional.of(resource.getContent());
        }  catch (Exception exception) {
            log.warning(ExceptionUtils.getStackTrace(exception));
            return Optional.empty();
        }
    }

    public GenericUrl buildUri(String path, String id) {
        String resourcePath = path.format(path, id);
        try {
            return new GenericUrl(electreBaseUrl + resourcePath);
        } catch (IllegalArgumentException exception) {
            log.warning(ExceptionUtils.getStackTrace(exception));
            throw exception;
        }
    }

}
