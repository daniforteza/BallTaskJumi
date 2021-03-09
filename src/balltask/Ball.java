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
import communications.Channel;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;


public class Ball implements Runnable, VisualObject, Serializable {

    private int velX;
    private int velY;
    private int cordX;
    private int cordY;
    private int sleepTime;
    private int dimension=20;
    private Thread ballThread;

    private BallTask ballTask;
    private Random random;
    private Color color;
    private Color borderColor;
    private String status;
    private Rectangle rect;
    private boolean running;
    private boolean moving;
    private boolean stopped;
    private Channel channel;

    public Ball(){

    }

    public Ball(BallTask ballTask , Channel channel) {
        this.random = new Random();
        this.ballTask = ballTask;
        this.moving = true;
        this.channel = channel;
        this.color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        this.borderColor = new Color(0);
        this.cordY = this.random.nextInt(this.ballTask.getHeight() - (this.dimension * 3));
        this.cordX = this.random.nextInt(this.ballTask.getWidth() - (this.dimension * 4));
        this.velY = 1;
        this.velX = 1;
        this.sleepTime = 3;
        this.stopped = false;
        this.rect = new Rectangle(this.dimension, this.dimension);
        ballThread = new Thread(this);
        ballThread.start();
    }


    public Thread getBallThread() {
        return ballThread;
    }

    public void setBallThread(Thread ballThread) {
        this.ballThread = ballThread;
    }

    public BallTask getBallTask() {
        return ballTask;
    }

    public void setBallTask(BallTask ballTask) {
        this.ballTask = ballTask;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getCordX() {
        return cordX;
    }

    public void setCordX(int cordX) {
        this.cordX = cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public void setCordY(int cordY) {
        this.cordY = cordY;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    public void setOutSide(boolean outSide) {
        this.moving = outSide;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * Método para mover la bola
     */
    public void moveBall(String action) {
        int absX = Math.abs(this.getVelX());
        int absY = Math.abs(this.getVelY());

        if (!this.stopped) {
            if (action.equals("left")) {
                if (this.channel.isOk()) {
                    this.running = false;
                    this.channel.send(this);
                }
                this.setVelX(absX);
                this.rect.setBounds(this.cordX, this.cordY, this.dimension, this.dimension);
                cordX = cordX + velX;
                cordY = cordY + velY;

            } else if (action.equals("right")) {
                if (this.channel.isOk()) {
                    this.running = false;
                    this.channel.send(this);
                }
                this.setVelX(-absX);
                this.rect.setBounds(this.cordX, this.cordY, this.dimension, this.dimension);
                cordX = cordX + velX;
                cordY = cordY + velY;

            } else if (action.equals("up")) {
                this.setVelY(absY);
                this.rect.setBounds(this.cordX, this.cordY, this.dimension, this.dimension);
                cordX = cordX + velX;
                cordY = cordY + velY;
            } else if (action.equals("down")) {
                this.setVelY(-absY);
                this.rect.setBounds(this.cordX, this.cordY, this.dimension, this.dimension);
                cordX = cordX + velX;
                cordY = cordY + velY;
            } else {
                this.rect.setBounds(this.cordX, this.cordY, this.dimension, this.dimension);
                cordX = cordX + velX;
                cordY = cordY + velY;
            }
        }
    }

    /**
     * Método para pintar la bola.
     */
    public void paint(Graphics g) {
        if(running){
            g.setColor(this.color);
            g.fillOval(this.cordX, this.cordY, this.dimension, this.dimension);
        }
    }

    public void run() {
        this.running = true;
        while (running) {
                try {
                    Thread.sleep(this.sleepTime);
                    ballTask.checkNextMove(this);
                } catch (Exception e) {
                    e.printStackTrace();

                }
        }
    }
}
