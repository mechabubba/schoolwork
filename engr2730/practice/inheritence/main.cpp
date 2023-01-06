#include <iostream>
using namespace std;

class TwoDimensionalShape {
public:
    TwoDimensionalShape(float _x = 0, float _y = 0) {
        x = _x;
        y = _y;
    }
    float getX() { return x; }
    float getY() { return y; }
    void setX(float _x) { x = _x; }
    void setY(float _y) { y = _y; }

protected:
    float x;
    float y;
};

// this public can also be "private" or "protected".
// i believe it defines what you can access inside this class from the inherited class?
// something something principle of least privilege
class Square : public TwoDimensionalShape {
    Square(float _x = 0, float _y = 0, float _length = 0) : TwoDimensionalShape(_x, _y) {
        length = _length;
    }
    float getLength() { return length; }
    float getArea() {
        return (length * length);
    }

    void setLength(float _length) { length = _length; }

protected:
    float length;
};

class Circle : public TwoDimensionalShape {
    Circle(float _x = 0, float _y = 0, float _radius = 0) : TwoDimensionalShape(_x, _y) {
        radius = _radius;
    }
    float getRadius() { return radius); }
    float getArea() {
        return (radius * radius) * 3.14159;
    }

    void setRadius(float _radius) { radius = _radius; }

protected:
    int radius;
};

int main() {
    return 0;
}