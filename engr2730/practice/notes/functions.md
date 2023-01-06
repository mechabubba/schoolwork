## Parameter Stuff
### Passing
Parameters are passed two ways;
- **Pass by Value:** The function makes a *copy* of the input parameter (changing the copy does not modify the original source).
  - Objects are passed by value to functions by default.
- **Pass by Reference:** The *address* of the input parameter is passed to the function. (changing the value at the address *does* change the original source).
  - Arrays are always passed by reference.
  -
    
Keep note of the **Principle of Least Privilege;** Always give a function enough access to the data in its parameters to accomplish the specified task, but no more.
- Example: Don't allow an `average()` method to modify the values stored in the data array or the count.
- You can use the `const` keyword to make the data read only.

```cpp
// example function: swaps two numbers
// within functions, the ampersand means the variable `a` or `b` is an alias or reference for another variable.
void swap(int &a, int &b) {
    int temp = a;
    a = b;
    b = temp;
}

// off topic: alias stuff
int x  = 5;
int &y = x; // y is an alias to x. changing y would also change x
y = 7; // x now equals 7.
```

### Default Arguments
You can name default arguments for when you don't pass in a specific (or any) value(s). They're defined in the function declaration and work as shown;
```cpp
// any number of parameters can be initialized with a default value
unsigned int boxVolume(unsigned int length = 1, unsigned int width = 1, unsigned int height = 1);
unsigned int boxVolume(unsigned int length, unsigned int width, unsigned int height) {
    return length * width * height;
}

// all of these are valid.
boxVolume()          // x: 1,  y: 1, z: 1
boxVolume(10);       // x: 10, y: 1, z: 1
boxVolume(10, 5);    // x: 10, y: 5, z: 1
boxVolume(10, 5, 8); // x: 10, y: 5, z: 8
```

## Function Overloading
Overloading functions is when you have two of the same functions with different parameters.
```cpp
int square(int x) {
    return x * x;
}

double square(double x) {
    return x * x
}

double(2);   // 4
double(2.5); // 6.25 
```
You need to be careful using default values; if you don't pass in enough information you may get undesirable results!

E.g. if I predefined `int square(int x = 3);` and `double square(double x = 4.5)`, what happens if I call `double()`?
