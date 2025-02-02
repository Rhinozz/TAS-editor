import java.awt.*;
import java.awt.event.MouseEvent;

public class StickPosition {

    private int x;
    private int y;

    private double theta;
    private double radius;

    private final static int MAX_SIZE = 32767;

    private StickPosition previousPos;

    public StickPosition (){
        this(0,0);
    }

    public StickPosition (StickPosition pos){
        this(pos.getX(), pos.getY());
    }

    public StickPosition (int x, int y){
        this.x = x;
        this.y = y;
    }

    private void updatePolar (){
        radius = Math.sqrt(Math.pow(x,2) + Math.pow(y,2))/(double)MAX_SIZE;

        if (radius > 1){
            radius = 1;
            updateCart();
        }

        if (x >= 0 && y >= 0) {
            theta = Math.atan((double) y / (double) x);
        }

        if ((x < 0 && y >= 0) || (x < 0 && y < 0)){
            theta = Math.PI - Math.atan( (double) y/ (double) x);
        }

        if (x >= 0 && y < 0){
            theta = (Math.PI*2) + Math.atan((double)y/(double)x);
        }

       // System.out.println("theta: " + Math.toDegrees(theta));

    }

    private void updateCart (){
        x = (int)((radius*MAX_SIZE)*Math.cos(theta));
        y = (int)((radius*MAX_SIZE)*Math.sin(theta));
    }

    public double getAngle (Point circleOrigin, MouseEvent e){
        double side1 = e.getX() - circleOrigin.getX();
        double side2 = e.getY() - circleOrigin.getY();

        double hypot = Math.hypot(side1,side2);

        double alpha = Math.asin(side1/hypot);
        double beta = Math.asin(side2/hypot);

        return alpha;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getTheta() {
        return theta;
    }

    public double getRadius() {
        return radius;
    }

    public static int getMaxSize() {
        return MAX_SIZE;
    }

    public void setXY(int x, int y) {

        previousPos = new StickPosition(this.x,this.y);
        this.x = x;
        this.y = y;

        updatePolar();
    }

    public void setBackPos (){
        //setXY(previousPos.getX(), previousPos.getY());
    }

    public void setTheta(double theta) {
        this.theta = theta;
        updateCart();
    }

    public void setRadius(double radius) {
        this.radius = radius;
        updateCart();
    }
}
