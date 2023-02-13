package persistence;

import org.json.JSONObject;

// CITATION: code obtained from Writable in JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

