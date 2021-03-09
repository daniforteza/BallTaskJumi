/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balltask;

/**
 *
 * @author dfort
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Viewer extends Canvas implements Runnable {

    private BufferedImage background;
    private ArrayList<Ball> balls;
    private ArrayList<BlackHole> blackHoles;
    private BallTask ballTask;

    public Viewer(int width, int height, BallTask ballTask, ArrayList<Ball> balls) {
        this.ballTask = ballTask;
        this.balls = balls;
        this.setSize(width, height);
        this.loadBackground();

    }

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    public void setBlackHoles(ArrayList<BlackHole> blackHoles) {
        this.blackHoles = blackHoles;
    }

    public void paint() {
        BufferStrategy bs;
        bs = this.getBufferStrategy();

        Graphics g = bs.getDrawGraphics();
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);

        if (this.blackHoles != null) {
            for (BlackHole blackHole : this.blackHoles) {
                blackHole.paint(g);
            }
        }
        if (this.balls != null) {
            for (int i = 0; i < this.balls.size(); i++) {
                this.balls.get(i).paint(g);
            }
        }
        bs.show();
        g.dispose();

    }


    public void run() {
        this.createBufferStrategy(2);
        do {
            paint();
        } while (true);
    }

    public void loadBackground() {
        try {
            this.background = ImageIO.read(getClass().getResource("../res/background.jpg"));
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}