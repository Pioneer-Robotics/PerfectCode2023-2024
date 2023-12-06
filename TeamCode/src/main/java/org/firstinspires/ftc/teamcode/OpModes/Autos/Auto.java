package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@Autonomous
public class Auto extends OpScript {
    boolean moveRight = true;
    @Override
    public void run() {
        bot.gripperClosed();
        bot.startMove(58);
        sleep(2000);
        bot.rightDropUp();
        bot.update();
        sleep(2000);
        //bot.startStrafe(-20);
        bot.startTurn(90);
        bot.startMove(90);
        if(moveRight) {
            bot.startStrafe(30);
        }
        while(bot.getSlideLevel() != 1100) {
            bot.setSlideLevel(1100);
            bot.wristVertical();
            bot.update();
        }
        bot.gripperOpen();
        bot.update();
//        bot.elevateOpen();
//        bot.update();
//        bot.startStrafe(20);
    }

    @Override
    public void runOpMode() throws InterruptedException {runOpMode(this);}
}
