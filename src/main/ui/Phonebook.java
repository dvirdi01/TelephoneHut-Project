package ui;

import model.CallingLog;
import model.Contact;
import model.ContactList;

import java.util.Scanner;

/**
 This class represents a phonebook which contains all information
 about the contacts stored in the book.
 */

public class Phonebook {

    Scanner myScanner = new Scanner(System.in);
    int menuChoice;
    ContactList contactList = new ContactList();
    CallingLog callingLog = new CallingLog();


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
        System.out.println("5. Make a Call");
        System.out.println("6. View your Call Log");

    }

    //EFFECTS: chooses which method to call based on user's menu choice
    public void choosePhonebookOperation() {
        if (menuChoice == 1) {
            addContactOptionPressed();
        } else if (menuChoice == 2) {
            viewContactOptionPressed();
        } else if (menuChoice == 3) {
            modifyContactOptionPressed();
        } else if (menuChoice == 4) {
            deleteContactOptionPressed();
        } else if (menuChoice == 5) {
            makeCallOptionPressed();
        } else {
            viewCallLogOptionPressed();
        }
    }

    //EFFECTS: allows user to create a new contact and add it to contact list
    public void addContactOptionPressed() {
        System.out.println("Enter Contact Name: ");
        String name = myScanner.nextLine();

        System.out.println("Enter Contact's phone number in quotations: ");
        String phoneNumber = myScanner.nextLine();
        System.out.println("Enter Contact's email: ");
        String email = myScanner.nextLine();
        System.out.println("Enter Contact type (FAMILY/FRIEND/WORK): ");
        String type = myScanner.nextLine();

        Contact contact = new Contact(name, phoneNumber, email, type);
        contactList.addContact(contact);
        System.out.println("This contact has been saved in your Contacts List!");

        //return back to menu options or add another contact TODO: a loop?
    }


    //EFFECTS: allows user to either view one contact or all contacts
    public void viewContactOptionPressed() {
        System.out.println("Press 1 to view one contact, 2 to view all contacts: ");
        int viewingChoice = myScanner.nextInt();

        if (viewingChoice == 1) {
            System.out.println("Enter the name of contact you would like to view: ");
            String nameToFind = myScanner.nextLine();
            viewOnlyOneContact(nameToFind);
        } else {
            viewAllContacts();
        }

    }


    //EFFECTS: allows user to modify a contact
    public void modifyContactOptionPressed() {
        //display all contacts first
        viewAllContacts();

        //allow user to enter name of contact they want to modify
        System.out.println("Enter name of contact you want to modify: ");
        String nameToModify = myScanner.nextLine();

        //get contact that you are searching for
        contactList.getContactByName(nameToModify);

        //display that contact's information
        viewOnlyOneContact(nameToModify);

        //display modifying options
        modifyingOperations();
    }



    //EFFECTS: allows user to delete a contact
    public void deleteContactOptionPressed() {
        viewAllContacts();
        System.out.println("Enter name of contact you want to delete: ");
        String name = myScanner.nextLine();

        contactList.deleteContact(contactList.getContactByName(name));
        System.out.println("Contact has been deleted");

//        for (Contact c: contactList.getAllContacts()) {
//            if (c.getName() == name) {
//                contactList.deleteContact(c);
//                System.out.println("Contact has been deleted");
//            } else {
//                System.out.println("This contact does not exist:(");
//            }
//        }

    }

    //EFFECTS: allows user to make a call.
    public void makeCallOptionPressed() {
        System.out.println("Please enter the name of contact who you would like to call: ");
        String nameToCall = myScanner.nextLine();

        callingLog.makeCall(contactList.getContactByName(nameToCall));
        System.out.println("Making a call to..." + contactList.getContactByName(nameToCall).getName());

    }

    //EFFECTS: allows user to view their calling log and the current number of calls made
    public void viewCallLogOptionPressed() {
        System.out.println("Loading your call log...");

        for (String s: callingLog.getCallingLog()) {
            System.out.println("You called " + s);
        }
        System.out.println("Total Number of calls made so far: " + callingLog.getNumberOfCallsMade());

    }


    //------Helper Functions------


    //EFFECTS: displays the contact information for the chosen contact
    public void viewOnlyOneContact(String nameToFind) {

        for (Contact c: contactList.getAllContacts()) {
            if (c.getName() == nameToFind) {
                displayContactInformation(c);
            } else {
                System.out.println("This contact does not exist:(");
            }
        }

    }

    //EFFECTS: displays all contacts in the calling log
    public void viewAllContacts() {
        System.out.println("Displaying all Contacts below");
        System.out.println();

        for (Contact c: contactList.getAllContacts()) {
            displayContactInformation(c);
        }

    }


    //EFFECTS: displays the contact information
    public void displayContactInformation(Contact c) {
        System.out.println("Name: " + c.getName());
        System.out.println("PhoneNumber: " + c.getPhoneNumber());
        System.out.println("Email: " + c.getEmail());
        System.out.println("Type: " + c.getType());
        System.out.println();
    }


    //MODIFIES: this
    //EFFECTS: allows the user to choose which information they want to modify
    public void modifyingOperations() {
        System.out.println("Would you like to change this contact's name (yes/no): ");
        String answer1 = myScanner.nextLine();

        if (answer1 == "yes") {
            System.out.println("Enter new name of Contact: ");
            String newName = myScanner.nextLine();


            //TODO: need help with the helper methods.
            //c.setName(newName);

        } else {
            // do nothing
        }

    }


}
