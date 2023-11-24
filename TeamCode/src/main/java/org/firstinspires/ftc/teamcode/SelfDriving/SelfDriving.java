package org.firstinspires.ftc.teamcode.SelfDriving;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.Helpers.VelocityMath;
import org.firstinspires.ftc.teamcode.Helpers.bMath;
import org.firstinspires.ftc.teamcode.Initializers.HardwareHelper;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;

public class SelfDriving extends HardwareHelper {
    //PIDs for x,y, and turning inputs
    double xPID, yPID, rxPID;

    //stores previous ticks inside updateOdometry() to add to rolling sum of location
    public int[] prevTicks = new int[3];
    /**
     * Stores the odometry for all three encoders. Currently we only need objects 0-1 since 2 was to calculte heading.
     * pose[1] stores x position and pose[0] stores y position
     */
    public double[] pose = new double[3];
    /**
     *
     * @param x x cooridinate to restart robot
     * @param y y coordinate to restart robot
     */
    public void resetOdometry(double x, double y, double theta){
        pose[0] = x;
        pose[1] = y;
        pose[2] = Math.toRadians(theta);
        pose[3] = 0;
        //for (int i=0; i<3; i++) prevTicks[i] = bot.getOdos()[i];
    }

    /**
     * This method updates the odometry. It keeps a running calculation of the x and y position
     * by checking the change of distance from last iteration
     * @return returns a double array. 0=y, 1=x, 2=rotation of robot calculated by odometry
     */

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
            pose[0] += turnRadius*Math.sin(headingChangeRadians)+strafeRadius*(1-Math.cos(headingChangeRadians)); //cumulate y axis
            pose[1] -= turnRadius*(Math.cos(headingChangeRadians)-1)+strafeRadius*(headingChangeRadians); //subtract to correct direction for x axis
            pose[2] = -bMath.regularizeAngleRad(pose[2] + headingChangeRadians); //cumulate total angle from odometry
        } else { //simple formulas if we haven't turned
            pose[1] += dyR;
            pose[0] += backDist;
        }
        /* old incomplete formulas
        pose[0] += -backDist*sin + dyR*cos;
        pose[1] += backDist*cos + dyR*sin;
        pose[2] = bMath.regularizeAngleRad(pose[2] + headingChangeRadians);*/
        return pose;
    }

    public double[] updateOdometry(){
        int[] ticks = new int[3];
        //for (int i=0; i<3; i++) ticks[i] = bot.encoders()[i].getCurrentPosition();
        int newRightTicks = ticks[0] - prevTicks[0];
        int newLeftTicks = ticks[1] - prevTicks[1];
        int newXTicks = ticks[2] - prevTicks[2];
        prevTicks = ticks;
        double rightDist = newRightTicks * (Config.goBuildaOdoTicksToCm);
        double leftDist = newLeftTicks * (Config.goBuildaOdoTicksToCm);
        double dyR = 0.5 * (rightDist + leftDist);
        double headingChangeRadians = (rightDist - leftDist) / Config.goBuildaOdoDiameter;
        double dxR = newXTicks * (Config.goBuildaOdoTicksToCm);
//        double avgHeadingRadians = pose[2] + headingChangeRadians / 2.0;
        double cos = Math.cos(bot.angleRAD());
        double sin = Math.sin(bot.angleRAD());
        pose[0] += -dxR*sin + dyR*cos;//y
        pose[1] += dxR*cos + dyR*sin;//x
//        pose[2] = AngleUtils.normalizeRadians(pose[2] + headingChangeRadians);
        return pose;
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
            pose = updateOdometry();//updating odometers
            double dX = movement.getdX(), dY = movement.getdY(), dTheta = movement.getdTheta();

            //updating PID values for x, y and theta
            xPID = movement.getCoefficients().getPID(dX - pose[1], Math.abs(dX), Config.speed);
            yPID = movement.getCoefficients().getPID(dY - pose[0], Math.abs(dY), Config.speed);
            rxPID = Config.turn.getPID(Config.turn, bMath.subtractAnglesDeg(dTheta, bot.angleDEG()), dTheta, 0.4);

            telemetry.addData("x:", pose[1]);
            telemetry.addData("y: ", pose[0]);
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
        double sq2 = VelocityMath.sq2;
        double imu_offset = Math.toRadians(bot.angleDEG());
        double angle = (Math.toRadians(bot.angleRAD()) - imu_offset);


        double leftDiagPower = -(((y - x) / sq2 * Math.sin(angle) + ((y + x) / sq2) * Math.cos(angle)));
        double rightDiagPower = -(((-(y + x) / sq2) * Math.sin(angle) + ((y - x) / sq2 * Math.cos(angle))));
        double leftRotatePower =  rx;
        double rightRotatePower = -rx;

        bot.setPowers((leftDiagPower + leftRotatePower),
                (rightDiagPower + leftRotatePower),
                (rightDiagPower +rightRotatePower),
                (leftDiagPower +rightRotatePower) );
    }

    /**
     * X position condition for loop
     * @param movement object to access desired X
     * @return true or false if robot has reached its dX
     */
    boolean xCondition(Movement movement){return Math.abs(movement.getdX() - pose[1]) > 0.25;}
    /**
     * Y position condition for loop
     * @param movement object to access desired Y
     * @return true or false if robot has reached its dY
     */
    boolean yCondition(Movement movement){return Math.abs(movement.getdY() - pose[0]) > 0.25;}
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

    public double subX(Movement movement){return movement.getdX() - pose[1];}

    public double subY(Movement movement){return movement.getdY() - pose[0];}

    public double getxPID(){return xPID;}

    public double getyPID() {return yPID;}

    public double getRxPID() {return rxPID;}

    public double[] getPose(){return pose;}




}
