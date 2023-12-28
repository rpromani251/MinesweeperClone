import java.awt.Color;
import java.awt.Graphics;

public class Border {
    private int width;
    private int height;

    public Border(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(3, 3, width, height);
    }
}
