package electre;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import electre.oauth.OAuthRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.InputStream;
import java.util.Optional;
import java.util.logging.Logger;

public class RestClient {

    Logger log = Logger.getLogger(RestClient.class.getName());

    private OAuthRequest requestExecutor;

    String electreBaseUrl = "http://restapi.electre.com/Service/";
    String thumbnailPath = "imagette/%s.jpg";
    String bookCoverPath = "couverture/%s.jpg";
    String backCoverPath = "quatrieme/%s.xml";
    String tableOfContentPath = "tdm/%s.xml";

    public RestClient() {
        requestExecutor = new OAuthRequest();
    }

    public Optional<InputStream> fetchthumbnail(ISBN isbn) {
        return execute(
            buildUri(thumbnailPath, isbn
        ));
    }

    public Optional<InputStream> fetchCover(ISBN isbn) {
        return execute(
            buildUri(bookCoverPath, isbn
        ));
    }

    public Optional<InputStream> fetchBackCover(ISBN isbn) {
        return execute(
            buildUri(backCoverPath, isbn
        ));
    }

    public Optional<InputStream> fetchTableOfContent(ISBN isbn) {
        return execute(
            buildUri(tableOfContentPath, isbn
        ));
    }

    public Optional<InputStream> execute(GenericUrl resourceUri) {
        log.info(resourceUri.toString());
        try {
            HttpResponse resource = requestExecutor.execute(resourceUri);
            return Optional.of(resource.getContent());
        }  catch (Exception exception) {
            log.warning(ExceptionUtils.getStackTrace(exception));
            return Optional.empty();
        }
    }

    public GenericUrl buildUri(String path, ISBN isbn) {
        String resourcePath = path.format(path, isbn.toString());
        try {
            return new GenericUrl(electreBaseUrl + resourcePath);
        } catch (IllegalArgumentException exception) {
            log.warning(ExceptionUtils.getStackTrace(exception));
            throw exception;
        }
    }

}
