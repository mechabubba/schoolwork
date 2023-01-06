///////////////////////////////////////////////////////////////
// Programmer:  Steven Vanni (vanni@uiowa.edu)
// Date:        09/27/21
// Name:        ProfileDatabase.cpp
// Description: A database of Profile's.
///////////////////////////////////////////////////////////////

#include "ProfileDatabase.h"
#include "Profile.h"

/**
 * Adds a Profile to the database.
 * @param profile - The profile to add.
 * @returns The index of the profile.
 */
void ProfileDatabase::addProfile(Profile p) {
    int i = getProfileIndex(p.getName());
    if(i != -1) {
        std::cout << "Profile name \"" << p.getName() << "\" is already taken!" << std::endl;
        return;
    }
    profiles.push_back(p);
    cout << "Added profile " << p.getName() << " with status \"" << p.getStatus() << "\"!" << endl;
}

/**
 * Gets a Profile object from a name.
 * @param name - The string name to look for.
 * @return - A profile, or a profile named "MissingProfile" if one is not found.
 */
Profile ProfileDatabase::getProfile(const string name) {
    int p_i = getProfileIndex(name);
    if(p_i == -1) {
        return Profile("__MissingProfile__", "", true);
    }
    return profiles[p_i];
}

/**
 * Deletes a Profile from the database.
 * @param name - The profile name to delete.
 */
void ProfileDatabase::deleteProfile(const string name) {
    int p_i = getProfileIndex(name);
    if(p_i == -1) {
        std::cout << "This profile does not exist!" << std::endl;
        return;
    }
    for(int i = 0; i < profiles.size(); i++) {
        profiles[i].deleteFriend(name);
    }
    profiles.erase(profiles.begin() + p_i);
    std::cout << "Deleted profile " << name << "." << std::endl;
}

/**
 * Gets a Profile's index in the profiles vector.
 * @param name - The name to look up.
 * @return An integer representing the index, or -1 if its not found.
 */
int ProfileDatabase::getProfileIndex(const string name) {
    for(int i = 0; i < profiles.size(); i++) {
        if(profiles[i].getName() == name) return i;
    }
    return -1;
}

/**
 * Adds two profiles as friends.
 * @param name1 - Profile name 1.
 * @param name2 - Profile name 2.
 */
void ProfileDatabase::addFriends(const string name1, const string name2) {
    if(name1 == name2) {
        std::cout << "Invalid input to addFriends: " << name1 << " cannot be added to their own friend list" << std::endl;
        return;
    }
    Profile p1 = getProfile(name1);
    Profile p2 = getProfile(name2);
    p1.addFriend(name2);
    p2.addFriend(name1);
    std::cout << name1 << " and " << name2 << " are now friends." << std::endl;
}

/**
 * Deletes two profiles' friendships.
 * @param name1 - Profile name 1.
 * @param name2 - Profile name 2.
 */
void ProfileDatabase::deleteFriends(const string name1, const string name2) {
    if(name1 == name2) {
        std::cout << "Invalid input to deleteFriends: " << name1 << " cannot be deleted from their own friend list" << std::endl;
        return;
    }
    getProfile(name1).deleteFriend(name2);
    getProfile(name2).deleteFriend(name1);
    std::cout << name1 << " and " << name2 << " are no longer friends." << std::endl;
}

/**
 * Updates a profile's status.
 * @param name - The name of the Profile.
 * @param status - The status to update it to.
 */
void ProfileDatabase::updateStatus(const string name, const string status) {
    Profile p = getProfile(name);
    if(p.getName() == "__MissingProfile__") {
        std::cout << "profile not found" << std::endl;
        return;
    }
    p.setStatus(status);
}

/**
 * Prints information of the Profiles database.
 */
void ProfileDatabase::print() {
    cout << "All profiles:" << endl;
    for(int i = 0; i < profiles.size(); i++) {
        std::cout << profiles[i].getName();
        if(i != profiles.size() - 1) {
            std::cout << ", ";
        }
    }
    std::cout << std::endl;
}