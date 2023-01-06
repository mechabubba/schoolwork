///////////////////////////////////////////////////////////////
// Programmer:  Steven Vanni (vanni@uiowa.edu)
// Date:        11/29/21
// Name:        main.cpp
// Description: The runner file for homework seven.
///////////////////////////////////////////////////////////////

#include <SFML/Graphics.hpp>
#include <iostream>
#include <time.h>
#include <list>
#include <cmath>

using namespace sf;

const int W = 1200;
const int H = 800;

const float DEGTORAD = 0.017453f;

/** An animation; frames are loaded in as a single texture. */
class Animation {
public:
	float frame, speed;
	Sprite sprite;
    std::vector<IntRect> frames;

	Animation() {}
    Animation(Texture &t, int x, int y, int w, int h, int count, float _speed) {
	    frame = 0;
        speed = _speed;

		for (int i = 0; i < count; i++) {
            frames.push_back(IntRect(x + i * w, y, w, h));
        }

		sprite.setTexture(t);
		sprite.setOrigin(w/2,h/2);
        sprite.setTextureRect(frames[0]);
	}

	void update() {
    	frame += speed;
		int n = frames.size();
		if (frame >= n) frame -= n;
		if (n > 0) sprite.setTextureRect(frames[int(frame)]);
	}

	bool isEnd() {
	    return (frame + speed) >= frames.size();
	}
};

/** A base entity. All entities are inherited from this class. */
class Entity {
public:
    float x, y, dx, dy, radius, angle;
    bool life;
    std::string name;
    Animation anim;

    Entity() {
        life = true;
    }

    void settings(Animation &_anim, int _x, int _y, float _angle = 0, int _radius = 1) {
        anim = _anim;
        x = _x;
        y = _y;
        angle = _angle;
        radius = _radius;
    }

    virtual void update() {};

    void draw(RenderWindow &app) {
        anim.sprite.setPosition(x, y);
        anim.sprite.setRotation(angle + 90);
        app.draw(anim.sprite);

        CircleShape circle(radius);
        circle.setFillColor(Color(255,0,0,170));
        circle.setPosition(x,y);
        circle.setOrigin(radius, radius);
        //app.draw(circle);
    }

    virtual ~Entity() {}
};

/** An asteroid entity. */
class Asteroid : public Entity {
public:
    Asteroid() {
        dx = rand() % 8 - 4;
        dy = rand() % 8 - 4;
        name = "asteroid";
    }

    void update() {
        x += dx;
        y += dy;
        if (x > W) x = 0; if (x < 0) x = W;
        if (y > H) y = 0; if (y < 0) y = H;
    }

    const static int AMOUNT = 15;
};

/** A bullet shot by a player. */
class Bullet : public Entity {
public:
    Bullet() {
        name = "bullet";
    }

    void update() {
        dx = cos(angle * DEGTORAD) * 6;
        dy = sin(angle * DEGTORAD) * 6;
        //angle += rand() % 6 - 3;
        x += dx;
        y += dy;

        if (x > W || x < 0 || y > H || y < 0) life = 0;
    }
};

class UFO : public Entity {
public:
    UFO() {
        name = "ufo";
    }

    void update() {
        dx = SPEED;
        x += dx;

        if (x > W || x < 0 || y > H || y < 0) life = 0;
    }

    const static int SPEED = 3;
};

/** A player entity. */
class Player : public Entity {
public:
    bool thrust;

    Player() {
        name = "player";
    }

    /** Updates the players position. */
    void update() {
        if (thrust) {
            dx += cos(angle * DEGTORAD) * 0.2;
            dy += sin(angle * DEGTORAD) * 0.2;
        } else {
            dx *= 0.99;
            dy *= 0.99;
        }

        int maxSpeed = 15;
        float speed = sqrt(dx * dx + dy * dy);
        if (speed > maxSpeed) {
            dx *= maxSpeed / speed;
            dy *= maxSpeed / speed;
        }

        x += dx;
        y += dy;

        if (x > W) x = 0; if (x < 0) x = W;
        if (y > H) y = 0; if (y < 0) y = H;
    }
};

/**
 * Tests if two entities are colliding.
 * @param {Entity} a
 * @param {Entity} b
 * @returns {bool} Whether a and b are colliding.
 */
bool isCollide(Entity *a, Entity *b) {
    return ((b->x - a->x) * (b->x - a->x) + (b->y - a->y) * (b->y - a->y)) < (a->radius + b->radius) * (a->radius + b->radius);
}

