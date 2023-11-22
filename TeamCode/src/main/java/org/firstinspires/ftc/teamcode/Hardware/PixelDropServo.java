package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

public class PixelDropServo {
    private final Servo pixelDropServo;

    public PixelDropServo(Servo servo){
        this.pixelDropServo = servo;
    }

    public void servoClosed() {
        pixelDropServo.setPosition(1);
    }

    public void servoOpen(){
        pixelDropServo.setPosition(0.7);
    }
}
