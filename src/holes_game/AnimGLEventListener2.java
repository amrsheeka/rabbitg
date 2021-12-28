/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holes_game;


import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.*;
import java.util.BitSet;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class AnimGLEventListener2 extends AnimListener implements MouseListener, MouseMotionListener {

    int a = 850, b = 600, c = 350, f = 100;

    Holes_game anim;
    GL gl;

    TextRenderer ren = new TextRenderer(new Font("sanaSerif", Font.BOLD, 10));
    TextRenderer ren1 = new TextRenderer(new Font("sanaSerif", Font.BOLD, 10));
    int score = 0;
    int arrx[] = {700, 300, 600};
    int arry[] = {500, 400, 200};

    int arrx2[] = {700, 200, 300, 600};
    int arry2[] = {500, 100, 400, 200};

    int arrx3[] = {210, 200, 800, 500, 820};
    int arry3[] = {180, 550, 630, 380, 160};
    int index;
    int maxWidth = 1000;
    int maxHeight = 1000;
    int x;
    int y;
    int xx;
    int yy;
    int k = 0;

    AudioInputStream audioStream, audio;

    Clip clip, clip1;

    boolean sound;
    static int X;
    static int Y;
    static int Z;
    String textureNames[] = {"home.png", "inst.png", "3levels.png", "background.png", "rabbit.png", "Hole.png", "hummer.png", "pause.png",
        "credits.png", "win.png", "final level.png", "lose.png", "hummer1.png", "mohamed.png", "aziz.png", "ahmed.png", "amr.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    int t = 100;
    int time = t;

    static int score1;
    int index1 = 6;
    int index2 = 12;

    int d = index1;

    public AnimGLEventListener2(Holes_game a) {

        anim = a;

    }

    @Override
    public void init(GLAutoDrawable gld) {

        try {

            audioStream = AudioSystem.getAudioInputStream(new File("song.wav"));
            audio = AudioSystem.getAudioInputStream(new File("alert.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            clip1 = AudioSystem.getClip();
            clip1.open(audio);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
                //                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }

        }

    }

    @Override
    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        switch (X) {

            case 0:
                DrawBackground(gl, 0);
                break;
            case 1:
                DrawBackground(gl, 1);
                break;
            case 2:
                DrawBackground(gl, 2);
                break;

            // case 3:
            case 4:

                DrawBackground(gl, 7);
                break;
            case 5:

                drawcreadits();
                break;
            case 6:
                DrawBackground(gl, 9);
                break;

            case 7:
                DrawBackground(gl, 10);
                break;

            case 8:
                DrawBackground(gl, 11);
                break;

            case 10:
                Z = 1;
                easylevel1();
                break;

            case 20:
                Z = 2;
                midlevel1();
                break;

            case 30:
                Z = 3;
                highlevel1();
                break;

        }

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawSprite(GL gl, int x, int y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();

        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);

        gl.glScaled(0.3 * scale, 0.3 * scale, 1);

        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void Drawsword(GL gl, int x, int y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();

        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);

        gl.glScaled(0.4 * scale, 0.2 * scale, 1);

        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawBackground(GL gl, int i) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_POLYGON);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public BitSet keyBits = new BitSet(256);

    @Override
    public void mouseClicked(MouseEvent e) {

        switch (X) {

            case 0:
                System.out.println("000000");
                System.out.println(e.getX() + "    " + e.getY());
                if (e.getX() > 50 && e.getX() < 280 && e.getY() > 388 && e.getY() < 477) {
                    System.out.println("***********");
                    X = 1;

                } else if (e.getX() > 50 && e.getX() < 280 && e.getY() > 195 && e.getY() < 290) {
                    System.out.println("***********");
                    X = 2;

                } else if (e.getX() > 50 && e.getX() < 280 && e.getY() > 760 && e.getY() < 850) {
                    int a = JOptionPane.showConfirmDialog(null, "are you enjoyed!");
                    if (a == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "Don't be late for us ", "Good bye", JOptionPane.WARNING_MESSAGE);
                        System.exit(0);
                    }
                } else if (e.getX() > 50 && e.getX() < 280 && e.getY() > 577 && e.getY() < 666) {
                    System.out.println("***********");
                    X = 5;

                } else if (e.getX() > 883 && e.getX() < 950 && e.getY() > 50 && e.getY() < 100) {
                    sound = !sound;
                    Sound();
                }

                break;

            case 1:
                System.out.println("1111111");
                System.out.println(e.getX() + "    " + e.getY());
                if (e.getX() > 24 && e.getX() < 105 && e.getY() > 25 && e.getY() < 95) {
                    System.out.println("***********");
                    X = 0;

                }
                break;

            case 2:
                System.out.println("22222222");
                System.out.println(e.getX() + "    " + e.getY());
                if (e.getX() > 24 && e.getX() < 105 && e.getY() > 25 && e.getY() < 95) {
                    System.out.println("***********");
                    X = 0;

                } else if (e.getX() > 275 && e.getX() < 454 && e.getY() > 335 && e.getY() < 527) {
                    System.out.println("***********");
                    X = 10;

                } else if (e.getX() > 600 && e.getX() < 783 && e.getY() > 320 && e.getY() < 515) {
                    System.out.println("***********");
                    X = 20;

                } else if (e.getX() > 453 && e.getX() < 630 && e.getY() > 556 && e.getY() < 750) {
                    System.out.println("***********");
                    X = 30;

                }

                break;

            case 4:

                if (e.getX() > 60 && e.getX() < 465 && e.getY() > 280 && e.getY() < 400) {
                    System.out.println("***********");
                    switch (Z) {

                        case 1:
                            X = 10;
                            break;

                        case 2:
                            X = 20;
                            break;

                        case 3:
                            X = 30;
                            break;

                    }

                } else if (e.getX() > 60 && e.getX() < 465 && e.getY() > 525 && e.getY() < 640) {
                    System.out.println("***********");
                    switch (Z) {

                        case 1:
                            time = t;
                            score = 0;
                            X = 10;
                            break;

                        case 2:
                            time = t;
                            score = 0;
                            X = 20;
                            break;

                        case 3:
                            time = t;
                            score = 0;
                            X = 30;
                            break;

                    }

                } else if (e.getX() > 60 && e.getX() < 465 && e.getY() > 767 && e.getY() < 888) {
                    System.out.println("***********");
                    X = 0;
                    score = 0;
                    time = t;

                }

                break;

            case 5:

                if (e.getX() > 24 && e.getX() < 105 && e.getY() > 25 && e.getY() < 95) {
                    System.out.println("***********");
                    X = 0;

                    a = 850;
                    b = 600;
                    c = 350;
                    f = 100;

                }

                break;

            case 6:

                if (e.getX() > 20 && e.getX() < 366 && e.getY() > 848 && e.getY() < 927) {
                    System.out.println("***********");
                    X = 0;

                } else if (e.getX() > 600 && e.getX() < 947 && e.getY() > 850 && e.getY() < 930) {
                    System.out.println("***********");

                    switch (Y) {

                        case 1:
                            X = 20;
                            break;

                        case 2:
                            X = 30;
                            break;

                    }

                }

                break;

            case 7:
                if (e.getX() > 20 && e.getX() < 366 && e.getY() > 848 && e.getY() < 927) {
                    System.out.println("***********");
                    X = 0;

                }

                break;

            case 8:
                if (e.getX() > 20 && e.getX() < 366 && e.getY() > 848 && e.getY() < 927) {
                    System.out.println("***********");
                    X = 0;
                } else if (e.getX() > 600 && e.getX() < 947 && e.getY() > 850 && e.getY() < 930) {

                    switch (Y) {

                        case 1:
                            X = 10;
                            break;

                        case 2:
                            X = 20;
                            break;

                        case 3:
                            X = 30;
                            break;

                    }

                }

            case 10:

                if (e.getX() > 24 && e.getX() < 105 && e.getY() > 25 && e.getY() < 95) {
                    System.out.println("***********");
                    X = 4;

                }
                break;

            case 20:

                if (e.getX() > 24 && e.getX() < 105 && e.getY() > 25 && e.getY() < 95) {
                    System.out.println("***********");
                    X = 4;

                }
                break;

            case 30:

                if (e.getX() > 24 && e.getX() < 105 && e.getY() > 25 && e.getY() < 95) {
                    System.out.println("***********");
                    X = 4;

                }
                break;

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (X == 10 || X == 20 || X == 30) {
           
            System.out.println(x + " " + y);
            System.out.println(e.getX() + " " + e.getY());
            if (e.getX() < (x + 110) && e.getX() > (x)
                    && (1000 - e.getY()) < (y + 180) && (1000 - e.getY()) > (y - 100)) {
                 d = index2;
                System.out.println("increase");
                
                score++;
                clip1.start();
                clip1.setMicrosecondPosition(0);

                
                d = index2;

            }
            

        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        d = index1;
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    @Override
    public void mouseDragged(MouseEvent me) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if (X == 10 || X == 20 || X == 30) {
            xx = e.getX();
            yy = 950 - e.getY();
            //System.out.println(e.getX() + "  " + e.getY());
        } else if (X == 6) {
            System.out.println("++++++++++");

            System.out.println(e.getX() + "  " + e.getY());

        }

    }

    public void Sound() {

        try {

            if (sound) {
                clip.stop();
            } else {
                clip.start();
                clip.loop(100);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());

        }
    }



    public void drawtime() {

        ren.beginRendering(300, 300);
        ren.setColor(Color.RED);
        ren.draw("Score : " + score, 120, 285);

        ren.draw("Timer : " + time, 8, 10);

        ren.setColor(Color.WHITE);

        ren.endRendering();


    }

    public void drawscore(int i) {

        ren.beginRendering(300, 300);
        ren.setColor(Color.white);
        ren.draw("you must hit  " + i + "  times to win", 150, 10);
        ren.setColor(Color.WHITE);
        ren.endRendering();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    public void easylevel1() {

        DrawBackground(gl, 3);

        Y = 1;
        drawtime();
 
        time--;
    
     
        drawscore(10);

        if (score == 10) {
            System.out.println("you win");
            X = 6;
            time = t;
            score = 0;
        } else if (time == 0) {

            X = 8;
            time = t;
            score = 0;

        }

        ren.beginRendering(300, 300);
        ren.setColor(Color.BLACK);
        ren.draw("you must hit 5 times" + score, 300, 300);
        //ren1.draw("Timer : " + time, 8, 10);
        ren.setColor(Color.WHITE);

        ren.endRendering();
  int mm =(int) (3.0 * Math.random());
        k++;
        if (k > 10) {
            k = k % 10;
            
            index = mm;
        }

        DrawSprite(gl, 700, 500, 5, 1);
        // DrawSprite(gl, 300, 700, 5, 1);
        DrawSprite(gl, 300, 400, 5, 1);
        DrawSprite(gl, 600, 200, 5, 1);
        //rabbit
        
        DrawSprite(gl, arrx[index], arry[index] + 132, 4, 1);
        
             
                
                     
          
             
        //hammer
        Drawsword(gl, xx, yy - 50, d, 1);

        x = arrx[index];
        y = arry[index] + 100;

    }


    public void midlevel1() {

        DrawBackground(gl, 3);
        drawscore(20);
        Y = 2;
        if (score == 20) {
            System.out.println("you win");
            X = 6;
            score = 0;
            time = t;
        } else if (time == 0) {

            X = 8;
            time = t;
            score = 0;

        }
        drawtime();
        time--;

        k++;
        if (k > 7) {
            k = k % 7;
            index = (int) (4.0 * Math.random());
        }

        DrawSprite(gl, 700, 500, 5, 1);
        DrawSprite(gl, 200, 100, 5, 1);
        DrawSprite(gl, 300, 400, 5, 1);
        DrawSprite(gl, 600, 200, 5, 1);

        DrawSprite(gl, arrx2[index], arry2[index] + 132, 4, 1);
 
        //hammer
        Drawsword(gl, xx, yy - 50, d, 1);

        x = arrx2[index];
        y = arry2[index] + 100;

    }


    public void highlevel1() {
        Y = 3;

        DrawBackground(gl, 3);
        drawscore(35);
        if (score == 35) {
            System.out.println("you win");
            X = 7;
            score = 0;
            time = t;
        } else if (time == 0) {

            X = 8;
            time = t;
            score = 0;

        }
        drawtime();
        time--;

        k++;
        if (k > 5) {
            k = k % 5;
            index = (int) (5.0 * Math.random());
        }

        DrawSprite(gl, 210, 180, 5, 1);
        DrawSprite(gl, 200, 550, 5, 1);
        DrawSprite(gl, 800, 630, 5, 1);
        DrawSprite(gl, 500, 380, 5, 1);
        DrawSprite(gl, 820, 160, 5, 1);
        //rabbit
        DrawSprite(gl, arrx3[index], arry3[index] + 132, 4, 1);

        //hammer
        Drawsword(gl, xx, yy - 50, d, 1);

        x = arrx3[index];
        y = arry3[index] + 100;

    }


    public void drawcreadits() {

        DrawBackground(gl, 8);
        DrawSprite(gl, 400, a, 13, 2.5f);
        DrawSprite(gl, 400, b, 14, 2.5f);
        DrawSprite(gl, 400, c, 15, 2.5f);
        DrawSprite(gl, 400, f, 16, 2.5f);

        a += 5;
        b += 5;
        c += 5;
        f += 5;

        if (a == maxHeight) {
            a = -100;
        }
        if (b == maxHeight) {
            b = -100;
        }
        if (c == maxHeight) {
            c = -100;
        }
        if (f == maxHeight) {
            f = -100;
        }
    }
}
