#include <iostream>
#include <fstream>

using namespace std;

int main() {
    ifstream fin("data.txt", ifstream::binary); // Assume file contains binary data

    if (fin.fail()){
        cout << "Error: Could not open file" << endl;
        return -1;
    }

    if (fin) {
        // get length of file:
        fin.seekg(0, fin.end);    // Go to end of stream (i.e., file)
        int length = fin.tellg(); // Get current position of stream

        fin.seekg(0, fin.beg);    // Go to start of stream (i.e., file)

        char * buffer = new char [length]; // Dynamically allocate buffer array

        fin.read(buffer,length);   // Read entire file into buffer
        fin.close();               // Finish with file so close it

        cout.write(buffer,length); // Print buffer array to screen

        delete [] buffer;          // Free buffer array memory
    }
    return 0;
}

/**
 * # NEW & DELETE OPERATORS
 * `new`: Dynamically allocates memory.
 *   - Finds unused memory to store object, calls the object constructor.
 *   - *If it works:* returns the *address* of the allocated memory (set any new created objects to a pointer!).
 *   - *If it doesnt:* returns `nullptr`.
 * `delete`: Releases allocated memory.
 *   - Calls the object destructor (see below) and releases allocated memory.
 *
 * ```cpp
 * // Using the `delete` parameter.
 * int *xptr = new int; // using the `new` keyword to allocate memory
 * *xptr = 5;
 * std::cout << "*xptr = " << *xptr << std::endl;
 * delete xptr; // calls destructor, releases memory.
 *
 * // NOTE: We did not delete `xptr`! We **released memory** at the pointers location, and now `xptr` points to an invalid area of memory.
 * // It would be best practice to set this equal to `nullptr`.
 * xptr = nullptr;
 *
 * // more object allocation
 * Complex *cptr = new Complex(5, 4);
 * std::cout << "*cptr = " << *cptr << std::endl;
 * delete cptr;
 *
 * // deleting arrays
 * Complex *cptr2 = new Complex[10];
 * ... // yadda yadda
 * delete[] cptr2;
 * ```
 */

/**
 * The new command creates a pointer toward an allocated object.
 * Lets say you create an object then;
 * ```cpp
 * Time tobj;
 * Time *tptr = new Time(); // tptr now equals the pointer to the Time object
 *
 * // you can access this without dereferencing the object. these are the same
 * tobj.set(3, 30, 15);.
 * tptr->set(3, 30, 15);
 *
 * (*tptr).setDate(10, 22, 21); // this also works
 * ```
 */

/**
* # DESTRUCTORS
 * Now that we have objects that allocate memory dynamically, we need to implement *destructors*.
 * Destructors are what is invoked when an object becomes inaccessible or is destroyed.
 * It has no arguments and its defined by the objects name prefaced by the '~' character.
 *
 * This is important to avoid memory leaks, or cases in which memory keeps getting allocated but not deallocated.
 *
 * ```cpp
 * class MyClass {
 * public:
 *     MyClass(size_t m_size = 0);
 *     ~MyClass();
 *     size_t getSize() {
 *         return m_size;
 *     }
 *
 * private:
 *     float *m_memory;
 *     size_t m_size;
 * }
 *
 * MyClass::MyClass : m_size(size) {
 *     m_memory = new float[size];
 *     std::cout << "MyClass object with size = " << m_size << " created." << std::endl;
 * }
 *
 * MyClass::~MyClass() {
 *     delete[] m_memory; // deallocates this array when inaccessible.
 *     std::cout << "MyClass object with size = " << m_size << " destroyed." << std::endl;
 * }
 * ```
 *
 */

/**
 * Kinda off topic...
 * ```cpp
 * class MyClass {
 * public:
 *     MyClass(int id = 0);              // Constructor
 *     MyClass(const MyClass &obj2Copy); // Copy Constructor (THE AMPERSAND IS IMPORTANT)
 *     ~MyClass();                       // Destructor
 *     const int getID() {
 *         return m_id;
 *     }
 *
 * private:
 *     int m_id;
 * }
 *
 * MyClass::MyClass(int id) : m_id(id) {
 *     std::cout << "Created MyClass #" << id << std::endl;
 * }
 *
 * // The copy constructor copies all properties from one object to another.
 * // If the ampersand weren't here, you'd be calling the copy constructor again, and... thats not good.
 * MyClass::MyClass(const MyClass &obj2Copy) {
 *     m_id = obj2Copy.getID();
 * }
 *
 * MyClass::~MyClass() {}
 *
 * int main() {
 *     MyClass obj(5);
 *     func1(obj);
 *     func2(obj);
 *     return 0;
 * }
 *
 * // ## COPY CONSTRUCTORS
 * // When you pass in an object **without** the alias operator, the function creates a copy of it.
 * // For example, this one takes the reference of `obj` in the main class. Easy.
 * void func1(MyClass &obj) {
 *     std::cout << obj.getID() << std::endl;
 * }
 *
 * // This one, however, **copies** `obj` into something completely new.
 * // This is done via the copy constructor.
 * void func2(MyClass obj) {
 * }
 * ```
 */

// swap aliases
void swap(int &a, int &b) {
    int temp = a;
    a = b;
    b = temp;
}

// swap pointer values
void swap2(int *a, int *b) {
    // use dereferencing to get pointer values
    int temp = *a;
    *a = *b;
    *b = temp;
}

// swap pointers
// (probably dangerous, if any functions are using the alias of this variable then they'll change too)
void swap3(int *a, int *b) {
    int *temp = a;
    a = b;
    b = temp;
}

int a = 1;
int b = 5;
swap(a, b);    // ez
swap2(&a, &b); // this is called via the *address operator* (NOT the alias operator, apparently)

swap3(&a, &b); // see above comment