package game;

import game.node.Node;
import game.node.connection.Connection;
import game.node.connection.ConnectionNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Circuit implements Serializable {

    private int inputs;
    private int outputs;

    ArrayList<Node> nodes;
    ArrayList<Connection> connections;
    ArrayList<ConnectionNode> input;
    ArrayList<ConnectionNode> output;

    public Circuit(ArrayList<Node> nodes, ArrayList<Connection> connections, ArrayList<ConnectionNode> inOutNodes, int input, int output) {
        this.nodes = nodes;
        this.connections = connections;
        this.inputs = input;
        this.outputs = output;

        this.input = new ArrayList<>();
        this.output = new ArrayList<>();

        for (int i = 0; i < inOutNodes.size(); i++) {
            if (i < input) {
                this.input.add(inOutNodes.get(i));
            } else {
                this.output.add(inOutNodes.get(i));
            }
        }

        this.nodes.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Float.compare(o1.posX, o2.posX);
            }
        });
    }

    // TODO do this sometime
    public void calculate() {
        for (Node n : nodes) {
            n.calculate();
            for (Connection c : connections) {
                c.update();
            }
        }
    }
}
