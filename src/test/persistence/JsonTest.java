package persistence;

import model.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

// CITATION: code obtained from JsonTest in JsonSerializationDemo
public class JsonTest {
    protected void checkBook(String title, String author, String date, String category, String readState,
                             String review, Book book) {
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(date, book.getDate());
        assertEquals(category, book.getCategory());
        assertEquals(readState, book.getReadState());
        assertEquals(review, book.getReview());
    }
}