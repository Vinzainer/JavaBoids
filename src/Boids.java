import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Boids extends JFrame{


    public Boids(){
        int height = 1200;
        int width = 1600;
        setPreferredSize(new Dimension(height, width));
        setVisible(true);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
}