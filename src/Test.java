import javax.swing.JFrame;
import java.awt.Dimension;

public abstract class Test {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setTitle("Boids in Java");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        Boids bs = new Boids(30);
        window.setSize(new Dimension(1200, 900));
        // window.setContentPane(bs);
        window.add(bs);

        long sTime, eTime;
        boolean end = false;
        while (!end) {
            System.out.println(bs.getBoids().get(0));
            sTime = System.currentTimeMillis();
            eTime = sTime + 1000 / Boids.getSpeedFraction();
            bs.next();
            bs.repaint();
            bs.getToolkit().sync(); 
            if (System.currentTimeMillis() < eTime) {
                try {
                    Thread.sleep(Math.abs(System.currentTimeMillis() - eTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}