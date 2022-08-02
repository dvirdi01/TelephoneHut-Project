package ui;

import model.CallingLog;
import model.Contact;
import model.ContactList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


/**
 This class represents a phonebook which contains all information
 about the contacts stored in the book. It allows user to add contacts,
 view contacts, make calls, and view their calling history

 Source and inspiration taken from: https://github.students.cs.ubc.ca/CPSC210/TellerApp
 */

public class Phonebook {

    private static final String CONTACTLIST_STORE = "./data/phonebook.json";
    private static final String CALLING_STORE = "./data/callingLog.json";

    Scanner myScanner = new Scanner(System.in);
    int menuChoice;

    ContactList contactList = new ContactList();
    CallingLog callingLog = new CallingLog();

    private JsonWriter jsonWriterCallingLog;
    private JsonWriter jsonWriterContactList;
    private JsonReader jsonReaderCallingLog;
    private JsonReader jsonReaderContactList;



    //EFFECTS: Instantiates a Phonebook with no contacts added
    public Phonebook() throws FileNotFoundException {
        //added this
        jsonWriterCallingLog = new JsonWriter(CALLING_STORE);
        jsonWriterContactList = new JsonWriter(CONTACTLIST_STORE);
        jsonReaderCallingLog = new JsonReader(CALLING_STORE);
        jsonReaderContactList = new JsonReader(CONTACTLIST_STORE);


        System.out.println("-----------------------------Welcome to your PhoneBook-------------------------------");
        System.out.println("Please enter your name: ");
        String userName = myScanner.nextLine();

        System.out.println();
        System.out.println("Welcome " + userName + ", what would you like to do? ");
        System.out.println();
        goToMenu();

    }

    //EFFECTS: takes user to phonebook menu and asks them for their choice of operation
    public void goToMenu() {
        displayMenuOptions();
        System.out.println();
        System.out.println("Please pick a number: ");
        this.menuChoice = myScanner.nextInt();
        choosePhonebookOperation();

    }

    //EFFECTS: displays the operations offered in the phonebook
    public void displayMenuOptions() {

        System.out.println();
        System.out.println("1. Add a Contact");
        System.out.println("2. View Existing Contacts");
        System.out.println("3. Modify a Contact");
        System.out.println("4. Delete a Contact");
        System.out.println("5. Make a Call");
        System.out.println("6. View your Call Log");
        System.out.println("7. Save PhoneBook Progress to file");
        System.out.println("8. Load PhoneBook from file");
    }

