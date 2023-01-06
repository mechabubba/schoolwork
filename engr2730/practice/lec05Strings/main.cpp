#include <iostream>

using namespace std;

int main() {
    string s1; // empty string
    string s2 = "Hello";
    string s3("world!");

    // assignment and concatenation example
    s1 = s2;    // assign one string to another
    s1 +=  " "; // concatenation with character string
    s1 += s3;   // concatenation with another string
    cout << "s1 = " << s1 << endl;
    cout << "s2 = " << s2 << endl;
    cout << "s3 = " << s3 << endl;

    // another option for concatenation
    string s4; // empty string
    s4 = s2 + " " + s3;
    cout << "s4 = " << s4 << endl;

    // comparing strings
    if (s1 == s4) {
        cout << "s1 is equal to s4" << endl;
    }

    if (s2 == "Hello") {
        cout << "s2 equals \"Hello\"" << endl;
    }

    // reading a string (one word)
    string name;
    cout << "Please enter your first name: ";
    cin >> name;
    cout << "Hello " << name << "!" << endl;

    // reading a line
    string completeName;
    cout << "Please enter your first and last name: ";
    cin.ignore(); // throw away remaining '\n'
    getline(cin, completeName, '\n'); //read until newline
    cout << "Hello " << completeName << "!" << endl;


    string myString = "CIE classroom";
    cout << "myString = " << myString << endl;

    // iterate through all elements of the string: option 1
    for (size_t i = 0; i < myString.length(); i++)
    {
        cout << myString[i] << " ";
    }
    cout << endl;

    // iterate through all elements of the string: option 2
    for (size_t i = 0; i < myString.length(); i++)
    {
        cout << myString.at(i) << " ";
    }
    cout << endl;

    // search/replace example
    string searchString = "class";
    size_t pos = myString.find(searchString);
    if (pos != string::npos)
    {
        cout << searchString << " found at "
             << pos << endl;
    }

    // replace class with fun
    size_t startPos = pos;
    size_t numberCharsToReplace = searchString.size();
    string replacementStr = "fun";
    myString.replace(startPos, numberCharsToReplace,
                     replacementStr);

    cout << "After replacement, myString = "
         << myString << endl;


    return 0;
}
