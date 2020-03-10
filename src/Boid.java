import java.lang.reflect.Array;
import java.util.ArrayList;

public class Boid {

    private static int nbBoid = 0;
    private static int nId = 0;
    private static double maxVelocity = 100.0;
    private static int collisionRadius = 5;
    private static int detectionRadius = 50;

    private double[] position;
    private double[] vector;
    private double velocity;
    private String id;


    // constructors

    /**
     * Class Constructor
     *
     * @param position  double[2]
     * @param velocity double
     */
    public Boid(double[] position, double[] vector) {
        this.position = position;
        this.vector = vector;
        this.velocity = maxVelocity;
        this.id = genId();
        addNId(1);
        addNbBoid(1);

    }

    /**
     * Class Constructor for Copy
     *
     * @param boid Boid to copy
     */
    public Boid(Boid boid) {
        this.velocity = boid.velocity;
        this.position = boid.position;
        this.vector = boid.vector;
        this.id = genId();
        addNId(1);
        addNbBoid(1);
    }


    // position

    /**
     * set-er for position
     *
     * @param new_position double[2]
     */
    public void setPosition(double[] new_position) {
        position = new_position;
    }

    /**
     * get-er for Position
     *
     * @return double[2]
     */
    public double[] getPosition() {
        return position;
    }

    public double[] getVector(){
        return vector;
    }

    public void warp() {
        position[0] = (position[0]+1200.0)%1200.0;
        position[1] = (position[1]+900.0)%900.0;
    }

    private static double[] normalize(double[] vect){
        if(vect.length == 0){
            return vect;
        }
        double sum = 0;
        double[] ret = new double[vect.length];
        for(double d : vect){
            sum += Math.abs(d);
        }
        for(int i = 0; i < vect.length; i++){
            ret[i] = vect[i]/sum;
        }
        return ret;
    }

    /**
     * TODO
     * @param velocityFraction
     * @param widthLimit
     * @param heightLimit
     * @param boids
     */
    public void nextPos(int velocityFraction, int widthLimit, int heightLimit, ArrayList<Boid> boids) {

        ArrayList<Boid> inDetectionRange = inRange(boids, detectionRadius);

        double[] alignementVect = alignement(inDetectionRange);
        double[] cohesionVect = cohesion(inDetectionRange);

        vector[0] = vector[0] + cohesionVect[0]; //+ alignementVect[0];
        vector[1] = vector[1] + cohesionVect[1]; //+ alignementVect[1];
        vector = normalize(vector);

        position[0] += (vector[0]) * velocity * 0.02;
        position[1] += (vector[1]) * velocity * 0.02;
        warp();
    }



    private double[] cohesion(ArrayList<Boid> boids){
        double[] retVector = {0,0};
        if(boids.isEmpty()) return retVector;
        for(int i = 0; i < boids.size(); i++){
            retVector[0] += boids.get(i).getPosition()[0];
            retVector[1] += boids.get(i).getPosition()[1];
        }
        retVector[0] = (retVector[0]/boids.size() - position[0]);
        retVector[1] = (retVector[1]/boids.size() - position[1]);

        return normalize(retVector);
    }

    ///**
    // * TODO
    // * @return

    private double[] alignement(ArrayList<Boid> boids){
      double[] retVector = {0,0};
      if(boids.isEmpty()) return retVector;
        for(int i = 0; i < boids.size(); i++){
          retVector[0] += boids.get(i).getPosition()[0];
          retVector[1] += boids.get(i).getPosition()[1];
        }
      retVector[0] = retVector[0]/boids.size();
      retVector[1] = retVector[1]/boids.size();

      return normalize(retVector);
    }
//
    ///**
    // * TODO
    // * @return
    // */
    //private double[] separation(){
//
    //}

    private ArrayList<Boid> inRange(ArrayList<Boid> boids, int range){
        ArrayList<Boid> res = new ArrayList<>();
        for(int i = 0; i < boids.size(); i++){
            if(dist(boids.get(i)) < range && boids.get(i).getId() != id){
                res.add(boids.get(i));
            }
        }
        return res;
    }

    private double getAngle(Boid boid){
        double dx = boid.getPosition()[0] - position[0];
        double dy = boid.getPosition()[1] - position[1];
        return angle_trunc(Math.atan2(dy,dx));
    }

    private double angle_trunc(double a){
        double b = a;
        while(b < 0.0){
            b += Math.PI *2;
        }
        return b;
    }

    private static boolean counterClockWise(double ax, double ay, double bx,double by,double cx,double cy){
        return (cy - ay) * (bx - ax) > (by - ay) * (cx - ax);
    }

    private static boolean intersect2Segments(double ax,double ay,double bx,double by,double cx,double cy,double dx,double dy){
        boolean tmp1 = counterClockWise(ax, ay, cx, cy, dx, dy) != counterClockWise(bx, by, cx, cy, dx, dy);
        boolean tmp2 = counterClockWise(ax, ay, bx, by, cx, cy) != counterClockWise(ax, ay, bx, by, dx, dy);
        return tmp1 && tmp2;
    }

    private boolean intersectWalls(double ax, double ay, int widthLimit,int  heightLimit){
        return (intersect2Segments(position[0], position[1], ax, ay, 0, 0, 0, heightLimit) ||
                intersect2Segments(position[0], position[1], ax, ay, 0, heightLimit, widthLimit, heightLimit) ||
                intersect2Segments(position[0], position[1], ax, ay, widthLimit, heightLimit, widthLimit, 0) ||
                intersect2Segments(position[0], position[1], ax, ay, widthLimit, 0, 0, 0));
    }


    public void del(){
        nbBoid--;
        position = null;
        vector = null;
        id = null;
    }

    public boolean isOutOfBounds(int widthLimit, int heightLimit){
        return (position[0] >= widthLimit || position[0] < 0 || position[1] >= heightLimit || position[1] < 0);
    }

    private double dist(Boid boid){
        double sx = getPosition()[0];
        double sy = getPosition()[1];
        return Math.sqrt((boid.getPosition()[0] - sx)*(boid.getPosition()[0] - sx) + (boid.getPosition()[1] - sy)*(boid.getPosition()[1] - sy));
    }


    public static int getDetectionRadius(){
        return detectionRadius;
    }

    public static int getCollisionRadius(){
        return collisionRadius;
    }

    //velocity

    /**
     * set-er for velocity
     * @param spd double
     */
    public void setVelocity(double spd){
        velocity = spd;
    }

    /**
     * get-er for velocity
     * @return velocity attribute
     */
    public double getVelocity(){
        return velocity;
    }


    //id

    /**
     * add x to nId
     * @param x int
     */
    public static void addNId(int x){
        nId += x;
    }

    /**
     * generate an id with nIdS
     */
    private String genId(){
        return "b" + nId;
    }

    /**
     * get-er for id
     * @return id attribute
     */
    public String getId(){
        return id;
    }

    /***
     * set-er for id
     * @param nId String
     */
    public void setId(String nId){
        id = nId;
    }

    //nbBoid

    /**
     * get-er for nbBoid
     * @return nbBoid
     */
    public static int getNbBoid(){
        return nbBoid;
    }

    /**
     * add x to nbBoid
     * @param x int
     */
    public static void addNbBoid(int x){
        nbBoid += x;
    }

    //others

    /**
     * Make a simple description of the object and return it as a String
     */
    @Override
    public String toString(){
        String res = id + ":\n";
        res += "position  x: " + position[0] + " y: " + position[1] + "\n";
        res += "vector : " + vector[0] + "," + vector[1] + "rad : " + Math.atan2(vector[0], vector[1]);
        return res;
    }

}
