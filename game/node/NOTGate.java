package game.node;

import game.util.Color;
import processing.core.PApplet;
import processing.core.PConstants;

public class NOTGate extends Node {

    public NOTGate(PApplet sketch, float posX, float posY, float width, float height) {
        super(sketch, 1, 1, posX, posY, width, height);
        color = new Color(220, 50, 50);
    }

    @Override
    public void show() {
        sketch.stroke(0);
        sketch.strokeWeight(1);
        sketch.fill(color.r, color.g, color.b);
        sketch.rect(posX, posY, width, height);

        showNode();

        sketch.fill(255);
        sketch.textAlign(PConstants.CENTER, PConstants.CENTER);
        sketch.textSize(width / 3f);
        sketch.text("NOT", posX + width / 2f, posY + height / 2f);

    }

    @Override
    public void calculate() {
        output[0].state = !input[0].state;
    }
}
