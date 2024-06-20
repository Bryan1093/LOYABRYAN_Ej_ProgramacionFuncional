package Render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import Interfaces.Drawable;
import Models.Cercle;
import Models.Quadrat;
import Models.Triangle;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;

public class Render extends JFrame {

    private enum Shape {TRIANGLE, SQUARE, CIRCLE}

    private Shape currentShape = Shape.TRIANGLE;

    // Instancias para el panel y las figuras
    private GLJPanel glPanel;
    private Triangle triangle;
    private Quadrat quadrat;
    private Cercle cercle;

    public Render() {
        setTitle("Figuras con JOGL en JAVA");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        glPanel = new GLJPanel();
        glPanel.addGLEventListener(new GLEventListener() {

            @Override
            public void init(GLAutoDrawable drawable) {
            }

            @Override
            public void dispose(GLAutoDrawable drawable) {
            }

            public void display(GLAutoDrawable drawable) {

                GL2 gl2 = drawable.getGL().getGL2();
                gl2.glClearColor(1.0f, 0.7529f, 0.7961f, 1.0f);
                gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);

                switch (currentShape) {

                    case TRIANGLE:
                        if (triangle != null) {
                            triangle.draw();
                        }
                        break;

                    case SQUARE:
                        if (quadrat != null) {
                            quadrat.draw();
                        }
                        break;

                    case CIRCLE:
                        if (cercle != null) {
                            cercle.draw();
                        }
                        break;
                }
            }

            @Override
            public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            }
        });

        getContentPane().setLayout(new GridLayout(1, 2));

        // Panel
        JPanel botones = new JPanel();
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
        getContentPane().add(botones, BorderLayout.WEST);
        getContentPane().add(glPanel);

        // Botones
        addButton(botones, "Triangle", e -> setShape(Shape.TRIANGLE, Triangle::new, Drawable::draw));
        addButton(botones, "Quadrat", e -> setShape(Shape.SQUARE, Quadrat::new, Drawable::draw));
        addButton(botones, "Cercle", e -> setShape(Shape.CIRCLE, Cercle::new, Drawable::draw));

        setVisible(true); // Hace visible la ventana
    }

    private <T> void setShape(Shape shape, Consumer<GLAutoDrawable> constructor, Consumer<T> drawAction) {
        currentShape = shape;
        constructor.accept(glPanel);
        drawAction.accept((T) shape);
        glPanel.repaint();
    }

    private void addButton(JPanel panel, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        panel.add(Box.createVerticalStrut(50));
        panel.add(button);
    }

    // Método principal, crea una instancia de Render
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Render());
    }
}