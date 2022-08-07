package ui;

import model.CallingLog;
import model.ContactList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;


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

    private JsonWriter jsonWriterCallingLog;
    private JsonWriter jsonWriterContactList;
    private JsonReader jsonReaderCallingLog;
    private JsonReader jsonReaderContactList;

    JFrame mainFrame = new JFrame();
    BorderLayout borderLayout = new BorderLayout();

    //Panels
    JPanel mainPanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel bottomPanel = new JPanel();

    //Buttons
    JButton returnButton = new JButton("Return");
    JButton addButton = new JButton("Add");
    JButton modifyButton = new JButton("Modify");
    JButton deleteButton = new JButton("Delete");
    JButton callButton = new JButton("Call");

    //TextFields
    JTextField nameField = new JTextField();
    JTextField phoneField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField typeField = new JTextField();

    //Tables and table models
    JTable contactListTable;
    DefaultTableModel contactListTableModel;
    String[] rowComponents;
    TableCellEditor tableCellEditor;



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
    }


    private void setUpPanel() {
        displayTopPanel();
        displayMainPanel();
        displayBottomPanel();
        displayLeftPanel();
       //displayRightPanel();
    }

    private void displayTopPanel() {
        BorderLayout topPanelLayout = new BorderLayout();
        topPanel.setLayout(topPanelLayout);
        topPanel.setBackground(new Color(74, 86, 119));
        topPanel.setPreferredSize(new Dimension(50, 30));
        topPanel.setVisible(true);

        // create label
        JLabel topPanelLabel = new JLabel();
        topPanelLabel.setText("Contacts Manager");
        topPanelLabel.setForeground(Color.WHITE);
        topPanelLabel.setFont(new Font("Verdana", Font. BOLD, 18));
        topPanel.add(topPanelLabel, topPanelLayout.CENTER);

        //-----------------------------------------------------------------
        topPanel.setVisible(true);
        mainFrame.add(topPanel, borderLayout.NORTH);
    }

    private void displayMainPanel() {
        BorderLayout centerPanelLayout = new BorderLayout();
        mainPanel.setLayout(centerPanelLayout);
        mainPanel.setBackground(new Color(219, 128, 222));

        displayTextFields(centerPanelLayout);
        displayButtons(centerPanelLayout);

        //-----------------------------------------------------------------
        mainPanel.setVisible(true);
        mainFrame.add(mainPanel, borderLayout.CENTER);

    }

    private void displayButtons(BorderLayout centerPanelLayout) {
        JPanel helperPanel2 = new JPanel();

        //source: https://www.youtube.com/watch?v=pDqjHozkMBs
        FlowLayout helperLayout = new FlowLayout(FlowLayout.LEADING);
        helperPanel2.setBackground(new Color(88, 115, 98));
        helperPanel2.setLayout(helperLayout);
        helperPanel2.setPreferredSize(new Dimension(100, 35));

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
        helperPanel1.setBackground(new Color(142, 222, 154));
        helperPanel1.setLayout(helperLayout);
        helperPanel1.setPreferredSize(new Dimension(100, 50));

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

        bottomPanel.add(contactListLabel, bottomPanelLayout.NORTH);
        displayContactListTable();

        //-----------------------------------------------------------------
        bottomPanel.setVisible(true);
        mainFrame.add(bottomPanel, borderLayout.SOUTH);

    }

    private void displayLeftPanel() {

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(245, 203, 203));
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

        //setUpTypeColumn(contactListTable, contactListTable.getColumnModel().getColumn(3));

        contactListTable.setRowSelectionAllowed(true);
        contactListTable.setCellSelectionEnabled(true);
        //tableCellEditor.isCellEditable(true);


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

    }

    private void performAddButtonTask() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addButton) {
                    String name = nameField.getText();
                    checkInvalidName(name);

                    String phoneNumber = phoneField.getText();
                    checkPhoneNumber(phoneNumber);

                    String email = emailField.getText();
                    checkEmail(email);

                    String type = typeField.getText();
                    checkType(type);

                    //source: https://www.youtube.com/watch?v=hfwO0Jmdq7c
                    rowComponents = new String[]{name, phoneNumber, email, type};
                    contactListTableModel.addRow(rowComponents);
                    clearFields();
                }
            }
        });
    }

    private void performCallButtonTask() {
        callButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == callButton) {
                    //todo
                }
            }
        });
    }

    private void performDeleteButtonTask() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRowSelected()) {
                    if (e.getSource() == deleteButton) {
                        int confirmation = JOptionPane.showConfirmDialog(mainPanel,
                                "Are you sure you want to delete this contact?",
                                "Delete Selected", JOptionPane.YES_NO_OPTION);
                        if (confirmation == JOptionPane.YES_OPTION) {
                            contactListTableModel.removeRow(contactListTable.getSelectedRow());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(mainPanel,
                            "Please select a row to delete",
                            "Invalid Row Selection", JOptionPane.DEFAULT_OPTION);
                }
            }
        });
    }

    private void performModifyButtonTask() {
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == modifyButton) {

//                    Object nameSelected = contactListTableModel.getValueAt(contactListTable.getSelectedRow(), 0);
//                    Object phoneNumberSelected = contactListTableModel.getValueAt(contactListTable.getSelectedRow(),1);
//                    Object emailSelected = contactListTableModel.getValueAt(contactListTable.getSelectedRow(), 2);
//                    Object typeSelected = contactListTableModel.getValueAt(contactListTable.getSelectedRow(), 3);
//
//                    nameField.setText(String.valueOf(nameSelected));
//                    phoneField.setText(String.valueOf(phoneNumberSelected));
//                    emailField.setText(String.valueOf(emailSelected));
//                    typeField.setText(String.valueOf(typeSelected));
//
//                    contactListTableModel.removeRow(contactListTable.getSelectedRow());

                    int selectedRowIndex = contactListTable.getSelectedRow();
                    if (!isRowSelected()) {

                        String newName = JOptionPane.showInputDialog(mainPanel, "Enter new name of Contact");
                        if (checkInvalidName(newName)) {
                            contactListTable.setValueAt(newName, selectedRowIndex, 0);
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "Invalid name Entered");
                        }

                        String newPhone = JOptionPane.showInputDialog(mainPanel, "Enter new phone number of Contact");
                        if (checkPhoneNumber(newPhone)) {
                            contactListTable.setValueAt(newPhone, selectedRowIndex, 1);
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "Invalid phone Number Entered");
                        }

                        String newEmail = JOptionPane.showInputDialog(mainPanel, "Enter new email of Contact");
                        if (checkEmail(newEmail)) {
                            contactListTable.setValueAt(newEmail, selectedRowIndex, 2);
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "Invalid Email Entered");
                        }

                        String newType = JOptionPane.showInputDialog(mainPanel, "Enter new type of Contact");
                        if (checkType(newType)) {
                            contactListTable.setValueAt(newType, selectedRowIndex, 3);
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "Invalid Type Entered");
                        }

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

    private boolean isRowSelected() {
        return contactListTable.getSelectionModel().isSelectionEmpty();
    }



    //EFFECTS: checks if name entered only contains alphabets. Otherwise prompts
    // user to enter contact's information again
    // Source: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
    private boolean checkInvalidName(String name) {
        if (!name.matches("([A-z]|[a-z])+")) {
            JOptionPane.showMessageDialog(mainPanel, "Name can only contain alphabets!");
            nameField.setText("");
            return false;
        } else {
            return true;
        }
    }


    //EFFECTS: checks if phone number entered is 10 digits long and doesn't start with a 0.
    //Otherwise prompts user to enter contact's information again
    //source: https://stackoverflow.com/questions/24592808/regular-expression-in-java-validating-user-input
    private boolean checkPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("[1-9][0-9]{9}")) {
            JOptionPane.showMessageDialog(mainPanel, "Invalid Phone number entered. \n "
                    + "Phone number must be 10 digits long and it must not start with a 0 ");
            phoneField.setText("");
            return false;
        } else {
            return true;
        }
    }

    //EFFECTS: checks if email entered matches the given email pattern
    //Otherwise prompts user to enter contact's information again
    private boolean checkEmail(String email) {
        if (!email.matches("[A-z]+([0-9]*[A-z]*)*\\@[a-z]+\\.com")) {
            JOptionPane.showMessageDialog(mainPanel, "Invalid email entered.");
            emailField.setText("");
            return false;
        } else {
            return false;
        }
    }

    //EFFECTS: checks if type entered is one of FAMILY, WORK, or FRIEND.
    //Otherwise prompts user to enter contact's information again
    private boolean checkType(String type) {
        if (!type.matches("FAMILY|FRIEND|WORK")) {
            JOptionPane.showMessageDialog(mainPanel, "Incorrect Type entered \n "
                    + "Please enter one of FAMILY/FRIEND/WORK.");
            typeField.setText("");
            return false;
        } else {
            return true;
        }
    }



    //    private void displayRightPanel() {
