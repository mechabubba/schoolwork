#include <iostream>
using namespace std;

class Circle {
public:
    /**
     * `explicit` is not a return type! In this case, it is used with overloaded constructors.
     * ```cpp
     * class String {
     * public:
     *     explicit String (int n); // allocate n bytes
     *     String(const char *p);   // initialize sobject with string p
     * };
     * ```
     * "The character 'x' will be implicitly converted to int and then the String(int) constructor will be called. But, this is not what the user might have intended. So, to prevent such conditions, we shall define the constructor as explicit."
     * (Credit: https://stackoverflow.com/a/121216)
     */
    explicit Circle(double radius = 0) {
        setRadius(radius);
    }

    // Getters should be constant, setters couldn't.
    void setRadius(double radius) {
        if(radius >= 0) {
            m_radius = radius;
        } else {
            m_radius = 0;
        }
    }
    double getRadius() const {
        return m_radius;
    }
private:
    double m_radius;
};

int main() {
    Circle one(5); // class initiated :)
    cout << one.getRadius() << endl; // 5.0
    one.setRadius(10);
    cout << one.getRadius() << endl; // 10.0
}