package src.scenes;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Scene_home_panel extends JPanel implements Runnable {
	
	 private static final long serialVersionUID = 1L;
    private int PANEL_WIDTH = 500;
    private int PANEL_HEIGHT = 500;
    private int BALL_DIAMETER = 50;
    private int BALL_MAX_VELOCITY = 5;
    private int MAX_BALLS = 10;
    private String BACKGROUND_TEXT = "Java Swing Ball Collision";
    private static final Font BACKGROUND_TEXT_FONT = new Font("Arial", Font.ITALIC, 100);

    private List<Ball> balls = new ArrayList<>();


    public Scene_home_panel(int PANEL_WIDTH, int PANEL_HEIGHT, int BALL_DIAMETER, int BALL_MAX_VELOCITY, int MAX_BALLS,
            String BACKGROUND_TEXT) {
        this.PANEL_WIDTH = PANEL_WIDTH;
        this.PANEL_HEIGHT = PANEL_HEIGHT;
        this.BALL_DIAMETER = BALL_DIAMETER;
        this.BALL_MAX_VELOCITY = BALL_MAX_VELOCITY;
        this.MAX_BALLS = MAX_BALLS;
        this.BACKGROUND_TEXT = BACKGROUND_TEXT;

        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.WHITE);
    }

    @Override
    public void run() {
        while (true) {
            if (balls.size() < MAX_BALLS) {
                balls.add(new Ball());
            }
            for (int i = 0; i < balls.size(); i++) {
                Ball ball1 = balls.get(i);
                ball1.update();
                for (int j = i + 1; j < balls.size(); j++) {
                    Ball ball2 = balls.get(j);
                    if (ball1.intersects(ball2)) {
                        ball1.collide(ball2);
                    }
                }
            }
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background text
        g2d.setFont(BACKGROUND_TEXT_FONT);
        g2d.setColor(Color.GRAY);
        g2d.drawString(BACKGROUND_TEXT, PANEL_WIDTH / 2 - g2d.getFontMetrics().stringWidth(BACKGROUND_TEXT) / 2,
                PANEL_HEIGHT / 2);

        for (Ball ball : balls) {
            g2d.setColor(ball.getColor());
            g2d.fill(new Ellipse2D.Double(ball.getX(), ball.getY(), BALL_DIAMETER, BALL_DIAMETER));
        }
    }

    private class Ball {
        private int x;
        private int y;
        private int velocityX;
        private int velocityY;
        private Color color;

        public Ball() {
            Random random = new Random();
            this.x = random.nextInt(PANEL_WIDTH - BALL_DIAMETER);
            this.y = random.nextInt(PANEL_HEIGHT - BALL_DIAMETER);
            this.velocityX = random.nextInt(BALL_MAX_VELOCITY) + 1;
            this.velocityY = random.nextInt(BALL_MAX_VELOCITY) + 1;
            this.color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }

        public void update() {
            x += velocityX;
            y += velocityY;
            if (x < 0 || x > PANEL_WIDTH - BALL_DIAMETER) {
                velocityX = -velocityX;
            }
            if (y < 0 || y > PANEL_HEIGHT - BALL_DIAMETER) {
                velocityY = -velocityY;
            }

        }

        public boolean intersects(Ball other) {
            if (this == other) {
                return false;
            }
            double dx = x - other.x;
            double dy = y - other.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            return distance <= BALL_DIAMETER;
        }

        public void collide(Ball other) {
            int tmpVelocityX = velocityX;
            int tmpVelocityY = velocityY;
            velocityX = other.velocityX;
            velocityY = other.velocityY;
            other.velocityX = tmpVelocityX;
            other.velocityY = tmpVelocityY;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Color getColor() {
            return color;
        }
    }

}