    //EFFECTS: chooses which method to call based on user's menu choice. Displays menu again if user input
    // invalid menu choice
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
        } else if (menuChoice == 6) {
            viewCallLogOptionPressed();
        } else if (menuChoice == 7) {
            saveProgress();
        } else if (menuChoice == 8) {
            loadFromFile();
        } else {                           //out of bounds menu option chosen
            System.out.println("You entered an invalid number \n Please pick a menu option from 1-6");
            goToMenu();
        }
    }

    //MODIFIES: this
    //EFFECTS: allows user to create a new contact
    public void addContactOptionPressed() {

        System.out.println();
        System.out.println("Enter Contact Name: ");
        String name = myScanner.next();

        System.out.println("Enter Contact's phone number: ");
        String phoneNumber = myScanner.next();
        checkPhoneNumber(phoneNumber);

        System.out.println("Enter Contact's email: ");
        String email = myScanner.next();
        checkEmail(email);

        System.out.println("Enter Contact type (FAMILY/FRIEND/WORK): ");
        String type = myScanner.next();
        checkType(type);

        Contact contact = new Contact(name, phoneNumber, email, type);
        contactList.addContact(contact);

        continueOrExit();

    }


    //EFFECTS: allows user to either view one contact or all contacts. If user inputs incorrect option,
    // display viewing options again
    public void viewContactOptionPressed() {
        System.out.println();
        System.out.println("Press 1 to view one contact, 2 to view all contacts: ");
        int viewingChoice = myScanner.nextInt();

        if (viewingChoice == 1) {
            System.out.println();
            System.out.println("Enter the name of contact you would like to view: ");
            String nameToFind = myScanner.next();
            viewOnlyOneContact(nameToFind);

        } else if (viewingChoice == 2) {
            viewAllContacts();
            goToMenu();
        } else {
            System.out.println("Please choose from one of the options above.");
            viewContactOptionPressed();
        }

    }


    //EFFECTS: Asks user for which contact they want to modify and displays modifying operations
    public void modifyContactOptionPressed() {
        viewAllContacts();
        System.out.println("Enter name of contact you want to modify: ");
        String nameToModify = myScanner.next();

        if (nameNotFound(nameToModify)) {
            System.out.println("This contact does not exist in your Contact List");
            System.out.println("Would you like to modify another contact instead? (yes/no) ");
            String modifyAnother = myScanner.next();

            if (modifyAnother.equals("yes")) {
                modifyContactOptionPressed();
            } else {
                continueOrExit();
            }

        } else {
            Contact contactToModify = contactList.getContactByName(nameToModify);
            modifyingOperations(contactToModify);
        }
        continueOrExit();
    }


    //MODIFIES: this
    //EFFECTS: allows user to delete a contact
    public void deleteContactOptionPressed() {
        viewAllContacts();
        System.out.println("Enter name of contact you want to delete: ");
        String name = myScanner.next();

        if (nameNotFound(name)) {
            System.out.println("This contact does not exist in your Contact List");
            System.out.println("Would you like to delete another contact instead? (yes/no) ");
            String deleteAnother = myScanner.next();

            if (deleteAnother.equals("yes")) {
                deleteContactOptionPressed();
            } else {
                continueOrExit();
            }

        } else {
            contactList.deleteContact(contactList.getContactByName(name));
            System.out.println("Contact has been deleted");
            continueOrExit();
        }
    }

    //EFFECTS: allows user to make a call
    public void makeCallOptionPressed() {
        System.out.println("Please enter the name of contact who you would like to call: ");
        String nameToCall = myScanner.next();

        if (nameNotFound(nameToCall)) {
            System.out.println("This contact does not exist in your Contact List");
            System.out.println("Would you like to call another contact? (yes/no): ");
            String callAnother = myScanner.next();

            if (callAnother.equals("yes")) {
                makeCallOptionPressed();
            } else {
                continueOrExit();
            }

        } else {
            callingLog.makeCall(contactList.getContactByName(nameToCall));
            System.out.println("Making a call to..." + contactList.getContactByName(nameToCall).getName());
            continueOrExit();
        }

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


    //EFFECTS: displays the contact information for the chosen contact in menu option 2
    public void viewOnlyOneContact(String nameToFind) {

        for (Contact c: contactList.getAllContacts()) {
            if (c.getName().equals(nameToFind)) {
                displayContactInformation(c);
            }
        }
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


    //EFFECTS: displays the contact information for a contact
    public void displayContactInformation(Contact c) {
        System.out.println("Name: " + c.getName());
        System.out.println("PhoneNumber: " + c.getPhoneNumber());
        System.out.println("Email: " + c.getEmail());
        System.out.println("Type: " + c.getType());
        System.out.println();
    }


    //EFFECTS: allows the user to choose which information of a contact they want to modify
    public void modifyingOperations(Contact c) {
        changeNameRequest(c);
        changePhoneNumberRequest(c);
        changeEmailRequest(c);
        changeTypeRequest(c);

        System.out.println();
        continueOrExit();
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
    //EFFECTS: allows user to change contact's phone number
    public void changePhoneNumberRequest(Contact c) {
        System.out.println();
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

    //EFFECTS: if user wants to continue doing more operations, takes them to menu again,
    //otherwise exits the phonebook.
    public void continueOrExit() {
        System.out.println("Would you like to continue or exit the phonebook (continue/exit): ");
        String decision = myScanner.next();

        if (decision.equals("continue")) {
            goToMenu();
        } else if (decision.equals("exit")) {
            System.out.println("If you exit now your progress won't be saved. "
                    + "\n Would you like to save your progress? (yes/no): ");
            String savingDecision = myScanner.next();

            if (savingDecision.equals("yes")) {
                saveProgress();
            } else {
                return;
            }
        } else {
            return;
        }
    }


    //EFFECTS: checks if name entered only contains alphabets. Otherwise prompts
    // user to enter contact's information again
    private void checkInvalidName(String name) {
        if (!name.matches("([A-z]|[a-z])+")) {
            System.out.println("Name can only contain alphabets!");
            addContactOptionPressed();
        }
    }

    //EFFECTS: checks if phone number entered is 10 digits long and doesn't start with a 0.
    //Otherwise prompts user to enter contact's information again
    //source: https://stackoverflow.com/questions/24592808/regular-expression-in-java-validating-user-input
    private void checkPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("[1-9][0-9]{9}")) {
            System.out.println("Invalid Phone number entered. ");
            System.out.println("Phone number must be 10 digits long and it must not start with a 0");
            addContactOptionPressed();
        }
    }

    //EFFECTS: checks if email entered matches the given email pattern
    //Otherwise prompts user to enter contact's information again
    private void checkEmail(String email) {
        if (!email.matches("[A-z]+([0-9]*[A-z]*)*\\@[a-z]+\\.com")) {
            System.out.println("Invalid email entered .");
            addContactOptionPressed();
        }
    }

    //EFFECTS: checks if type entered is one of FAMILY, WORK, or FRIEND.
    //Otherwise prompts user to enter contact's information again
    private void checkType(String type) {
        if (!type.matches("FAMILY|FRIEND|WORK")) {
            System.out.println("Incorrect Type entered \n Please enter one of FAMILY/FRIEND/WORK.");
            addContactOptionPressed();
        }
    }


    //EFFECTS: returns true if a given contact's name is not found (i.e name is null) in the
    //contact list otherwise returns false if name is found.
    public boolean nameNotFound(String name) {
        if (contactList.getContactByName(name) == null) {
            return true;
        } else {
            return false;
        }
    }



    //----------------------------------------PHASE 2 METHODS----------------------------------------------------

    //SAVING METHODS

    //EFFECTS: saves both contact list and calling log into separate file.
    public void saveProgress() {
        saveContactList();
        saveCallingLog();
    }


    //EFFECTS: saves contact list into file.
    public void saveContactList() {
        try {
            jsonWriterContactList.open();
            jsonWriterContactList.writeContactList(contactList);
            jsonWriterContactList.closeWriter();
            System.out.println("Saved your Contact List to " + CONTACTLIST_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + CONTACTLIST_STORE);
        }
    }


    //EFFECTS: saves calling log into file.
    public void saveCallingLog() {
        try {
            jsonWriterCallingLog.open();
            jsonWriterCallingLog.writeCallingLog(callingLog);
            jsonWriterCallingLog.closeWriter();
            System.out.println("Saved your Calling Log to " + CALLING_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + CALLING_STORE);
        }
    }



    //LOADING METHODS

    //MODIFIES: this
    //EFFECTS: loads both contact list and calling log from file and displays menu options again
    public void loadFromFile() {
        loadContactList();
        loadCallingLog();
        System.out.println("Loading complete");
        goToMenu();
    }


    // MODIFIES: this
    // EFFECTS: loads ContactList from file
    private void loadContactList() {
        try {
            contactList = jsonReaderContactList.readContactList();
            System.out.println("Loaded Contact List from " + CONTACTLIST_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + CONTACTLIST_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Calling Log from file
    private void loadCallingLog() {
        try {
            callingLog = jsonReaderCallingLog.readCallingLog();
            System.out.println("Loaded Calling Log from " + CALLING_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + CALLING_STORE);
        }
    }





//    private String askForName() {
//
//        System.out.println("Enter Contact Name: ");
//        String name = myScanner.next();
//        checkInvalidName(name);
//        this.userName = name;
//        return userName;
//    }
//
//    private String askForPhoneNumber() {
//        System.out.println("Enter Contact's phone number: ");
//        String phoneNumber = myScanner.next();
//        checkPhoneNumber(phoneNumber);
//        this.userPhoneNumber = phoneNumber;
//        return userPhoneNumber;
//
//    }
//
//    private String askForEmail() {
//        System.out.println("Enter Contact's email: ");
//        String email = myScanner.next();
//        checkEmail(email);
//        this.userEmail = email;
//        return userEmail;
//
//    }
//
//    private String askForType() {
//        System.out.println("Enter Contact's type: ");
//        String type = myScanner.next();
//        checkType(type);
//        this.userType = type;
//        return userType;
//
//    }


}
