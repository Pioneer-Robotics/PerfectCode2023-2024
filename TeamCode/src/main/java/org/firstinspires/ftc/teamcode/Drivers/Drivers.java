package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

/**
 * / This class will hold three methods that will run driver 1, 2, and the telemetry.
 * It uses Toggle and Counter
 */
public class Drivers extends AbstractHardwareComponent {
    //Toggles
    private final Toggle mecanumToggle = new Toggle(true);
    private final Toggle collectorToggle = new Toggle(false);
    private final Toggle grabberToggle = new Toggle(true);
    private final Toggle intakeToggle = new Toggle(true);

    //Counter
    private final Counter scalePower = new Counter(.3, .2, 0.8);

    public void Driver1(Gamepad gamepad){
        mecanumToggle.toggle(gamepad.y);//toggles between regular and coordinate lock mecanum

        if(mecanumToggle.getBool()){bot.coordinateLock(scalePower);}
        else{bot.regularMecanum(scalePower);}

        if(gamepad.x){//resets IMU/Odometry just in case robot is not facing directly north
            bot.resetIMU();
            //bot.resetOdometers();
        }

        if(gamepad.right_bumper){
            bot.launchAirplane();
        }
        else{
            bot.holdAirplane();
        }
    }

    public void Driver2(Gamepad gamepad){
        bot.rightDropUp();
        bot.leftDropUp();
        grabberToggle.toggle(gamepad.left_bumper);
        if(grabberToggle.getBool()){
            bot.gripperOpen();
        }
        else{
             bot.gripperClosed();
        }

        if(gamepad.right_bumper){
            bot.wristVertical();
        } else{
            bot.wristHorizontal();
        }

        intakeToggle.toggle(gamepad.dpad_left);
        if(intakeToggle.getBool()){
            bot.intakeUp();
        } else{
            bot.intakeDown();
        }

        if(gamepad.x){
            bot.setSlideLevel(1200);
        }else if(gamepad.y){
            bot.setSlideLevel(2200);
        }
        else if(gamepad.b){
            bot.setSlideLevel(0);
        }
        collectorToggle.toggle(gamepad.dpad_down);

        if(gamepad.dpad_up){
            bot.moveCollectorBack();
        }
        else{
            if(collectorToggle.getBool()){
                bot.moveCollector();
            }
            else{
                bot.stopCollector();
            }
        }
    }

    public void telemetry(){
        bot.newOdoCalc();
        telemetry.addData("Scale Power", scalePower.getNum());
        telemetry.addData("Coordinate Lock Mecanum:", mecanumToggle.getBool());
        telemetry.addData("Angle:", bot.angleDEG());
        telemetry.addData("arm pos", bot.getSlideLevel());
        telemetry.addData("X in cm", bot.getXX());
        telemetry.addData("Y in cm", bot.getYY());
        telemetry.addData("left", bot.getRawOdos()[0].getCurrentPosition());
        telemetry.addData("right", -bot.getRawOdos()[1].getCurrentPosition());
        telemetry.addData("middle", bot.getRawOdos()[2].getCurrentPosition());
    }

    public void TeleOp(Gamepad gamepad1, Gamepad gamepad2){
        Driver1(gamepad1);
        Driver2(gamepad2);
        telemetry();
    }

    public void example(){
        telemetry.addLine(bot.isAuto() ? "Example auto succeeded" : "Example tele succeeded");
    }
}