///////////////////////////////////////////////////////////////
// Name: Steven Vanni (vanni@uiowa.edu)
// Date: 09/01/21
// Description: Implements problem one from ProjectEuler.net.
///////////////////////////////////////////////////////////////
#include <iostream>

int euler1(int n = 10);

int main() {
    std::cout << euler1() << std::endl;
    return 0;
}

/**
 * Finds the sum of all values below parameter `n` that are multiples of 3 or 5.
 * @param {int} n - the limit we approach; default is 10.
 * @return {int} the sum we achieve
 */
int euler1(int n) {
    int sum = 0;
    for(int i = 0; i < n; i++) {
        if(i % 3 == 0 || i % 5 == 0) {
            sum += i;
        }
    }
    return sum;
}
