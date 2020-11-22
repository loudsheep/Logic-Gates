package main;

import game.Scene;
import processing.core.PApplet;
import processing.event.MouseEvent;


public class Main extends PApplet {

    Scene scene;

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        scene = new Scene(this, width, height);
    }

    public void draw() {
        scene.show();
    }

    public void mousePressed() {
        scene.mousePressed();
    }

    public void mouseReleased() {
        scene.mouseReleased();
    }

    public void mouseWheel(MouseEvent e) {
        scene.mouseWheel(e);
    }

    public static void main(String[] args) {
        PApplet.main("main.Main", args);
    }
}