//        rightPanel.setBackground(new Color(234, 205, 7));
//        rightPanel.setPreferredSize(new Dimension(50, 50));
//        rightPanel.setVisible(true);
//        mainFrame.add(rightPanel, borderLayout.EAST);
//
//
//
//
//    //----------------------------------------PHASE 2 METHODS----------------------------------------------------
//
//    //SAVING METHODS
//
//    //EFFECTS: saves both contact list and calling log into separate file.
//    public void saveProgress() {
//        saveContactList();
//        saveCallingLog();
//    }
//
//
//    //EFFECTS: saves contact list into file.
//    public void saveContactList() {
//        try {
//            jsonWriterContactList.open();
//            jsonWriterContactList.writeContactList(contactList);
//            jsonWriterContactList.closeWriter();
//            System.out.println("Saved your Contact List to " + CONTACTLIST_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + CONTACTLIST_STORE);
//        }
//    }
//
//
//    //EFFECTS: saves calling log into file.
//    public void saveCallingLog() {
//        try {
//            jsonWriterCallingLog.open();
//            jsonWriterCallingLog.writeCallingLog(callingLog);
//            jsonWriterCallingLog.closeWriter();
//            System.out.println("Saved your Calling Log to " + CALLING_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + CALLING_STORE);
//        }
//    }
//
//
//
//    //LOADING METHODS
//
//    //MODIFIES: this
//    //EFFECTS: loads both contact list and calling log from file and displays menu options again
//    public void loadFromFile() {
//        loadContactList();
//        loadCallingLog();
//        System.out.println("Loading complete");
//        goToMenu();
//    }
//
//
//    // MODIFIES: this
//    // EFFECTS: loads ContactList from file
//    private void loadContactList() {
//        try {
//            contactList = jsonReaderContactList.readContactList();
//            System.out.println("Loaded Contact List from " + CONTACTLIST_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + CONTACTLIST_STORE);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads Calling Log from file
//    private void loadCallingLog() {
//        try {
//            callingLog = jsonReaderCallingLog.readCallingLog();
//            System.out.println("Loaded Calling Log from " + CALLING_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + CALLING_STORE);
//        }
//    }
//

}
