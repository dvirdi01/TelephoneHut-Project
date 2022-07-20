package ui;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import model.Contact;

import java.util.Scanner;

/**
 This class represents a phonebook which contains all information
 about the contacts stored in the book.
 */

public class Phonebook {

    Scanner myScanner = new Scanner(System.in);

    String name;
    String phoneNumber;
    String email;
    String type;
    int menuChoice;


    //EFFECTS: Instantiates a Phonebook
    public Phonebook() {

        System.out.println("-------Welcome to your PhoneBook-------");
        System.out.println("Please enter your name: ");
        String userName = myScanner.nextLine();



        System.out.println("Welcome " + userName + ", what would you like to do? ");
        displayMenuOptions();
        System.out.println();
        System.out.println("Please pick a number: ");
        this.menuChoice = myScanner.nextInt();

        choosePhonebookOperation();

    }

    //EFFECTS: displays the operations present in the phonebook
    public void displayMenuOptions() {
        System.out.println("1. Add a Contact");
        System.out.println("2. View Existing Contacts");
        System.out.println("3. Modify a Contact");
        System.out.println("4. Delete a Contact");
        System.out.println("5. View your Call Log");


    }

    //EFFECTS: chooses which method to call based on user's menu choice
    // TODO: this will get really long
    public void choosePhonebookOperation() {
        if (menuChoice == 1) {
            // do something
        } else if (menuChoice == 2) {
            //do something
        } else if (menuChoice == 3) {
            //do something
        } else if (menuChoice == 4) {
            //do something
        } else {
            //do something
        }
    }

    //EFFECTS: allows user to create a new contact and add it to contactslist
    public void menuOption1Pressed() {
        System.out.println("Enter Contact Name: ");
        String name = myScanner.nextLine();

        System.out.println("Enter Contact's PhoneNumber in quotations: ");
        String phoneNumber = myScanner.nextLine();
        System.out.println("Enter Contact's email: ");
        String email = myScanner.nextLine();
        System.out.println("Enter Contact type: ");
        String type = myScanner.nextLine();

        Contact contact = new Contact(name, phoneNumber, email, type);
        //checkContactAlreadyExists(contact); //check if contact already exists. if no, call addcontact otherwise say
        // sorry this contact already exists.

        //call addcontact method from contactlist
        System.out.println("This contact has been saved in your Contacts List!");
    }


    //EFFECTS: allows user to view either one or all contacts
    public void menuOption2Pressed() {
        System.out.println("Press one to view 1 contact or press 2 to view all contacts: ");
        int viewingChoice = myScanner.nextInt();

        if (viewingChoice == 1) {
            System.out.println("Enter the name of contact you would like to view: ");
            String name = myScanner.nextLine();

            //check if contactlist contains it . if yes: print it out
            // if no, say "Sorry, that contact does not exist!

        } else {
            //viewAllContacts();
        }

    }

    //EFFECTS: allows user to modify a contact
    public void menuOption3Pressed() {
        System.out.println("Enter name of contact you want to modify: ");
        String name = myScanner.nextLine();

        // check if current contact name even exist:
        // if it does, then

        System.out.println("Enter new name of Contact: ");
        String newName = myScanner.nextLine();
        System.out.println("Enter new number of Contact: ");
        String newNumber = myScanner.nextLine();
        System.out.println("Enter new email of Contact: ");
        String newEmail = myScanner.nextLine();
        System.out.println("Enter new type of Contact: ");
        String newType = myScanner.nextLine();

        //modifyContact(newName, newNumber, newEmail,newType);
        System.out.println("All changes have been made!");
    }

    //EFFECTS: allows user to delete a contact
    public void menuOption4Pressed() {
        System.out.println("Enter name of contact you want to delete: ");
        String name = myScanner.nextLine();

        // checkContactAlreadyThere();
        //deleteContact();
        System.out.println("Contact has been deleted.");

        // otherwise:
        System.out.println("Sorry that contact doesn't exist.");

    }

    //EFFECTS: allows user to delete a contact
    public void menuOption5Pressed() {
        System.out.println("Loading your call log...");
        //viewCallLog();
    }

}
