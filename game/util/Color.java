package game.util;

public final class Color {
    public float r;
    public float g;
    public float b;

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static Color random() {
        return new Color((float) Math.random() * 255, (float) Math.random() * 255, (float) Math.random() * 255);
    }
}
