package org.firstinspires.ftc.teamcode.SelfDrivingAuto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Helpers.bMath;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

import java.util.Arrays;

public class Pose extends AbstractHardwareComponent {
    DcMotorEx leftOdo;
    DcMotorEx rightOdo;
    DcMotorEx middleOdo;
    private DcMotorEx[] odos = new DcMotorEx[3];

    private double x = 0, y = 0, theta = 0;
    private int[] prevTicks = new int[3];

    private double previousAngleRAD = 0;
    private double dX = 0, dLeft = 0, dRight = 0;
    private double leftDistanceCM = 0, rightDistanceCM = 0, xDistanceCM;

    public Pose(double x, double y, double theta, DcMotorEx leftOdo, DcMotorEx middleOdo, DcMotorEx rightOdo){
        this.x = x;
        this.y = y;
        this.theta = theta;

        odos[0] = leftOdo;
        odos[1] = rightOdo;
        odos[2] = middleOdo;



        for(DcMotorEx odo: odos){
            odo.setDirection(DcMotorSimple.Direction.REVERSE);
            odo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            odo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        this.leftOdo = odos[0];
        this.rightOdo = odos[1];
        this.middleOdo = odos[2];

    }

    public double getX(){
        return (((double) (leftOdo.getCurrentPosition() - rightOdo.getCurrentPosition()) / 2) * Config.odoTicksToCm);
    }

    public double getY(){
        return (middleOdo.getCurrentPosition() * Config.odoTicksToCm);
    }

    public void resetY(){
        this.middleOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.middleOdo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public DcMotorEx[] getRawOdoValues(){return odos;}

    public void updateOdos() {
        //WORKS
        int[] currentTicks = new int[3];
        for (int i = 0; i < 3; i++) currentTicks[i] = getRawOdoValues()[i].getCurrentPosition();
//        currentTicks[1] = -currentTicks[1]; // reverse backwards odo

        //get current ticks and set previous ticks from last iteration
        int newLeftTicks    = currentTicks[0] - prevTicks[0];
        int newRightTicks   = currentTicks[1] - prevTicks[1];
        int newXTicks       = currentTicks[2] - prevTicks[2];
        prevTicks           = Arrays.copyOf(currentTicks, currentTicks.length);

        //get current angle from IMU and set from last iteration
        theta = -bot.angleRAD();
        double dTheta = bMath.subtractAnglesRad(theta, previousAngleRAD);
        previousAngleRAD = theta;

        //convert to CM
        leftDistanceCM = newLeftTicks * Config.goBuildaOdoTicksToCm;
        rightDistanceCM = newRightTicks * Config.goBuildaOdoTicksToCm;
        xDistanceCM = newXTicks * Config.goBuildaOdoTicksToCm;

        //solve for arc length in each odometer
        double odoTicksToCM = Config.goBuildaOdoTicksToCm;
        double xRotation        = dTheta * (Config.x20FullRotationOdosInTicksDiv40pi * odoTicksToCM);
        double leftRotation     = dTheta * (Config.left20FullRotationOdosInTicksDiv40pi * odoTicksToCM);
        double rightRotation    = dTheta * (Config.right20FullRotationOdosInTicksDiv40pi * odoTicksToCM);

        //take out the distance traveled in rotation from total distance to only get translation
        dX = xDistanceCM - xRotation;
        dLeft = leftDistanceCM - leftRotation;
        dRight = rightDistanceCM - rightRotation;
        double avgDY = (dLeft - dRight) / 2; //average the two

        //add distances onto x,y using trig
        x += dX * Math.cos(theta) + avgDY * Math.sin(theta);
        y += -dX * Math.sin(theta) + avgDY * Math.cos(theta);

        telemetry.addData("ACTUAL ANGLE", Math.toDegrees(theta));
        telemetry.addData("x: ", x);
        telemetry.addData("y: ", y);
    }

    public double getXX(){return x;}

    public double getYY(){return y;}




}
