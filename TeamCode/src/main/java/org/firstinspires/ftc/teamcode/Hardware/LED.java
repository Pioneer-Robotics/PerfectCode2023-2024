package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class LED extends AbstractHardwareComponent {
    RevBlinkinLedDriver led;
    ElapsedTime elapsedTime = new ElapsedTime();
    public boolean isFirsttime = true;

    public void lightsOn(RevBlinkinLedDriver.BlinkinPattern revBlinkinLedDriver){led.setPattern(revBlinkinLedDriver);}
    public void lightsOff(){led.close();}

    public LED(RevBlinkinLedDriver light) {
        led = light;
        chooseLightsForInit();
    }

    public void chooseLights(){
        if(bot.isRed()){lightsOn(RevBlinkinLedDriver.BlinkinPattern.RED);}
        else {lightsOn(RevBlinkinLedDriver.BlinkinPattern.BLUE);}
    }

    public void chooseLightsForInit(){
        if(bot.isAuto()) {
            if (bot.isRed()) {
                lightsOn(RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
            } else {
                lightsOn(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
            }
        }
        else{
            lightsOn(RevBlinkinLedDriver.BlinkinPattern.BREATH_GRAY);
        }
    }

    public void teleOpTimer(){
        if(isFirsttime){
            elapsedTime.reset();
            isFirsttime = false;
        }
        if(elapsedTime.seconds() >= 90){lightsOn(RevBlinkinLedDriver.BlinkinPattern.CONFETTI);}
        else if(elapsedTime.seconds() >= 75){lightsOn(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_FOREST_PALETTE);}
        else{lightsOn(RevBlinkinLedDriver.BlinkinPattern.LIGHT_CHASE_GRAY);}
    }

    public void teleLights(){
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.CONFETTI);
    }
}
