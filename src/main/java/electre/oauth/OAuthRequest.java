package electre.oauth;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.util.logging.Logger;

public class OAuthRequest {

    Logger log = Logger.getLogger(OAuthRequest.class.getName());

    private String consumerKey = Credential.ELECTRE.key;
    private String secret = Credential.ELECTRE.secret;

    private OAuthParameters createParameters() {
        OAuthHmacSigner signer = new OAuthHmacSigner();
        signer.clientSharedSecret = secret;

        OAuthParameters result = new OAuthParameters();
        result.signer = signer;
        result.consumerKey = consumerKey;
        result.version = "1.0";

        return result;
    }

    private void sign(HttpRequest request) throws IOException{
        createParameters().intercept(request);
    }

    public HttpResponse execute(GenericUrl resource) throws IOException {
        HttpRequest request = new NetHttpTransport().createRequestFactory().buildGetRequest(resource);
        sign(request);
        return request.execute();
    }
}
