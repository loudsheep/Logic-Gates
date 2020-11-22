package game.node;

import game.node.connection.ConnectionNode;
import game.util.Color;
import processing.core.PApplet;

public abstract class Node {

    PApplet sketch;
    public final int inNodes;
    public final int outNodes;
    public float posX;
    public float posY;
    public float r;

    ConnectionNode[] input;
    ConnectionNode[] output;
    ConnectionNode[] allNodes;

    Color color;
    public float width;
    public float height;

    public Node(PApplet sketch, int inNodes, int outNodes, float posX, float posY, float width, float height) {
        this.sketch = sketch;
        this.inNodes = inNodes;
        this.outNodes = outNodes;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;

        this.r = height / 3f;

        this.height = 2 * r * Math.max(inNodes, outNodes);

        this.r /= 2;

        input = new ConnectionNode[inNodes];
        output = new ConnectionNode[outNodes];
        allNodes = new ConnectionNode[inNodes + outNodes];

        float dist = this.height / (inNodes + 1);
        for (int i = 0; i < inNodes; i++) {
            float y = (i + 1) * dist;
            input[i] = new ConnectionNode(sketch, posX, posY + y, true, r);
            allNodes[i] = input[i];
        }

        dist = this.height / (outNodes + 1);
        for (int i = 0; i < outNodes; i++) {
            float y = (i + 1) * dist;
            output[i] = new ConnectionNode(sketch, posX + width, posY + y, false, r);
            allNodes[i + inNodes] = output[i];
        }


    }

    public abstract void show();

    public abstract void calculate();

    public void shift(float mouseX, float mouseY) {
        for (ConnectionNode c : allNodes) {
            c.posX -= posX - mouseX;
            c.posY -= posY - mouseY;
        }

        posX = mouseX;
        posY = mouseY;
    }

    public void showNode() {
        sketch.stroke(0);
        sketch.strokeWeight(1);
        sketch.fill(color.r, color.g, color.b);
        sketch.rect(posX, posY, width, height);

        for (ConnectionNode node : allNodes) {
            node.show();
        }


//        float dist = height / (inNodes + 1);
//        for (int i = 0; i < inNodes; i++) {
//            float y = (i + 1) * dist;
//            sketch.ellipse(posX, posY + y, r, r);
//        }
//
//        dist = height / (outNodes + 1);
//        for (int i = 0; i < outNodes; i++) {
//            float y = (i + 1) * dist;
//            sketch.ellipse(posX + width, posY + y, r, r);
//        }


    }

    public Color getColor() {
        return color;
    }

    public ConnectionNode[] getInput() {
        return input;
    }

    public void setInput(ConnectionNode[] input) {
        this.input = input;
    }

    public ConnectionNode[] getOutput() {
        return output;
    }

    public void setOutput(ConnectionNode[] output) {
        this.output = output;
    }

    public ConnectionNode[] getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(ConnectionNode[] allNodes) {
        this.allNodes = allNodes;
    }
}

//public class Node {
//
//    int inNodes;
//    int outNodes;
//    float posX;
//    float posY;
//
//    public Color color;
//    private float width;
//    private float height;
//
//    boolean[] input;
//    boolean[] output;
//
//    public Node() {
//    }
//
//    public void passInput(boolean[] values) {
//    }
//
//    public boolean[] getOutput() {
//        return new boolean[0];
//    }
//
//    public void calculateOutput() { // calculate output through all nodes inside
//    }
//
//    public void show() { // show node in scene
//    }
//
//    public int getInNodes() {
//        return inNodes;
//    }
//
//    public int getOutNodes() {
//        return outNodes;
//    }
//
//    public float getPosX() {
//        return posX;
//    }
//
//    public float getPosY() {
//        return posY;
//    }
//
//    public Color getColor() {
//        return color;
//    }
//
//    public float getWidth() {
//        return width;
//    }
//
//    public float getHeight() {
//        return height;
//    }
//}
