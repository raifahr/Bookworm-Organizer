package model;

import model.exceptions.AlreadyReadException;
import model.exceptions.ReadStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    private Book testBook1;
    private Book testBook2;
    private Book testBook3;

    @BeforeEach
    public void setup() {
        this.testBook1 = new Book("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Read", "Makes my head hurt... the metafiction is strong.");
        this.testBook2 = new Book("World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Read", "Makes my head hurt... the metafiction is strong.");
        this.testBook3 = new Book("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Read", "Makes my head hurt... the metafiction is strong.");
    }

    @Test
    public void testConstructor() {
        assertEquals("Sophie's World", testBook1.getTitle());
        assertEquals("Jostein Gaarder", testBook1.getAuthor());
        assertEquals("August 6, 1999", testBook1.getDate());
        assertEquals("Philosophy", testBook1.getCategory());
        assertEquals("Read", testBook1.getReadState());
        assertEquals("Makes my head hurt... the metafiction is strong.", testBook1.getReview());
    }

    @Test
    public void testAddReview() {
        this.testBook1 = new Book("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "completed", "");
        testBook1.addReview("Really confusing at times!");
        assertEquals("Really confusing at times!", testBook1.getReview());
    }

    @Test
    public void testUpdateReadStateToReadingFromUnreadNoExceptionExpected()  {
        this.testBook1 = new Book("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Unread",
                "");
        try {
            testBook1.updateReadStatus();
        } catch (AlreadyReadException e) {
            fail("No exception expected");
        } catch (ReadStateException e) {
            fail("No exception expected");
        }
        assertEquals("Reading", testBook1.getReadState());
    }

    @Test
    public void testUpdateReadStateToCompletedFromCurrentlyReadingNoExceptionExpected() {
        this.testBook1 = new Book("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Reading",
                "");
        try {
            testBook1.updateReadStatus();
        } catch (AlreadyReadException e) {
            fail("No exception expected");
        } catch (ReadStateException e) {
            fail("No exception expected");
        }
        assertEquals("Read", testBook1.getReadState());
    }

    @Test
    public void testUpdateReadStatusAlreadyReadExceptionExpected() {
        try {
            testBook1.updateReadStatus();
            fail("Exception expected");
        } catch (AlreadyReadException e) {
            // exception expected
        } catch (ReadStateException e) {
            fail("ReadStateException not expected");
        }
    }

    @Test
    public void testUpdateReadStatusReadStateExceptionExpected() {
        Book newBook = new Book("Sophie's World", "Jostein Gaarder", "August 6, 1999",
                "Philosophy", "Completed", "Makes my head hurt... the metafiction is strong.");
        try {
            newBook.updateReadStatus();
            fail("Exception expected");
        } catch (AlreadyReadException e) {
            fail("AlreadyReadException not expected");
        } catch (ReadStateException e) {
            // exception expected
        }
    }

    @Test
    public void testEqualsOnSameBook() {
        assertTrue(testBook1.equals(testBook1));
        assertTrue(testBook1.hashCode() == testBook1.hashCode());
    }
    @Test
    public void testEqualsTwoSameBooks() {
        assertTrue(testBook1.equals(testBook3));
        assertTrue(testBook1.hashCode() == testBook3.hashCode());
    }

    @Test
    public void testEqualsTwoDiffBook() {
        assertFalse(testBook1.equals(testBook2));
        assertFalse(testBook1.hashCode() == testBook2.hashCode());
    }

    @Test
    public void testEqualsBookWithNullObject() {
        assertFalse(testBook1.equals(null));
    }

}
