# Strings
- C: Basically a `char[]`; length is unchanging.
- C++: Length can expand and in general is far more dynamic than C strings.

**Note:** Quotes matter. 'Single quotes' mean it's a char; "double-quotes" mean its a null-terminated string.

# Arrays
A good ol' array. For an `int[]`, All elements are initialized to 0.

```cpp
// example usage
double data[10] = {1.3, 2.7, 3.1, 4, 5, 6, 7, 8, -9.9, 10};

// then you can loop through it, do whatever you need
for(int i = 0; i < 10; i++) {
    cout << data[i] << " "; // "1.3 2.7 3.1 4 5 6 7 8 -9.9 10 "
}

// fun lil c++ 11 range based loop.
// can be used sometimes but sometimes its necessary to have the index of the array
for(auto num : data) {
    cout << num << " ";
}
```

# Vectors
Basically better arrays. They can grow and shrink.
```cpp
// you need to include it at the top of the file
#include <vector>
...
vector<int> numbers; // creates an empty vector of integers
cout << "vectors are initially of size " << numbers.size() << endl; // "vectors are initially of size 0" 

```
[Documentation](https://www.cplusplus.com/reference/vector/)
