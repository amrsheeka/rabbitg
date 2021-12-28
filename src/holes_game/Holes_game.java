package holes_game;

import com.sun.opengl.util.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;

public class Holes_game extends JFrame {

    Animator animator;
    GLCanvas glcanvas;
    AnimGLEventListener2 listener2;

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Holes_game();

    }

    public Holes_game() {

        
        
        

        listener2 = new AnimGLEventListener2(this);

        glcanvas = new GLCanvas();

        glcanvas.addGLEventListener(listener2);
        glcanvas.addMouseListener((MouseListener) listener2);
        glcanvas.addMouseMotionListener((MouseMotionListener) listener2);
        add(glcanvas, BorderLayout.CENTER);

        animator = new FPSAnimator(20);
        animator.add(glcanvas);
        animator.start();

        setTitle("Crazy Rabbit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();

    }

}
