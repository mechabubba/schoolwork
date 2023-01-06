///////////////////////////////////////////////////////////////
// Programmer: Fam Trinli
// Fam Trinli's Source Code from: https://www.youtube.com/watch?v=YzhhVHb0WVY
//
// Modified by: Steven Vanni (vanni@uiowa.edu)
// Date: 10/08/21
// Description: This program is a car racing game
///////////////////////////////////////////////////////////////
#include <SFML/Graphics.hpp>
#include <cmath>

using namespace sf;

// The checkpoints of our track.
// Each pair consists of the points x and y coordinates.
const int checkpointNum = 8;
const int checkpoints[checkpointNum][2] = {
        300,  610,
        1270, 430,
        1380, 2380,
        1900, 2460,
        1970, 1700,
        2550, 1680,
        2560, 3150,
        500,  3300
};

// The resolution of the game. {x, y}
const int res[2] = {640, 480};

/**
 * The Car class; our representation of a car.
 */
class Car {
public:
    Car() {
        speed = 0;
        angle = 0;
        n = 0;
    }

    // Get methods.
    float getX() { return x; }
    float getY() { return y; }
    float getSpeed() { return speed; }
    float getAngle() { return angle; }

    // Set methods. None of them do anything special; all take one parameter and just set the variable.
    void setX(float _x) { x = _x; }
    void setY(float _y) { y = _y; }
    void setSpeed(float _speed) { speed = _speed; }
    void setAngle(float _angle) { angle = _angle; }

    /**
     * Moves the car programatically, based on angle and speed.
     */
    void move() {
        x += sin(angle) * speed;
        y -= cos(angle) * speed;
    }

    /**
     * Finds the next checkpoint for the car to approach.
     */
    void findTarget() {
        float cpx = checkpoints[n][0];
        float cpy = checkpoints[n][1];
        float beta = angle - atan2(cpx - x, -cpy + y);

        if (sin(beta) < 0) {
            angle += 0.005 * speed;
        } else {
            angle -= 0.005 * speed;
        }

        // If we make it within a certain radius of the checkpoint, it gets updated and we move to the next one.
        if (((x - cpx) * (x - cpx) + (y - cpy) * (y - cpy)) < (25 * 25)) {
            n = (n + 1) % checkpointNum;
        }
    }

private:
    float x; // X coordinate of the cars center.
    float y; // Y coordinate of the cars center.
    float speed; // The cars speed.
    float angle; // The cars angle.
    int n; // The current checkpoint goal.
};

