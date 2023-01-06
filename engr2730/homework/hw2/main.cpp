///////////////////////////////////////////////////////////////
// Programmer:  Steven Vanni (vanni@uiowa.edu)
// Date:        09/15/21
// Name:        main.cpp
// Description: The runner file for homework two. Validates emails and implements the EmailMessage class.
///////////////////////////////////////////////////////////////
#include <iostream>
#include <ctime>
#include <regex>
#include <vector>
using namespace std;

/**
 * Small helper function that checks if a char exists in a vector<char>.
 * @param arr - The vector<char> to search.
 * @param val - The char to search for.
 * @returns bool - Does it exist or not?
 */
bool exists(vector<char> arr, char val) {
    for(auto a : arr) {
        if(a == val) return true;
    }
    return false;
}

/**
 * Validates email addresses.
 * See https://help.xmatters.com/ondemand/trial/valid_email_format.htm for info about valid email addresses.
 * @param email - The email to validate.
 * @returns bool - Is it a valid email?
 */
vector<char> interjections = {'_', '.', '-'}; // Non-alphanumeric characters that can be part of the email prefix and domain.
bool validateEmailAddress(const string &email) {
    // First things first, I'm using regex to test if there are any characters in the email that are not valid; we only support 'a-z', 'A-Z', '0-9', '@', '.', '_', and '.'.
    // Source: https://stackoverflow.com/a/3532074, with additional modification help from https://regexr.com/
    if(!regex_match(email, regex("^[A-Za-z0-9@._-]*$"))) return false;

    // Next, we need to make sure our email has an '@' and a '.' in the right places.
    size_t at_i = email.find_first_of("@");
    size_t period_i = email.find_last_of(".");

    // We can take some shortcuts here;
    if(at_i == string::npos || at_i == 0) return false; // The '@' doesn't exist, or is the very first character in the string.
    if(period_i == string::npos) {
        return false; // The '.' doesn't exist.
    } else {
        if(email.length() - 3 < period_i) return false; // The domain extension is less than two characters long.
    }
    if(at_i > period_i) return false; // The first '@' is further in the string than the last '.'; ie, "example.email@com"

    string prefix = email.substr(0, at_i);
    string domain = email.substr(at_i + 1, email.length() - at_i - 1);

    // We loop through the prefix to make sure it doesn't break any of the standard rules.
    for(int i = 0; i < prefix.length(); i++) { // Check for interjections. The `interjections` char array (charray?) has the characters that are allowed
        if(exists(interjections, prefix.at(i))) {
            if(i == 0) return false; // According to the spec, none of these chars can be first.
            if(i == prefix.length() - 1) return false; // We're at the end of the string; we cannot follow this character with an alphanumeric one.
            if(exists(interjections, prefix.at(i + 1))) {
                return false;
            }
        }
    }

    // From here the prefix is validated. Now we validate the domain.
    bool isExtension = false;
    int period_i_local = period_i - prefix.length() - 1; // The periods index local to the domain string.
    for(int i = 0; i < domain.length(); i++) {
        if(i == period_i_local) {
            if(i == 0) return false; // Theres no domain body; ie, "example@.com"
            isExtension = true;
        }
        // I made it so that the extension cannot include any of our allowed "interjection" characters.
        // This wasn't in the email validity specs, but I have not seen a single email with a non-alphanumeric symbol in the domain extension, so I just disallowed it.
        if(isExtension && i != period_i_local) {
            if(exists(interjections, domain.at(i))) return false;
            continue;
        }
        if(exists(interjections, domain.at(i))) {
            if(i == 0) return false; // According to the spec, none of these chars can be first.
            if(i == domain.length() - 1) return false; // We're at the end of the string; we cannot follow this character with an alphanumeric one.
            if(exists(interjections, domain.at(i + 1))) {
                return false;
            }
        }
    }

    return true;
}

/**
 * @class EmailMessage
 * The EmailMessage class; stores information about our emails.
 */
class EmailMessage {
public:
    // The homework said to implement a single overloaded constructor that can take zero to five inputs... I was able to do it with just one? Not sure if this is okay or not.
    EmailMessage(string _sender = "", string _recipient = "", time_t _timeSent = 0, string _subject = "", string _body = "") {
        setSender(_sender);
        setRecipient(_recipient);
        setTimeSent(_timeSent);
        setSubject(_subject);
        setBody(_body);
    }

    void print() {
        cout << "From: " << sender << endl;
        cout << "To: " << recipient << endl;
        cout << "Time: " << timeSent << " seconds since 00:00 hours, Jan 1, 1970 UTC" << endl;
        cout << "Subject: " << subject << endl;
        cout << body << endl;
    }

    string getSender() const {
        return sender;
    }
    string getRecipient() const {
        return recipient;
    }
    time_t getTimeSent() const {
        return timeSent;
    }
    string getSubject() const {
        return subject;
    }
    string getBody() const {
        return body;
    }

    /**
     * Sets the senders email.
     * If this is invalid, it is set to an empty string.
     * @param _sender - The senders email.
     */
    void setSender(string _sender) {
        if(validateEmailAddress(_sender)) {
            sender = _sender;
        } else {
            sender = "";
        }
    }
    /**
     * Sets the recipients email.
     * If this is invalid, it is set to an empty string.
     * @param _recipient - The recipients email.
     */
    void setRecipient(string _recipient) {
        if(validateEmailAddress(_recipient)) {
            recipient = _recipient;
        } else {
            recipient = "";
        }
    }
    void setTimeSent(time_t _timeSent) {
        timeSent = _timeSent;
    }
    void setSubject(string _subject) {
        subject = _subject;
    }
    void setBody(string _body) {
        body = _body;
    }

private:
    string sender;
    string recipient;
    time_t timeSent;
    string subject;
    string body;
};

