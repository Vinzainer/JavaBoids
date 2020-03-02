

public class Boid{

    private double[] position;
    private double[] direction;
    private static double speed = 1.0;

    public Boid(double[] position, double[] direction, double speed){
        this.position = position;
        this.direction = direction;
        this.speed = speed;
    }

    public Boid(double[] position,double[] direction){
        this.position = position;
        this.direction = direction;
        this.speed = 1.0;
    }

    public Boid(Boid boid){
        this.direction = boid.direction;
        this.position = boid.position;
    }

}
