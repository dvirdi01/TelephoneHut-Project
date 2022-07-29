package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 This class represents a Calling Log that contains the names of the people the user has called
 and the number of calls made so far.
 */

public class CallingLog implements Writable {

    private int numberOfCallsMade;
    List<String> callingLog;


    //EFFECTS: creates an empty call log where total number of calls made is 0
    public CallingLog() {
        callingLog = new ArrayList<>();
        numberOfCallsMade = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds Contact name in the calling log and increments the
    //number of calls made by 1
    public void makeCall(Contact c) {
        callingLog.add(c.getName());
        this.numberOfCallsMade++;
    }


    //EFFECTS: returns the name of last call made
    public String getLastCallMade() {
        int size = callingLog.size() - 1;
        return callingLog.get(size);
    }

    //EFFECTS: returns the callingLog
    public List<String> getCallingLog() {
        return callingLog;
    }

    //MODIFIES: this
    //EFFECTS: clears the call log by removing all calls made.
    //source: https://www.java67.com/2016/07/how-to-remove-all-elements-of-arraylist.html
    public void clearCallLog() {
        callingLog.clear();
    }

    //EFFECTS: returns the number of calls made so far
    public int getNumberOfCallsMade() {
        return numberOfCallsMade;
    }


    //---------------------------------PHASE-2 STUFF --------------------------------------- (looks correct)

    @Override
    //EFFECTS: returns Calling Log as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Calling Log", callNamesToJson());
        json.put("Number of calls made", numberOfCallsMade);
        return json;
    }


    //EFFECTS: returns names in the calling log as a JSON array
    private JSONArray callNamesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String s : callingLog) {
            jsonArray.put(s);
        }
        return jsonArray;
    }

}
