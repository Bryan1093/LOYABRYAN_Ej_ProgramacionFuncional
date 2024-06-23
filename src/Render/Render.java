package Render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
    private Drawable currentDrawable;

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

            @Override
            public void display(GLAutoDrawable drawable) {
                GL2 gl2 = drawable.getGL().getGL2();
                gl2.glClearColor(1.0f, 0.7529f, 0.7961f, 1.0f);
                gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);

                if (currentDrawable != null) {
                    currentDrawable.draw();
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
        addButtons(botones,
                new String[]{"Triangle", "Quadrat", "Cercle"},
                new Shape[]{Shape.TRIANGLE, Shape.SQUARE, Shape.CIRCLE},
                new Supplier[]{() -> new Triangle(glPanel), () -> new Quadrat(glPanel), () -> new Cercle(glPanel)});

        setVisible(true); // Hace visible la ventana
    }

    private void addButtons(JPanel panel, String[] labels, Shape[] shapes, Supplier<? extends Drawable>[] constructors) {
        Stream.of(labels)
                .forEach(label -> {
                    int index = Stream.of(labels).toList().indexOf(label);
                    JButton button = new JButton(label);
                    button.setAlignmentX(Component.CENTER_ALIGNMENT);
                    button.addActionListener(e -> setShape(shapes[index], constructors[index]));
                    panel.add(Box.createVerticalStrut(50));
                    panel.add(button);
                });
    }

    private void setShape(Shape shape, Supplier<? extends Drawable> constructor) {
        currentShape = shape;
        currentDrawable = constructor.get();
        glPanel.repaint();
    }

    // Método principal, crea una instancia de Render
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Render::new);
    }
}
