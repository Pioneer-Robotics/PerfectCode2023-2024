package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

public class GripperServo {
    private final Servo servo;
    public double OpenPos;
    public double ClosePos;

    public GripperServo(Servo servo, double openPos, double closePos){
        this.servo = servo;
        this.OpenPos = openPos;
        this.ClosePos = closePos;
    }

    public void servoClosed() {servo.setPosition(ClosePos);}
    public void servoOpen(){servo.setPosition(OpenPos);}
}
