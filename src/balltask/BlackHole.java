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
import java.awt.*;
import java.util.Random;

public class BlackHole implements VisualObject {

    private int width;
    private int height;
    private int cordY;
    private int cordX;
    private Random random;
    private BallTask ballTask;
    private Rectangle rect;
    private Color color;
    private Ball ball;
    private Statistics statistics;

    public BlackHole(BallTask ballTask) {
        this.ballTask = ballTask;
        this.random = new Random();
        this.width = 150;
        this.height = 75;
        this.cordY = this.random.nextInt(this.ballTask.getHeight() - (this.height * 1));
        this.cordX = this.random.nextInt(this.ballTask.getWidth() - (this.width * 2));
        this.rect = new Rectangle(width, height);
        this.rect.setBounds(this.cordX, this.cordY, width, height);
        this.color = Color.orange;
    }

    public Rectangle getRect() {
        return rect;
    }


    public void paint(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.cordX, this.cordY, width, height);

    }

    //Metodo para añadir una pelota
    public synchronized void putBall(Ball ball) {
        while (this.ball != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.ball = ball;
        ball.setBorderColor(new Color(155,160,0));
        ball.setOutSide(false);
        ball.setSleepTime(35);
        notifyAll();
    }

    //Metodo para retirar la pelota
    public synchronized void removeBall(Ball ball) {
        if (this.ball != null) {
            if (ball.equals(this.ball)) {
                this.ball = null;
                ball.setBorderColor(ball.getColor());
                ball.setOutSide(true);
                ball.setSleepTime(15);
                notifyAll();
            }
        }
    }
}