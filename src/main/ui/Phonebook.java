package ui;

import model.CallingLog;
import model.Contact;
import model.ContactList;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 This class represents a phonebook which contains all information
 about the contacts stored in the book. It allows user to add contacts,
 view contacts, make calls, and view their calling history

 Source and inspiration taken from: https://github.students.cs.ubc.ca/CPSC210/TellerApp
 */

public class Phonebook {

    private static final String CONTACTLIST_STORE = "./data/phonebook.json";
    private static final String CALLING_STORE = "./data/callingLog.json";
    static final int FRAME_WIDTH = 600;
    static final int FRAME_HEIGHT = 400;

    ContactList contactList = new ContactList();
    CallingLog callingLog = new CallingLog();

    private final JsonWriter jsonWriterCallingLog;
    private final JsonWriter jsonWriterContactList;
    private final JsonReader jsonReaderCallingLog;
    private final JsonReader jsonReaderContactList;

    JFrame mainFrame = new JFrame();
    BorderLayout borderLayout = new BorderLayout();
    CardLayout cardLayout = new CardLayout();

    //Panels
    JPanel introPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel cardHolderPanel;
    JPanel p1;
    JPanel p2;

    //Buttons
    JButton returnButton = new JButton("Return");
    JButton addButton = new JButton("Add");
    JButton modifyButton = new JButton("Modify");
    JButton deleteButton = new JButton("Delete");
    JButton callButton = new JButton("Call");
    JButton saveButton = new JButton("Save");
    JButton loadButton = new JButton("Load");

    //TextFields
    JTextField nameField = new JTextField();
    JTextField phoneField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField typeField = new JTextField();

    //Tables and table models
    JTable contactListTable;
    DefaultTableModel contactListTableModel;
    String[] rowComponents;
    String[] loadedRowComponents;

    JTable callingLogTable;
    DefaultTableModel callingLogTableModel;
    DefaultTableCellRenderer callingLogCellRenderer;



    //EFFECTS: Instantiates a Phonebook with no contacts added
    public Phonebook() throws FileNotFoundException {

        //added this
        jsonWriterCallingLog = new JsonWriter(CALLING_STORE);
        jsonWriterContactList = new JsonWriter(CONTACTLIST_STORE);
        jsonReaderCallingLog = new JsonReader(CALLING_STORE);
        jsonReaderContactList = new JsonReader(CONTACTLIST_STORE);

        setUpFrame();
        setUpPanel();
    }

    private void setUpFrame() {
        mainFrame = new JFrame();
        mainFrame.setTitle("Telephone Hut");
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(borderLayout);
        mainFrame.getContentPane().setBackground(new Color(24, 45, 86, 255));
        mainFrame.setResizable(false);

//        introPanel.setBackground(new Color(255, 179, 0));
//        JButton welcomeButton = new JButton("Welcome");
//        introPanel.add(welcomeButton, BorderLayout.SOUTH);
//
//        welcomeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (e.getSource() == welcomeButton) {
//                    setUpPanel();
//                }
//            }
//        });
//
//        introPanel.setVisible(true);
        mainFrame.add(introPanel);
    }



    private void setUpPanel() {
        displayTopPanel();
        displayMainPanel();
        displayBottomPanel();
        displayLeftPanel();
       //displayRightPanel();
    }

    private void displayTopPanel() {

        BoxLayout topPanelLayout = new BoxLayout(topPanel, BoxLayout.X_AXIS);
        topPanel.setLayout(topPanelLayout);
        topPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        topPanel.setBackground(new Color(74, 86, 119));
        topPanel.setPreferredSize(new Dimension(50, 30));
        topPanel.setVisible(true);

        // create label
        JLabel topPanelLabel = new JLabel();
        topPanelLabel.setText("Contacts Manager");
        topPanelLabel.setForeground(Color.WHITE);
        topPanelLabel.setFont(new Font("Verdana", Font. BOLD, 18));

        //create Buttons
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(Box.createRigidArea(new Dimension(70, 30)));
        topPanel.add(topPanelLabel);

        //-----------------------------------------------------------------
        topPanel.setVisible(true);
        mainFrame.add(topPanel, borderLayout.NORTH);
    }

