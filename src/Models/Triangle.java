package Models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import Interfaces.Drawable;

import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Triangle implements Drawable {

    private GLAutoDrawable drawable;
    private boolean dibuixarTriangle;

    private static final Random random = new Random();

    // Funciones de orden superior para generar colores aleatorios
    private Supplier<Float> randomColor = () -> random.nextFloat();
    private Supplier<Color> randomColorSupplier = () -> new Color(randomColor.get(), randomColor.get(), randomColor.get());

    // Constructor que acepta un GLAutoDrawable como argumento
    public Triangle(GLAutoDrawable drawable) {
        this.drawable = drawable;
        this.dibuixarTriangle = true;
    }

    @Override
    public void draw() {
        final GL2 gl = drawable.getGL().getGL2();

        if (dibuixarTriangle) {
            gl.glBegin(GL2.GL_TRIANGLES);
            // Colores aleatorios
            Color color = randomColorSupplier.get();
            gl.glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);

            // Definir los vértices del triángulo
            float[][] vertices = {
                    {0f, 0.8f}, // Vértice superior
                    {-0.8f, -0.7f}, // Vértice inferior izquierdo
                    {0.8f, -0.7f} // Vértice inferior derecho
            };

            // Usar Streams y lambdas para iterar sobre los vértices
            Stream.of(vertices).forEach(vertex -> gl.glVertex2f(vertex[0], vertex[1]));

            gl.glEnd();
        }
    }

    public void limpiarDibujo(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
    }
}