int main() {
    // Seed the random number generator with the current unix timestamp.
    srand(time(0));

    // First, we create the window.
    RenderWindow app(VideoMode(W, H), "Asteroids!");
    app.setFramerateLimit(60);

    // Loads textures.
    Texture t1, t2, t3, t4, t5, t6, t7, t8;
    t1.loadFromFile("images/spaceship.png");
    t2.loadFromFile("images/background.jpg");
    t3.loadFromFile("images/explosions/type_C.png");
    t4.loadFromFile("images/rock.png");
    t5.loadFromFile("images/fire_blue.png");
    t6.loadFromFile("images/rock_small.png");
    t7.loadFromFile("images/explosions/type_B.png");
    t8.loadFromFile("images/ufo.png");

    t1.setSmooth(true);
    t2.setSmooth(true);

    Sprite background(t2);

    Animation sExplosion(t3, 0,0,256,256, 48, 0.5);
    Animation sRock(t4, 0,0,64,64, 16, 0.2);
    Animation sRock_small(t6, 0,0,64,64, 16, 0.2);
    Animation sBullet(t5, 0,0,32,64, 16, 0.8);
    Animation sPlayer(t1, 40,0,40,40, 1, 0);
    Animation sPlayer_go(t1, 40,40,40,40, 1, 0);
    Animation sExplosion_ship(t7, 0,0,192,192, 64, 0.5);
    Animation sUFO(t8, 0, 0, 40, 40, 1, 0.5);

    std::list<Entity*> entities;

    // Creates a number of asteroids and places them randomly on the screen.
    for(int i = 0; i < Asteroid::AMOUNT; i++) {
        Asteroid *a = new Asteroid();
        a->settings(sRock, rand() % W, rand() % H, rand() % 360, 25);
        entities.push_back(a);
    }

    // Creates the player and sets the players position.
    Player *p = new Player();
    p->settings(sPlayer, 200, 200, 0, 20);
    entities.push_back(p);

    // This is the main loop.
    while (app.isOpen()) {
        Event event;
        while (app.pollEvent(event)) {
            if (event.type == Event::Closed) app.close();
            if (event.type == Event::KeyPressed) {
                if (event.key.code == Keyboard::Space) {
                    Bullet *b = new Bullet();
                    b->settings(sBullet, p->x, p->y, p->angle, 10);
                    entities.push_back(b);
                }
            }
        }

        // Orients and adds thrust to the player if certain keys are pressed.
        if (Keyboard::isKeyPressed(Keyboard::Right)) p->angle += 3;
        if (Keyboard::isKeyPressed(Keyboard::Left))  p->angle -= 3;
        if (Keyboard::isKeyPressed(Keyboard::Up))    p->thrust = true;
        else p->thrust = false;

        // We loop through all the entities to determine their interactions with eachother.
        for (auto a : entities) {
            for (auto b : entities) {
                if (a->name == "asteroid" && b->name == "bullet") {
                    // If an asteroid and bullet are colliding, we "fragment" the asteroid and create an explosion effect.
                    if (isCollide(a, b)) {
                        a->life = false;
                        b->life = false;

                        Entity *e = new Entity();
                        e->settings(sExplosion, a->x, a->y);
                        e->name = "explosion";
                        entities.push_back(e);

                        for (int i = 0; i < 2; i++) {
                            if (a->radius == 15) continue;
                            Entity *e = new Asteroid();
                            e->settings(sRock_small, a->x, a->y, rand() % 360, 15);
                            entities.push_back(e);
                        }
                    }
                } else if (a->name == "ufo" && b->name == "bullet") {
                    if (isCollide(a, b)) {
                        a->life = false;
                        b->life = false;

                        Entity *e = new Entity();
                        e->settings(sExplosion, a->x, a->y);
                        e->name = "explosion";
                        entities.push_back(e);
                    }
                } else if (a->name == "player" && b->name == "asteroid") {
                    // If a player and an asteroid are colliding, we kill the player and create an explosion effect.
                    if (isCollide(a, b)) {
                        b->life = false;

                        Entity *e = new Entity();
                        e->settings(sExplosion_ship, a->x, a->y);
                        e->name = "explosion";
                        entities.push_back(e);

                        p->settings(sPlayer, W / 2, H / 2, 0, 20);
                        p->dx = 0;
                        p->dy = 0;
                    }
                } else if (a->name == "player" && b->name == "ufo") {
                    if(isCollide(a, b)) {
                        b->life = false;

                        Entity *e1 = new Entity();
                        e1->settings(sExplosion_ship, a->x, a->y);
                        e1->name = "explosion";
                        entities.push_back(e1);

                        Entity *e2 = new Entity();
                        e2->settings(sExplosion, b->x, b->y);
                        e2->name = "explosion";
                        entities.push_back(e2);

                        p->settings(sPlayer, W / 2, H / 2, 0, 20);
                        p->dx = 0;
                        p->dy = 0;
                    }
                }
            }
        }

        // If the player is thrusting, we set the players animation to one that looks as such.
        if (p->thrust) {
            p->anim = sPlayer_go;
        } else {
            p->anim = sPlayer;
        }

        // For all entities that are just an explosion animation,
        for (auto e : entities) {
            if (e->name == "explosion") {
                if (e->anim.isEnd()) e->life = false;
            }
        }

        /*
        // Theres a 1/150 chance every frame that a new asteroid gets created.
        if (rand() % 150 == 0) {
            Asteroid *a = new Asteroid();
            a->settings(sRock, 0, rand() % H, rand() % 360, 25);
            entities.push_back(a);
        }
        */
        if (rand() % 750 == 0) {
            UFO *a = new UFO();
            a->settings(sUFO, 0, rand() % H, -90, 25);
            entities.push_back(a);
        }

        // We're done computing everything, so we update all entity positions.
        // If an entity is no longer alive, we remove it.
        for(auto i = entities.begin(); i != entities.end();) {
            Entity *e = *i;
            e->update();
            e->anim.update();
            if (e->life == false) {
                i = entities.erase(i);
                delete e;
            } else {
                i++;
            }
        }

        // Now we draw everything and start over.
        app.draw(background);
        for(auto i : entities) {
            i->draw(app);
        }
        app.display();
    }

    return 0;
}
