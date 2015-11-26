package electre;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ISBNTest {

    private ISBN isbn10;
    private ISBN isbn13;

    @Before
    public void setUp() throws Exception {
        isbn10 = new ISBN("2800165510");
        isbn13 = new ISBN("978-2800165516");
    }

    @Test
    public void testSanitize() throws Exception {
        assertThat(isbn13.toString()).isEqualTo("9782800165516");
    }

    @Test
    public void testIsIsbn10() throws Exception {
        assertThat(isbn13.isIsbn10()).isFalse();
        assertThat(isbn10.isIsbn10()).isTrue();
    }

    @Test
    public void testItTransformsIsbn10ToIsbn13() throws Exception {
        assertThat(isbn10.toIsbn13().toString()).isEqualTo("9782800165516");
    }

    @Test
    public void testToStringReturnAlwaysIsbn13() throws Exception {
        assertThat(isbn10.toString()).isEqualTo("9782800165516");
    }

    @Test
    public void testEquals() throws Exception {
        assertThat(isbn10.equals(isbn10)).isTrue();
        assertThat(isbn10.equals(isbn13)).isTrue();
        assertThat(isbn13.equals(isbn10)).isTrue();
    }

    @Test
    public void testHashCode() throws Exception {
        assertThat(isbn10.hashCode()).isEqualTo(901860106);
        assertThat(isbn10.hashCode()).isEqualTo(isbn13.hashCode());
    }
}