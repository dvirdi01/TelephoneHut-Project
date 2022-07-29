package persistence;

import org.json.JSONObject;

/**
 Represents a ...?
 */
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
