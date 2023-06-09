package ui;

import java.awt.*;
import java.io.FileNotFoundException;

/**
 This class represents the main class which creates a new instance of the
 phonebook and runs all operations from the Phonebook class.
 */

public class Main {
    public static void main(String[] args) {

        try {
            Phonebook myPhoneBook = new Phonebook();
            myPhoneBook.mainFrame.setVisible(true);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
