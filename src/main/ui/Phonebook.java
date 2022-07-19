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

    String userName;
    int userPhoneNumber;
    String userEmail;
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

}