    private void displayMainPanel() {
        BorderLayout centerPanelLayout = new BorderLayout();
        mainPanel.setLayout(centerPanelLayout);
        //mainPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        mainPanel.setBackground(new Color(219, 128, 222));

        displayCardLayoutPanel(centerPanelLayout);

        displayTextFields(centerPanelLayout);
        displayButtons(centerPanelLayout);

        //-----------------------------------------------------------------
        mainPanel.setVisible(true);
        mainFrame.add(mainPanel, borderLayout.CENTER);

    }


    //Source: CardLayout Demo Project
    // Link: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/
    // javase/tutorial/uiswing/examples/layout/CardLayoutDemoProject/src/layout/CardLayoutDemo.java

    public void displayCardLayoutPanel(BorderLayout centerPanelLayout) {

        cardHolderPanel = new JPanel();
        cardHolderPanel.setLayout(cardLayout);
        cardHolderPanel.setBackground(new Color(255, 159, 19));

        //put more panels on top
        p1 = new JPanel();
        p1.setBackground(new Color(122, 123, 123));

        p2 = new JPanel();
        p2.setBackground(new Color(252, 7, 130));
        JButton returnButton = new JButton("Return");
        p2.add(returnButton, BorderLayout.NORTH);

        JButton clearAllButton = new JButton("Clear All");
        p2.add(clearAllButton);

        displayCallLogTable();

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isCallingLogRowNotSelected()) {
                    if (e.getSource() == clearAllButton) {
                        callingLogTableModel.removeRow(callingLogTable.getSelectedRow());
                    }
                } else {
                    displayInvalidRowSelectionMessage();
                }

            }
        });

        cardHolderPanel.add(p1, "Panel with user info");
        cardHolderPanel.add(p2, "Panel with call log");
        cardLayout.first(cardHolderPanel);


        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == returnButton) {
                    cardLayout.show(cardHolderPanel, "Panel with user info");
                }
            }
        });

        mainPanel.add(cardHolderPanel, centerPanelLayout.CENTER);

    }

    //todo
    private void displayCallLogTable() {
        callingLogTable = new JTable();
        callingLogTableModel = new DefaultTableModel();
        callingLogTable.setModel(callingLogTableModel);
        //callingLogTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        callingLogTableModel.addColumn("Call History");
        callingLogCellRenderer = new DefaultTableCellRenderer();

        //set cell text placing to center
        callingLogCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // apply center placing to column
        // Source: https://www.tabnine.com/code/java/methods/javax.swing.table.DefaultTableCellRenderer/
        // setHorizontalAlignment
        // Source: https://docs.oracle.com/javase/7/docs/api/javax/swing/table/TableColumn.html
        TableColumn callHistoryColumn;
        callHistoryColumn = callingLogTable.getColumnModel().getColumn(0);
        callHistoryColumn.setCellRenderer(callingLogCellRenderer);

        callingLogTable.setRowSelectionAllowed(false);
        callingLogTable.setCellSelectionEnabled(false);

        JScrollPane scrollPane = new JScrollPane(callingLogTable);
        callingLogTable.setBackground(new Color(213, 199, 199, 255));
        callingLogTable.setFillsViewportHeight(true);
        callingLogTable.setVisible(true);
        scrollPane.setVisible(true);

        p2.add(scrollPane, BorderLayout.CENTER);

    }


    //todo: get contents of table
    private void storeContactListTableData() {

        for (int i = 0; i < contactListTable.getRowCount(); i++) {
            String name = String.valueOf(contactListTable.getValueAt(i, 0));
            String phoneNumber = String.valueOf(contactListTable.getValueAt(i, 1));
            String email = String.valueOf(contactListTable.getValueAt(i, 2));
            String type = String.valueOf(contactListTable.getValueAt(i, 3));

            Contact contact = new Contact(name, phoneNumber, email, type);

            //adds them to row components to be added in the table when load is pressed.
            //loadedRowComponents = new String[][] {{name, phoneNumber, email, type}};

            if (!contactList.getAllContacts().contains(contact)) {
                contactList.addContact(contact);
            }

        }

    }

    //todo: get contents of table
    private void storeCallingLogTableData() {

        for (int i = 0; i < callingLogTable.getRowCount(); i++) {
            String name = String.valueOf(contactListTable.getValueAt(i, 0));
            String phoneNumber = "1111111111";
            String email = "defaultemail@email.com";
            String type = "FRIEND";

            Contact contactWithDefaultFields = new Contact(name, phoneNumber, email, type);
            callingLog.makeCall(contactWithDefaultFields);
        }

    }



    private void displayButtons(BorderLayout centerPanelLayout) {
        JPanel helperPanel2 = new JPanel();

        //source: https://www.youtube.com/watch?v=pDqjHozkMBs
        FlowLayout helperLayout = new FlowLayout(FlowLayout.LEADING);
        helperPanel2.setBackground(new Color(88, 115, 98));
        helperPanel2.setLayout(helperLayout);
        helperPanel2.setPreferredSize(new Dimension(100, 35));
        helperPanel2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        //helperPanel2.add(Box.createRigidArea(new Dimension(100, 0)));
        helperPanel2.add(addButton);
        helperPanel2.add(modifyButton);
        helperPanel2.add(deleteButton);
        helperPanel2.add(callButton);
        performButtonAction();

        //-----------------------------------------------------------------
        helperPanel2.setVisible(true);
        mainPanel.add(helperPanel2, centerPanelLayout.SOUTH);
    }



    private void displayTextFields(BorderLayout centerPanelLayout) {
        //-------------------------helper----------------------------
        JPanel helperPanel1 = new JPanel();

        BoxLayout helperLayout = new BoxLayout(helperPanel1, BoxLayout.Y_AXIS);
        helperPanel1.setBackground(new Color(142, 222, 14));
        helperPanel1.setLayout(helperLayout);
        helperPanel1.setPreferredSize(new Dimension(125, 50));

        nameField.setBorder(BorderFactory.createLoweredBevelBorder());
        phoneField.setBorder(BorderFactory.createLoweredBevelBorder());
        emailField.setBorder(BorderFactory.createLoweredBevelBorder());
        typeField.setBorder(BorderFactory.createLoweredBevelBorder());

        helperPanel1.add(Box.createRigidArea(new Dimension(0, 8)));
        helperPanel1.add(nameField);
        helperPanel1.add(Box.createRigidArea(new Dimension(0, 8)));
        helperPanel1.add(phoneField);
        helperPanel1.add(Box.createRigidArea(new Dimension(0, 8)));
        helperPanel1.add(emailField);
        helperPanel1.add(Box.createRigidArea(new Dimension(0, 8)));
        helperPanel1.add(typeField);
        helperPanel1.add(Box.createRigidArea(new Dimension(0, 120)));

        //-----------------------------------------------------------------
        helperPanel1.setVisible(true);
        mainPanel.add(helperPanel1, centerPanelLayout.WEST);
    }


    private void displayBottomPanel() {
        BorderLayout bottomPanelLayout = new BorderLayout();

        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanel.setBackground(new Color(109, 123, 162));
        bottomPanel.setPreferredSize(new Dimension(50, 175));

        JLabel contactListLabel = new JLabel("Contact List");
        contactListLabel.setForeground(Color.WHITE);
        contactListLabel.setFont(new Font("Verdana", Font. BOLD, 15));
        //contactListLabel.setBorder(BorderFactory.createEtchedBorder());

        bottomPanel.add(contactListLabel, bottomPanelLayout.NORTH);
        displayContactListTable();

        //-----------------------------------------------------------------
        bottomPanel.setVisible(true);
        mainFrame.add(bottomPanel, borderLayout.SOUTH);

    }

    private void displayLeftPanel() {

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(171, 184, 227));
        leftPanel.setPreferredSize(new Dimension(100, 50));

        // create labels
        JLabel contactNameLabel = new JLabel("Name");
        JLabel contactPhoneNumberLabel = new JLabel("Phone Number");
        JLabel contactEmailLabel = new JLabel("Email");
        JLabel contactTypeLabel = new JLabel("Type");

        // add labels
        leftPanel.add(Box.createRigidArea(new Dimension(15, 10)));
        leftPanel.add(contactNameLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(15, 10)));
        leftPanel.add(contactPhoneNumberLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(15, 10)));
        leftPanel.add(contactEmailLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(15, 10)));
        leftPanel.add(contactTypeLabel);

        //-----------------------------------------------------------------
        leftPanel.setVisible(true);
        mainFrame.add(leftPanel, borderLayout.WEST);

    }

    private void displayContactListTable() {

        //source: https://docs.oracle.com/javase/7/docs/api/javax/swing/table/DefaultTableModel.html#DefaultTableModel()
        contactListTableModel = new DefaultTableModel();
        contactListTable = new JTable();

        //source: https://www.youtube.com/watch?v=hfwO0Jmdq7c
        contactListTable.setModel(contactListTableModel);
        contactListTableModel.addColumn("Name");
        contactListTableModel.addColumn("Phone Number");
        contactListTableModel.addColumn("Email");
        contactListTableModel.addColumn("Type");

        contactListTable.setRowSelectionAllowed(true);
        contactListTable.setCellSelectionEnabled(true);

        // Source: https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#simple
        //adding scroll pane to table
        JScrollPane scrollPane = new JScrollPane(contactListTable);
        contactListTable.setBackground(new Color(201, 215, 253, 255));
        contactListTable.setFillsViewportHeight(true);
        scrollPane.setVisible(true);
        bottomPanel.add(scrollPane);

    }



    private void performButtonAction() {
        performAddButtonTask();
        performModifyButtonTask();
        performDeleteButtonTask();
        performCallButtonTask();
        performSaveButtonTask();
        performLoadButtonTask();

    }

    private void performLoadButtonTask() {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loadButton) {
                    loadFromFile();
                    convertContactListToObject();
                }
            }
        });
    }

    private void performSaveButtonTask() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == saveButton) {
                    storeContactListTableData();
                    storeCallingLogTableData();
                    saveProgress();
                }
            }
        });
    }

    private void performAddButtonTask() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addButton) {

                    String name;
                    String phoneNumber;
                    String email;
                    String type;

                    name = nameField.getText();
                    if (checkInvalidName(name)) {
                        phoneNumber = phoneField.getText();
                        if (checkPhoneNumber(phoneNumber)) {
                            email = emailField.getText();
                            if (checkEmail(email)) {
                                type = typeField.getText();
                                if (checkType(type)) {
                                    rowComponents = new String[]{name, phoneNumber, email, type};
                                    contactListTableModel.addRow(rowComponents);
                                    clearFields();
                                } else {
                                    typeField.setText("");
                                }
                            } else {
                                emailField.setText("");
                            }
                        } else {
                            phoneField.setText("");
                        }
                    } else {
                        nameField.setText("");
                    }


                    //source: https://www.youtube.com/watch?v=hfwO0Jmdq7c
//                    rowComponents = new String[]{name, phoneNumber, email, type};
//                    contactListTableModel.addRow(rowComponents);
//                    clearFields();
                }
            }
        });
    }


    private void performCallButtonTask() {
        callButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isContactListRowNotSelected()) {
                    if (e.getSource() == callButton) {

                        int selectedRow = contactListTable.getSelectedRow();

                        Object namesToAdd = contactListTable.getValueAt(selectedRow, 0);
                        String name = namesToAdd.toString();
                        callingLogTableModel.addRow(new String[]{name});
                        callingLogTable.getSelectionModel().clearSelection();

                        cardLayout.show(cardHolderPanel, "Panel with call log");
                    }
                } else {
                    displayInvalidRowSelectionMessage();
                }
            }
        });
    }

    private void performDeleteButtonTask() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isContactListRowNotSelected()) {
                    if (e.getSource() == deleteButton) {
                        int confirmation = JOptionPane.showConfirmDialog(mainPanel,
                                "Are you sure you want to delete this contact?",
                                "Delete Selected", JOptionPane.YES_NO_OPTION);
                        if (confirmation == JOptionPane.YES_OPTION) {
                            contactListTableModel.removeRow(contactListTable.getSelectedRow());
                        }
                    }
                } else {
                    displayInvalidRowSelectionMessage();
                }
            }
        });
    }

    //todo
    private void displayInvalidRowSelectionMessage() {
        ImageIcon oopsIcon = new ImageIcon("./data/oopsIcon.jpg");
        JOptionPane.showMessageDialog(mainPanel,
                "You must select a row to perform this operation",
                "Invalid Row Selection", JOptionPane.DEFAULT_OPTION, oopsIcon);
    }

    private void performModifyButtonTask() {
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == modifyButton) {
                    int selectedRowIndex = contactListTable.getSelectedRow();

                    if (!isContactListRowNotSelected()) {

                        String newName = JOptionPane.showInputDialog(mainPanel,
                                "Enter new name of Contact");
                        if (checkInvalidName(newName)) {
                            contactListTable.setValueAt(newName, selectedRowIndex, 0);
                        } else {
                            displayInvalidNameMessage();
                        }

                        String newPhone = JOptionPane.showInputDialog(mainPanel,
                                "Enter new phone number of Contact");
                        if (checkPhoneNumber(newPhone)) {
                            contactListTable.setValueAt(newPhone, selectedRowIndex, 1);
                        } else {
                            displayInvalidPhoneMessage();
                        }

                        String newEmail = JOptionPane.showInputDialog(mainPanel,
                                "Enter new email of Contact");
                        if (checkEmail(newEmail)) {
                            contactListTable.setValueAt(newEmail, selectedRowIndex, 2);
                        } else {
                            displayInvalidEmailMessage();
                        }

                        String newType = JOptionPane.showInputDialog(mainPanel,
                                "Enter new type of Contact");
                        if (checkType(newType)) {
                            contactListTable.setValueAt(newType, selectedRowIndex, 3);
                        } else {
                            displayInvalidTypeMessage();
                        }

                    } else {
                        displayInvalidRowSelectionMessage();
                    }

                }
            }
        });
    }

    public void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        typeField.setText("");
    }

    private boolean isContactListRowNotSelected() {
        return contactListTable.getSelectionModel().isSelectionEmpty();
    }

    private boolean isCallingLogRowNotSelected() {
        return callingLogTable.getSelectionModel().isSelectionEmpty();
    }



    //EFFECTS: checks if name entered only contains alphabets. Otherwise prompts
    // user to enter contact's information again
    // Source: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
    private boolean checkInvalidName(String name) {
        if (!name.matches("([A-z]|[a-z])+")) {
            displayInvalidNameMessage();
            nameField.setText("");
            return false;
        } else {
            return true;
        }
    }

    private void displayInvalidNameMessage() {
        JOptionPane.showMessageDialog(mainPanel, "Name can only contain alphabets!");
    }


    //EFFECTS: checks if phone number entered is 10 digits long and doesn't start with a 0.
    //Otherwise prompts user to enter contact's information again
    //source: https://stackoverflow.com/questions/24592808/regular-expression-in-java-validating-user-input
    private boolean checkPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("[1-9][0-9]{9}")) {
            displayInvalidPhoneMessage();
            phoneField.setText("");
            return false;
        } else {
            return true;
        }
    }

    private void displayInvalidPhoneMessage() {
        JOptionPane.showMessageDialog(mainPanel, "Invalid Phone number entered. \n "
                + "Phone number must be 10 digits long and it must not start with a 0 ");
    }

    //EFFECTS: checks if email entered matches the given email pattern
    //Otherwise prompts user to enter contact's information again
    private boolean checkEmail(String email) {
        if (!email.matches("[A-z]+([0-9]*[A-z]*)*\\@[a-z]+\\.com")) {
            displayInvalidEmailMessage();
            emailField.setText("");
            return false;
        } else {
            return true;
        }
    }

    private void displayInvalidEmailMessage() {
        JOptionPane.showMessageDialog(mainPanel, "Invalid email entered.");
    }

    //EFFECTS: checks if type entered is one of FAMILY, WORK, or FRIEND.
    //Otherwise prompts user to enter contact's information again
    private boolean checkType(String type) {
        if (!type.matches("FAMILY|FRIEND|WORK")) {
            displayInvalidTypeMessage();
            typeField.setText("");
            return false;
        } else {
            return true;
        }
    }

    private void displayInvalidTypeMessage() {
        JOptionPane.showMessageDialog(mainPanel, "Incorrect Type entered \n "
                + "Please enter one of FAMILY/FRIEND/WORK.");
    }




//    //----------------------------------------PHASE 2 METHODS----------------------------------------------------
//
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

    public void convertContactListToObject() {
        for (Contact c: contactList.getAllContacts()) {
            String loadedName = c.getName();
            String loadedPhoneNumber = c.getPhoneNumber();
            String loadedEmail = c.getEmail();
            String loadedType = c.getType();
            loadedRowComponents = new String[]{loadedName, loadedPhoneNumber, loadedEmail, loadedType};
            contactListTableModel.addRow(loadedRowComponents);

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


}
