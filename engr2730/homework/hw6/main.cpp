///////////////////////////////////////////////////////////////
// Programmer:  Steven Vanni (vanni@uiowa.edu)
// Date:        11/03/21
// Name:        main.cpp
// Description: The runner file for homework six.
// Note:        Code was modified from Figure 11.3.1: Overriding member function in the CIE ZyBook class textbook.
///////////////////////////////////////////////////////////////

#include <iostream>
using namespace std;

int test();

/**
 * A base Computer class.
 */
class Computer {
public:
    explicit Computer() {
        cpuUsage = "10%";
        internetStatus = "connected";
        memorySize = new float;
    }

    Computer(Computer &comp) {
        comp.setComputerStatus(cpuUsage, internetStatus);
        comp.setMemorySize(*memorySize);
    }

    Computer operator=(Computer comp) {
        //return Computer(*this);
    }

    ~Computer() {
        cout << "You are in the Computer destructor." << endl;
        print();
        delete memorySize;
    }

    /**
     * Sets the computers status.
     * @param usage - The CPU usage.
     * @param status - The computers internet status.
     */
    void setComputerStatus(const string &usage, const string &status) {
        cpuUsage = usage;
        internetStatus = status;
    };

    /** Getters. */
    string getCPUUsage() { return cpuUsage; }
    string getInternetStatus() { return internetStatus; }
    float getMemorySize() { return *memorySize; }

    /** Setters. */
    void setCPUUsage(string _cpuUsage) { cpuUsage = _cpuUsage; }
    void setInternetStatus(string _internetStatus) { internetStatus = _internetStatus; }

    /**
     * Sets the memory size.
     * @param _memorySize - The memorys size, in gigabytes.
     */
    void setMemorySize(float _memorySize) {
        if(memorySize == nullptr) return;
        *memorySize = _memorySize;
    }

    /**
     * Prints information about the computer.
     */
    void print() {
        cout << "CPU usage: " << cpuUsage << endl;
        cout << "Internet status: " << internetStatus << endl;
        cout << "Memory size: " << *memorySize << "GB" << endl;
    };

protected:
    float *memorySize = nullptr;

private:
    string cpuUsage; // CPU usage, represented as a string of a percentage.
    string internetStatus; // Internet status - if we're connected or not.
};

/**
 * A laptop, a type of Computer.
 */
class Laptop : public Computer {
public:
    explicit Laptop() : Computer() {
        wifiQuality = "good";
    }

    Laptop(Laptop &lap) {
        lap.setComputerStatus(getCPUUsage(), getInternetStatus(), wifiQuality);
        lap.setMemorySize(getMemorySize());
    }

    Laptop operator=(Laptop lap) {
        // todo
    }


    ~Laptop() {
        cout << "You are in the Laptop destructor." << endl;
        print();
    }

    /**
     * Sets the computers status.
     * @param usage - The CPU usage.
     * @param status - The computers internet status.
     * @param quality - The computers wifi quality.
     */
    void setComputerStatus(const string &usage, const string &status, const string &quality) {
        setCPUUsage(usage);
        setInternetStatus(status);
        wifiQuality = quality;
    };

    /**
     * Prints information about the computer.
     */
    void print() {
        cout << "CPU usage: " << getCPUUsage() << endl;
        cout << "Internet status: " << getInternetStatus() << endl;
        cout << "Memory size: " << getMemorySize() << "GB" << endl;
        cout << "WiFi quality: " << wifiQuality << endl;
    };

private:
    string wifiQuality; // Wifi quality - how good your connection is.
};

int main() {
    Laptop myLaptop;

    myLaptop.setComputerStatus("25%", "connected", "good");
    myLaptop.print();

    return 0;
}

int test() {

}