package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.Initializers.HardwareHelper;

public class Collector extends HardwareHelper {
    private final DcMotorEx collector;
    private double speed;

    public Collector(DcMotorEx collector){
        this.collector = collector;
        this.collector.setDirection(DcMotorSimple.Direction.FORWARD);
        this.collector.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.collector.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        speed = 0.3;
    }

    public void setVelocitySpeed(double speed){
        this.speed = speed;
    }

    public void moveCollector(){
        collector.setVelocity(Config.encoderRatio * speed);
    }
    public void moveCollectorBack(){
        collector.setVelocity(-Config.encoderRatio * .1);
    }

    public void stopCollector(){
        collector.setVelocity(0);
    }
}