int main() {
    RenderWindow app(VideoMode(res[0], res[1]), "Car Racing Game!");
    app.setFramerateLimit(60); // Limit the framerate to 60 FPS.

    // Loading images for the racing game as textures.
    Texture t1, t2, t3;
    if (!t1.loadFromFile("images/background.png")) return EXIT_FAILURE;
    if (!t2.loadFromFile("images/car.png")) return EXIT_FAILURE;

    // Smooths the image when its displayed on the screen.
    t1.setSmooth(true);
    t2.setSmooth(true);

    // Creates a drawable representation of a Texture.
    Sprite sBackground(t1);
    Sprite sCar(t2);
    sBackground.scale(2, 2); // Scales the background by 2.

    sCar.setOrigin(22, 22); // Sets the sprites origin to the center of the image.
    const float carRadius = 22; // The cars radius.

    const int cars = 5;
    Car car[cars];
    for (int i = 0; i < cars; i++) {
        car[i].setX(300 + i * 50);
        car[i].setY(1700 + i * 80);
        car[i].setSpeed(7 + i);
    }
    const int tracked = 0; // The index of the car we want our camera to track. Not practical, but fun :p

    // Initiates various variables of the players car.
    // The players car is part of the `car` array at index 0; computer controlled cars are later in the array.
    float speed = 0; // Initial speed.
    float angle = 0; // Initial angle (facing upward in this case).
    const float maxSpeed = 12.0;  // The max speed of the car.
    const float acc = 0.2;        // Acceleration factor.
    const float dec = 0.3;        // Deceleration factor.
    const float turnSpeed = 0.08; // Turn speed; how fast you turn

    // These two variables offset the background image by the position of the car on the map.
    // Simply speaking, the car doesn't move; the background does.
    int offsetX = 0;
    int offsetY = 0;

    // This loops as long as the application is running.
    while (app.isOpen()) {
        // For each frame, we poll the top event of the application.
        // If the event type equals Event::Closed, the application has requested to close and we can close it via `app.close()`.
        // This will break us out of this while loop.
        Event e;
        while (app.pollEvent(e)) {
            if (e.type == Event::Closed) {
                app.close();
            }
        }

        // These booleans hold whether each of their respective keys are pressed.
        // We read this via the isKeyPressed() method of the Keyboard class.
        bool up = 0, right = 0, down = 0, left = 0;
        if (Keyboard::isKeyPressed(Keyboard::Up))    up = 1;
        if (Keyboard::isKeyPressed(Keyboard::Right)) right = 1;
        if (Keyboard::isKeyPressed(Keyboard::Down))  down = 1;
        if (Keyboard::isKeyPressed(Keyboard::Left))  left = 1;

        // Accelerates the car up to max speed, if up is pressed.
        if (up && speed < maxSpeed) {
            if (speed < 0) {
                speed += dec;
            } else {
                speed += acc;
            }
        }

        // Accelerates the car down to negative max speed, if down is pressed.
        if (down && speed > -maxSpeed) {
            if (speed > 0) {
                speed -= dec;
            } else {
                speed -= acc;
            }
        }

        // If neither up or down are pressed, decelerate to zero.
        if (!up && !down) {
            if (speed - dec > 0) {
                speed -= dec;
            } else if (speed + dec < 0) { // We need to handle cases if the car is going backwards.
                speed += dec;
            } else {
                speed = 0;
            }
        }

        // Turns right/left if right/left is pressed.
        // This only happens if speed is greater than 0; if otherwise, you won't turn.
        if (right && speed != 0) {
            angle += turnSpeed * speed / maxSpeed;
        }
        if (left && speed != 0) {
            angle -= turnSpeed * speed / maxSpeed;
        }

        // Set our speed and angle based on the above conditions.
        car[0].setSpeed(speed);
        car[0].setAngle(angle);

        // This loop moves all of the cars (programatically; just setting the cars x and y variables).
        for (int i = 0; i < cars; i++) {
            car[i].move();
        }

        // This loop causes each car to adjust their AI to move toward a given checkpoint.
        for (int i = 1; i < cars; i++) {
            car[i].findTarget();
        }

        // This loop handles collisions between cars; it checks the displacement of each car by their radius and tries to "push them out" of each other.
        // The two inner loops check the relationships between each car.
        for (int i = 0; i < cars; i++) {
            for (int j = 0; j < cars; j++) {
                if(i == j) {
                    continue;
                } else {
                    int dx = 0, dy = 0;
                    while (dx * dx + dy * dy < 4 * carRadius * carRadius) {
                        car[i].setX(car[i].getX() + dx / 10.0);
                        car[i].setY(car[i].getY() + dy / 10.0);
                        car[j].setX(car[j].getX() - dx / 10.0);
                        car[j].setY(car[j].getY() - dy / 10.0);
                        dx = car[i].getX() - car[j].getX();
                        dy = car[i].getY() - car[j].getY();
                    }
                }
            }
        }

        // We clear the screen with a color right before we start drawing.
        // As a side effect, this sets the out of bounds background color. I've set it to black.
        app.clear(Color::Black);

        // Here we offset the background based on the tracked cars position, and draw it.
        // The `tracked` variable tracks a single car by its index in the `car` array.
        // In this case, we're actually setting the backgrounds position *around* this cars position.
        if (car[tracked].getX() > (res[0] / 2)) {
            offsetX = car[tracked].getX() - (res[0] / 2);
        }
        if (car[tracked].getY() > (res[1] / 2)) {
            offsetY = car[tracked].getY() - (res[1] / 2);
        }
        sBackground.setPosition(-offsetX, -offsetY);
        app.draw(sBackground);

        // These are the colors for each of the cars.
        // They correspond to the index in the `car` array.
        Color colors[10] = {
                Color::Red,
                Color::Green,
                Color::Magenta,
                Color::Blue,
                Color::White
        };

        // This *visually* sets the positions, rotations, and colors of all of the cars on the screen, and draws them.
        for (int i = 0; i < cars; i++) {
            sCar.setPosition(car[i].getX() - offsetX, car[i].getY() - offsetY);
            sCar.setRotation(car[i].getAngle() * 180 / 3.141593);
            sCar.setColor(colors[i]);
            app.draw(sCar);
        }

        // Finally, we display everything we've worked on.
        app.display();
    }

    return 0;
}
