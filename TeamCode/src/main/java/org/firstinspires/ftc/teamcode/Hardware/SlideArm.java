package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class SlideArm {
    DcMotorEx slideArm;
    double speed;

    public SlideArm(DcMotorEx slideArm){
        this.slideArm = slideArm;
        this.slideArm.setDirection(DcMotorSimple.Direction.FORWARD);
        this.slideArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.slideArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        speed = .4;
    }

    public void setLevel(int level){
        slideArm.setTargetPosition(level);
        slideArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.slideArm.setDirection(DcMotorSimple.Direction.REVERSE);
        slideArm.setVelocity(1000 * speed);
    }

    public void setVelocity(double speed){
        slideArm.setVelocity(speed);
    }

    public void resetVelocity(double speed){
        this.speed = speed;
    }

    public double getSlideLevel(){
        return slideArm.getCurrentPosition();
    }
}