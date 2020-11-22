package game.node.connection;

import processing.core.PApplet;

public class InputConnection extends ConnectionNode {

    public InputConnection(PApplet sketch, float posX, float posY, boolean in, float r) {
        super(sketch, posX, posY, in, r);
    }

    public void mousePressed(float mouseX, float mouseY) {
        float dist = PApplet.dist(mouseX, mouseY, posX, posY);
        if (dist <= r) {
            state = !state;
        }
    }
}
