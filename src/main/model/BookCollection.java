package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// represents an empty collection of books
public class BookCollection implements Writable {
    private String name;
    private List<Book> books;

    // EFFECTS: constructs empty book collection
    public BookCollection(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }


    public String getName() {
        return name;
    }


    // EFFECTS: returns size of book collection
    public int collectionSize() {
        return books.size();
    }



    // EFFECTS: finds given title in collection
    public Book findBook(String searchTitle) {
        for (Book book : books) {
            if (searchTitle.equals(book.getTitle())) {
                return book;
            }
        }
        return null;
    }


    // EFFECTS: return true if given title is in collection
    public boolean isBookInCollection(String searchTitle) {
        for (Book book : books) {
            if (searchTitle.equals(book.getTitle())) {
                return true;
            }
        }
        return false;
    }


    /* MODIFIES: BookCollection
     * EFFECTS: adds a new book to the book collection
     */
    public void addBook(String title, String author, String date, String category, String readState,
                        String review) {
        Book book = new Book(title, author, date, category, readState, review);
        this.books.add(book);
    }


    /* REQUIRES: BookCollection >= 1
     * MODIFIES: BookCollection
     * EFFECTS:  removes a book from the collection
     * This code is attributed to a Stack Overflow thread:
     * https://stackoverflow.com/questions/1921104/loop-on-list-with-remove
     */
    public void removeBook(String title) {
        for (Iterator<Book> iterator = books.iterator(); iterator.hasNext(); ) {
            Book book = iterator.next();
            if (title.equals(book.getTitle())) {
                iterator.remove();
            }
        }
    }

    // CITATION: code obtained from WorkRoom in JsonSerializationDemo
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    // CITATION: code obtained from WorkRoom in JsonSerialization Demo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("books", booksToJson());
        return json;
    }


    // CITATION: code obtained from WorkRoom in JsonSerialization Demo
    // EFFECTS: returns things in this book collection as a JSON array
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b : books) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }
}