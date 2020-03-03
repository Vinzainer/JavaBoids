

public class Boid{

    //attributes
    private double[] position;
    private double[] direction;
    private static double speed = 1.0;
    private static int nbBoid = 0;
    private static int nId = 0;
    private String id;

    //constructors

    /**
     * Class Constructor  
     * @param position double[2]
     * @param direction double[2]
     */
    public Boid(double[] position, double[] direction){
        this.position = position;
        this.direction = direction;
        this.id = genId();
        addNId(1);
        addNbBoid(1);

    }

    /**
     * Class Constructor for Copy  
     * @param boid Boid to copy
     */
    public Boid(Boid boid){
        this.direction = boid.direction;
        this.position = boid.position;
        this.id = genId();
        addNId(1);
        addNbBoid(1);
    }

    //direction
    /**
     * Get-er for direction
     * @return double[2] direction attribute
     */
    public double[] getDirection(){
        return direction;
    }
    
    /**
     * set-er for direction
     * @param nDir double[2]
     */
    public void setDirection(double[] nDir){
        direction = nDir;
    }

    /**
     * modifier for direction
     * @param delta double[2]
     */
    public void modDir(double[] delta){
        //to do
    }

    //position

    /**
    * set-er for position
    * @param new_position double[2]
    */
    public void setPosition(double[] new_position){
        position = new_position;
    }

    /**
     * get-er for Position
     * @return double[2]
     */
    public double[] getPosition(){
        return position;
    }

    /**
     * modifier for position
     * @param delta double[2]
     */
    public void modPosition(double[] delta){
        double[] newPos = {position[0] + delta[0], position[1] + delta[1]};
        position = newPos;
    }

    //speed

    /**
     * set-er for speed
     * @param spd double
     */
    public static void setSpeed(double spd){
        speed = spd;
    }

    /**
     * get-er for speed
     * @return speed attribute
     */
    public static double getSpeed(){
        return speed;
    }

    /**
     * modifier for speed
     * @param delta double[2]
     */
    public static void modSpeed(double delta){
        speed += delta;
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
        res += "direction x: " + direction[0] + " y: " + direction[1];
        return res;
    }

}
