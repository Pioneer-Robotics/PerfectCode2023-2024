package org.firstinspires.ftc.teamcode.SelfDrivingAuto;

import org.firstinspires.ftc.teamcode.Config;
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

        while((thetaCondition(movement) || xCondition(movement) ||  yCondition(movement))  && (bot.opmode.opModeIsActive() && bot.opmode.isStarted() && !bot.opmode.isStopRequested())){
            pose.updateOdos();
            double dX = movement.getdX(), dY = movement.getdY(), dTheta = movement.getdTheta();
            double x = pose.getXX(), y = pose.getYY();

            //updating PID values for x, y and theta
            xPID = movement.getCoefficients().PID(dX - x, Math.abs(dX), Config.speed);
            yPID = movement.getCoefficients().PID(dY - y, Math.abs(dY), Config.speed);
            rxPID = Config.turn.PID(Config.turn, bMath.subtractAnglesDeg(dTheta, -bot.angleDEG()), dTheta, 0.4);

            telemetry.addData("x:", x);
            telemetry.addData("y: ", y);
            telemetry.addData("xPID", xPID);
            telemetry.addData("yPID", yPID);
            telemetry.addData("rxPID", rxPID);
            telemetry.addData("subx:", subX(movement));
            telemetry.addData("suby:", subY(movement));
            telemetry.addData("subhead", subHead(movement));

            movement.runExtra();//run extra if needed
            drive2(xPID, yPID, Math.copySign(rxPID, subHead(movement)));//drive there
            bot.update();
        }
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

    private void drive2(double x, double y, double rx) {
        double imu_offset = Math.toRadians(bot.angleDEG());//current angle
        double angle = (Math.toRadians(bot.angleRAD()) - imu_offset);//difference ƒrom last time

        double leftDiagPower = -(((y + x) * Math.sin(angle) + ((-y + x)) * Math.cos(angle)));
        double rightDiagPower = (((-(y -x)) * Math.sin(angle) + ((-y - x) * Math.cos(angle))));
        double leftRotatePower =  0;
        double rightRotatePower = 0;

        telemetry.addData("left front power", (leftDiagPower+leftRotatePower));
        telemetry.addData("left back power", (rightDiagPower+leftRotatePower));
        telemetry.addData("right front power", (rightDiagPower+rightRotatePower));
        telemetry.addData("right back power", (leftDiagPower+rightRotatePower) );

        //left front and right back
        bot.setPowers((leftDiagPower+leftRotatePower),
                (rightDiagPower+leftRotatePower),
                (rightDiagPower+rightRotatePower),
                (leftDiagPower+rightRotatePower) );
    }

    /**
     * X position condition for loop
     * @param movement object to access desired X
     * @return true or false if robot has reached its dX
     */
    boolean xCondition(Movement movement){return Math.abs(movement.getdX() - pose.getXX()) > 2;}
    /**
     * Y position condition for loop
     * @param movement object to access desired Y
     * @return true or false if robot has reached its dY
     */
    boolean yCondition(Movement movement){return Math.abs(movement.getdY() - pose.getYY()) > 2;}
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
}
