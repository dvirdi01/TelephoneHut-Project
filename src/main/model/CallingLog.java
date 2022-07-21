package model;

import java.util.ArrayList;
import java.util.List;

/**
 Represents a callLog that contains the names of the people the user called.
 */

public class CallingLog {

    private int numberOfCallsMade;
    List<String> callingLog;


    //EFFECTS: creates an empty call log.
    public CallingLog() {
        callingLog = new ArrayList<>();
        numberOfCallsMade = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds Contact name in the calling log and increments the
    //number of calls made by 1
    public void makeCall(Contact c) {
        callingLog.add(c.getName());
        this.numberOfCallsMade++; //TODO
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
        // could have also done callingLog = new ArrayList;
    }

    //EFFECTS: returns the number of calls made so far
    public int getNumberOfCallsMade() {
        return numberOfCallsMade;
    }


}
