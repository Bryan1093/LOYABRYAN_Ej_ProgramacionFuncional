package Models;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import Interfaces.Drawable;

public class Cercle implements Drawable {
    private GLAutoDrawable drawable;
    private boolean dibuixarCercle;
    private static final Random random = new Random();

    private Color getRandomColor() {
        return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    // Funciones de orden superior para generar colores aleatorios
    private Supplier<Float> randomColor = () -> random.nextFloat();

    // Constructor que acepta un GLAutoDrawable como argumento
    public Cercle(GLAutoDrawable drawable) {
        this.drawable = drawable;
        this.dibuixarCercle = false;
    }

    public void dibujarCercle() {
        dibuixarCercle = true;
        drawable.display(); // Vuelve a dibujar el panel
    }

    @Override
    public void draw() {
        final GL2 gl = drawable.getGL().getGL2();

        if (dibuixarCercle) {
            int segmentos = 100;
            float red = randomColor.get();
            float green = randomColor.get();
            float blue = randomColor.get();

            gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glColor3f(red, green, blue);

            // Centro del círculo
            gl.glVertex2f(-50.0f, -50.0f); // Mover hacia la izquierda y arriba

            // Calcular los vértices del círculo usando Streams y lambdas
            List<float[]> verticesList = IntStream.rangeClosed(0, segmentos)
                    .mapToObj(i -> {
                        double angle = 2.0 * Math.PI * i / segmentos;
                        return new float[]{(float) Math.cos(angle) * 50, (float) Math.sin(angle) * 50};
                    })
                    .collect(Collectors.toList());

            verticesList.forEach(vertex -> gl.glVertex2f(vertex[0], vertex[1]));

            gl.glEnd();
        }
    }

    public void limpiarDibujo(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
    }
}