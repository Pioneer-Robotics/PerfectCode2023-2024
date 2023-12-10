package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Universal Servo class that can create multiple servos that we use on the robot
 */
public class UniServo {
    private final Servo servo;
    //Two positions used
    public double OpenPos;
    public double ClosePos;

    public UniServo(Servo servo, double openPos, double closePos){
        this.servo = servo;
        this.OpenPos = openPos;
        this.ClosePos = closePos;
    }

    public void servoClosed() {servo.setPosition(ClosePos);}
    public void servoOpen(){servo.setPosition(OpenPos);}
}
