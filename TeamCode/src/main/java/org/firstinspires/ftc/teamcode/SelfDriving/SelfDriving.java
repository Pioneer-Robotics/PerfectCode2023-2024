package org.firstinspires.ftc.teamcode.SelfDriving;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.Helpers.bMath;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class SelfDriving extends AbstractHardwareComponent {
    //PIDs for x,y, and turning inputs
    double xPID, yPID, rxPID;

    public int[] prevTicks = new int[3];
    public double[] poseOld = new double[3];

    private Pose pose;

    public SelfDriving(Pose pose){
        this.pose = pose;
    }

    /**
     * This is driving method that goes to x,y, and theta with tuned PID.
     * Input a movement object that has that data and the method goes there.
     * To drive there it uses mecanum driving code tht takes in x, y, and turning input. To drive there,
     * there is PID for each position which then scales speed and where to go.
     * Lastly, movement has an abstract method called runExtra() which is ran in the method--use to run other stuff.
     * @param movement abstract object that has x,y, theta, and PID values that are used
     */
    public void drive(Movement movement){
        movement.getCoefficients().resetForPID();//resetting value to begin loop

        while(thetaCondition(movement) || xCondition(movement) ||  yCondition(movement)){
            pose.updateOdo();
            double dX = movement.getdX(), dY = movement.getdY(), dTheta = movement.getdTheta();
            double x = pose.getXX(), y = pose.getYY();

            //updating PID values for x, y and theta
            xPID = movement.getCoefficients().getPID(dX - x, Math.abs(dX), Config.speed);
            yPID = movement.getCoefficients().getPID(dY - y, Math.abs(dY), Config.speed);
            rxPID = Config.turn.getPID(Config.turn, bMath.subtractAnglesDeg(dTheta, bot.angleDEG()), dTheta, 0.4);

            telemetry.addData("x:", x);
            telemetry.addData("y: ", y);
            telemetry.addData("subx:", subX(movement));
            telemetry.addData("suby:", subY(movement));
            telemetry.addData("subhead", subHead(movement));

            movement.runExtra();//run extra if needed
            drive(xPID, yPID, Math.copySign(rxPID, subHead(movement)));//drive there
            bot.update();
        }
    }

    /**
     * Driving code to go to position. It is essentially mecanum driving but uses PID to drive there.
     * All inputs are scaled to be between -1 and 1 since power goes to motors
     * @param x x input
     * @param y y input
     * @param rx turn input
     */
    private void drive(double x, double y, double rx){
        double rotX = x * Math.cos(bot.angleRAD()) - y * Math.sin(bot.angleRAD());
        double rotY = x * Math.sin(bot.angleRAD()) + y * Math.cos(bot.angleRAD());
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        bot.setPowers(frontLeftPower, backLeftPower, frontRightPower, backRightPower);
    }

    /**
     * X position condition for loop
     * @param movement object to access desired X
     * @return true or false if robot has reached its dX
     */
    boolean xCondition(Movement movement){return Math.abs(movement.getdX() - pose.getXX()) > 0.25;}
    /**
     * Y position condition for loop
     * @param movement object to access desired Y
     * @return true or false if robot has reached its dY
     */
    boolean yCondition(Movement movement){return Math.abs(movement.getdY() - pose.getYY()) > 0.25;}
    /**
     * Theta position condition for loop. Uses subtractingAnglesDeg to calculate error
     * @param movement object to access desired theta
     * @return true or false if robot has reached its dTheta
     */
    boolean thetaCondition(Movement movement){return Math.abs(bMath.subtractAnglesDeg(movement.getdTheta(), bot.angleDEG())) > 1;}

    /**
     *
     * @param movement
     * @return current pos
     */
    public double subHead(Movement movement){return bMath.subtractAnglesDeg(movement.getdTheta(), bot.angleDEG());}

    public double subX(Movement movement){return movement.getdX() - pose.getXX();}

    public double subY(Movement movement){return movement.getdY() - pose.getYY();}

    public double getxPID(){return xPID;}

    public double getyPID() {return yPID;}

    public double getRxPID() {return rxPID;}

    public double[] getPoseOld(){return poseOld;}


    //OLD
    public double[] updateOdometryJ(){
        int[] ticks = new int[3];
        //for (int i=0; i<3; i++) ticks[i] = bot.encoders()[i].getCurrentPosition(); //get encoder positions
        ticks[1] = -ticks[1]; //correct for backwards odometer
        int newRightTicks = ticks[0] - prevTicks[0];
        int newLeftTicks =  ticks[1] - prevTicks[1];
        int newXTicks = ticks[2] - prevTicks[2];
        prevTicks = ticks;
        double rightDist = newRightTicks * (Config.goBuildaOdoTicksToCm); //convert from ticks to cm
        double leftDist = newLeftTicks * (Config.goBuildaOdoTicksToCm); //convert from ticks to cm
        double backDist = newXTicks * (Config.goBuildaOdoTicksToCm); //convert from ticks to cm
        double dyR = 0.5 * (rightDist + leftDist); //average of left/right odometer delta
        //double headingChangeRadians = (rightDist - leftDist) / Config.goBuildaOdoDiameter; //incorrect formula
        double headingChangeRadians = (rightDist - leftDist) / (Config.odoXOffset * 2);
        //double avgHeadingRadians = pose[2] + headingChangeRadians / 2.0;//gets heading if needed
        //double cos = Math.cos(bot.angleRAD());
        //double sin = Math.sin(bot.angleRAD());
        if (Math.abs(headingChangeRadians) != 0) { //if robot has turned since last update accout for turning
            double turnRadius = Config.odoXOffset * (leftDist + rightDist) / (rightDist - leftDist);
            double strafeRadius = backDist / headingChangeRadians - Config.odoYOffset;
            poseOld[0] += turnRadius*Math.sin(headingChangeRadians)+strafeRadius*(1-Math.cos(headingChangeRadians)); //cumulate y axis
            poseOld[1] -= turnRadius*(Math.cos(headingChangeRadians)-1)+strafeRadius*(headingChangeRadians); //subtract to correct direction for x axis
            poseOld[2] = -bMath.regularizeAngleRad(poseOld[2] + headingChangeRadians); //cumulate total angle from odometry
        } else { //simple formulas if we haven't turned
            poseOld[1] += dyR;
            poseOld[0] += backDist;
        }
        /* old incomplete formulas
        pose[0] += -backDist*sin + dyR*cos;
        pose[1] += backDist*cos + dyR*sin;
        pose[2] = bMath.regularizeAngleRad(pose[2] + headingChangeRadians);*/
        return poseOld;
    }




}