int main() {
    time_t currentTime;
    time(&currentTime);  /* get current time; same as: currentTime = time(NULL)  */

    cout << "### CLASS OUTPUT TESTS" << endl;

    EmailMessage email1;
    email1.setTimeSent(currentTime);
    email1.setSender("gary-christensen@uiowa.edu");
    email1.setRecipient("cie@engineering.uiowa.edu");
    email1.setSubject("Test");
    email1.setBody("Hello, World!");
    cout << "Should print:" << endl;
    cout << "From: gary-christensen@uiowa.edu" << endl;
    cout << "To: cie@engineering.uiowa.edu" << endl;
    cout << "Time: " << currentTime << " seconds since 00:00 hours, Jan 1, 1970 UTC" << endl;
    cout << "Subject: Test" << endl;
    cout << "Hello, World!" << endl << endl;
    cout << "Actually prints: " << endl;
    email1.print();
    cout << endl;

    EmailMessage email2;
    email2.setTimeSent(currentTime);
    email2.setSender("minecraftguy66@gmail.com");
    email2.setRecipient("president@uiowa.edu");
    email2.setSubject("hello????");
    email2.setBody("does the president get these emails??? i have important questions");
    cout << "Should print:" << endl;
    cout << "From: minecraftguy66@gmail.com" << endl;
    cout << "To: president@uiowa.edu" << endl;
    cout << "Time: " << currentTime << " seconds since 00:00 hours, Jan 1, 1970 UTC" << endl;
    cout << "Subject: hello????" << endl;
    cout << "does the president get these emails??? i have important questions" << endl << endl;
    cout << "Actually prints: " << endl;
    email2.print();
    cout << endl;

    EmailMessage email3;
    cout << "Should print:" << endl;
    cout << "From:" << endl;
    cout << "To:" << endl;
    cout << "Time: 0 seconds since 00:00 hours, Jan 1, 1970 UTC" << endl;
    cout << "Subject:" << endl << endl;
    cout << "Actually prints: " << endl;
    email3.print();

    // All of these are taken from the email format spec site thing, minus the bottom which are just some special case tests.
    cout << boolalpha;
    cout << "### EMAIL VALIDITY TESTS" << endl;
    cout << "abc-@mail.com is invalid, validation returns:     " << validateEmailAddress("abc-@mail.com") << endl;
    cout << "abc..def@mail.com is invalid, validation returns: " << validateEmailAddress("abc..def@mail.com") << endl;
    cout << ".abc@mail.com is invalid, validation returns:     " << validateEmailAddress(".abc@mail.com") << endl;
    cout << "abc#def@mail.com is invalid, validation returns:  " << validateEmailAddress("abc#def@mail.com") << endl;
    cout << "abc.def@mail.c is invalid, validation returns:    " << validateEmailAddress("abc.def@mail.c") << endl;
    cout << "abc.def@mail#archive.com is invalid, validation returns: " << validateEmailAddress("abc.def@mail#archive.com") << endl;
    cout << "abc.def@mail is invalid, validation returns:      " << validateEmailAddress("abc.def@mail") << endl;
    cout << "abc.def@mail..com is invalid, validation returns: " << validateEmailAddress("abc.def@mail..com") << endl;
    cout << "- - - - - - - - - - - - - - - - - - - - - - - - - - - - -" << endl;
    cout << "abc-d@mail.com is valid, validation returns:   " << validateEmailAddress("abc-d@mail.com") << endl;
    cout << "abc.def@mail.com is valid, validation returns: " << validateEmailAddress("abc.def@mail.com") << endl;
    cout << "abc@mail.com is valid, validation returns:     " << validateEmailAddress("abc@mail.com") << endl;
    cout << "abc_def@mail.com is valid, validation returns: " << validateEmailAddress("abc_def@mail.com") << endl;
    cout << "abc.def@mail.cc is valid, validation returns:  " << validateEmailAddress("abc.def@mail.cc") << endl;
    cout << "abc.def@mail-archive.com is valid, validation returns: " << validateEmailAddress("abc.def@mail-archive.com") << endl;
    cout << "abc.def@mail.org is valid, validation returns: " << validateEmailAddress("abc.def@mail.org") << endl;
    cout << "abc.def@mail.com is valid, validation returns: " << validateEmailAddress("abc.def@mail.com") << endl;
    cout << "- - - - - - - - - - - - - - - - - - - - - - - - - - - - -" << endl;
    cout << "minecraft@gmail.com is valid, validation returns: " << validateEmailAddress("minecraft@gmail.com") << endl;
    cout << "m!necraft@gmail.com is invalid, validation returns: " << validateEmailAddress("m!necraft@gmail.com") << endl;
    cout << "minecraft.gmail@com is invalid, validation returns: " << validateEmailAddress("minecraft.gmail@com") << endl;
    cout << "<empty string> is invalid, validation returns:      " << validateEmailAddress("") << endl;
    cout << "m.i.n.e.c.r.a.f.t@g.m.a.i.l.com is valid, validation returns: " << validateEmailAddress("m.i.n.e.c.r.a.f.t@g.m.a.i.l.com") << endl;
}
