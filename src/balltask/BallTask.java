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
import communications.ClientConnection;
import communications.ServerConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



public class BallTask extends JFrame implements ActionListener {
    private Viewer viewer;
    private String direction = "derecha";
    private ControlPanel controlPanel;
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<BlackHole> blackHoles;
    private ArrayList<Ball> toRemove = new ArrayList<>();
    private ArrayList<Ball> toAdd = new ArrayList<>();
    private Dimension dimension;
    private Statistics statistics;
    private Channel channel;
    private ServerConnection serverConnection;
    private ClientConnection clientConnection;

    public BallTask(String ip) {
        this.setTitle("BallTask Jumi");

        this.dimension = new Dimension(700,700);
        this.setSize(dimension.width, dimension.height);
        this.setVisible(true);
        this.channel = new Channel(this);
        this.serverConnection = new ServerConnection(this.channel, this);
        this.clientConnection = new ClientConnection(this.channel, ip, this);
        this.setLayout(new GridBagLayout());
        GridBagConstraints alfa = new GridBagConstraints();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.statistics = new Statistics();
        this.viewer = new Viewer(dimension.width, dimension.height, this,this.balls);
        this.createBH();
        this.viewer.setBalls(this.balls);
        this.addViewer(alfa);
        this.addControlPanel(alfa);
        this.pack();


    }



    public static void main(String[] args) {
        String remoteIp = null;
        while (remoteIp==null){
            remoteIp = JOptionPane.showInputDialog("Introduce la ip: ");
        }
        BallTask ballTask = new BallTask(remoteIp);

    }

    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();

        switch(event){
            case ControlPanel.PLAY:
                controlPanel.createBall.setSelected(false);
                controlPanel.pause.setSelected(false);
                playBalls();
                break;
            case ControlPanel.CREATE_BALLS:
                controlPanel.play.setSelected(false);
                controlPanel.pause.setSelected(false);
                createBalls();
                break;
            case ControlPanel.PAUSE:
                controlPanel.play.setSelected(false);
                controlPanel.createBall.setSelected(false);
                pauseBalls();
                break;


        }

    }
    private void addControlPanel(GridBagConstraints alfa) {
        this.controlPanel = new ControlPanel(this,statistics, balls);
        this.controlPanel.setBackground(Color.black);
        alfa.anchor = GridBagConstraints.CENTER;
        alfa.fill = GridBagConstraints.BOTH;
        alfa.gridy = 0;
        alfa.gridx = 0;
        alfa.weighty = 1.0;
        alfa.weightx = 0.1;
        alfa.gridwidth = 1;
        this.add(controlPanel, alfa);
    }

    private void addViewer(GridBagConstraints alfa) {
        alfa.gridx = 1;
        alfa.weightx = 1.9;
        this.add(this.viewer, alfa);
        Thread viewerThread = new Thread(this.viewer);
        viewerThread.start();

    }


    private void createBalls() {
        balls = new ArrayList<Ball>();
        for (int i = 0; i < 15; i++) {
            Ball ball = new Ball(this,this.channel);
            balls.add(ball);
            this.statistics.newBall();
        }
        this.viewer.setBalls(balls);
    }

    public void addNewBall(Ball ball) {
        this.statistics.setBall();
        this.balls.add(ball);

    }
    private void createBH() {
        this.blackHoles = new ArrayList<BlackHole>();
        for (int i = 0; i < 2; i++) {
            BlackHole blackHole = new BlackHole(this);
            blackHoles.add(blackHole);
        }
        this.viewer.setBlackHoles(this.blackHoles);
    }

    public void removeBall(Ball ball) {
        this.statistics.removeBall();
        this.balls.remove(ball);

    }
    public void checkNextMove(Ball ball) {
        if (ball.getCordX() - ball.getVelX() <= 0) {

            ball.moveBall("left");

        } else if (ball.getCordX() + ball.getVelX() >= this.viewer.getWidth() - ball.getDimension()) {

            ball.moveBall("right");

        } else if (ball.getCordY() - ball.getVelY() <= 0) {

            ball.moveBall("up");

        } else if (ball.getCordY() + ball.getVelY() >= this.viewer.getHeight() - ball.getDimension()) {
            ball.moveBall("down");
        } else {
            ball.moveBall("");
        }
        checkBH(ball);


    }

    private void checkBH(Ball ball) {

        for (BlackHole blackHole : this.blackHoles) {
            if (blackHole.getRect().intersects(ball.getRect()) && ball.isMoving()) {
                blackHole.putBall(ball);
            }
            if (!blackHole.getRect().intersects(ball.getRect()) && !ball.isMoving()
            ) {
                blackHole.removeBall(ball);
            }
        }
    }
    public int generateRandomInt(int min, int max) {

        int randomValue = (int) Math.floor(Math.random() * (max - min + 1) + min);

        return randomValue;
    }
    public void setClientIp(String ip){
        this.clientConnection.setIp(ip);

    }

    private void pauseBalls() {
        for (Ball ball : this.balls){
            ball.setRunning(false);
        }
    }
    private void playBalls (){
        for (Ball ball:this.balls){
            ball.setRunning(true);
        }
    }
    //Getters and Setters
    public ArrayList<Ball> getToRemove() {
        return toRemove;
    }

    public void setToRemove(ArrayList<Ball> toRemove) {
        this.toRemove = toRemove;
    }

    public ArrayList<Ball> getToAdd() {
        return toAdd;
    }

    public void setToAdd(ArrayList<Ball> toAdd) {
        this.toAdd = toAdd;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }
}

