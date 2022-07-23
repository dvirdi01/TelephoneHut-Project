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
        goToMenu();

    }

    //EFFECTS: takes user to menu
    public void goToMenu() {
        displayMenuOptions();
        System.out.println();
        System.out.println("Please pick a number: ");
        this.menuChoice = myScanner.nextInt();
        choosePhonebookOperation();

    }

    //EFFECTS: displays the operations present in the phonebook
    public void displayMenuOptions() {
        System.out.println("Here are your options");
        System.out.println();
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
        String name = myScanner.next();
        System.out.println("Enter Contact's phone number in quotations: ");
        String phoneNumber = myScanner.next();
        System.out.println("Enter Contact's email: ");
        String email = myScanner.next();
        System.out.println("Enter Contact type (FAMILY/FRIEND/WORK): ");
        String type = myScanner.next();

        Contact contact = new Contact(name, phoneNumber, email, type);
        contactList.addContact(contact);
        System.out.println("This contact has been saved in your Contacts List!");

        continueOrExit();

    }


    //EFFECTS: allows user to either view one contact or all contacts
    public void viewContactOptionPressed() {
        System.out.println("Press 1 to view one contact, 2 to view all contacts: ");
        int viewingChoice = myScanner.nextInt();

        if (viewingChoice == 1) {
            System.out.println("Enter the name of contact you would like to view: ");
            String nameToFind = myScanner.next();
            viewOnlyOneContact(nameToFind);


        } else {
            viewAllContacts();
            goToMenu();
        }

    }


    //EFFECTS: allows user to modify a contact
    public void modifyContactOptionPressed() {
        viewAllContacts();
        System.out.println("Enter name of contact you want to modify: ");
        String nameToModify = myScanner.next();

        //return that contact
        Contact contactToModify = contactList.getContactByName(nameToModify);
        //viewOnlyOneContact(nameToModify);

        //display modifying options
        modifyingOperations(contactToModify);
        continueOrExit();
        //goToMenu();
    }



    //TODO: why not working
    //EFFECTS: allows user to delete a contact
    public void deleteContactOptionPressed() {
        viewAllContacts();
        System.out.println("Enter name of contact you want to delete: ");
        String name = myScanner.next();

        contactList.deleteContact(contactList.getContactByName(name));
        System.out.println("Contact has been deleted");

        continueOrExit();

    }

    //EFFECTS: allows user to make a call.
    public void makeCallOptionPressed() {
        System.out.println("Please enter the name of contact who you would like to call: ");
        String nameToCall = myScanner.next();

        callingLog.makeCall(contactList.getContactByName(nameToCall));
        System.out.println("Making a call to..." + contactList.getContactByName(nameToCall).getName());
        continueOrExit();

    }

    //EFFECTS: allows user to view their calling log and the current number of calls made
    public void viewCallLogOptionPressed() {
        System.out.println("Loading your call log...");

        for (String s: callingLog.getCallingLog()) {
            System.out.println("You called " + s);
        }
        System.out.println("Total Number of calls made so far: " + callingLog.getNumberOfCallsMade());
        continueOrExit();

    }


    //------Helper Functions------


    //EFFECTS: displays the contact information for the chosen contact
    public void viewOnlyOneContact(String nameToFind) {

        for (Contact c: contactList.getAllContacts()) {
            if (c.getName().equals(nameToFind)) {
                displayContactInformation(c);
                //return;
            }
        }
        //System.out.println("This contact does not exist:(");
        continueOrExit();

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


    //EFFECTS: allows the user to choose which information they want to modify
    public void modifyingOperations(Contact c) {

        changeNameRequest(c);
        changePhoneNumberRequest(c);
        changeEmailRequest(c);
        changeTypeRequest(c);
        //continueOrExit();

    }

    //MODIFIES: this
    //EFFECTS: allows user to change contact's name
    public void changeNameRequest(Contact c) {
        System.out.println("Would you like to change this contact's name (yes/no): ");
        String answer = myScanner.next();

        if (answer.equals("yes")) {
            System.out.println("Enter new name of Contact: ");
            String newName = myScanner.next();
            c.setName(newName);
            System.out.println("Contact's name has been changed!");
        }
    }

    //MODIFIES: this
    //EFFECTS: allows user to change contact's number
    public void changePhoneNumberRequest(Contact c) {
        System.out.println("Would you like to change this contact's phone number (yes/no): ");
        String answer = myScanner.next();

        if (answer.equals("yes")) {
            System.out.println("Enter new phone number of Contact: ");
            String newPhoneNumber = myScanner.next();
            c.setPhoneNumber(newPhoneNumber);
            System.out.println("Contact's phone number has been changed!");
        }

    }

    //MODIFIES: this
    //EFFECTS: allows user to change contact's email
    public void changeEmailRequest(Contact c) {
        System.out.println("Would you like to change this contact's email (yes/no): ");
        String answer = myScanner.next();

        if (answer.equals("yes")) {
            System.out.println("Enter new email of Contact: ");
            String newEmail = myScanner.next();
            c.setEmail(newEmail);
            System.out.println("Contact's email has been changed!");
        }
    }

    //MODIFIES: this
    //EFFECTS: allows user to change contact's type
    public void changeTypeRequest(Contact c) {
        System.out.println("Would you like to change this contact's type (yes/no): ");
        String answer = myScanner.next();

        if (answer.equals("yes")) {
            System.out.println("Enter the new type of this Contact: ");
            String newType = myScanner.next();
            c.setEmail(newType);
            System.out.println("Contact's type has been changed!");
        }
    }

    public void continueOrExit() {
        System.out.println("Would you like to continue or exit the phonebook (continue/exit): ");
        String decision = myScanner.next();

        if (decision.equals("continue")) {
            goToMenu();
        } else {
            return;
        }
    }

}
