package org.firstinspires.ftc.teamcode.SelfDriving;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.Helpers.bMath;
import org.firstinspires.ftc.teamcode.Initializers.HardwareHelper;

public class Pose extends HardwareHelper {
    private double x, y, theta; //odometer positions

    private int[] prevTicks = new int[3];

    public Pose(double x, double y, double theta)
    {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public void updatePose(){
        int[] ticks = new int[3];
        //for (int i=0; i<3; i++) ticks[i] = bot.encoders()[i].getCurrentPosition(); //get encoder positions
        ticks[1] = -ticks[1]; //correct for backwards odometer
        int newRightTicks = ticks[0] - prevTicks[0];
        int newLeftTicks =  ticks[1] - prevTicks[1];
        int newXTicks = ticks[2] - prevTicks[2];
        prevTicks = ticks;
        double rightDist = newRightTicks * (Config.goBuildaOdoTicksToCm); //convert from ticks to cm
        double leftDist = newLeftTicks * (Config.goBuildaOdoTicksToCm); //convert from ticks to cm
        double backDist = newXTicks * (Config.odoTicksToCm); //convert from ticks to cm
        double dyR = 0.5 * (rightDist + leftDist); //average of left/right odometer delta
        double headingChangeRadians = (rightDist - leftDist) / (Config.odoXOffset * 2);
        if (Math.abs(headingChangeRadians) != 0) { //if robot has turned since last update accout for turning
            double turnRadius = Config.odoXOffset * (leftDist + rightDist) / (rightDist - leftDist);
            double strafeRadius = backDist / headingChangeRadians - Config.odoYOffset;
            y += turnRadius*Math.sin(headingChangeRadians)+strafeRadius*(1-Math.cos(headingChangeRadians)); //cumulate y axis
            x -= turnRadius*(Math.cos(headingChangeRadians)-1)+strafeRadius*(headingChangeRadians); //subtract to correct direction for x axis
            theta = -bMath.regularizeAngleRad(theta + headingChangeRadians); //cumulate total angle from odometry
        } else { //simple formulas if we haven't turned
            x += dyR;
            y += backDist;
        }
    }

    public double getX(){return x;}

    public double getY() {return y;}

    public double getTheta(){return theta;}
}
