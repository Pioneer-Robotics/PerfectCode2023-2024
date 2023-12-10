package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

/**
 * Initializes our collector
 */
public class Collector extends AbstractHardwareComponent {
    private final DcMotorEx collector;
    private double defaultVelocity;

    public Collector(DcMotorEx collector){
        this.collector = collector;
        this.collector.setDirection(DcMotorSimple.Direction.FORWARD);
        this.collector.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.collector.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        defaultVelocity = 0.3;
    }

    public void setVelocity(double speed){
        this.defaultVelocity = speed;
    }

    public void moveCollector(){
        collector.setVelocity(Config.encoderRatio * defaultVelocity);
    }
    public void moveCollectorBack(){
        collector.setVelocity(-Config.encoderRatio * .1);
    }

    public void stopCollector(){
        collector.setVelocity(0);
    }
}