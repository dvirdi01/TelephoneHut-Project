package ui;

import model.CallingLog;
import model.Contact;
import model.ContactList;
import model.EventLog;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;


/**
 This class represents a phonebook which contains all information
 about the contacts stored in the book. It allows user to add contacts,
 view contacts, make calls, and view their calling history
 Source and inspiration taken from: https://github.students.cs.ubc.ca/CPSC210/TellerApp
 //NOTE: Sources are cited at the end of this class
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
    JPanel mainPanel = new JPanel();
    JPanel topPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel cardHolderPanel;
    JPanel p1;
    JPanel p2;

    //Buttons
    JButton addButton = new JButton("Add");
    JButton modifyButton = new JButton("Modify");
    JButton deleteButton = new JButton("Delete");
    JButton callButton = new JButton("Call");
    JButton viewCallLogButton = new JButton("View");
    JButton clearAllButton = new JButton("Clear");
    JButton returnButton = new JButton("Return");
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
    String[] callLogRowComponents;


    //MODIFIES: this
    //EFFECTS: Instantiates a Phonebook with no contacts added and empty call log, sets up
    //json writers and json readers and the frame and panels
    public Phonebook() throws FileNotFoundException {

        jsonWriterCallingLog = new JsonWriter(CALLING_STORE);
        jsonWriterContactList = new JsonWriter(CONTACTLIST_STORE);
        jsonReaderCallingLog = new JsonReader(CALLING_STORE);
        jsonReaderContactList = new JsonReader(CONTACTLIST_STORE);

        setUpFrame();
        setUpPanel();

    }


    @SuppressWarnings("methodlength")
    //MODIFIES: this
    //EFFECTS: sets up the JFrame
    private void setUpFrame() {
        ImageIcon frameIcon = new ImageIcon("./data/phonebookImageOption2.jpg");
        mainFrame = new JFrame();
        mainFrame.setTitle("Telephone Hut");
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(borderLayout);
        mainFrame.getContentPane().setBackground(new Color(24, 45, 86, 255));
        mainFrame.setIconImage(frameIcon.getImage());
        mainFrame.setResizable(false);
        mainFrame.addWindowListener(new WindowListener() {


            //MODIFIES: this
            //EFFECTS: displays a dialog box which asks user if they want to reload their previous data
            //if they say yes, load data back on contact list and calling history, otherwise do nothing
            @Override
            public void windowOpened(WindowEvent e) {
                int pane = JOptionPane.showConfirmDialog(mainFrame,
                        "Do you want to load your previous data?",
                        "Load Progress", JOptionPane.YES_NO_OPTION);
                if (pane == JOptionPane.YES_OPTION) {
                    loadFromFile();
                    loadBackContactList();
                    loadBackCallingLog();
                }
            }

            //MODIFIES: this
            //EFFECTS: displays a dialog box which asks user if they want to save their progress before exiting
            //if they say yes, save contact list table and calling history table data to file, otherwise doesn't save
            //and prints out all events that have been logged during one session.
            @Override
            public void windowClosing(WindowEvent e) {

                int pane = JOptionPane.showConfirmDialog(mainFrame,
                        "Do you want to save your progress before exiting?",
                        "Save Progress", JOptionPane.YES_NO_OPTION);
                if (pane == JOptionPane.YES_OPTION) {
                    saveProgress();

                } else {
                    mainFrame.dispose();
                }
                printLog();
            }

            //Not used:
            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

        });

    }

    //EFFECTS: prints out the logged events from the Eventlog
    private void printLog() {

        for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
            Event e = it.next();
            System.out.println(e.toString());
            System.out.println();
        }

    }


    //MODIFIES: this
    //EFFECTS: adds panels on top of frame
    private void setUpPanel() {
        displayTopPanel();
        displayMainPanel();
        displayBottomPanel();
        displayLeftPanel();
    }

    //MODIFIES: this
    //EFFECTS: displays the top panel and its components on JFrame
    private void displayTopPanel() {
        BoxLayout topPanelLayout = new BoxLayout(topPanel, BoxLayout.X_AXIS);
        topPanel.setLayout(topPanelLayout);
        topPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        topPanel.setBackground(new Color(2, 22, 82));
        topPanel.setPreferredSize(new Dimension(50, 30));
        topPanel.setVisible(true);

        // create label
        JLabel topPanelLabel = new JLabel();
        topPanelLabel.setText("Contacts Manager");
        topPanelLabel.setForeground(Color.WHITE);
        topPanelLabel.setFont(new Font("Verdana", Font. BOLD, 18));


        topPanel.add(Box.createRigidArea(new Dimension(70, 30)));
        topPanel.add(topPanelLabel);

        //-----------------------------------------------------------------
        topPanel.setVisible(true);
        mainFrame.add(topPanel, borderLayout.NORTH);
    }


    //MODIFIES: this
    //EFFECTS: displays center panel where user can enter their contacts and view their
    // user information
    private void displayMainPanel() {
        BorderLayout centerPanelLayout = new BorderLayout();
        mainPanel.setLayout(centerPanelLayout);

        displayCardLayoutPanel(centerPanelLayout);
        displayTextFields(centerPanelLayout);
        displayButtons(centerPanelLayout);

        //-----------------------------------------------------------------
        mainPanel.setVisible(true);
        mainFrame.add(mainPanel, borderLayout.CENTER);

    }



    @SuppressWarnings("methodlength")
    //MODIFIES: this
    //EFFECTS: adds panels that displays author's info
    // and calling history upon button click

    // Source: CardLayout Demo Project
    // Link: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/
    // javase/tutorial/uiswing/examples/layout/CardLayoutDemoProject/src/layout/CardLayoutDemo.java
    public void displayCardLayoutPanel(BorderLayout centerPanelLayout) {

        cardHolderPanel = new JPanel();
        cardHolderPanel.setLayout(cardLayout);

        Color color = new Color(225, 225, 231);
        p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEADING));
        p1.setBackground(color);
        ImageIcon userIcon = new ImageIcon("./data/userIcon2.png");


        JPanel lastPanel = new JPanel();
        lastPanel.setPreferredSize(new Dimension(150, 170));
        lastPanel.setBackground(color);
        JLabel userLabel = new JLabel(userIcon);
        lastPanel.add(userLabel);

        //------------------------------------------------------------
        JPanel oneLastPanel = new JPanel();
        oneLastPanel.setPreferredSize(new Dimension(170, 175));
        oneLastPanel.setLayout(new BoxLayout(oneLastPanel, BoxLayout.Y_AXIS));
        oneLastPanel.setBackground(color);

        JLabel userInfo = new JLabel("User Information");
        userInfo.setFont(new Font("Verdana", Font. BOLD, 12));
        JLabel userName = new JLabel("Name: Divjot Virdi");
        JLabel numberOfContacts = new JLabel("Phone Number: XXXXXXXXXX");
        JLabel school = new JLabel("School: UBC");
        JLabel year = new JLabel("Year Level: 2");

        oneLastPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        oneLastPanel.add(userInfo);
        oneLastPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        oneLastPanel.add(userName);
        oneLastPanel.add(numberOfContacts);
        oneLastPanel.add(school);
        oneLastPanel.add(year);

        p1.add(lastPanel);
        p1.add(oneLastPanel);
        //------------------------------------------------------------
        p2 = new JPanel();
        p2.setBackground(new Color(4, 88, 166));
        p2.add(returnButton, BorderLayout.NORTH);

        p2.add(clearAllButton);
        displayCallLogTable();

        cardHolderPanel.add(p1, "Panel with user info");
        cardHolderPanel.add(p2, "Panel with call log");
        cardLayout.first(cardHolderPanel);

        mainPanel.add(cardHolderPanel, centerPanelLayout.CENTER);

    }

    //MODIFIES: this
    //EFFECTS: creates a call log which is displayed upon button click
    private void displayCallLogTable() {
        callingLogTable = new JTable();
        callingLogTableModel = new DefaultTableModel();
        callingLogTable.setModel(callingLogTableModel);

        callingLogTableModel.addColumn("Call History");
        callingLogCellRenderer = new DefaultTableCellRenderer();
        callingLogCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Source: https://www.tabnine.com/code/java/methods/javax.swing.table.DefaultTableCellRenderer/
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


    //MODIFIES: this
    //EFFECTS: displays buttons to add, delete, modify contacts, as well as to make a call
    // and view call history
    private void displayButtons(BorderLayout centerPanelLayout) {
        JPanel helperPanel2 = new JPanel();

        //source: https://www.youtube.com/watch?v=pDqjHozkMBs
        FlowLayout helperLayout = new FlowLayout(FlowLayout.LEADING);
        helperPanel2.setBackground(new Color(54, 54, 115));
        helperPanel2.setLayout(helperLayout);
        helperPanel2.setPreferredSize(new Dimension(100, 35));
        helperPanel2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        helperPanel2.add(addButton);
        helperPanel2.add(modifyButton);
        helperPanel2.add(deleteButton);
        helperPanel2.add(callButton);
        helperPanel2.add(viewCallLogButton);
        performButtonAction();

        //-----------------------------------------------------------------
        helperPanel2.setVisible(true);
        mainPanel.add(helperPanel2, centerPanelLayout.SOUTH);
    }



    //MODIFIES: this
    //EFFECTS: creates text fields on main panel which allows user to enter contact's name
    //phone number, email, and type.
    private void displayTextFields(BorderLayout centerPanelLayout) {
        JPanel helperPanel1 = new JPanel();

        BoxLayout helperLayout = new BoxLayout(helperPanel1, BoxLayout.Y_AXIS);
        helperPanel1.setBackground(new Color(180, 192, 211));
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


    //MODIFIES: this
    //EFFECTS: displays the bottom panel on JFrame where user can view all contacts that
    //they have added
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


    //MODIFIES: this
    //EFFECTS: displays labels corresponding to the text fields for user input.
    private void displayLeftPanel() {

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(180, 192, 211));
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


    //MODIFIES: this
    //EFFECTS: creates a table which displays contact information of every contact added
    //and adds the table to bottomPanel.
    private void displayContactListTable() {

        //source: https://docs.oracle.com/javase/7/docs/api/javax/swing/table/DefaultTableModel.
        //html#DefaultTableModel()
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
        contactListTable.setBackground(new Color(202, 210, 234, 255));
        contactListTable.setFillsViewportHeight(true);
        scrollPane.setVisible(true);
        bottomPanel.add(scrollPane);

    }


    //--------------------------------------------BUTTON EVENTS----------------------------------------------


    //EFFECTS: For every button on JFrame, allows their corresponding actions to happen
    private void performButtonAction() {
        performAddButtonTask();
        performModifyButtonTask();
        performDeleteButtonTask();
        performCallButtonTask();
        performViewButtonTask();
        performClearButtonTask();
        performReturnButtonTask();

    }


    @SuppressWarnings("methodlength")
    //MODIFIES: this
    //EFFECTS: if all contact information is valid, adds contact to contactListTable
    //and clears out text fields.
    //otherwise, displays error message related to the fields that are invalid.
    //source: https://www.youtube.com/watch?v=hfwO0Jmdq7c
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
                                    //added this
                                    Contact contact = new Contact(name, phoneNumber, email, type);
                                    //
                                    rowComponents = new String[]{name, phoneNumber, email, type};
                                    contactListTableModel.addRow(rowComponents);
                                    //added this
                                    contactList.addContact(contact);
                                    //
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

                }
            }
        });
    }


    @SuppressWarnings("methodlength")
    //MODIFIES: this
    //EFFECTS: allows user to modify a selected contact's information.
    //if a contact is not selected before button click, displays an error message
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

    @SuppressWarnings("methodlength")
    //MODIFIES: this
    //EFFECTS: deletes the selected contact. Displays an error message if
    //a contact is not selected before button click.
    private void performDeleteButtonTask() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isContactListRowNotSelected()) {
                    if (e.getSource() == deleteButton) {
                        //added this
                        int selectedRowIndex = contactListTable.getSelectedRow();
                        //
                        int confirmation = JOptionPane.showConfirmDialog(mainPanel,
                                "Are you sure you want to delete this contact?",
                                "Delete Selected", JOptionPane.YES_NO_OPTION);
                        if (confirmation == JOptionPane.YES_OPTION) {
//                            //added this
                            String name =  String.valueOf(contactListTable.getValueAt(selectedRowIndex, 0));
                            String phoneNumber =  String.valueOf(contactListTable.getValueAt(selectedRowIndex, 1));
                            String email =  String.valueOf(contactListTable.getValueAt(selectedRowIndex, 2));
                            String type =  String.valueOf(contactListTable.getValueAt(selectedRowIndex, 3));
                            Contact contact = new Contact(name, phoneNumber, email, type);

                            Contact nameToFind = contactList.getContactByName(name);
                            if (contact.equals(nameToFind)) {
                                contactList.deleteContact(contact);
                            }
                            contactListTableModel.removeRow(contactListTable.getSelectedRow());

                        }
                    }
                } else {
                    displayInvalidRowSelectionMessage();
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: adds contact's name in callLogTable upon button click. Displays error message
    //if a contact is not selected before button click.
    private void performCallButtonTask() {
        callButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isContactListRowNotSelected()) {
                    if (e.getSource() == callButton) {

                        int selectedRow = contactListTable.getSelectedRow();

                        Object namesToAdd = contactListTable.getValueAt(selectedRow, 0);
                        //added this//
                        String phoneToAdd = contactListTable.getValueAt(selectedRow, 1).toString();
                        String emailToAdd = contactListTable.getValueAt(selectedRow, 2).toString();
                        String typeToAdd = contactListTable.getValueAt(selectedRow, 3).toString();
                        //

                        String name = namesToAdd.toString();
                        callingLogTableModel.addRow(new String[]{name});

                        //added this
                        Contact contactToCall = new Contact(name, phoneToAdd, emailToAdd, typeToAdd);
                        callingLog.makeCall(contactToCall);
                        //
                        callingLogTable.getSelectionModel().clearSelection();

                        cardLayout.show(cardHolderPanel, "Panel with call log");
                    }
                } else {
                    displayInvalidRowSelectionMessage();
                }
            }
        });
    }


    //EFFECTS: Takes user to the panel that stores the callLogTable
    private void performViewButtonTask() {
        viewCallLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == viewCallLogButton) {
                    cardLayout.show(cardHolderPanel, "Panel with call log");
                }
            }
        });
    }


    //EFFECTS: if returnButton is clicked, takes user back to the panel
    // that displays user's information
    private void performReturnButtonTask() {
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == returnButton) {
                    cardLayout.show(cardHolderPanel, "Panel with user info");
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: removes the selected row in callLogTable upon button click.
    private void performClearButtonTask() {

        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clearAllButton) {
                    for (int i = 0; i < callingLogTable.getRowCount(); i++) {
                        callingLog.clearCallLog();
                        callingLogTableModel.setRowCount(0);
                    }
                }
            }
        });

    }

    //MODIFIES: this
    //EFFECTS: Loads contents of contactList and callLog from JSON file.
    // and adds them back to contactListTable and CallingLogTable
    private void performLoadButtonTask() {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loadButton) {
                    loadFromFile();
                    loadBackContactList();
                    loadBackCallingLog();
                }
            }
        });
    }


    //EFFECTS: saves the data from contactListTable and CallingLogTable into JSON file
    private void performSaveButtonTask() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == saveButton) {
                    saveProgress();
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: clears the text fields
    public void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        typeField.setText("");
    }


    //EFFECTS: returns true if a row in contactListTable is not selected, false otherwise
    private boolean isContactListRowNotSelected() {
        return contactListTable.getSelectionModel().isSelectionEmpty();
    }


    //EFFECTS: returns true if a row in callLogTable is not selected, false otherwise
    private boolean isCallingLogRowNotSelected() {
        return callingLogTable.getSelectionModel().isSelectionEmpty();
    }


    //----------------------------------------REGEX METHODS----------------------------------------------------



    //EFFECTS: displays an error message that prompts user to select a row
    //before doing any operation
    private void displayInvalidRowSelectionMessage() {
        ImageIcon oopsIcon = new ImageIcon("./data/oopsImage.png");
        JOptionPane.showMessageDialog(mainPanel,
                "You must select a row to perform this operation",
                "Invalid Row Selection", JOptionPane.DEFAULT_OPTION, oopsIcon);
    }


    //MODIFIES: this
    //EFFECTS: returns true if name entered only contains alphabets. If it doesn't,
    //displays an error message and clears name textfield and returns false
    //Source: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
    private boolean checkInvalidName(String name) {
        if (!name.matches("([A-z]|[a-z])+")) {
            displayInvalidNameMessage();
            nameField.setText("");
            return false;
        } else {
            return true;
        }
    }



    //MODIFIES: this
    //EFFECTS: returns true if phone number entered is 10 digits long and doesn't start with a 0.
    //If it doesn't, displays an error message and clears phonetextfield and returns false
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



    //EFFECTS: returns true if email entered matches the given email pattern
    //Otherwise displays error message, clears email text field and returns false;
    private boolean checkEmail(String email) {
        if (!email.matches("[A-z]+([0-9]*[A-z]*)*\\@[a-z]+\\.com")) {
            displayInvalidEmailMessage();
            emailField.setText("");
            return false;
        } else {
            return true;
        }
    }


    //EFFECTS: returns true if type entered is one of FAMILY, WORK, or FRIEND.
    //Other wise displays error message, clears type field, and returns false;
    private boolean checkType(String type) {
        if (!type.matches("FAMILY|FRIEND|WORK")) {
            displayInvalidTypeMessage();
            typeField.setText("");
            return false;
        } else {
            return true;
        }
    }

    //----------------------------------------ERROR MESSAGES----------------------------------------------------


    //EFFECTS: displays a message that lets user know why the name they entered
    //while adding a contact is invalid.
    private void displayInvalidNameMessage() {
        JOptionPane.showMessageDialog(mainPanel, "Name can only contain alphabets!");
    }


    //EFFECTS: displays a message that lets user know why the phone Number they entered
    //while adding a contact is invalid.
    private void displayInvalidPhoneMessage() {
        JOptionPane.showMessageDialog(mainPanel, "Invalid Phone number entered. \n "
                + "Phone number must be 10 digits long and it must not start with a 0 ");
    }

    //EFFECTS: displays a message that lets user know why the email they entered
    //while adding a contact is invalid.
    private void displayInvalidEmailMessage() {
        JOptionPane.showMessageDialog(mainPanel, "Invalid email entered.");
    }


    //EFFECTS: displays a message that lets user know why the type they entered
    //while adding a contact is invalid.
    private void displayInvalidTypeMessage() {
        JOptionPane.showMessageDialog(mainPanel, "Incorrect Type entered \n "
                + "Please enter one of FAMILY/FRIEND/WORK.");
    }



    //----------------------------------------SAVING METHODS----------------------------------------------------

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



    //----------------------------------------LOADING METHODS----------------------------------------------------

    //MODIFIES: this
    //EFFECTS: loads both contact list and calling log from file
    public void loadFromFile() {
        loadContactList();
        loadCallingLog();

    }


    // MODIFIES: this
    // EFFECTS: loads ContactList from file
    private void loadContactList() {
        try {
            contactList = jsonReaderContactList.readContactList();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainPanel, "Unable to read file from" + CONTACTLIST_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads the contactlist's contents back into contactListTable
    public void loadBackContactList() {
        for (Contact c: contactList.getAllContacts()) {
            String loadedName = c.getName();
            String loadedPhoneNumber = c.getPhoneNumber();
            String loadedEmail = c.getEmail();
            String loadedType = c.getType();
            loadedRowComponents = new String[]{loadedName, loadedPhoneNumber, loadedEmail, loadedType};
            contactListTableModel.addRow(loadedRowComponents);

        }

    }


    //MODIFIES: this
    //EFFECTS: loads the contents of callLog back into callLogTable
    public void loadBackCallingLog() {
        for (String s: callingLog.getCallingLog()) {
            callLogRowComponents = new String[] {s};
            callingLogTableModel.addRow(callLogRowComponents);
        }

    }

    // MODIFIES: this
    // EFFECTS: loads Calling Log from file
    private void loadCallingLog() {
        try {
            callingLog = jsonReaderCallingLog.readCallingLog();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainPanel,
                    "Unable to read file from" + CONTACTLIST_STORE);
        }
    }


    /**
     * Sources---------------------------------------------------------------------------------------------
     *
     * MAIN SOURCE: https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
     *
     * 1. Regex: https:
     * //stackoverflow.com/questions/24592808/regular-expression-in-java-validating-user-input
     *
     * 2. JOptionPanes:
     * https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
     *
     * 3. ScrollPanes and Tables:
     * https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#simple
     * https://www.youtube.com/watch?v=hfwO0Jmdq7c
     * https://docs.oracle.com/javase/7/docs/api/javax/swing/table/DefaultTableModel.html#DefaultTableModel()
     * https://www.tabnine.com/code/java/methods/javax.swing.table.DefaultTableCellRenderer/
     * https://docs.oracle.com/javase/7/docs/api/javax/swing/table/TableColumn.html
     * https://www.youtube.com/watch?v=hfwO0Jmdq7c
     * https://www.youtube.com/watch?v=Tfov0ERfdFk
     * https://www.youtube.com/watch?v=CQMpXGwHeYQ&t=520s
     * https://www.youtube.com/watch?v=F0Zq2fAUpXg&t=240s
     *
     * 4. Layouts:
     * https://www.youtube.com/watch?v=pDqjHozkMBs
     * Card Layout Demo Project: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https:
     * //docs.oracle.com/javase/tutorial/uiswing/examples/layout/CardLayoutDemoProject/src/layout/
     * CardLayoutDemo.java
     * https://www.youtube.com/watch?v=sAReaaTxNGU
     *
     */


}
