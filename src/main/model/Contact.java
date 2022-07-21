package model;

import java.util.ArrayList;
import java.util.List;


/**
 Represents a contact in the phonebook which has a name, phone number,email, and type
 associated with it.
 */

public class Contact {

    private String name;
    private String phoneNumber;
    private String email;
    private String type;
    Contact firstContact;


    //REQUIRES: phoneNumber is 10 digits long, and it is not 0000000000 or beginning with one or more 0s,
    // type is entered in upper case
    //EFFECTS: creates a new contact entry
    public Contact(String name, String phoneNumber, String email, String type) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.type = type;
    }

    //Setters

    //MODIFIES: this
    //EFFECTS: modifies the contact's name
    public void setName(String name) {
        this.name = name;
    }

    //MODIFIES: this
    //EFFECTS: modifies the contact's phone number
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //MODIFIES: this
    //EFFECTS: modifies the contact's email
    public void setEmail(String email) {
        this.email = email;
    }


    //MODIFIES: this
    //EFFECTS: modifies the contact's type
    public void setType(String type) {
        this.type = type;
    }


    // Getters

    //EFFECTS: returns name of the contact
    public String getName() {
        return name;
    }

    //EFFECTS: returns contact's phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }


    //EFFECTS: returns the contact's email
    public String getEmail() {
        return email;
    }

    //EFFECTS: returns the contact's type
    public String getType() {
        return type;
    }

}
