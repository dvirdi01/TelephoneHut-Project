# TelephoneHut:The Modern Phonebook

## What is the TelephoneHut?

For my Personal Project, I will be designing a TelephoneHut which is a Phonebook application where users can store, 
modify, or retrieve their contacts, manage calls, and view their calling history. Similar to a phonebook app built 
inside our phones, TelephoneHut is a smart phonebook application that provides people with a simple yet secure way to 
store their contacts’ personal information all in one place. Although this application is easy to use, its sophisticated 
design features allow users to add ample amounts of details regarding their contacts. Rather than just saving people’s 
names, the user can also add additional details about their contacts, such as their email IDs, and what type of category 
they belong in (*FAMILY, FRIEND, or WORK*). Along with that, this application allows users to track the number of calls 
they have made and view their calling history so that they can reflect on their daily usage of this app as well. 
TelephoneHut is designed for people of all ages, and it can be easily accessed through one’s desktop. With the 
continuous innovations in technology, many people keep updating their phones to keep up with the latest trends. With the 
ever-changing designs of phones and apps, it also becomes more difficult for the older generation to keep track of the 
trending cell phone features. Nevertheless, the simplicity of TelephoneHut is particularly useful for elders as they 
can use it to easily store and retrieve their contacts in a secure manner.

I am interested in making my TelephoneHut because I think it will be a great way for me to get acquainted with how I can
build a user-friendly application that can be used by everyone. TelephoneHut can provide users with an alternative way 
to store their contacts in their laptops or computers. Consequently, if someone happens to lose or damage their phone, 
they would not run the risk of losing all their contacts. Moreover, I want to get a taste of what app designing looks 
like. Although this project is just a desktop application, I want to hone my creativity by designing a project that 
functions like a real app. Choosing to design a phonebook application will allow me to learn more about coding in Java 
and how I can build a sophisticated application, which will help me build more projects in the future as well.


## User Stories
- As a user, I want to be able to add any number of contacts into my contact list.
- As a user, I want to be able to view any contact of my choice.
- As a user, I want to be able to view all my contacts displayed together.
- As a user, I want to be able to modify a contact’s information in my phonebook.
- As a user, I want to be able to delete a contact from my phonebook.
- As a user, I want to be able to make calls and access my calling log at any given time.
- As a user, I want to be able to either continue doing operations in my phonebook, or exit it altogether. 
- As a user, I want to have the option of saving my phonebook to file or quit the application if I don’t want to save 
it. 
- As a user, when I start the application, I want to be given the option to load my saved phonebook from the file.

# Instructions for Grader
- My first required event allows you to enter a contact into your Contact List which is a table that displays 
all your contacts. You can generate the first required event by entering the name, phone number, email, and 
type of the contact that you want to add to your contact book in the text fields and then pressing the “Add” button. 
If you have entered any of the four fields incorrectly, a dialog box will appear to let you know that you need to 
enter that information again. If all information about the contact is entered correctly, the contact and his 
information will be added in the Contact List at the bottom half of the screen. 

- My second required event allows you to delete a previously added contact. You can generate the second 
required event by first selecting the row that you want to delete from the Contact List and then clicking the 
“Delete” button. You will then see a confirmation dialog box that will ask you if you are sure that you want 
to delete that contact. If you click yes, the selected contact will be deleted from the Contact List, and if 
you click no, the contact will not be deleted. If you do not select a row before clicking the “Delete” button, 
an error message will be displayed that lets you know that you need to select a row first. 

- Although optional, I have also implemented more user stories. These include the ability to modify a 
contact’s information, call a contact, view the calling log, and delete contents of the calling log. 
If the user selects a row from the Contact List and clicks the “Modify” button, dialog boxes will display 
which will ask you to enter the contact’s new name, phone number, email, and type. The user’s information 
will be updated accordingly. If the user selects a Contact and clicks on the “Call” button, the selected 
contact’s name will be added in the calling log. To go back to the main panel, the user can click the “Return” 
button. Similarly, if the user wants to delete a contact’s name from their calling log, they can select that 
row and click the “Clear” button. If the user wants to simply view their calling log without making any calls 
first, they can click on the “View” button. 

- The visual component that I have added is displayed right away when you run my code. It is a picture of a 
blue user icon which is displayed in the centre of my frame and consists of the author’s information written 
beside it.  The second visual that I have added is a small phonebook Icon that is displayed on the top left 
corner of my JFrame when you run my code. You can view it by simply running my code. The third visual component 
is a picture of a bird that is displayed when you have not selected a row from the Contact List before clicking 
on either “Modify”, “Delete”, or “Call” buttons. The user can view this visual by purposely entering wrong words 
for name, phone number, email, and type (for example, entering a number instead of a name in the name text field, 
entering a phone number that is more than 10 digits long etc) and then clicking the “Add” button. 

- You can save the state of your application by exiting the JFrame. If you exit the application, a dialog 
box will appear that will ask you if you want to save your data. If you click “Yes”, your data will be saved, 
and otherwise, it will not be saved. 

- If you want to reload the state of your application when you start a new session, you can do so by 
clicking “yes” in the dialog box that asks you if you want to load your previous data from file. This dialog 
box appears every time you run the application. The loaded Contacts can then be seen in the Contact List and 
previous calls made will be added back to the Call History Table. 







