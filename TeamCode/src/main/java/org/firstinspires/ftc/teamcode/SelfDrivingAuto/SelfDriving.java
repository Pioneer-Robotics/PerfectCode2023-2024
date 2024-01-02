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

    /**
     * This is driving method that goes to x,y, and theta with tuned PID.
     * Input a movement object that has that data and the method goes there.
     * To drive there it uses mecanum driving code tht takes in x, y, and turning input. To drive there,
     * there is PID for each position which then scales speed and where to go.
     * Lastly, movement has an abstract method called runExtra() which is ran in the method--use to run other stuff.
     * @param movement abstract object that has x,y, theta, and PID values that are used
     */
    public void drive(Movement movement){
        movement.getPidXY().resetForPID();//resetting value to begin loop
        movement.getPidTheta().resetForPID();

        //store the initial x,y values for PID
        iX = pose.getXX();
        iY = pose.getYY();
        iTheta = -bot.angleDEG();

        while((thetaCondition(movement) || xCondition(movement) ||  yCondition(movement))  && (bot.opmode.opModeIsActive() && bot.opmode.isStarted() && !bot.opmode.isStopRequested())){
            pose.updateOdos(); //update odos
            double dX = movement.getdX(), dY = movement.getdY(), dTheta = movement.getdTheta(); //get x,y,theta from desired movement
            double x = pose.getXX(), y = pose.getYY(), theta = -bot.angleDEG(); // get x,y,theta current
            PIDCoefficients pidXY = movement.getPidXY(), pidTheta = movement.getPidTheta();

            //updating PID values for x, y and theta
            if(Math.abs(iX - dX) > Config.drivingThresholdCM) {
                xPID = pidXY.PID(dX - x, Math.abs(iX - dX), Config.speed);
            }
            if(Math.abs(iY - dY) > Config.drivingThresholdCM){
                yPID = pidXY.PID(dY - y, Math.abs(iY - dY), Config.speed);
            }
            if(Math.abs(bMath.subtractAnglesDeg(iTheta,dTheta)) > Config.turningThresholdDEG) {
                rxPID = pidTheta.PID(bMath.subtractAnglesDeg(dTheta, theta), Math.abs(bMath.subtractAnglesDeg(iTheta, dTheta)), Config.speed);
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
            bot.autoFieldCentricDriving(xPID, yPID, Math.copySign(rxPID, subHead(movement)));//drive there
            bot.update();
        }
        telemetry.addLine("DONE");
        bot.setMotorPower(0);
        bot.brake();
    }

    /**
     * X position condition for loop
     * @param movement object to access desired X
     * @return true or false if robot has reached its dX
     */
    boolean xCondition(Movement movement){
        if (Math.abs(iX - movement.getdX()) < Config.drivingThresholdCM){
            return false;
        }
        return Math.abs(movement.getdX() - pose.getXX()) > Config.drivingThresholdCM;
    }
    /**
     * Y position condition for loop
     * @param movement object to access desired Y
     * @return true or false if robot has reached its dY
     */
    boolean yCondition(Movement movement){
        if (Math.abs(iY - movement.getdY()) < Config.drivingThresholdCM){
            return false;
        }
        return Math.abs(movement.getdY() - pose.getYY()) > Config.drivingThresholdCM;
    }
    /**
     * Theta position condition for loop. Uses subtractingAnglesDeg to calculate error
     * @param movement object to access desired theta
     * @return true or false if robot has reached its dTheta
     */
    boolean thetaCondition(Movement movement){
        if(Math.abs(bMath.subtractAnglesDeg(iTheta, movement.getdTheta())) < Config.turningThresholdDEG){
            return false;
        }
        return Math.abs(bMath.subtractAnglesDeg(movement.getdTheta(), -bot.angleDEG())) > Config.turningThresholdDEG;
    }

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
