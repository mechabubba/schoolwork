///////////////////////////////////////////////////////////////
// Programmer:  Steven Vanni (vanni@uiowa.edu)
// Date:        09/24/21
// Name:        main.cpp
// Description: The runner file for homework three.
///////////////////////////////////////////////////////////////

#include <iostream>
#include "Profile.h"
#include "ProfileDatabase.h"
using namespace std;

int mainmenu();
int modifymenu();
Profile getProfile();
void addprofile();
void deleteprofile();
void addfriends(Profile p);
void deletefriends(Profile p);
void changestatus(Profile p);
void test();

ProfileDatabase db;

// The main loop.
int main() {
    int status = -1;
    while(status != 0) {
        status = mainmenu();
    }
    return 0;
}

/**
 * Helper function that returns an int from standard input.
 * @returns the value given.
 */
int getInt() {
    cout << "> ";
    string input;
    getline(cin, input);
    cout << endl;
    int value = stoi(input);
    return value;
}

/**
 * Helper function that returns a string from standard input.
 * @returns the value given.
 */
string getString() {
    cout << "> ";
    string input;
    getline(cin, input);
    cout << endl;
    return input;
}

/**
 * The main menu loop.
 * @returns `0` if we're finished; other numbers if we want to continue.
 */
int mainmenu() {
    cout << endl;
    cout << "Choose an option." << endl;
    cout << "1) Print names of all profiles" << endl;
    cout << "2) Add a profile" << endl;
    cout << "3) Delete a profile" << endl;
    cout << "4) Lookup/modify a profile" << endl;
    cout << "5) Run tests (this will add profiles to the db!)" << endl;
    cout << "6) Exit" << endl;

    int choice = getInt();
    switch(choice) {
        case 1: { // Print names of all profiles
            db.print();
            break;
        }
        case 2: { // Add a profile
            addprofile();
            break;
        }
        case 3: { // Delete a profile
            deleteprofile();
            break;
        }
        case 4: { // Lookup/modify a profile
            int status = -1;
            while(status != 0) {
                status = modifymenu();
            }
            break;
        }
        case 5: { // Run tests
            // I hid tests behind a setting in case someone wanted to start the program without running the tests.
            test();
            break;
        }
        case 6: { // Exit
            return 0;
        }
        default: {
            cout << "Unknown option chosen." << endl;
            break;
        }
    }

    return 1;
}

/**
 * The profile modification menu.
 * @returns `0` if we're finished; other numbers if we want to continue.
 */
int modifymenu() {
    cout << "Modifying a profile!" << endl;
    Profile p = getProfile();
    if(p.getName() == "__MissingProfile__") {
        cout << "Profile not found." << endl;
        return 0; // return to main menu
    }
    p.print();

    cout << endl;
    cout << "Choose an option." << endl;
    cout << "1) Add a friend" << endl;
    cout << "2) Delete a friend" << endl;
    cout << "3) Change status" << endl;
    cout << "4) Return to main menu" << endl;

    int choice = getInt();
    switch(choice) {
        case 1: { // Add a friend
            addfriends(p);
            break;
        }
        case 2: { // Delete a friend
            deletefriends(p);
            break;
        }
        case 3: { // Change status
            changestatus(p);
            break;
        }
        case 4: { // Return to main menu
            return 0;
        }
        default: {
            cout << "Unknown option chosen." << endl;
            break;
        }
    }

    return 1;
}

/**
 * Gets a profile based on input.
 * @return {Profile} The names profile; or the "__MissingProfile__" profile if one is not found.
 */
Profile getProfile() {
    cout << "What is the profiles name?" << endl;
    string name = getString();
    return db.getProfile(name);
}

/**
 * Adds a profile.
 */
void addprofile() {
    cout << "What would you like to name this profile?" << endl;
    string name = getString();
    cout << endl;

    Profile p(name);
    if(p.getName().empty()) {
        cout << "Failed to create a profile; this name is not allowed to be used." << endl;
        return;
    }
    int i = db.getProfileIndex(name);
    if(i != -1) {
        cout << "Failed to create a profile; this name is already in use." << endl;
        return;
    }

    cout << "What is this persons status?" << endl;
    string status = getString();
    p.setStatus(status);
    cout << endl;

    db.addProfile(p);
}

/**
 * Deletes a profile.
 */
void deleteprofile() {
    Profile p = getProfile();
    if(p.getName() == "__MissingProfile__") {
        cout << "Profile not found." << endl;
        return;
    }

    db.deleteProfile(p.getName());
}

/**
 * Adds a friend to a profile.
 * @param p1 - The profile to add a friend to.
 */
void addfriends(Profile p1) {
    cout << "Adding a friend to " << p1.getName() << "!" << endl;
    Profile p2 = getProfile();
    if(p2.getName() == "__MissingProfile__") {
        cout << "Profile not found." << endl;
        return;
    }

    db.addFriends(p1.getName(), p2.getName());
}

/**
 * Deletes a friend from a profile.
 * @param p1 - The profile to delete a friend from.
 */
void deletefriends(Profile p1) {
    cout << "Deleting a friend from " << p1.getName() << "!" << endl;
    Profile p2 = getProfile();
    if(p2.getName() == "__MissingProfile__") {
        cout << "Profile not found." << endl;
        return;
    }

    db.deleteFriends(p1.getName(), p2.getName());
}

/**
 * Changes a status of a profile.
 * @param p - The profile to change the status of.
 */
void changestatus(Profile p) {
    cout << "Changing the status of " << p.getName() << "." << endl;
    cout << "What do you want their status to be?" << endl;
    string status = getString();

    p.setStatus(status);
}

/**
 * Tests. :)
 */
void test() {
    cout << "### Creating a Profile named Jane" << endl;
    Profile person1("Jane", "Seeing someone");
    person1.print();
    cout << endl;

    cout << "### Adding friends to Jane" << endl;
    person1.addFriend("Joe");
    person1.addFriend("Sally");
    person1.print();
    cout << endl;

    // One of these should fail; Jane would have already existed in db.
    cout << "### Adding same person to DB" << endl;
    db.addProfile(person1);
    db.addProfile(person1);
    cout << endl;

    cout << "### Adding some more profiles + Creating complex relationships between them" << endl;
    Profile profileA("Sam", "programming");
    Profile profileB("Sally", "reading");
    Profile profileC("Sally", "sleeping");
    db.addProfile(profileA);
    db.addProfile(profileB);
    db.addProfile(profileC); // This shouldn't get added to the db, same name as personB.
    db.addFriends("Sam", "Sally");
    db.addFriends("Sam", "Sally");
    db.addFriends("Sam", "Jane");
    db.deleteFriends("Sam", "Jane");
    db.deleteFriends("Sam", "Sally");
    db.addFriends("Sally", "Jane");
    cout << endl;

    // this should work just fine; Mark should be only friends with Sam.
    cout << "### Adding one Profile and making it a friend of an existing Profile" << endl;
    Profile person2("Mark", "hangin' out");
    db.addProfile(person2);
    db.addFriends("Mark", "Sam");
    person2.print();
    cout << endl;

    cout << "### Printing the names of the DB" << endl;
    db.print();
    cout << endl;

    cout << "### Deleting a Profile + Updating friends" << endl;
    db.deleteProfile("Jane");
    db.print();
    profileB.print(); // Sally should no longer have Jane as a friend.
    cout << endl;

    cout << "### Updating someones status" << endl;
    person2.print();
    db.updateStatus("Mark", "gettin' work done"); // todo: this is the only thing that doesnt work for some reason. tried debugging it but never got it to work. hopefully will work eventually...
    person2.print();
}