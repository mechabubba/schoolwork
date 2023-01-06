//
// Created by steve on 9/17/2021.
//

#include <cmath>
#include "CoolCircle.h"

// "::" puts us in the circles scope
double CoolCircle::getArea() const {
    return M_PI * (m_radius * m_radius);
}

