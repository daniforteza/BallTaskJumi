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
public class Statistics {

    private int totalBalls;
    private int stoppedBalls;
    private int ballsInside;

    public void newBall() {
        this.totalBalls += 1;

    }

    public int getTotalBalls() {

        return this.totalBalls;
    }

    public void setPausedBalls() {
        this.stoppedBalls++;

    }

    public int getStoppedBalls() {

        return this.stoppedBalls;
    }

    public void setInsideBH() {
        this.ballsInside += 1;
    }


    public void setStoppedBalls(){
        this.stoppedBalls +=1;
    }

    public int getBallsInside() {

        return this.ballsInside;
    }

    public void removeBall(){

        this.totalBalls -=1;
    }
    public void removeFromBH(){
        this.ballsInside -=1;
    }

    public void removeStoppedBalls(){
        this.stoppedBalls -=1;
    }

    public void resetStatistics(){
        this.totalBalls = 0;
        this.stoppedBalls = 0;
        this.ballsInside = 0;
    }

    public void setBall() {
        this.totalBalls +=1;
    }
}