package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Universal Servo class that can create multiple servos that we use on the robot
 */
public class UniServo {
    private final Servo servo;
    //Two positions used
    public double openPos, pos1, pos2, pos3, pos4;
    public double closePos;

    public UniServo(Servo servo, double openPos, double closePos){
        this.servo = servo;
        this.openPos = openPos;
        this.closePos = closePos;
        pos1 = openPos + 0.04;
        pos2 = pos1 + 0.04;
        pos3 = pos2 + 0.04;
        pos4 = pos3 + 0.04;
    }

    public void servoClosed() {servo.setPosition(closePos);}
    public void servoOpen(){servo.setPosition(openPos);}
    public void servoCounter(double pos){
        if(pos == 0){servo.setPosition(openPos);}
        else if(pos == 1){servo.setPosition(pos1);}
        else if(pos == 2){servo.setPosition(pos2);}
        else if(pos == 3){servo.setPosition(pos3);}
        else{servo.setPosition(pos4);}
    }
}
