#include <iostream>
#include <fstream>

using namespace std;

int main() {
    ifstream fin("data.txt"); // Attempt to open file

    if (fin.fail()){
        cout << "Error: could not open data.txt";
        return -1;
    }

    int num, sum=0, count=0;

#if 1
    fin >> num;
    while (fin.good()){
        sum += num;
        count++;
        fin >> num;
    }

#else
    while (!fin.eof()){
        fin >> num;
        if(!fin.fail()) {
            sum += num;
            count++;
        }
    }
#endif
    fin.close();

    cout << "ave = " << static_cast<float>(sum)/count << endl;
    return 0;
}