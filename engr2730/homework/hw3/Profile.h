///////////////////////////////////////////////////////////////
// Programmer:  Steven Vanni (vanni@uiowa.edu)
// Date:        09/25/21
// Name:        Profile.h
// Description: Definitions of the program class.
///////////////////////////////////////////////////////////////

#include <vector>
#include <iostream>
using namespace std;

#ifndef HW3_PROFILE_H
#define HW3_PROFILE_H

class Profile {
public:
    Profile(string _name = "NoName", string _status = "", bool validityoverride = false);

    string getName();
    string getStatus();

    void setName(string _name, bool _validityoverride);
    void setStatus(string _status);

    void addFriend(string _friend);
    void deleteFriend(string _friend);
    int isFriend(string _friend);

    void print();

private:
    // Names that cannot be used as the `name` parameter.
    // Mainly here because I want certain special profiles to be protected.
    string invalid[1] = { "__MissingProfile__" };
    int invalid_length = 1;

    string name;
    string status;
    vector<string> friends;
};

#endif // HW3_PROFILE_H
