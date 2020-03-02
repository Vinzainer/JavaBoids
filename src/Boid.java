

public class Boid{

    //attributes
    private double[] position;
    private double[] direction;
    private static double speed = 1.0;
    private static int nbBoid = 0;
    private static int nId = 0;
    private String id;

    //constructors
    public Boid(double[] position, double[] direction){
        this.position = position;
        this.direction = direction;
        this.id = genId();
        addNId(1);
        addNbBoid(1);

    }

    public Boid(Boid boid){
        this.direction = boid.direction;
        this.position = boid.position;
        this.id = genId();
        addNId(1);
        addNbBoid(1);
    }

    //direction
    public double[] getDirection(){
        return direction;
    }

    public void setDirection(double[] nDir){
        direction = nDir;
    }

    public void modDir(double[] delta){
        //to do
    }

    //position
    public void setPosition(double[] new_position){
        position = new_position;
    }

    public double[] getPosition(){
        return position;
    }

    public void modPosition(double[] delta){
        double[] newPos = {position[0] + delta[0], position[1] + delta[1]};
        position = newPos;
    }

    //speed
    public static void setSpeed(double spd){
        speed = spd;
    }

    public static double getSpeed(){
        return speed;
    }

    public static void modSpeed(double delta){
        speed += delta;
    }

    //id
    public static void addNId(int x){
        nId += x;
    }

    private String genId(){
        return "b" + nId;
    }

    public String getId(){
        return id;
    }

    public void setId(String nId){
        id = nId;
    }

    //nbBoid
    public static int getNbBoid(){
        return nbBoid;
    }

    public static void addNbBoid(int x){
        nbBoid += x;
    }

    //others
    public String toString(){
        String res = id + ":\n";
        res += "position  x: " + position[0] + " y: " + position[1] + "\n";
        res += "direction x: " + direction[0] + " y: " + direction[1];
        return res;
    }

}
