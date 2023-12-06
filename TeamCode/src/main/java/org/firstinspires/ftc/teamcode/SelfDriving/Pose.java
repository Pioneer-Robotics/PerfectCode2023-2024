package org.firstinspires.ftc.teamcode.SelfDriving;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.Initializers.HardwareHelper;

import java.util.Arrays;

public class Pose extends HardwareHelper {
    DcMotorEx leftOdo;
    DcMotorEx rightOdo;
    DcMotorEx middleOdo;
    private DcMotorEx[] odos = new DcMotorEx[3];

    private double x = 0, y = 0, theta = 0;
    private int[] prevTicks = new int[3];


    public Pose(DcMotorEx leftOdo, DcMotorEx middleOdo, DcMotorEx rightOdo){
        this.leftOdo = leftOdo;
        this.middleOdo = middleOdo;
        this.rightOdo = rightOdo;

        this.leftOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.middleOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.leftOdo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.middleOdo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightOdo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public Pose(double x, double y, double theta, DcMotorEx leftOdo, DcMotorEx middleOdo, DcMotorEx rightOdo){
        this.x = x;
        this.y = y;
        this.theta = theta;

        odos[0] = leftOdo;
        odos[1] = rightOdo;
        odos[2] = middleOdo;

        for(DcMotorEx odo: odos){
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

    public void updateOdo(){
        int[] ticks = new int[3];
        for (int i=0; i<3; i++) ticks[i] = getRawOdoValues()[i].getCurrentPosition();
        ticks[1] = -ticks[1];
        int newLeftTicks = ticks[0] - prevTicks[0];
        int newRightTicks = ticks[1] - prevTicks[1];
        int newXTicks = ticks[2] - prevTicks[2];
        prevTicks = Arrays.copyOf(ticks, ticks.length);
        double rightDist = newRightTicks * (Config.goBuildaOdoTicksToCm);
        double leftDist = newLeftTicks * (Config.goBuildaOdoTicksToCm);
        double dyR = 0.5 * (rightDist + leftDist);
        double headingChangeRadians = (rightDist - leftDist) / Config.goBuildaOdoDiameter;
        double dxR = newXTicks * (Config.goBuildaOdoTicksToCm);
        double cos = Math.cos(bot.angleRAD());
        double sin = Math.sin(bot.angleRAD());

        //        if (Math.abs(rightDist + leftDist) > .3 || Math.abs(dxR) > 0.5) {
//        if (Math.abs(rightDist + leftDist) > .5) {
            //moving
//            x += dxR * cos + dyR * sin;//x
//            y += -dxR * sin + dyR * cos;//y
//        }
    }

    public void gruberOdoCalculations()
    {
        //conversions and storing arrays
        int[] ticks = new int[3];
        for (int i=0; i<3; i++) ticks[i] = getRawOdoValues()[i].getCurrentPosition();
        ticks[1] = -ticks[1];
        int newLeftTicks = ticks[0] - prevTicks[0];
        int newRightTicks = ticks[1] - prevTicks[1];
        int newXTicks = ticks[2] - prevTicks[2];
        prevTicks = Arrays.copyOf(ticks, ticks.length);
        double rightDistToCM = newRightTicks * Config.goBuildaOdoTicksToCm;
        double leftDistToCM = newLeftTicks * Config.goBuildaOdoTicksToCm;
        double middleDistToCM = newXTicks * Config.goBuildaOdoTicksToCm;
        double avgY_odos = 0/5 * (rightDistToCM + leftDistToCM);

        //calculate and update
        double xRotation = Config.odoXOffset * Math.cos(bot.angleDEG());
        double yRotation = Config.odoYOffset * Math.sin(bot.angleDEG());

        x += middleDistToCM - xRotation;
        y += avgY_odos - yRotation;
    }

    public double getXX(){return x;}

    public double getYY(){return y;}




}
