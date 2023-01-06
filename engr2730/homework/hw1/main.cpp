///////////////////////////////////////////////////////////////
// Programmer:  Steven Vanni (vanni@uiowa.edu)
// Date:        09/05/21
// Name:        main.cpp
// Description: The runner file for homework one. Takes sequences of DNA from files/dna.txt and matches it to data given in files/aliceSequence.txt.
///////////////////////////////////////////////////////////////

#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
using namespace std;

// This is called a forward declaration.
// You need to declare a function before referencing it, or else C++ will have no idea what its doing.
vector<string> csv2vector(string input);
int testPattern(string pattern);

vector<string> names;
vector<string> patterns;
vector<vector<int>> counts; // i: name index, j: pattern index

int main() {
    string temp;

    // Get the CSV data.
    ifstream data("files/aliceSequence.txt");
    if (data.fail()) {
        cout << "Error: Could not open files/aliceSequence.txt!" << endl;
        return -1;
    }

    vector<vector<string>> lines;
    while (data.good()) { // This loop turns every line into a vector.
        getline(data, temp);
        if(!temp.empty()) {
            lines.push_back(csv2vector(temp));
        }
    }
    data.close();

    // Get the DNA sequences we want to test.
    // Each line in this file is a DNA sequence we want to test over our data.
    ifstream seq("files/dna.txt");
    if (seq.fail()) {
        cout << "Error: Could not open files/dna.txt!" << endl;
        return -1;
    }

    vector<string> dna;
    while (seq.good()) {
        getline(seq, temp);
        if(!temp.empty()) {
            dna.push_back(temp);
        }
    }
    seq.close();

    // These loops use the `lines` vectors to set up the `names`, `patterns`, and `counts` vectors.
    for (int i = 0; i < lines.size(); i++) {
        vector<string> inner = lines[i];
        if(i > 0) {
            counts.push_back(vector<int>()); // First line in the file is just junk for our pattern counts, so ignore it.
        }
        for (int j = 0; j < inner.size(); j++) {
            if (i == 0) {
                if (j == 0) {
                    continue; // Ignore *only* the top right of the table.
                } else {
                    patterns.push_back(inner[j]);
                }
            } else {
                if (j == 0) {
                    names.push_back(inner[j]);
                } else {
                    counts[i - 1].push_back(stoi(inner[j]));
                }
            }
        }
    }

    for (int i = 0; i < dna.size(); i++) {
        int test = testPattern(dna[i]);
        cout << "Test pattern " << i + 1 << ": " << (test == -1 ? "No match." : names[test]) << endl;
    }

    return 0;
}


/**
 * Converts a delimiter-seperated string into a vector, where each value is the string between the delimiter.
 * @param input - The CSV line.
 * @return A `vector<string>` containing values from the CSV line.
 */
vector<string> csv2vector(const string input) {
    string tmp;
    vector<string> result;
    stringstream ss(input); /* Credit: https://stackoverflow.com/a/55263720 */
    while (getline(ss, tmp, ',')) {
        result.push_back(tmp);
    }
    return result;
}

/**
 * Tests a pattern and returns the index of the name it matches.
 * @param pattern - The pattern we want to test.
 * @return The index of the name, or -1 if no such name exists.
 */
int testPattern(const string pattern) {
    vector<int> longest(patterns.size()); // This vector counts the largest consecutive runs of every pattern we're trying to search for.

    for (int i = 0; i < patterns.size(); i++) {
        int run = 0;
        string _pattern = pattern;
        while(_pattern.length() >= 4) {
            string key = _pattern.substr(0, 4); // The four character sequence to test.

            // We check if our "key" matches the pattern we're currently searching for.
            // If it does, great! We add one to the current run.
            // Otherwise, we save the largest current run and start over as we continue to the end of the string.
            if (key == patterns[i]) {
                run++;
                _pattern = _pattern.substr(4, _pattern.length() - 4); // Continue searching the next four characters.
            } else {
                if(run > longest[i]) {
                    longest[i] = run;
                }
                run = 0;
                _pattern = _pattern.substr(1, _pattern.length() - 1); // Stop this search; continue one character at a time.
            }
        }
    }

    // Now we go through our longest runs and check to see if they match anyone in our database.
    int match = -1;
    for (int i = 0; i < longest.size(); i++) {
        bool matches = true;
        for (int j = 0; j < counts[i].size(); j++) {
            if (longest[j] != counts[i][j]) {
                matches = false;
                break;
            }
        }
        if(matches) { // We have our match; its the name at index i. We can exit the loop.
            match = i;
            break;
        }
    }

    return match; // This is our guy.
}
