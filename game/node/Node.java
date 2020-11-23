package game.node;

import game.node.connection.Connection;
import game.node.connection.ConnectionNode;
import game.node.connection.InputConnection;
import game.node.connection.OutputConnection;
import game.util.Color;
import processing.core.PApplet;
import processing.core.PConstants;

import java.io.Serializable;
import java.util.ArrayList;

public class Node implements Serializable {

    PApplet sketch;
    public final int inNodes;
    public final int outNodes;
    public float posX;
    public float posY;
    public float r;

    ConnectionNode[] input;
    ConnectionNode[] output;
    ConnectionNode[] allNodes;
    Connection[] connections;
    public Node[] nodes;
    String name;

    Color color;
    public float width;
    public float height;

    public Node(PApplet sketch, int inNodes, int outNodes, float posX, float posY, float width, float height, String name) {
        this.sketch = sketch;
        this.inNodes = inNodes;
        this.outNodes = outNodes;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.color = Color.random();

        this.r = height / 3f;
        System.out.println(r + " <<<<<<<<<<< rrr");

        this.height = 2 * r * Math.max(inNodes, outNodes);

        this.r /= 2;

        this.name = name;

        input = new ConnectionNode[inNodes];
        output = new ConnectionNode[outNodes];
        allNodes = new ConnectionNode[inNodes + outNodes];

        nodes = new Node[0];

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

    public Node(String name, int inNodes, int outNodes, float posX, float posY, float width, float height) {
        this(null, inNodes, outNodes, posX, posY, width, height, name);
    }

    public void show() {
        showNode();
    }


    public static Node createNewNode(String name, int inNodes, int outNodes, float width, float height, ArrayList<Node> nodes, ArrayList<Connection> connections,
                                     ArrayList<InputConnection> input, ArrayList<OutputConnection> output) {

        Node node = new Node(name, inNodes, outNodes, 0, 0, width, height);

        int j = 0;
        float dist = node.height / (inNodes + 1);
        for (int i = 0; i < input.size(); i++) {
            float y = (i + 1) * dist;
            ConnectionNode c = new ConnectionNode(0, y, true, node.r);
            node.allNodes[j] = c;
            System.out.println(" >>>> " + c.getClass());

            j++;
        }

        dist = node.height / (outNodes + 1);
        for (int i = 0; i < output.size(); i++) {
            float y = (i + 1) * dist;
            ConnectionNode c = new ConnectionNode(width, y, false, node.r);
            node.allNodes[j] = c;
            System.out.println(" <<< " + c.getClass());
            j++;
        }

        for (int i = 0; i < connections.size(); i++) {
            for (int k = 0; k < input.size(); k++) {
                if (connections.get(i).nodeFrom == input.get(k)) {
                    connections.get(i).nodeFrom = node.allNodes[k];
                }
            }
        }

        for (int i = 0; i < connections.size(); i++) {
            for (int k = 0; k < output.size(); k++) {
                if (connections.get(i).nodeTo == output.get(k)) {
                    connections.get(i).nodeTo = node.allNodes[k + input.size()];
                }
            }
        }

//        node.allNodes = new ConnectionNode[connectionNodes.size()];
//        for (int i = 0; i < connectionNodes.size(); i++) {
//            node.allNodes[i] = connectionNodes.get(i);
//        }

        node.nodes = new Node[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            node.nodes[i] = nodes.get(i);
        }

        node.connections = new Connection[connections.size()];
        for (int i = 0; i < connections.size(); i++) {
            node.connections[i] = connections.get(i);
        }

        System.out.println("newNode: nodes: " + node.nodes.length + " conns: " + node.connections.length + " allNodes: " + node.allNodes.length);


        return node;
    }

    public void calculate() {
        for (Node n : nodes) {
            for (Connection c : connections) {
                c.update();
            }
            n.calculate();
        }
    }

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

        sketch.fill(255);
        sketch.textAlign(PConstants.CENTER, PConstants.CENTER);
        sketch.textSize(width / 3f);
        sketch.text(name, posX + width / 2f, posY + height / 2f);
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
        for (ConnectionNode c : allNodes) {
            c.setSketch(sketch);
        }

        if (nodes != null && nodes.length > 0) {
            for (Node n : nodes) {
                n.setSketch(sketch);
            }
        }

//        for (ConnectionNode c : input) {
//            c.setSketch(sketch);
//        }
//
//        for (ConnectionNode c : output) {
//            c.setSketch(sketch);
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