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


//    Contact firstContact = new Contact("Divjot", "1234567890",
//            "virdidivjot@gmail.com", "FRIEND");

    //REQUIRES: type is in upper case
    //EFFECTS: creates a new contact entry
    public Contact(String name, String phoneNumber, String email, String type) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.type = type;
    }

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

    //EFFECTS: returns name of the contact
    public String getName() {
        return name;
    }

    //REQUIRES: phoneNumber is 10 digits long, and it is not 0000000000
    //EFFECTS: returns contact's phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }


    //EFFECTS: returns the contact's email
    public String getEmail() {
        return email;
    }

    //EFFECTS: returns the contact type
    public String getType() {
        return type;
    }


}
