///////////////////////////////////////////////////////////////
// Programmer:  Steven Vanni (vanni@uiowa.edu)
// Date:        09/27/21
// Name:        ProfileDatabase.h
// Description: Definitions of the ProfileDatabase class.
///////////////////////////////////////////////////////////////

#include "Profile.h"
#include <vector>

#ifndef HW3_PROFILEDATABASE_H
#define HW3_PROFILEDATABASE_H

class ProfileDatabase {
public:
    ProfileDatabase() = default;

    Profile getProfile(string name);
    int getProfileIndex(string name);

    void addProfile(Profile profile);
    void deleteProfile(string name);
    void updateStatus(string name, string status);

    void addFriends(string name1, string name2);
    void deleteFriends(string name1, string name2);
    bool areFriends(string name1, string name2);

    void print();
private:
    vector<Profile> profiles;
};

#endif // HW3_PROFILEDATABASE_H
