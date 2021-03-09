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
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel implements Runnable {

    public static final String PLAY = "PLAY";
    public static final String PAUSE = "PAUSE";
    public static final String CREATE_BALLS = "CREATE BALLS";


    public JToggleButton play, pause , createBall;
    private Statistics statistics;
    private ArrayList<Ball> ballList;
    private BallTask ballTask;
    private JTable statisticsTable;
    private Thread threadControlPanel;

    public ControlPanel(BallTask ballTask,Statistics statistics, ArrayList balls) {
        this.ballTask = ballTask;
        this.statistics = statistics;
        this.ballList = balls;
        this.setLayout(new GridBagLayout());
        GridBagConstraints bagConstraints = new GridBagConstraints();
        this.createControlPanel(bagConstraints);
        this.addStatistics(bagConstraints);
        this.threadControlPanel = new Thread(this);
        this.threadControlPanel.start();
    }

    public void createControlPanel(GridBagConstraints bagConstraints) {


        this.play = new JToggleButton(PLAY);
        this.play.addActionListener(this.ballTask);
        bagConstraints.fill = GridBagConstraints.HORIZONTAL;
        bagConstraints.weightx = 1.5;
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 0;
        bagConstraints.gridwidth = 2;
        bagConstraints.ipadx = 150;
        bagConstraints. insets = new Insets(0,10,0,0);
        this.add(this.play, bagConstraints);

        this.pause = new JToggleButton(PAUSE);
        this.pause.addActionListener(this.ballTask);
        bagConstraints.fill = GridBagConstraints.BOTH;
        bagConstraints.weightx = 0.5;
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 1;
        this.add(this.pause, bagConstraints);

        this.createBall = new JToggleButton(CREATE_BALLS);
        this.createBall.addActionListener(this.ballTask);
        bagConstraints.fill = GridBagConstraints.BOTH;
        bagConstraints.weightx = 0.5;
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 2;
        this.add(this.createBall, bagConstraints);

        addStatistics(bagConstraints);

    }



    private void addStatistics(GridBagConstraints bagConstraints) {

        this.statisticsTable = new JTable(4, 2);
        this.statisticsTable.getColumnModel().getColumn(0).setHeaderValue("INFO");
        this.statisticsTable.getColumnModel().getColumn(1).setHeaderValue("TOTAL");
        this.statisticsTable.setValueAt("Total Balls ", 0, 0);
        this.statisticsTable.setValueAt("Balls Inside", 1, 0);
        this.statisticsTable.setValueAt("Stopped Balls", 2, 0);
        this.statisticsTable.setValueAt("Balls waiting: ", 3, 0);
        this.statisticsTable.setVisible(true);
        bagConstraints.gridx = 1;
        bagConstraints.gridy = 3;
        bagConstraints.gridwidth = 2;
        bagConstraints.insets = new Insets(100,15,0,15);
        this.statisticsTable.getTableHeader().setVisible(true);
        this.add(this.statisticsTable.getTableHeader(), bagConstraints);
        bagConstraints.gridy = 4;
        bagConstraints.gridheight = 4;
        bagConstraints.insets = new Insets(0,10,0,0);
        this.add(this.statisticsTable, bagConstraints);
    }



    private void refreshStatistics(){
        this.statisticsTable.setValueAt(statistics.getTotalBalls(),0,1);
        this.statisticsTable.setValueAt(statistics.getTotalBalls()-statistics.getBallsInside(),1,1);
        this.statisticsTable.setValueAt(statistics.getBallsInside(),2,1);
        this.statisticsTable.setValueAt(statistics.getStoppedBalls(),3,1);
    }

    public void run() {
       do {
           try {
               refreshStatistics();}
           catch (Exception e){
               e.printStackTrace();
           }
       }while (true);
    }
}
