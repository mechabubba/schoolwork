///////////////////////////////////////////////////////////////
// Programmer: Fam Trinli
// Fam Trinli's Source Code from: https://www.youtube.com/watch?v=YzhhVHb0WVY
//
// Modified by: Steven Vanni (vanni@uiowa.edu)
// Date: 10/08/21
// Description: Code for homework 5.
///////////////////////////////////////////////////////////////
#include <SFML/Graphics.hpp>
#include <cmath>
#include <iostream>
#include <string>

using namespace sf;

// The checkpoints of our track.
// Each pair consists of the points x and y coordinates.
const int checkpointNum = 9;
const int checkpoints[checkpointNum][2] = {
        300,  610,
        1270, 430,
        1380, 2380,
        1900, 2460,
        1970, 1700,
        2550, 1680,
        2560, 3150,
        500,  3300,
        325,  1750
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
        checkpoint = 0;
        lap = 0;
    }

    // Get methods.
    float getX() { return x; }
    float getY() { return y; }
    float getSpeed() { return speed; }
    float getAngle() { return angle; }
    int getCheckpoint() { return checkpoint; }
    int getLap() { return lap; }

    // Set methods. None of them do anything special; all take one parameter and just set the variable.
    void setX(float _x) { x = _x; }
    void setY(float _y) { y = _y; }
    void setMaxX(float _maxX) { maxX = _maxX; }
    void setMaxY(float _maxY) { maxY = _maxY; }
    void setSpeed(float _speed) { speed = _speed; }
    void setAngle(float _angle) { angle = _angle; }
    void setCheckpoint(int _checkpoint) { checkpoint = _checkpoint; }

    /**
     * Moves the car programatically, based on angle and speed.
     */
    void move() {
        float _x = x + (sin(angle) * speed);
        float _y = y - (cos(angle) * speed);
        if(_x < maxX && _x >= 0) {
            x = _x;
        }
        if(_y < maxY && _y >= 0) {
            y = _y;
        }
    }

    /**
     * Finds the next checkpoint for the car to approach.
     */
    void findTarget() {
        float cpx = checkpoints[checkpoint][0];
        float cpy = checkpoints[checkpoint][1];
        float beta = angle - atan2(cpx - x, -cpy + y);

        if (sin(beta) < 0) {
            angle += 0.005 * speed;
        } else {
            angle -= 0.005 * speed;
        }

        checkCheckpoint(100);
    }

    /**
     * Checks if the car is within a certain radius of the checkpoint. If it is, the checkpoint updates to a new one.
     * @param {float} rad - The radius of the checkpoint to look into.
     */
    void checkCheckpoint(float rad) {
        float cpx = checkpoints[checkpoint][0];
        float cpy = checkpoints[checkpoint][1];

        if (((x - cpx) * (x - cpx) + (y - cpy) * (y - cpy)) < (rad * rad)) {
            checkpoint = (checkpoint + 1) % checkpointNum;
            if(checkpoint == 0) {
                lap++;
            }
        }
    }

private:
    float x; // X coordinate of the cars center.
    float y; // Y coordinate of the cars center.
    float maxX; // The maximum X value the car can go. This is typically the size of the course.
    float maxY; // The maximum Y value. This is typically the size of the course.
    float speed; // The cars speed.
    float angle; // The cars angle.

    int checkpoint; // The current checkpoint goal.
    int lap; // The current lap.
};

enum GameState { WIN, LOSS, FAILURE, QUIT, RESTART };

GameState welcome();
GameState race();
GameState finish();

RenderWindow app(VideoMode(res[0], res[1]), "Cool Car Game");

int main() {
    app.setFramerateLimit(60); // Limit the framerate to 60 FPS
    GameState state;

    GameState start = welcome();
    if(start == QUIT) return 0;

    while(state != QUIT || state != FAILURE) {
        state = race();
    }

    return 0;
}

/**
 * Shows a welcome screen with instructions.
 * @returns {GameState} RESTART to start.
 */
GameState welcome() {
    app.clear(Color::White);

    Texture welcome;
    if (!welcome.loadFromFile("images/cool_car_game.png")) return FAILURE;

    Sprite swelcome(welcome);
    app.draw(swelcome);
    app.display();

    while (app.isOpen()) {
        Event e;
        while (app.pollEvent(e)) {
            if (e.type == Event::Closed) {
                app.close();
            } else if(e.type == Event::KeyPressed) {
                if(e.key.code == Keyboard::R || e.key.code == Keyboard::Space) {
                    return RESTART;
                } else if(e.key.code == Keyboard::Escape) {
                    app.close();
                }
            }
        }
    }
    return QUIT;
}

/**
 * Shows the finishing screen.
 * @return {GameState} GameState.RESTART to restart the game, GameState.QUIT to quit.
 */
