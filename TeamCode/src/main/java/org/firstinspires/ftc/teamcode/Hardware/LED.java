package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class LED extends AbstractHardwareComponent {
    RevBlinkinLedDriver led;

    public void lightsOn(RevBlinkinLedDriver.BlinkinPattern revBlinkinLedDriver){led.setPattern(revBlinkinLedDriver);}
    public void lightsOff(){led.close();}

    public LED(RevBlinkinLedDriver light, RevBlinkinLedDriver.BlinkinPattern initPattern) {
        led = light;
        led.setPattern(initPattern);
    }

    public void chooseLights(){
        if(bot.isRed()){lightsOn(RevBlinkinLedDriver.BlinkinPattern.RED);}
        else {lightsOn(RevBlinkinLedDriver.BlinkinPattern.BLUE);}
    }

    public void teleLights(){
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.CONFETTI);
    }
}
