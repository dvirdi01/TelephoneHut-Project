package persistence;

import org.json.JSONObject;

/**
 Represents an interface which returns objects as JSON objects
 Source: JsonSerializationDemo Project
 */
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
