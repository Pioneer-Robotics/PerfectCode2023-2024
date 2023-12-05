package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.Initializers.HardwareHelper;

public class Drivers extends HardwareHelper {
    //Toggles
    private final Toggle mecanumToggle = new Toggle(true);

    //Counter
    private final Counter scalePower = new Counter(.3, .2, 0.8);

    public void Driver1(Gamepad gamepad){
        mecanumToggle.toggle(gamepad.y);//toggles between regular and coordinate lock mecanum

//        if(gamepad.a){
//            bot.setPowers(.15,.15,.15,.15);
//        } else if(gamepad.b){
//            bot.setPowers(-0.1,-0.1,-0.1,-0.1);
//        } else{
//            bot.setPowers(0,0,0,0);
//        }

        if(mecanumToggle.getBool()){bot.regularMecanum(scalePower);}
        else{bot.coordinateLock(scalePower);}

        if(gamepad.b){
            bot.leftDropOpen();
        }

        if(gamepad.x){//resets IMU/Odometry just in case robot is not facing directly north
            bot.resetIMU();
            bot.resetOdometers();
        }
    }

    public void Driver2(Gamepad gamepad){
        if(gamepad.a){
            bot.leftDropOpen();
        } else{
            bot.leftDropClosed();
        }
        if(gamepad.dpad_left){
            bot.rightDropOpen();
        } else{
            bot.rightDropClosed();
        }
        if(gamepad.left_bumper){
            bot.gripperOpen();
        } else{
            bot.gripperClosed();
        }
        if(gamepad.right_bumper){
            bot.elevateOpen();
        } else{
            bot.elevateClosed();
        }
        if(gamepad.dpad_right){
            bot.intakeUp();
        } else{
            bot.intakeDown();
        }

        if(gamepad.x){
            bot.setSlideLevel(600);
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
        telemetry.addData("Scale Power", scalePower.getNum());
        telemetry.addData("Regular Mecanum:", mecanumToggle.getBool());
        telemetry.addData("Angle:", bot.angleDEG());
        telemetry.addData("arm pos", bot.getSlideLevel());
        telemetry.addData("X in cm", bot.getX());
        telemetry.addData("Y in cm", bot.getY());
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