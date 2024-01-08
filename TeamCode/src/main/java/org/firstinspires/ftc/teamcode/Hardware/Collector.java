package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

/**
 * Initializes our collector
 */
public class Collector extends AbstractHardwareComponent {
    private final DcMotorEx collector;
    private double defaultVelocity; //speed for power. This is reversed

    public Collector(DcMotorEx collector){
        this.collector = collector;
        this.collector.setDirection(DcMotorSimple.Direction.FORWARD);
        this.collector.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.collector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        defaultVelocity = -0.5;
    }

    public void setVelocity(double speed){
        this.defaultVelocity = speed;
    }

    public void moveCollector(){
        collector.setPower(defaultVelocity);
    }
    public void moveCollectorBack(){
        collector.setPower(.4);
    }

    public void stopCollector(){
        collector.setPower(0);
    }
}