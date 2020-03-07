import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import java.lang.Math;
//import javax.swing.JFrame;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Boids extends JPanel{

    private ArrayList<Boid> boids;
    private static final int speedFraction = 60;

    public Boids(){
        int height = 900;
        int width = 1200;
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        setVisible(true);
        setBackground(Color.WHITE);
        boids = new ArrayList<Boid>();
    }

    public Boids(int nbBoid){
        int height = 900;
        int width = 1200;
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
        setBackground(Color.WHITE);
        boids = new ArrayList<Boid>();
        for(int i = 0; i < nbBoid; i++){
            double[] pos = {Math.random() * (width-50),Math.random() * (height-50)};
            double dir = (Math.random() * Math.PI);
            boids.add(new Boid(pos, dir));
            //System.out.println(boids.get(i));
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawBoids(g);
    }

    public void drawBoid(Boid boid, Graphics g){
        double bx = boid.getPosition()[0];
        double by = boid.getPosition()[1];
        double dir = boid.getDirection();
        int r = Boid.getCollisionRadius();
        int dr = Boid.getDetectionRadius();
        g.setColor(Color.BLACK);
        g.drawOval(round(bx - dr/2), round(by - dr/2), dr, dr);

        g.drawLine(round(bx), round(by), round(bx + 30*Math.cos(dir)), round(by + 30*Math.sin(dir)));
        g.setColor(Color.RED);
        g.fillOval(round(bx - r),round(by - r), r*2, r*2);
    }

    public void next(){
        for(Boid boid : boids){
            boid.nextPos(speedFraction, getWidth(),getHeight(),boids);
        }
    }

    public void drawBoids(Graphics g){
        for(int i = 0; i < boids.size(); i++){ 
            drawBoid(boids.get(i), g);
        };
    }

    private int round(double d){
        return (int) Math.round(d);
    }

    public static int getSpeedFraction(){
        return speedFraction;
    }
    
    public ArrayList<Boid> getBoids(){
        return boids;
    }

}