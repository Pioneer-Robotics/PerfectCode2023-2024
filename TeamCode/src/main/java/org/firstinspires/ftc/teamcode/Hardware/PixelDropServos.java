package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

public class PixelDropServos {
    private final Servo pixelDropServo;
    public double OpenPos;
    public double ClosePos;

    public PixelDropServos(Servo servo, double openPos, double closePos){
        this.pixelDropServo = servo;
        this.OpenPos = openPos;
        this.ClosePos = closePos;
    }

    public void servoClosed() {
        pixelDropServo.setPosition(ClosePos);
    }
    public void servoOpen(){pixelDropServo.setPosition(OpenPos);}
}
