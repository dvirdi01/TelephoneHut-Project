package model;

import java.util.ArrayList;
import java.util.List;

/**
 Represents a collLog that contains the names of the people the user called.
 */
public class CallingLog {

    private int numberOfCallsMade;
    List<String> callLogDirectory = new ArrayList<>();


    //EFFECTS: creates an empty call log.
    public CallingLog() {
        numberOfCallsMade = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds Contact name in the calling log and increments the
    //number of calls made by 1
    //TODO: problem with incrementing?
    public void makeCall(String name) {
        callLogDirectory.add(name);
        numberOfCallsMade++;
    }


    //EFFECTS: returns the number of calls made so far
    public int getNumberOfCallsMade() {
        return numberOfCallsMade;
    }

    //EFFECTS: returns the name of last call made
    public String getLastCallMade() {
        int size = callLogDirectory.size() - 1;
        return callLogDirectory.get(size);
    }

    //EFFECTS: displays the callingLog
    public List<String> viewCallLog() {
        return callLogDirectory;
    }

    //MODIFIES: this
    //EFFECTS: clears the call log by removing all calls made.
    //TODO: CONCURRENT MODIFICATION EXCEPTION WHAT WAS WRONG WITH MY FOR LOOP
    //source: https://www.java67.com/2016/07/how-to-remove-all-elements-of-arraylist.html
    public void clearCallLog() {

        callLogDirectory.clear();

        //for (String s: callLogDirectory) {
            //callLogDirectory.remove(s);
        //}
        //numberOfCallsMade = 0;
    }

}
