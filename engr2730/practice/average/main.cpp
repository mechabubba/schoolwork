#include <iostream>
using namespace std;

int main() {
    cout << "Hello, World!" << endl;

    int x = 3;
    cout << "x = " << x << endl;

    float a_f = 9.0;
    float b_f = 10.0;
    int a_i = 9;
    int b_i = 10;

    cout << a_f / b_f << ", " << a_i / b_i << ", " << b_i % a_i << endl;

    float y = static_cast<float>(a_i) / b_i; // these two are different but prof doesnt know how, lol. in the future we'll use dynamic cast
    float y2 = (float)(a_i) / b_i;

    int len = 100;
    int sum = 0;
    for(int i = 0; i < len; i++) {
        sum += i + 1;
    }
    cout << (float)(sum) / len << endl;

    return 0;
}
