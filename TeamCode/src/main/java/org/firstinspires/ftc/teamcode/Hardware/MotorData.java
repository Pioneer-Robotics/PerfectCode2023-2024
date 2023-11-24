package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.Initializers.HardwareHelper;


public class MotorData extends HardwareHelper {
    private static DcMotorEx leftFront;
    private static DcMotorEx rightFront;
    private static DcMotorEx leftBack;
    private static DcMotorEx rightBack;
    private static DcMotorEx leftOdo;
    private static DcMotorEx middleOdo;
    private static DcMotorEx rightOdo;

    public MotorData(DcMotorEx lFront, DcMotorEx rFront, DcMotorEx lBack, DcMotorEx rBack) {
        leftFront = lFront;
        rightFront = rFront;
        leftBack = lBack;
        rightBack = rBack;

        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void resetOdometers(){
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setMotorPower(double power){
        leftFront.setVelocity(power * Config.encoderRatio);
        leftBack.setVelocity(power * Config.encoderRatio);
        rightBack.setVelocity(power * Config.encoderRatio);
        rightFront.setVelocity(power * Config.encoderRatio);
    }

    public void setMotorPowerRight(double power){
        leftFront.setVelocity(-power * Config.encoderRatio);
        leftBack.setVelocity(power * Config.encoderRatio);
        rightBack.setVelocity(-power * Config.encoderRatio);
        rightFront.setVelocity(power * Config.encoderRatio);
    }

    public void setMotorPowerLeft(double power){
        leftFront.setVelocity(power * Config.encoderRatio);
        leftBack.setVelocity(-power * Config.encoderRatio);
        rightBack.setVelocity(power * Config.encoderRatio);
        rightFront.setVelocity(-power * Config.encoderRatio);
    }

    public void setMotorPower(double left, double right){
        leftFront.setVelocity(left * Config.encoderRatio);
        leftBack.setVelocity(left * Config.encoderRatio);
        rightBack.setVelocity(right * Config.encoderRatio);
        rightFront.setVelocity(right * Config.encoderRatio);
    }

    public void setVel(double LFvel, double LBvel, double RFvel, double RBvel){
        leftFront.setVelocity(LFvel * Config.encoderRatio);
        leftBack.setVelocity(LBvel * Config.encoderRatio);
        rightBack.setVelocity(RBvel * Config.encoderRatio);
        rightFront.setVelocity(RFvel * Config.encoderRatio);
    }

    public void setPowers(double LFpower, double LBpower, double Rfpower, double RBpower){
        leftFront.setPower(LFpower);
        leftBack.setPower(LBpower);
        rightBack.setPower(RBpower);
        rightFront.setPower(Rfpower);
    }

    public void brake(){
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setPower(0);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setPower(0);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setPower(0);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setPower(0);
    }

    public void setPowerBehavior(DcMotor.ZeroPowerBehavior Lbehavior, DcMotor.ZeroPowerBehavior Rbehavior ){
        leftFront.setZeroPowerBehavior(Lbehavior);
        leftBack.setZeroPowerBehavior(Lbehavior);
        rightBack.setZeroPowerBehavior(Rbehavior);
        rightFront.setZeroPowerBehavior(Rbehavior);
    }

    public void setRunMode(DcMotor.RunMode mode){
        leftFront.setMode(mode);
        leftBack.setMode(mode);
        rightBack.setMode(mode);
        rightFront.setMode(mode);
    }

    public double getLeftPosition(){return odoTicksToCm(leftFront.getCurrentPosition());}
    public double getRightPosition(){return odoTicksToCm(rightBack.getCurrentPosition());}
    public double getMiddlePosition(){return odoTicksToCm(leftBack.getCurrentPosition());}
    public static double odoTicksToCm(int ticks){return ticks * Config.odoTicksToCm;}
}
