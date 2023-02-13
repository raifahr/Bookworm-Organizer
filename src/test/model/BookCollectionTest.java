package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class BookCollectionTest {
    private BookCollection myBookCollection;

    @BeforeEach
    public void setup() {
        this.myBookCollection = new BookCollection("Raifah's BookCollection");
    }

    @Test
    public void testAddBookNoBooksAdded() {
        assertEquals("Raifah's BookCollection", myBookCollection.getName());
        assertEquals(0, myBookCollection.collectionSize());
    }

    @Test
    public void testAddBookAddOneBook() {
        myBookCollection.addBook("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Read", "Makes my head hurt... the metafiction is strong.");
        assertEquals(1, myBookCollection.collectionSize());
    }

   @Test
    public void testRemoveBookOneBookRemoved() {
        myBookCollection.addBook("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Read", "Makes my head hurt... the metafiction is strong.");
        myBookCollection.removeBook("Sophie's World");
        assertEquals(0, myBookCollection.collectionSize());
    }

    @Test
    public void testRemoveBookNoBookRemoved() {
        myBookCollection.addBook("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Read", "Makes my head hurt... the metafiction is strong.");
        myBookCollection.removeBook("CPSC 210");
        assertEquals(1, myBookCollection.collectionSize());
    }

    @Test
    public void testCollectionSizeEmptyCollection() {
        assertEquals(0, myBookCollection.collectionSize());
    }

    @Test
    public void testCollectionSizeNonEmptyCollection() {
        myBookCollection.addBook("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Read", "Makes my head hurt... the metafiction is strong.");
        assertEquals(1, myBookCollection.collectionSize());
    }

    @Test
    public void testisBookInCollectionBookPresentInCollection() {
        myBookCollection.addBook("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Read", "Makes my head hurt... the metafiction is strong.");
        assertTrue(myBookCollection.isBookInCollection("Sophie's World"));

    }

    @Test
    public void testisBookInCollectionBookIsNotPresentInCollection() {
        myBookCollection.addBook("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Read", "Makes my head hurt... the metafiction is strong.");
        assertFalse(myBookCollection.isBookInCollection("Illuminae"));

    }

    @Test
    public void testFindBookBookIsFound() {
        Book testbook = new Book("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Read", "Makes my head hurt... the metafiction is strong.");
        myBookCollection.addBook("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Read", "Makes my head hurt... the metafiction is strong.");
        assertTrue(testbook.equals(myBookCollection.findBook("Sophie's World")));
    }

    @Test
    public void testFindBookBookNotFound() {
        Book testbook = new Book("Illuminae", "Amie Kaufman, Jay Kristoff", "October 20, 2015",
                "Sci-Fi", "Reading", "Thrilling Sci-Fi that leaves you wanting more!");
        assertFalse(testbook.equals(myBookCollection.findBook("Sophie's World")));

    }

}
