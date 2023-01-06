#include <iostream>

int main() {
    // A pointer is a *variable* which contains a *memory address* of a particular type.
    // Pointers give you special access to places in memory.
    // They are treated like any other variable; you can add to them, subtract from them, etc.

    // You can define a pointer by prefacing your pointer with a '*'.
    float *ptr = nullptr; // This is the null pointer; its essentially the very first address. It represents an undefined variable. This is a global C++ keyword!

    float var = 5.0;
    std::cout << "The value of var is " << var << std::endl;    // The value of var is 5
    std::cout << "The address of var is " << &var << std::endl; // The address of var is 0xdeadbeef

    ptr = &var;
    std::cout << "The value of ptr is " << ptr << std::endl;    // The value of ptr is 0xdeadbeef (same from above; this is the address of `var`!)
    std::cout << "The address of ptr is " << &ptr << std::endl; // The address of ptr is 0xdeadbef0 (different!)
    std::cout << "*ptr is " << *ptr << std::endl;               // *ptr is 5

    *ptr = *ptr + 1; // We change the *value* at the address. This is called "dereferencing the pointer".
    std::cout << "*ptr is now " << *ptr << std::endl;           // *ptr is now 6

    // More shenanigans; pointers pointing at other pointers.
    float mailbox = 2.5;
    float *mail_ptr;
    float **mail_ptr2;

    mail_ptr = &mailbox;
    mail_ptr2 = &mail_ptr;

    std::cout << mail_ptr2 << std::endl;   // address of mail_ptr
    std::cout << *mail_ptr2 << std::endl;  // address of mailbox
    std::cout << **mail_ptr2 << std::endl; // value of mailbox

    // This can be visualized as;
    // [ mail_ptr2 ]
    //       \\
    //   [ mail_ptr ]
    //         \\
    //     [ mailbox ]

    // TL;DR:
    // - `ptr` is the address.
    // - `*ptr` is the value at the address.

    // # ARRAYS
    // An array is a const pointer to the first element of the array.
    int a[5] = {1, 2, 3, 4, 5};
    // ^ Think of this as a series of boxes in memory of the length of the array. We can access said elements by accessing the offset of the initial memory address.

    // There are two ways to access values from an array; for both we take the memory address, offset it by a value, and dereference it to access the arrays values. .
    // THESE ARE THE SAME!
    std::cout << std::boolalpha << (a[2] == *(a + 2)) << std::endl; // true

    int *p = a; // p[2] == *(p + 2)

    // Silly example; this *works.*
    for(int i = 0; i < 5; i++) {
        // It looks like we're indexing an integer, but we're actually doing *(i + a). So it works.
        std::cout << i[a] << " ";
    }
    std::cout << std::endl;

    // # SIZEOF OPERATOR
    // The sizeof operator can be used to determine the size (in bytes) of a datatype.
    // This is useful because the number of bytes used to store certain datatypes may vary between systems.
    std::cout << sizeof(char)        << std::endl; // 1
    std::cout << sizeof(short)       << std::endl; // 2
    std::cout << sizeof(int)         << std::endl; // 4
    std::cout << sizeof(long)        << std::endl; // 4
    std::cout << sizeof(float)       << std::endl; // 4
    std::cout << sizeof(double)      << std::endl; // 8
    std::cout << sizeof(long double) << std::endl; // 16

    int sarr[5] = {};
    std::cout << sizeof(sarr)        << std::endl; // 20
    int *sptr = sarr;
    std::cout << sizeof(sptr)        << std::endl; // 8

    /**
     * When dealing with pointers,
     */

    return 0;
}
