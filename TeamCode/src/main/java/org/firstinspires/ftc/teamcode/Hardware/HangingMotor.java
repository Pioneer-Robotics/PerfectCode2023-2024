package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class HangingMotor extends AbstractHardwareComponent {
    public DcMotorEx hangingMotor;

    public HangingMotor(DcMotorEx hangingMotor){
        this.hangingMotor = hangingMotor;
        this.hangingMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.hangingMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.hangingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setSpeed(double speed){
        hangingMotor.setPower(speed);
    }
}
