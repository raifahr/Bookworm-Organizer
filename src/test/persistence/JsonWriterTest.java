package persistence;

import model.Book;
import model.BookCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: code obtained from JsonWriterTest in JsonSerializationDemo
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            BookCollection bc = new BookCollection("My BookCollection");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            BookCollection bc = new BookCollection("My BookCollection");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBookCollection.json");
            writer.open();
            writer.write(bc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBookCollection.json");
            bc = reader.read();
            assertEquals(0, bc.collectionSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            BookCollection bc = new BookCollection("My BookCollection");
            bc.addBook("Sophie's World", "Jostein Gaarder", "1991", "Philosophy",
                    "Read", "Metafiction that played with my mind...");
            bc.addBook("Illuminae", "Amie Kaufman, Jay Kristoff", "October 20, 2015",
                    "Sci-Fi", "Read", "Thrilling Sci-Fi that leaves you wanting more!");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBookCollection.json");
            writer.open();
            writer.write(bc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBookCollection.json");
            bc = reader.read();
            List<Book> books = bc.getBooks();
            assertEquals(2, books.size());
            checkBook("Sophie's World", "Jostein Gaarder", "1991", "Philosophy",
            "Read", "Metafiction that played with my mind...", books.get(0));
            checkBook("Illuminae", "Amie Kaufman, Jay Kristoff", "October 20, 2015",
                    "Sci-Fi", "Read", "Thrilling Sci-Fi that leaves you wanting more!",
                    books.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}