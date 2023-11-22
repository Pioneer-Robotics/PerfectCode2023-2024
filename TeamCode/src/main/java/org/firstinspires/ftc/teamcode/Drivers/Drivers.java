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
            bot.pixelDropOpen();
        }

        if(gamepad.x){//resets IMU/Odometry just in case robot is not facing directly north
            bot.resetIMU();
            bot.resetOdometers();
        }
    }

    public void Driver2(Gamepad gamepad){
        if(gamepad.a){
            bot.pixelDropOpen();
        } else{
            bot.pixelDropClosed();
        }

        if(gamepad.x){
            bot.setSlideLevel(-200);
        }else if(gamepad.y){
            bot.setSlideLevel(-600);
        }
        else if(gamepad.b){
             bot.setSlideLevel(0);
        }
    }

    public void telemetry(){
        double[] pos = bot.updateOdometry();
        telemetry.addData("Scale Power", scalePower.getNum());
        telemetry.addData("Regular Mecanum:", mecanumToggle.getBool());
        telemetry.addData("Angle:", bot.angleDEG());
        telemetry.addData("x:", pos[1]);
        telemetry.addData("y:", pos[0]);
        telemetry.addData("Angle from odometry (rad):", pos[2]);
        telemetry.addData("Angle delta Odo-IMU", pos[2]-bot.angleRAD());
        telemetry.addData("odo Left Raw", bot.getOdos()[0]);
        telemetry.addData("odo Right Raw", -bot.getOdos()[1]);
        telemetry.addData("odo Back Raw", bot.getOdos()[2]);
        telemetry.addData("odo Back Cm", bot.getOdos()[0]* (Config.goBuildaOdoTicksToCm));
        telemetry.addData("odo Right Cm", bot.getOdos()[1] * (Config.goBuildaOdoTicksToCm));
        telemetry.addData("odo Left Cm", bot.getOdos()[2] * Config.goBuildaOdoTicksToCm);

        telemetry.addData("arm pos", bot.getSlideLevel());
    }

    public void example(){Telemetry.Line line = bot.isAuto() ? telemetry.addLine("Example auto succeeded") : telemetry.addLine("Example tele succeeded");}
}
