package electre;

public class ISBN {

    private String isbn;

    public ISBN(String id) {
        isbn = sanitize(id);
    }

    public String sanitize(String isbn) {
        return isbn.replaceAll("[- ]+", "");
    }

    public String toString() {
        return isbn;
    }

}
