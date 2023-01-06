///////////////////////////////////////////////////////////////
// Programmer:  Steven Vanni (vanni@uiowa.edu)
// Date:        09/25/21
// Name:        Profile.cpp
// Description: Our representation of a Profile.
///////////////////////////////////////////////////////////////

#include "Profile.h"
#include <iostream>
using namespace std;

/**
 * The constructor for the profile class.
 * @param _name - The name of the Profile; by default is "NoName".
 * @param _status - The Profile's status; by default is an empty string.
 */
Profile::Profile(const string _name, const string _status, const bool _validityoverride) {
    setName(_name, _validityoverride);
    setStatus(_status);
}

// Get methods for `name` and `status`.
string Profile::getName() { return name; }
string Profile::getStatus() { return status; }

// Small == overload to compare Profiles.
// todo: never got these to work :p
/*
bool operator==(Profile p1, Profile p2) {
    return p1.getName() == p2.getName();
}
bool operator!=(Profile p1, Profile p2) {
    return p1.getName() != p2.getName();
}
*/

/**
 * Sets the Profile's name.
 * @param _name - The new name of the Profile.
 * @param _validityoverride - An override to name validity; if set to true, invalid names can be added to profiles.
 */
void Profile::setName(const string _name, const bool _validityoverride) {
    if(_name.empty()) {
        cout << "You can't use an empty string as a name!" << endl;
        return;
    }

    for(int i = 0; i < invalid_length; i++) {
        if(_name == invalid[i] && !_validityoverride) {
            cout << "The chosen name is not allowed." << endl;
            return;
        }
    }

    for(int i = 0; i < _name.length(); i++) {
        if(_name[i] == ' ') {
            cout << "This name includes a space!" << endl;
            return;
        }
    }
    name = _name;
}

/**
 * Sets the Profile's status.
 * @param _status - The new status of the Profile.
 */
void Profile::setStatus(const string _status) {
    status = _status;
    cout << "Status set for " << getName() << " to \"" << _status << "\"" << endl;
}

/**
 * Checks if this Profile is friends with `_friend`.
 * @param _friend
 * @return The index of the friend in this Profile's `friends` vector; -1 if it doesn't exist.
 */
int Profile::isFriend(const string _friend) {
    for(int i = 0; i < friends.size(); i++) {
        if(friends[i] == _friend) {
            return i;
        }
    }
    return -1;
}

/**
 * Adds a friend to a Profile.
 * @param _friend - The friend to add.
 */
void Profile::addFriend(const string _friend) {
    if(getName() == _friend) {
        cout << _friend << " tried to add themself as friend" << endl;
        return;
    }
    int friend_i = isFriend(_friend);
    if(friend_i != -1) {
        cout << name << " already has " << _friend << " as a friend" << endl;
        return;
    } else {
        friends.push_back(_friend);
        cout << _friend << " added as a friend to " << name << endl;
    }
}

/**
 * Deletes a friend from a Profile.
 * @param _friend - The friend to delete.
 */
void Profile::deleteFriend(const string _friend) {
    if(getName() == _friend) {
        cout << _friend << " tried to delete theirself as friend" << endl;
        return;
    }
    int friend_i = isFriend(_friend);
    if(friend_i == -1) {
        cout << name << " was not a friend of " << _friend << endl;
        return;
    } else {
        friends.erase(friends.begin() + friend_i);
        cout << _friend << " deleted as a friend" << endl;
    }
}

/**
 * Prints information about a Profile to the terminal.
 */
void Profile::print() {
    cout << "Name:   " << name << endl;
    cout << "Status: " << status << endl;
    cout << "Friends:" << endl;
    for(int i = 0; i < friends.size(); i++) {
        cout << "    " << friends[i] << endl;
    }
}