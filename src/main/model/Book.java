package model;

import model.exceptions.AlreadyReadException;
import model.exceptions.ReadStateException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// represents a book having a title, author, date of publish, category, read state, review
public class Book implements Writable {
    private String title;
    private String author;
    private String date;
    private String category;
    private String readState;
    private String review;


    /* EFFECTS: Constructs a new Book object;
     *          with readState either "Unread" or "Reading" or "Read"
     */
    public Book(String title, String author, String date, String category, String readState, String review) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.category = category;
        this.readState = readState;
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getReadState() {
        return readState;
    }

    public String getReview() {
        return review;
    }


    /*
     * MODIFIES: Book
     * EFFECTS:  adds review to Book
     */
    public void addReview(String userReview) {
        this.review = userReview;
    }


    // Hash Code and Equals override to compare books for testing
    // EFFECTS: return true if books being compared are the same, false otherwise
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, date, category, readState, review);
    }

    /* MODIFIES: Book
     * EFFECTS:  returns updated read state, bumped up as follows:
     *           not yet started -> currently reading
     *           currently reading -> completed
     *           throws AlreadyReadException if readState is "Read"
     *           throws ReadStateException if readState is not "Unread" or "Reading"
     */
    public void updateReadStatus() throws AlreadyReadException, ReadStateException {
        if (readState.equals("Unread")) {
            this.readState = "Reading";
        } else if (readState.equals("Reading")) {
            this.readState = "Read";
        } else if (readState.equals("Read")) {
            throw new AlreadyReadException();
        } else {
            throw new ReadStateException();
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("date", date);
        json.put("category", category);
        json.put("read state", readState);
        json.put("review", review);
        return json;
    }
}
