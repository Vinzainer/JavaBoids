
public abstract class Test{


    public static void main(String[] args){

        double[] pos1 = {2,3};
        double[] dir1 = {1,1};
        double[] pos2 = {6,6};

        Boid b0 = new Boid(pos1,dir1);
        Boid b1 = new Boid(b0);

        b1.modPosition(dir1);

        System.out.println(b0);
        System.out.println(b1);

    }
}