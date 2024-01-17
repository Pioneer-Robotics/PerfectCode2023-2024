package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config;

/**
 * Universal Servo class that can create multiple servos that we use on the robot
 */
public class UniServo {
    private final Servo servo;
    //Two positions used
    private double openPos, pos1, pos2, pos3, pos4;
    public double closePos;

    public UniServo(Servo servo, double openPos, double closePos){
        this.servo = servo;
        this.openPos = openPos;
        this.closePos = closePos;

        //unused right now
        pos1 = openPos + 0.04;
        pos2 = pos1 + 0.04;
        pos3 = pos2 + 0.04;
        pos4 = pos3 + 0.04;
    }

    public void servoClosed() {servo.setPosition(closePos);}
    public void servoOpen(){servo.setPosition(openPos);}
    public void setServo(double pos){servo.setPosition(pos);}
    public void servoCounter(double pos){
        if(pos == 0){servo.setPosition(openPos);}
        else if(pos == 1){servo.setPosition(Config.secondPixelPos);}
        else if(pos == 2){servo.setPosition(Config.thirdPixelPos);}
        else if(pos == 3){servo.setPosition(Config.fourthPixelPos);}
        else{servo.setPosition(Config.fifthPixelPos);}
    }
}
