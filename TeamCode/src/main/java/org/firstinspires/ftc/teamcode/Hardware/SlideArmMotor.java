package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class SlideArmMotor extends AbstractHardwareComponent {
    DcMotorEx slideArm;
    double defaultSpeed;

    public SlideArmMotor(DcMotorEx slideArm){
        this.slideArm = slideArm;
        this.slideArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.slideArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        defaultSpeed = .7;
    }

    public void setLevel(int level){
        slideArm.setTargetPosition(level);
        slideArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideArm.setVelocity(Config.encoderRatio * defaultSpeed);
//        slideArm.setVelocity(500);
    }

    public void shutOffWhateverWeCan(){
        slideArm.setVelocity(0);
        slideArm.setMotorDisable();
    }

    public void turnOnWhateverWeCan(){
        slideArm.setVelocity(0);
        slideArm.setMotorEnable();
    }

    public void resetArmToTheBottom(){
        slideArm.setTargetPosition(-2000);
        slideArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.slideArm.setDirection(DcMotorSimple.Direction.REVERSE);
        slideArm.setVelocity(Config.encoderRatio * defaultSpeed);
    }

    public void runReset(Gamepad gamepad){
        if(gamepad.y){
            this.slideArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.slideArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        slideArm.setPower(gamepad.left_stick_y > 0.05 || gamepad.left_stick_y < 0.05 ? gamepad.left_stick_y : 0);
    }

    public void setVelocity(double speed){
        slideArm.setVelocity(speed);
    }

    public void setDefaultVelocity(double speed) {
        this.defaultSpeed = speed;
    }

    public double getSlideLevel() {
        return slideArm.getCurrentPosition();
    }

    public double getTarg() {
        return slideArm.getTargetPosition();
    }
}