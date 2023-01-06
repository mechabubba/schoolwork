//
// Created by steve on 9/17/2021.
//

#ifndef CIRCLE2_COOLCIRCLE_H
#define CIRCLE2_COOLCIRCLE_H

class CoolCircle {
public:
    CoolCircle() {

    }

    double getRadius() {
        return m_radius;
    }

    void setRadius(double radius) {
        if(radius >= 0) {
            m_radius = radius;
        } else {
            m_radius = 0;
        }
    }
private:
    double m_radius;
};

#endif //CIRCLE_COOL2CIRCLE_H