GameState finish(GameState state) {
    Font font;
    if (!font.loadFromFile("fonts/RobotoMono-Regular.ttf")) return FAILURE;

    Text message;
    message.setFont(font);
    message.setCharacterSize(64);

    Text retry;
    retry.setFont(font);
    retry.setCharacterSize(16);

    if(state == WIN) {
        app.clear(Color::White);
        message.setFillColor(Color::Black);
        message.setString("You won! :)");
        retry.setFillColor(Color::Black);
    } else if(state == LOSS) {
        app.clear(Color::Black);
        message.setFillColor(Color::Red);
        message.setString("You lost. :(");
        retry.setFillColor(Color::White);
    }

    // Centers the text on the screen.
    // Source: https://stackoverflow.com/a/15253837/17188891
    FloatRect messageRect = message.getGlobalBounds();
    message.setOrigin(messageRect.left + messageRect.width / 2.0f, messageRect.top + messageRect.height / 2.0f);
    message.setPosition(res[0] / 2.0f, res[1] / 2.0f);

    retry.setString("Press \"R\" to retry, or escape to quit.");
    FloatRect retryRect = retry.getGlobalBounds();
    retry.setOrigin(retryRect.left + retryRect.width / 2.0f, retryRect.top + retryRect.height / 2.0f);
    retry.setPosition(res[0] / 2.0f, (res[1] / 2.0f) + 64 + 8);

    app.draw(message);
    app.draw(retry);

    app.display();

    while (app.isOpen()) {
        Event e;
        while (app.pollEvent(e)) {
            if (e.type == Event::Closed) {
                app.close();
            } else if(e.type == Event::KeyPressed) {
                if(e.key.code == Keyboard::R) {
                    return RESTART;
                } else if(e.key.code == Keyboard::Escape) {
                    app.close();
                }
            }
        }
    }
    return QUIT;
}

GameState race() {
    // Loading images for the racing game as textures.
    Texture t1, t2, t3;
    if (!t1.loadFromFile("images/background.png")) return FAILURE;
    if (!t2.loadFromFile("images/car.png")) return FAILURE;

    // This font is licensed under the [Apache License v2.0](http://www.apache.org/licenses/LICENSE-2.0).
    Font font;
    if (!font.loadFromFile("fonts/RobotoMono-Regular.ttf")) return FAILURE;

    Text timeText;
    timeText.setFont(font);
    timeText.setCharacterSize(24);
    timeText.setFillColor(Color::White);
    timeText.setOutlineColor(Color::Black);
    timeText.setOutlineThickness(2);
    timeText.setPosition(8, 8);

    Text lapText;
    lapText.setFont(font);
    lapText.setCharacterSize(24);
    lapText.setFillColor(Color::White);
    lapText.setOutlineColor(Color::Black);
    lapText.setOutlineThickness(2);
    lapText.setPosition(8, 8 + 24 + 8);
    lapText.setString("test");

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
        car[i].setMaxX(t1.getSize().x * 2); // These are multiplied by 2 to account for the backgrounds scale.
        car[i].setMaxY(t1.getSize().y * 2);
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

    const float checkpointRadius = 100; // Size of visual checkpoint.
    const int laps = 3;

    // These two variables offset the background image by the position of the car on the map.
    // Simply speaking, the car doesn't move; the background does.
    int offsetX = 0;
    int offsetY = 0;

    Clock clock;
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

        if(car[0].getLap() >= laps) break;

        // Displays the time in the top left, as 00:00:00.000
        // Source: https://stackoverflow.com/a/17624456/17188891
        Time _elapsed = clock.getElapsedTime();
        int elapsed = _elapsed.asMilliseconds();
        std::string millis = std::to_string(elapsed % 1000);
        std::string secs = std::to_string((elapsed / 1000) % 60);
        std::string mins = std::to_string(((elapsed / 1000) / 60) % 60);
        std::string hour = std::to_string(((elapsed / 1000) / 60) / 60);
        std::string time = hour.insert(0, 2 - hour.length(), '0') + ":" + mins.insert(0, 2 - mins.length(), '0') + ":" + secs.insert(0, 2 - secs.length(), '0') + "." + millis.insert(0, 3 - millis.length(), '0');
        timeText.setString(time);

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
        car[0].checkCheckpoint(checkpointRadius);

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

        // Draw a circle representing our checkpoint.
        int currentCheckpoint = car[0].getCheckpoint();
        CircleShape circ(checkpointRadius);
        circ.setPosition((checkpoints[currentCheckpoint][0] - checkpointRadius) - offsetX, (checkpoints[currentCheckpoint][1] - checkpointRadius) - offsetY);
        circ.setOutlineColor(Color::Magenta);
        circ.setOutlineThickness(4);
        circ.setFillColor(Color(255, 0, 255, 127));
        app.draw(circ);

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

        app.draw(timeText);

        lapText.setString("Lap: " + std::to_string(car[0].getLap()));
        app.draw(lapText);

        // Finally, we display everything we've worked on.
        app.display();
    }

    int lap = car[0].getLap();
    for(int i = 1; i < cars; i++) {
        if(car[i].getLap() >= lap) {
            finish(LOSS);
        }
    }
    finish(WIN);
}