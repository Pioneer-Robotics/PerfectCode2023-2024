package org.firstinspires.ftc.teamcode.SelfDrivingAuto;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Helpers.bMath;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class SelfDriving extends AbstractHardwareComponent {
    private double xPID, yPID, rxPID; //PIDs for x,y, and turning inputs
    private double iX, iY, iTheta; //storing initial x,y values before driving

    private Pose pose; //position object

    public SelfDriving(Pose pose){
        this.pose = pose;
    }

    public boolean run(Movement movement){
        boolean run = false;
        if(movement.getdTheta() == -10000){
            run = yCondition(movement) || xCondition(movement);
        }
        else{
            run = (thetaCondition(movement) || xCondition(movement) ||  yCondition(movement));
        }
        return run;
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
        //store the initial x,y values for PID
        iX = pose.getXX();
        iY = pose.getYY();
        iTheta = -bot.angleDEG();

        while((thetaCondition(movement) || xCondition(movement) ||  yCondition(movement))  && (bot.opmode.opModeIsActive() && bot.opmode.isStarted() && !bot.opmode.isStopRequested())){
            pose.updateOdos(); //update odos
            double dX = movement.getdX(), dY = movement.getdY(), dTheta = movement.getdTheta(); //get x,y,theta from desired movement
            double x = pose.getXX(), y = pose.getYY(), theta = -bot.angleDEG(); // get x,y,theta current

            //updating PID values for x, y and theta
            if(Math.abs(iX - dX) > Config.drivingThresholdCM) {
                xPID = movement.getCoefficients().PID(dX - x, Math.abs(iX - dX), Config.speed);
            }
            if(Math.abs(iY - dY) > Config.drivingThresholdCM){
                yPID = movement.getCoefficients().PID(dY - y, Math.abs(iY - dY), Config.speed);
            }
            if(Math.abs(bMath.subtractAnglesDeg(iTheta,dTheta)) > Config.turningThresholdDEG) {
                rxPID = Config.turn.PID(Config.turn, bMath.subtractAnglesDeg(dTheta, theta), Math.abs(bMath.subtractAnglesDeg(iTheta, dTheta)), Config.speed);
            }

            telemetry.addData("x:", x);
            telemetry.addData("y: ", y);
            telemetry.addData("xPID", xPID);
            telemetry.addData("yPID", yPID);
            telemetry.addData("rxPID", rxPID);
            telemetry.addData("target angle", Math.abs(bMath.subtractAnglesDeg(iTheta , dTheta)));
            telemetry.addData("subtact angles", bMath.subtractAnglesDeg(dTheta, theta));
            telemetry.addData("subx:", subX(movement));
            telemetry.addData("suby:", subY(movement));
            telemetry.addData("subhead", subHead(movement));

            movement.doWhileMoving();//run extra if needed
            newDriveRobot(xPID, yPID, Math.copySign(rxPID, subHead(movement)));//drive there
            bot.update();
        }
        telemetry.addLine("DONE");
        bot.setMotorPower(0);
        bot.brake();
    }

    public void drive2(Movement movement){
        movement.getCoefficients().resetForPID();//resetting value to begin loop
        //store the initial x,y values for PID
        iX = pose.getXX();
        iY = pose.getYY();
        iTheta = -bot.angleDEG();

        while(run(movement)  && (bot.opmode.opModeIsActive() && bot.opmode.isStarted() && !bot.opmode.isStopRequested())){
            pose.updateOdos(); //update odos
            double dX = movement.getdX(), dY = movement.getdY(); //get x,y,theta from desired movement
            double x = pose.getXX(), y = pose.getYY(), theta = -bot.angleDEG(); // get x,y,theta current

            //updating PID values for x, y and theta
            if(Math.abs(iX - dX) > Config.drivingThresholdCM) {
                xPID = movement.getCoefficients().PID(dX - x, Math.abs(iX - dX), Config.speed);
            }
            if(Math.abs(iY - dY) > Config.drivingThresholdCM){
                yPID = movement.getCoefficients().PID(dY - y, Math.abs(iY - dY), Config.speed);
            }

            telemetry.addData("x:", x);
            telemetry.addData("y: ", y);
            telemetry.addData("xPID", xPID);
            telemetry.addData("yPID", yPID);
            telemetry.addData("rxPID", rxPID);
            telemetry.addData("subx:", subX(movement));
            telemetry.addData("suby:", subY(movement));
            telemetry.addData("subhead", subHead(movement));

            movement.doWhileMoving();//run extra if needed
            newDriveRobot(xPID, yPID, Math.copySign(rxPID, subHead(movement)));//drive there
            bot.update();
        }
        telemetry.addLine("DONE");
        bot.setMotorPower(0);
        bot.brake();
    }

    /**
     * Driving code to go to position. It is essentially mecanum driving but uses PID to drive there.
     * All inputs are scaled to be between -1 and 1 since power goes to motors
     * @param x x input
     * @param y y input
     * @param rx turn input
     */
    private void drive(double x, double y, double rx) {
        double imu_offset = Math.toRadians(bot.angleDEG());//current angle
        double angle = (Math.toRadians(bot.angleRAD()) - imu_offset);//difference ƒrom last time

        double leftDiagPower = -(((y - x) * Math.sin(angle) + ((-y - x)) * Math.cos(angle)));
        double rightDiagPower = -(((-(y + x)) * Math.sin(angle) + ((-y +  x) * Math.cos(angle))));
        double leftRotatePower =  rx;
        double rightRotatePower = -rx;

        telemetry.addData("left front power", (leftDiagPower+leftRotatePower));
        telemetry.addData("left back power", (rightDiagPower+leftRotatePower));
        telemetry.addData("right front power", (rightDiagPower+rightRotatePower));
        telemetry.addData("right back power", (leftDiagPower+rightRotatePower) );

        //left back and right front
        bot.setPowers((leftDiagPower+leftRotatePower),
                (rightDiagPower+leftRotatePower),
                (rightDiagPower+rightRotatePower),
                (leftDiagPower+rightRotatePower) );
    }

    public void newDriveRobot(double x, double y, double rx){
        double botHeading = bot.angleRAD();

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 2.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        bot.setPowers(
                frontLeftPower,
                backLeftPower,
                frontRightPower,
                backRightPower);
    }

    /**
     * X position condition for loop
     * @param movement object to access desired X
     * @return true or false if robot has reached its dX
     */
    boolean xCondition(Movement movement){return Math.abs(movement.getdX() - pose.getXX()) > Config.drivingThresholdCM;}
    /**
     * Y position condition for loop
     * @param movement object to access desired Y
     * @return true or false if robot has reached its dY
     */
    boolean yCondition(Movement movement){return Math.abs(movement.getdY() - pose.getYY()) > Config.drivingThresholdCM;}
    /**
     * Theta position condition for loop. Uses subtractingAnglesDeg to calculate error
     * @param movement object to access desired theta
     * @return true or false if robot has reached its dTheta
     */
    boolean thetaCondition(Movement movement){return Math.abs(bMath.subtractAnglesDeg(movement.getdTheta(), -bot.angleDEG())) > Config.turningThresholdDEG;}

    /**
     *
     * @param movement
     * @return current pos
     */
    public double subHead(Movement movement){return bMath.subtractAnglesDeg(movement.getdTheta(), -bot.angleDEG());}

    public double subX(Movement movement){return movement.getdX() - pose.getXX();}

    public double subY(Movement movement){return movement.getdY() - pose.getYY();}

    public double getxPID(){return xPID;}

    public double getyPID() {return yPID;}

    public double getRxPID() {return rxPID;}
}
