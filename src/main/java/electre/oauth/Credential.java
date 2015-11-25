package electre.oauth;

public enum Credential {

    ELECTRE;

    public final String key;
    public final String secret;

    Credential() {
        key = System.getenv("ELECTRE_KEY");
        secret = System.getenv("ELECTRE_SECRET");
    }
}