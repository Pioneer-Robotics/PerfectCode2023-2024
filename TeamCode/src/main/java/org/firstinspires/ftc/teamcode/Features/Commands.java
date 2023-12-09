package org.firstinspires.ftc.teamcode.Features;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Initializers.HardwareHelper;

public class Commands extends HardwareHelper {
    private double turn = 90d;
    boolean moveLeft = true;
    boolean moveRight = false;
    boolean moveMiddle = false;

    public void gearShift(Gamepad gamepad, Counter counter){counter.arithmetic(gamepad.right_bumper, gamepad.left_bumper, .1);}

    public void boardSide() {
        try {
            ElapsedTime time = new ElapsedTime();
            boolean isBlue = bot.opmode.getClass().getName().contains("Blue");
            int leftMiddleRight = bot.locationCamera();//left is one, middle is two, and right is three
//            if (leftMiddleRight == 1) {
//                moveLeft = true;
//            } else if (leftMiddleRight == 2) {
//                moveMiddle = true;
//            } else {
//                moveRight = true;
//            }

            if(!isBlue){
                turn = -turn;
            }

            if (!moveLeft) {
                bot.rightDropUp();
                bot.gripperClosed();
                bot.startMove(58);
                sleep(1000);
                if (moveRight) {
                    bot.rightDropDown();
                }
                bot.update();
                sleep(1000);
                //bot.startStrafe(-20);
                bot.startTurn(turn);
                sleep(1000);
                if (moveMiddle) {
                    bot.rightDropDown();
                }
                sleep(1000);
                bot.startMove(85.5);
                if (moveRight) {
                    bot.startStrafe(40);
                }
                if (moveMiddle) {
                    bot.startStrafe(20);
                }
                sleep(1000);
                while (bot.getSlideLevel() != 1600) {
                    bot.setSlideLevel(1600);
                    bot.wristVertical();
                    bot.update();
                }
                time.reset();
                while (time.milliseconds() < 2000) {
                    bot.gripperOpen();
                    bot.update();
                }
                time.reset();
                while (time.milliseconds() < 2000) {
                    bot.wristHorizontal();
                    bot.gripperClosed();
                    bot.setSlideLevel(0);
                    bot.update();
                }
            } else {
                bot.rightDropUp();
                bot.gripperClosed();
                bot.startMove(23);
                sleep(1000);
                bot.startTurn(turn);
                sleep(1000);
                bot.startMove(13);
                sleep(1000);
                bot.rightDropDown();
                sleep(1000);
                bot.startMove(74.5);
                sleep(1000);
                bot.startStrafe(36);
                sleep(1000);
                while (bot.getSlideLevel() != 1600) {
                    bot.setSlideLevel(1600);
                    bot.wristVertical();
                    bot.update();
                }
                sleep(1000);
                time.reset();
                while (time.milliseconds() < 2000) {
                    bot.gripperOpen();
                    bot.update();
                }
                time.reset();
                while (time.milliseconds() < 2000) {
                    bot.wristHorizontal();
                    bot.gripperClosed();
                    bot.setSlideLevel(0);
                    bot.update();
                }
            }
        }
        catch (Exception e){
            telemetry.addLine("failed");
        }
    }

    public void audienceSide(){
        ElapsedTime time = new ElapsedTime();
        try {
            bot.leftDropUp();
            bot.gripperClosed();
            bot.startMove(58);
            sleep(1000);
            if(moveLeft){
                bot.startStrafeNegative(-15);
                sleep(1000);
                time.reset();
                while (time.milliseconds() < 2000) {
                    bot.leftDropDown();
                    bot.update();
                }
            }
            else{
                bot.startMove(-90);
                if(moveMiddle){
                    bot.startMove(10);
                    while (time.milliseconds() < 2000) {
                        bot.leftDropDown();
                        bot.update();
                    }
                }
                else{
                    bot.startMove(30);
                    while (time.milliseconds() < 2000) {
                        bot.leftDropDown();
                        bot.update();
                    }
                }
            }
        }
        catch (Exception e){
            telemetry.addLine("failed");
        }

    }
}
