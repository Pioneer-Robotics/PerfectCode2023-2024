package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.Initializers.HardwareHelper;

public class Drivers extends HardwareHelper {
    //Toggles
    private final Toggle mecanumToggle = new Toggle(false);

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
    }

    public void Driver2(Gamepad gamepad){
        if(gamepad.a){
            bot.leftDropDown();
        } else{
            bot.leftDropUp();
        }

        if(gamepad.right_bumper){
            bot.gripperClosed();
        }
        else if(gamepad.left_bumper){
            bot.gripperOpen();
        }
        if(gamepad.dpad_left){
            bot.rightDropUp();
        } else{
            bot.rightDropDown();
        }
        if(gamepad.left_bumper){
            bot.gripperOpen();
        } else{
            bot.gripperClosed();
        }
        if(gamepad.right_bumper){
            bot.wristVertical();
        } else{
            bot.wristHorizontal();
        }
        if(gamepad.dpad_right){
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

        if(gamepad.dpad_up){
            bot.moveCollector();
        }
        else if(gamepad.dpad_down){
            bot.stopCollector();
        }
    }

    public void telemetry(){
        bot.gruberOdos();
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