package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Helpers.Timer;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class LED extends AbstractHardwareComponent {
    RevBlinkinLedDriver led;
    ElapsedTime elapsedTime = new ElapsedTime();
    public boolean isFirsttime = true;

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

    public void autoTimer(){
        if(isFirsttime){
            elapsedTime.reset();
            isFirsttime = false;
        }
        if(elapsedTime.seconds() >= 90){lightsOn(RevBlinkinLedDriver.BlinkinPattern.CONFETTI);}
        else if(elapsedTime.seconds() >= 75){lightsOn(RevBlinkinLedDriver.BlinkinPattern.DARK_GREEN);}
        else{chooseLights();}
    }

    public void teleLights(){
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.CONFETTI);
    }
}
