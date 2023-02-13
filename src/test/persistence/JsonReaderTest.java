package persistence;


import model.Book;
import model.BookCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: Code obtained from JsonReaderTest in JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BookCollection bc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBookCollection.json");
        try {
            BookCollection bc = reader.read();
            assertEquals("My BookCollection", bc.getName());
            assertEquals(0, bc.collectionSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBookCollection.json");
        try {
            BookCollection bc = reader.read();
            assertEquals("My BookCollection", bc.getName());
            List<Book> books = bc.getBooks();
            assertEquals(2, books.size());
            checkBook("Sophie's World", "Jostein Gaarder", "1991", "Philosophy",
                    "Read", "Metafiction that played with my mind...", books.get(0));
            checkBook("Illuminae", "Amie Kaufman, Jay Kristoff", "October 20, 2015",
                    "Sci-Fi", "Read", "Thrilling Sci-Fi that leaves you wanting more!",
                    books.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}