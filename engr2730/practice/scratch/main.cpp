// this file is a mess
#include <iostream>
#include <vector>
#include <regex>
using namespace std;
int main() {
    vector<int> vec;
    vec.push_back(10);
    vec.push_back(99);
    vec.erase(vec.begin() + -1);
    cout << std::string(vec.begin(), vec.end()) << endl;
}