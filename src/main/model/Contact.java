package model;


import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

/**
 This class represents a contact in the phonebook which has a name, phone number,email, and type
 associated with it.
 Source: https://github.com/UBCx-Software-Construction/data-abstraction-practice-projects/tree/master/Contact
 */

public class Contact implements Writable {

    private String name;
    private String phoneNumber;
    private String email;
    private String type;


    //REQUIRES: phoneNumber is 10 digits long, and it is not 0000000000 or beginning with one or more 0s,
    //type is entered in upper case, contact name is non-empty
    //EFFECTS: creates a new contact entry
    public Contact(String name, String phoneNumber, String email, String type) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) && Objects.equals(phoneNumber, contact.phoneNumber)
                && Objects.equals(email, contact.email) && Objects.equals(type, contact.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email, type);
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


    //---------------------------------PHASE-2 STUFF --------------------------------------- (looks correct)

    @Override
    //EFFECTS: returns contact as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("Phone Number", phoneNumber);
        json.put("Email", email);
        json.put("Type", type);
        return json;
    }

}
