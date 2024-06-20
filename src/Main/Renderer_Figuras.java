package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.function.Consumer;

public class Renderer_Figuras extends JFrame {
    private enum Shape {TRIANGLE, SQUARE, CIRCLE}

    private Shape currentShape = Shape.TRIANGLE;
    private static final Random random = new Random();

    public Renderer_Figuras() {
        setTitle("Dibujar Figuras");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crear botones
        JButton triangleButton = createButton("Triángulo", e -> setShape(Shape.TRIANGLE));
        JButton squareButton = createButton("Cuadrado", e -> setShape(Shape.SQUARE));
        JButton circleButton = createButton("Círculo", e -> setShape(Shape.CIRCLE));

        // Crear un panel para los botones y organizarlos verticalmente con espacios entre ellos
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(triangleButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(squareButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(circleButton);

        // Crear un panel para las figuras y centrarlo horizontalmente
        JPanel shapePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                switch (currentShape) {
                    case TRIANGLE:
                        drawTriangle(g2d);
                        break;
                    case SQUARE:
                        drawSquare(g2d);
                        break;
                    case CIRCLE:
                        drawCircle(g2d);
                        break;
                }
            }
        };

        // Agregar el panel de botones a la izquierda y el panel de figuras a la derecha
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.WEST);
        getContentPane().add(shapePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createButton(String text, Consumer<ActionEvent> action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(e -> action.accept(e));
        return button;
    }

    private void setShape(Shape shape) {
        currentShape = shape;
        repaint();
    }

    private void drawTriangle(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        int[] xPoints = {width / 2 - 50, (int) (width * 0.65) - 50, (int) (width * 0.35) - 50};
        int[] yPoints = {(int) (height * 0.25) - 20, (int) (height * 0.75) - 20, (int) (height * 0.75) - 20};

        g2d.setColor(getRandomColor());
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    private void drawSquare(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        int size = (int) (Math.min(width, height) * 0.5);
        int x = (width - size) / 2 - 50;
        int y = (height - size) / 2 - 20;

        g2d.setColor(getRandomColor());
        g2d.fillRect(x, y, size, size);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(x, y, size, size);
    }

    private void drawCircle(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        int size = (int) (Math.min(width, height) * 0.5);
        int x = (width - size) / 2 - 50;
        int y = (height - size) / 2 - 20;

        g2d.setColor(getRandomColor());
        g2d.fillOval(x, y, size, size);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawOval(x, y, size, size);
    }

    private Color getRandomColor() {
        return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    public static void main(String[] args) {
        new Renderer_Figuras();
    }
}