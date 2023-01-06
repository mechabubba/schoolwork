//
// Original code from: https://docs.microsoft.com/en-us/cpp/cpp/destructors-cpp?redirectedfrom=MSDN&view=vs-2019
// Modified by: Gary Christensen
// Date: 10/22/2020

#include <iostream>

using namespace std;

class String {
public:
    String( char *text );  // Declare constructor
    ~String();

    friend ostream &operator<<(ostream &os, const String &string1);
    //  and destructor.

    private:
    char   *m_text = nullptr;
public:
    char *getMText() const {
        return m_text;
    }

    void setMText(char *mText) {
        m_text = mText;
    }

    size_t getMSizeOfText() const {
        return m_sizeOfText;
    }

    void setMSizeOfText(size_t mSizeOfText) {
        m_sizeOfText = mSizeOfText;
    }

private:
    size_t  m_sizeOfText = 0;
};

// Define the constructor.
String::String( char * text ) {

    // Compute number of characters in the input text buffer
    char * tmpPtr = text;
    m_sizeOfText = 0;
    while (*tmpPtr != '\0'){
        m_sizeOfText++;
        tmpPtr++;
    }

    // Dynamically allocate the correct amount of memory.
    m_text = new char[ m_sizeOfText + 1 ];

    // If the allocation succeeds, copy the initialization string.
    if( m_text != nullptr ){
        for (int i = 0; i <= m_sizeOfText; i++){
            m_text[i] = text[i];
        }
    }
}

// Define the destructor.
String::~String() {
    // Deallocate the memory reserved for string.
    delete[] m_text;
}

// Overload the stream output operator to print string
ostream &operator<<(ostream &os, const String &string1) {
    os << "m_text: " << string1.m_text << " m_sizeOfText: " << string1.m_sizeOfText;
    return os;
}

int main() {
    char buffer[] = "Example of a class that uses dynamic memory allocation and a destructor.";
    String str(buffer);

    cout << str << endl;

    return 0;
}
