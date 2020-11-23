package game;

import processing.core.PApplet;

public class NodeBar {

    PApplet sketch;
    float x, y;
    float width, height;

    public NodeBar(PApplet sketch, float x, float y, float width, float height) {
        this.sketch = sketch;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void show() {
        sketch.stroke(255);
        sketch.strokeWeight(1);
        sketch.noFill();
        sketch.rect(x, y, width, height);
    }

}
