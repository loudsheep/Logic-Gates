package game.node.connection;

import processing.core.PVector;

public class Connection {

    public ConnectionNode nodeFrom;
    public ConnectionNode nodeTo;
    public float x, y;
    public boolean state;

    public Connection(ConnectionNode nodeFrom, ConnectionNode nodeTo) {
        this.nodeFrom = nodeFrom;
        this.nodeTo = nodeTo;
        this.state = nodeFrom.state;
    }

    public Connection(ConnectionNode nodeFrom, float x, float y) {
        this.nodeFrom = nodeFrom;
        this.state = nodeFrom.state;
        this.x = x;
        this.y = y;
    }

    public void update() {
        state = nodeFrom.state;
        nodeTo.state = state;
    }

    public PVector getStartPos() {
        return new PVector(nodeFrom.posX, nodeFrom.posY);
    }

    public PVector getEndPos() {
        if (nodeTo == null) {
            return new PVector(x, y);
        }
        return new PVector(nodeTo.posX, nodeTo.posY);
    }

}
