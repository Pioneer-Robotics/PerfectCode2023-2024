package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@Autonomous
public class Auto extends OpScript {
    boolean moveRight = false;
    boolean moveMiddle = false;
    boolean moveLeft = true;
    ElapsedTime time = new ElapsedTime();
    @Override
    public void run() {
        if(!moveLeft) {
            bot.rightDropDown();
            bot.gripperClosed();
            bot.startMove(58);
            sleep(1000);
            if (moveRight) {
                bot.rightDropUp();
            }
            bot.update();
            sleep(1000);
            //bot.startStrafe(-20);
            bot.startTurn(90);
            sleep(1000);
            if (moveMiddle) {
                bot.rightDropUp();
            }
            sleep(1000);
            bot.startMove(83);
            if (moveRight) {
                bot.startStrafe(40);
            }
            if (moveMiddle) {
                bot.startStrafe(26);
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
                bot.setSlideLevel(0);
                bot.update();
            }
//        bot.elevateOpen();
//        bot.update();
//        bot.startStrafe(20);
        }
        else{
            bot.rightDropDown();
            bot.gripperClosed();
            bot.startMove(20);
            sleep(1000);
            bot.startTurn(86);
            sleep(1000);
            bot.startMove(13);
            sleep(1000);
            bot.rightDropUp();
            sleep(1000);
            bot.startMove(74.5);
            sleep(1000);
            bot.startStrafe(41);
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
                bot.setSlideLevel(0);
                bot.update();
            }
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {runOpMode(this);}
}
