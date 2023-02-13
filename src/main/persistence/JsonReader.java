package persistence;

import model.BookCollection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// CITATION: Code obtained from JsonReader in JsonSerializationDemo
// Represents a reader that reads book collection from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BookCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBookCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses book collection from JSON object and returns it
    private BookCollection parseBookCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        BookCollection bc = new BookCollection(name);
        addBooks(bc, jsonObject);
        return bc;
    }

    // MODIFIES: bc
    // EFFECTS: parses books from JSON object and adds them to book collection
    private void addBooks(BookCollection bc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("books");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(bc, nextBook);
        }
    }

    // MODIFIES: bc
    // EFFECTS: parses book from JSON object and adds it to book collection
    private void addBook(BookCollection bc, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        String date = jsonObject.getString("date");
        String category = jsonObject.getString("category");
        String readState = jsonObject.getString("read state");
        String review = jsonObject.getString("review");
        bc.addBook(title, author, date, category, readState, review);
    }
}