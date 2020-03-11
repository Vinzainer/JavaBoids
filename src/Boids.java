import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;
import java.lang.Math;
//import javax.swing.JFrame;
import java.util.ArrayList;

public class Boids extends JPanel{

    private ArrayList<Boid> boids;
    private static final int speedFraction = 60;
    private static final double[] angles = {0 , 2.44, 3.84};
    private static final int[] ranges = {7,5,5};

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
        double rdRad;
        for(int i = 0; i < nbBoid; i++){
            double[] pos = {Math.random() * width, Math.random() * height};
            rdRad = Math.random() * 2 * Math.PI;
            double[] vector = {Math.cos(rdRad), Math.sin(rdRad)};
            boids.add(new Boid(pos, vector));
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
        double[] vector = boid.getVector();
        int r = Boid.getCollisionRadius();
        int dr = Boid.getDetectionRadius();
        g.setColor(Color.BLACK);
        //g.drawOval(round(bx - dr/2), round(by - dr/2), dr, dr);

        //g.drawLine(round(bx), round(by), round(bx + 30*Math.cos(dir)), round(by + 30*Math.sin(dir)));
        g.setColor(Color.RED);
        g.fillOval(round(bx - r/2),round(by - r/2), r, r);
    }

    public void next(){
        for(Boid boid : boids){
            boid.nextPos(speedFraction, getWidth(),getHeight(),boids);
        }
    }

    public void drawBoids(Graphics g){
        for(int i = 0; i < boids.size(); i++){ 
            //drawBoid(boids.get(i), g);
            drawTriangle(boids.get(i), g);
        };
    }

    public void drawTriangle(Boid boid, Graphics g){
        double[] vector = boid.getVector();
        double dir = Math.atan2(vector[1],vector[0]);
        int[] xs = new int[3];
        int[] ys = new int[3];
        for(int i = 0; i < 3; i++){
            xs[i] = round(boid.getPosition()[0] + ranges[i] * Math.cos(dir + angles[i]));
            ys[i] = round(boid.getPosition()[1] + ranges[i] * Math.sin(dir + angles[i]));
        }
        g.fillPolygon(xs,ys,3);
    }

    public void remove(Boid boid){
        boids.remove(boid);
        boid.del();
    }

    public void removeOOB(){
        for(int i = 0 ; i < boids.size(); i++){
            if(boids.get(i).isOutOfBounds(getWidth(), getHeight())) remove(boids.get(i));
        }
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

    //public void printCollision(){
    //    for(int i = 0; i < boids.size(); i++){
    //        for(int j = 0; j < boids.size(); j++){
    //            if(boids.get(i).collide(boids.get(j))){
    //                System.out.println(boids.get(i).getId() + " collided with " + boids.get(j).getId());
    //            }
    //        }
    //    }
    //}

    public String toString(){
        String res = "";
        for(int i = 0; i < boids.size(); i++){
            res += boids.get(i).toString() + "\n";
        }
        return res;
    }

}