package game.node.connection;

import processing.core.PApplet;

public class ConnectionNode {
    PApplet sketch;
    public float posX;
    public float posY;

    private Connection connection;
    public boolean in;
    public boolean state;
    public boolean occupied;
    public final float r;

    public ConnectionNode(PApplet sketch, float posX, float posY, boolean in, float r) {
        this.sketch = sketch;
        this.posX = posX;
        this.posY = posY;
        this.in = in;
        this.r = r;
    }

    public ConnectionNode(float posX, float posY, boolean in, float r) {
        this.posX = posX;
        this.posY = posY;
        this.in = in;
        this.r = r;
    }

    public void show() {
        //sketch.stroke(0);
        sketch.noStroke();
        if (state) {
            sketch.fill(220, 50, 50);
        } else {
            sketch.fill(80);
        }
        sketch.ellipse(posX, posY, r * 2, r * 2);

        //System.out.println(this.getClass());
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        if (in && connection.nodeTo == this) {
            this.connection = connection;
            occupied = true;
        } else if (!in && connection.nodeFrom == this) {
            this.connection = connection;
            occupied = false;
        }
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }
}
