package electre;

public class ISBN {

    private final String isbn;

    public ISBN(String id) {
        isbn = sanitize(id);
    }

    public String sanitize(String isbn) {
        return isbn.replaceAll("[- ]+", "");
    }

    public String toString() {
        return (isIsbn10()) ? toIsbn13().toString() : isbn;
    }

    public boolean isIsbn10() {
        return isbn.length() == 10;
    }

    public ISBN toIsbn13() {
        String ean = "978" + isbn.substring(0, 9);

        int d;
        int sum = 0;
        for (int i = 0; i < ean.length(); i++) {
            d = ((i % 2 == 0) ? 1 : 3);
            sum += ((((int) ean.charAt(i)) - 48) * d);
        }
        sum = 10 - (sum % 10);
        ean += sum;

        return new ISBN(ean.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ISBN)) return false;

        return (this.toString().equals(obj.toString()));
    }

    @Override
    public int hashCode() {
        if(isIsbn10()) return toIsbn13().hashCode();
        return 31 + (isbn == null ? 0 : isbn.hashCode());
    }
}
