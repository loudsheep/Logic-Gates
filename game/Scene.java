package game;

import game.node.ANDGate;
import game.node.NOTGate;
import game.node.Node;
import game.node.connection.Connection;
import game.node.connection.ConnectionNode;
import game.node.connection.InputConnection;
import game.node.connection.OutputConnection;
import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class Scene {

    PApplet sketch;
    private int width;
    private int height;
    //Node n;
    Connection newConnection;
    private boolean grabbingNode = false;
    private Node grabbedNode;

    float r = 10;

    StringBuilder name;

    NodeBar bar;
    float barHeight;

    ArrayList<Connection> connections;
    ArrayList<Node> nodes;

    ArrayList<InputConnection> input;
    ArrayList<OutputConnection> output;

    public Scene(PApplet sketch, int width, int height) {
        this.sketch = sketch;
        this.width = width;
        this.height = height;

        this.barHeight = height / 20f;

        bar = new NodeBar(sketch, 0, height - barHeight, width, barHeight);
        this.height -= barHeight;

        sketch.getSurface().setTitle("Logic gates simulation");
        name = new StringBuilder();

        nodes = new ArrayList<>();
        connections = new ArrayList<>();

        input = new ArrayList<>();
        output = new ArrayList<>();

        input.add(new InputConnection(sketch, 10, height / 2f, false, r));
        output.add(new OutputConnection(sketch, width - 10, height / 2f, true, r));

        nodes.add(new ANDGate(sketch, 50, 50, 60, 40));
        nodes.add(new NOTGate(sketch, 300, 300, 60, 40));
        //nodes.add(new NOTGate(sketch, 300, 200, 60, 40));
    }

    public void show() {
        sketch.background(50);
        if (grabbingNode) {
            grabbedNode.shift(sketch.mouseX, sketch.mouseY);
        }

        sketch.noStroke();
        sketch.fill(255);
        sketch.textSize(10);
        sketch.text("Circuit name: " + name.toString(), sketch.textWidth("Circuit name: " + name.toString()) / 2, 10);

        bar.show();
        for (Node n : nodes) {
            n.calculate();
            n.show();
        }

        for (InputConnection i : input) {
            i.show();
        }
        for (OutputConnection o : output) {
            o.show();
        }

        if (newConnection != null) {
            newConnection.x = sketch.mouseX;
            newConnection.y = sketch.mouseY;

            sketch.stroke(0, 255, 0);
            sketch.strokeWeight(2);
            sketch.line(newConnection.getStartPos().x, newConnection.getStartPos().y, newConnection.getEndPos().x, newConnection.getEndPos().y);
        }

        for (Connection c : connections) {
            c.update();
            if (c.state) {
                sketch.stroke(255, 0, 0);
            } else {
                sketch.stroke(80);
            }

            sketch.strokeWeight(2);

            sketch.line(c.getStartPos().x, c.getStartPos().y, c.getEndPos().x, c.getEndPos().y);
        }
    }

    public void mousePressed() {
        for (Node n : nodes) {
            if (sketch.mouseX > n.posX - n.r && sketch.mouseX < n.posX + n.width + n.r) {
                if (sketch.mouseY > n.posY - n.r && sketch.mouseY < n.posY + n.height + n.r) {
                    for (ConnectionNode con : n.getAllNodes()) {

                        float dist = PApplet.dist(con.posX, con.posY, sketch.mouseX, sketch.mouseY);
                        if (dist <= con.r) {
                            if (con.in) {
                                continue;
                            }
                            System.out.println("click");
                            con.state = !con.state;
                            newConnection = new Connection(con, sketch.mouseX, sketch.mouseY);
                            return;
                        }
                    }


                    grabbingNode = true;
                    grabbedNode = n;
                    break;
                }
            }
        }

        for (InputConnection i : input) {
            float dist = PApplet.dist(i.posX, i.posY, sketch.mouseX, sketch.mouseY);
            if (dist <= i.r) {
                System.out.println("click");
                i.state = !i.state;
                newConnection = new Connection(i, sketch.mouseX, sketch.mouseY);
                break;
            }
        }
    }

    public void mouseReleased() {

        if (grabbingNode) {
            grabbingNode = false;
            grabbedNode = null;
        }

        for (Node n : nodes) {
            if (sketch.mouseX > n.posX - n.r && sketch.mouseX < n.posX + n.width + n.r) {
                if (sketch.mouseY > n.posY - n.r && sketch.mouseY < n.posY + n.height + n.r) {
                    for (ConnectionNode con : n.getAllNodes()) {
                        if (!con.in) {
                            continue;
                        }
                        float dist = PApplet.dist(con.posX, con.posY, sketch.mouseX, sketch.mouseY);
                        if (dist <= n.r) {
                            System.out.println("click_click");

                            if (newConnection != null) {
                                newConnection.nodeTo = con;

                                if (connectionExists(newConnection)) {
                                    connections.removeIf(c -> (
                                            c.nodeTo == newConnection.nodeTo && c.nodeFrom == newConnection.nodeFrom
                                    ));

                                    newConnection.nodeTo.state = false;
                                    newConnection.nodeTo.occupied = false;
                                    break;
                                }
                                if (newConnection.nodeTo.occupied) {
                                    break;
                                }
                                connections.add(newConnection);
                                newConnection.nodeTo.occupied = true;
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }

        for (OutputConnection o : output) {
            float dist = PApplet.dist(o.posX, o.posY, sketch.mouseX, sketch.mouseY);
            if (dist <= o.r) {
                System.out.println("click_click");

                if (newConnection != null) {
                    newConnection.nodeTo = o;

                    if (connectionExists(newConnection)) {
                        connections.removeIf(c -> (
                                c.nodeTo == newConnection.nodeTo && c.nodeFrom == newConnection.nodeFrom
                        ));

                        newConnection.nodeTo.state = false;
                        newConnection.nodeTo.occupied = false;
                        break;
                    }
                    if (newConnection.nodeTo.occupied) {
                        break;
                    }
                    connections.add(newConnection);
                    newConnection.nodeTo.occupied = true;
                    break;
                }
            }
        }

        newConnection = null;
        System.out.println(connections.size());
    }

    public void mouseWheel(MouseEvent e) {
        float amount = e.getCount();

        if (sketch.mouseX <= width / 2f) {
            if (amount > 0) {
                if (input.size() - amount > 0) {

                    for (Connection c : connections) {
                        if (c.nodeFrom == input.get(input.size() - 1)) {
                            c.nodeTo.state = false;
                            c.nodeTo.occupied = false;
                            connections.remove(c);
                            break;
                        }
                    }

                    input.remove(input.size() - 1);

                    float diff = height / (input.size() + 1f);
                    for (int i = 0; i < input.size(); i++) {
                        input.get(i).posY = diff * (i + 1);
                    }
                }
            } else {

                float diff = height / (input.size() + 1f);

                if (diff < 2 * 10) return;

                input.add(new InputConnection(sketch, 10, 0, false, 10));


                diff = height / (input.size() + 1f);
                for (int i = 0; i < input.size(); i++) {
                    input.get(i).posY = diff * (i + 1);
                }
            }

            return;
        }

        if (amount > 0) {
            if (output.size() - amount > 0) {

                for (Connection c : connections) {
                    if (c.nodeTo == output.get(output.size() - 1)) {
                        connections.remove(c);
                        break;
                    }
                }

                output.remove(output.size() - 1);

                float diff = height / (output.size() + 1f);
                for (int i = 0; i < output.size(); i++) {
                    output.get(i).posY = diff * (i + 1);
                }
            }
        } else {
            float diff = height / (output.size() + 1f);

            if (diff < 2 * 10) return;

            output.add(new OutputConnection(sketch, width - 10, 0, true, 10));

            diff = height / (output.size() + 1f);
            for (int i = 0; i < output.size(); i++) {
                output.get(i).posY = diff * (i + 1);
            }
        }

    }

    public void keyPressed() {
        if (sketch.keyCode == sketch.ENTER) {
            save(name.toString());
        } else if (sketch.keyCode == 32) {
            name.append(" ");
        } else if (sketch.keyCode == sketch.BACKSPACE) {
            if (name.length() > 0) {
                name.deleteCharAt(name.length() - 1);
            }
        } else {
            if (name.length() < 10) {
                if (isAlphabetic((char) sketch.keyCode) || isNumeric((char) sketch.keyCode)) {
                    name.append((char) sketch.keyCode);
                }
            }
        }
    }

    private boolean isNumeric(char chr) {
        return chr >= '0' && chr <= '9';
    }

    private boolean isAlphabetic(char chr) {
        return (chr >= 'A' && chr <= 'Z') ||
                (chr >= 'a' && chr <= 'z');
    }

    private boolean connectionExists(Connection connection) {
        for (Connection c : connections) {
            if (c.nodeFrom == connection.nodeFrom && c.nodeTo == connection.nodeTo) {
                return true;
            }
        }

        return false;
    }

    public void save(String name) {

        Node newNode = Node.createNewNode(name, input.size(), output.size(), 60, 40, nodes, connections, input, output);
        for (ConnectionNode c : newNode.getAllNodes()) {
            System.out.println(c.getClass());
        }
        newNode.setSketch(sketch);

        nodes = new ArrayList<>();
        connections = new ArrayList<>();

        input = new ArrayList<>();
        output = new ArrayList<>();
        this.name = new StringBuilder();

        input.add(new InputConnection(sketch, 10, height / 2f, false, r));
        output.add(new OutputConnection(sketch, width - 10, height / 2f, true, r));

        nodes.add(new ANDGate(sketch, 50, 50, 60, 40));
        nodes.add(new NOTGate(sketch, 300, 200, 60, 40));
        nodes.add(new NOTGate(sketch, 300, 300, 60, 40));

        nodes.add(newNode);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
    }
}
