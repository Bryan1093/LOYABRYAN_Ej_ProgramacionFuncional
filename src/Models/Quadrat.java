package Models;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import Interfaces.Drawable;

public class Quadrat implements Drawable {
    private GLAutoDrawable drawable;
    private boolean dibuixarQuadrat;

    private static final Random random = new Random();

    // Funciones de orden superior para generar colores aleatorios
    private Supplier<Float> randomColor = () -> random.nextFloat();
    private Supplier<Color> randomColorSupplier = () -> new Color(randomColor.get(), randomColor.get(), randomColor.get());

    // Constructor que acepta un GLAutoDrawable como argumento
    public Quadrat(GLAutoDrawable drawable) {
        this.drawable = drawable;
        this.dibuixarQuadrat = false;
    }

    @Override
    public void draw() {
        final GL2 gl = drawable.getGL().getGL2();

        if (dibuixarQuadrat) {
            gl.glBegin(GL2.GL_QUADS); // Dibujar el cuadrado

            Color color = randomColorSupplier.get();
            gl.glColor3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);

            // Definir los vértices del cuadrado
            float[][] vertices = {
                    {-0.5f, 0.5f}, // Vértice superior izquierdo
                    {0.5f, 0.5f}, // Vértice superior derecho
                    {0.5f, -0.5f}, // Vértice inferior derecho
                    {-0.5f, -0.5f} // Vértice inferior izquierdo
            };

            // Usar Streams y lambdas para iterar sobre los vértices
            Stream.of(vertices).forEach(vertex -> gl.glVertex2f(vertex[0], vertex[1]));

            gl.glEnd();
        }
    }

    public void limpiarDibujo(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Establecer el color de fondo como negro
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT); // Limpiar el búfer de color
    }
}
