import java.util.ArrayList;

public class Boid{

    //attributes
    private double[] position;
    private double direction; // in rad
    private static double speed = 100.0;
    private static int nbBoid = 0;
    private static int nId = 0;
    private static final double turnFraction = Math.PI;
    private static final int collisionRadius = 5;
    private static final int detectionRadius = 70;
    private static final double detectionAngle = 240;
    private String id;

    //constructors

    /**
     * Class Constructor  
     * @param position double[2]
     * @param direction double
     */
    public Boid(double[] position, double direction){
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

    //collisionRadius
    public static int getCollisionRadius(){
        return collisionRadius;
    }

    public static double getDetectionAngle(){
        return detectionAngle;
    }

    //direction
    /**
     * Get-er for direction
     * @return double direction attribute
     */
    public double getDirection(){
        return direction;
    }
    
    /**
     * set-er for direction
     * @param nDir double[2]
     */
    public void setDirection(double nDir){
        direction = nDir;
    }

    /**
     * modifier for direction todo
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

    public void nextPos(int speedFraction, int widthLimit, int heightLimit, ArrayList<Boid> boids){

        double angleStep = 5;   // angle between each collision segment
        double tmpx;            
        double tmpy;            
        double tmpdir = 0;
        double[] nPos = new double[2];      
        tmpx = position[0] + detectionRadius * Math.cos(direction);             // in front
        tmpy = position[1] + detectionRadius * Math.sin(direction);             // in front

        if( !intersects(position[0],position[1],tmpx,tmpy,boids) && !intersectWalls(tmpx, tmpy, widthLimit, heightLimit)){
            nPos[0] = position[0] + speed/speedFraction * Math.cos(direction);
            nPos[1] = position[1] + speed/speedFraction * Math.sin(direction);
            position = nPos;
            //System.out.println("all good");
            return;
        }

        //System.out.println(tmpx);
        int r = (int)(Math.random()*2);
        if(true){

            for(int i = 1; i*angleStep < detectionAngle/2; i++){
                tmpx = position[0] + detectionRadius * Math.cos(direction + i*angleStep);
                tmpy = position[1] + detectionRadius * Math.sin(direction + i*angleStep);
                if(!intersects(position[0], position[1], tmpx, tmpy, boids) || !intersectWalls(tmpx, tmpy, widthLimit, heightLimit)){
                    direction = (direction + (0.1));
                    break;
                }
                tmpx = position[0] + detectionRadius * Math.cos(direction - i*angleStep);
                tmpy = position[1] + detectionRadius * Math.sin(direction - i*angleStep);
                if(!intersects(position[0], position[1], tmpx, tmpy, boids) || !intersectWalls(tmpx, tmpy, widthLimit, heightLimit)){
                    direction = (direction - (0.1)); 
                    break;
                }
            }
        }
        else{
            for(int i = 1; i*angleStep < detectionAngle/2; i++){
                tmpx = position[0] + detectionRadius * Math.cos(direction - i*angleStep);
                tmpy = position[1] + detectionRadius * Math.sin(direction - i*angleStep);
                if(!intersects(position[0], position[1], tmpx, tmpy, boids) && !intersectWalls(tmpx, tmpy, widthLimit, heightLimit)){
                    direction = (direction - (0.1)) % 360;
                    break;
                }
                tmpx = position[0] + detectionRadius * Math.cos(direction + i*angleStep);
                tmpy = position[1] + detectionRadius * Math.sin(direction + i*angleStep);
                if(!intersects(position[0], position[1], tmpx, tmpy, boids) && !intersectWalls(tmpx, tmpy, widthLimit, heightLimit)){
                    direction = (direction + (0.1)) % 360; 
                    break;
                }
            }
        }

        nPos[0] = position[0] + speed/speedFraction * Math.cos(direction);
        nPos[1] = position[1] + speed/speedFraction * Math.sin(direction);
        position = nPos;
    }
    /**
     * todo
     * @param x
     * @param y
     * @return
     */
    private  boolean intersect(double ax, double ay, double bx, double by,Boid boid){
        if(boid == this) return false;

        return shortestDistance(ax, ay, bx, by, boid.getPosition()[0],boid.getPosition()[1]) < collisionRadius;
    }

    private ArrayList<Boid> inRange(ArrayList<Boid> boids){
        ArrayList<Boid> res = new ArrayList<>();
        for(int i = 0; i < boids.size(); i++){
            if(dist(boids.get(i)) < detectionRadius){
                res.add(boids.get(i));
            }
        }
        return res;
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

    private boolean intersects(double ax, double ay, double bx, double by, ArrayList<Boid> boids){
        for(int i = 0; i < boids.size(); i++){
            if(intersect(ax,ay,bx,by,boids.get(i))){
                return true;
            }
        }
        return false;
    }

    private static double shortestDistance(double x1,double y1,double x2,double y2,double x3,double y3)
    {
        double px=x2-x1;
        double py=y2-y1;
        double temp=(px*px)+(py*py);
        double u=((x3 - x1) * px + (y3 - y1) * py) / (temp);
        if(u>1){
            u=1;
        }
        else if(u<0){
            u=0;
        }
        double x = x1 + u * px;
        double y = y1 + u * py;
        double dx = x - x3;
        double dy = y - y3;
        double dist = Math.sqrt(dx*dx + dy*dy);
        return dist;

    }

    public static int getDetectionRadius(){
        return detectionRadius;
    }

    private double dist(Boid boid){
        double sx = getPosition()[0];
        double sy = getPosition()[1];
        return Math.sqrt((boid.getPosition()[0] - sx)*(boid.getPosition()[0] - sx) + (boid.getPosition()[1] - sy)*(boid.getPosition()[1] - sy));
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
        res += "direction rad: " + direction + " deg : " + direction * 180/Math.PI ;
        return res;
    }

}