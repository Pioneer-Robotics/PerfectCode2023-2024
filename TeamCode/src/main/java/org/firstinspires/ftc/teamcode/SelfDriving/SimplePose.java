package org.firstinspires.ftc.teamcode.SelfDriving;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Features.Config;

public class SimplePose {
    DcMotorEx leftOdo;
    DcMotorEx rightOdo;
    DcMotorEx middleOdo;

    public SimplePose(DcMotorEx leftOdo, DcMotorEx middleOdo, DcMotorEx rightOdo){
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
}
