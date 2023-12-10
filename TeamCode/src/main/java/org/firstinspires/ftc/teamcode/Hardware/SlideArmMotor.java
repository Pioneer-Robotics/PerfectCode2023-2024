package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class SlideArmMotor extends AbstractHardwareComponent {
    DcMotorEx slideArm;
    double defaultSpeed;

    public SlideArmMotor(DcMotorEx slideArm){
        this.slideArm = slideArm;
        this.slideArm.setDirection(DcMotorSimple.Direction.REVERSE);
        this.slideArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.slideArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        defaultSpeed = .7;
    }

    public void setLevel(int level){
        slideArm.setTargetPosition(level);
        slideArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideArm.setVelocity(Config.encoderRatio * defaultSpeed);
    }

    public void resetArmToTheBottom(){
        slideArm.setTargetPosition(-2000);
        slideArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.slideArm.setDirection(DcMotorSimple.Direction.REVERSE);
        slideArm.setVelocity(Config.encoderRatio * defaultSpeed);
    }

    public void setVelocity(double speed){
        slideArm.setVelocity(speed);
    }

    public void setDefaultVelocity(double speed){
        this.defaultSpeed = speed;
    }

    public double getSlideLevel(){
        return slideArm.getCurrentPosition();
    }
}