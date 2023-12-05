package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@Autonomous
public class Auto extends OpScript {
    @Override
    public void run() {
        bot.startMove(58);
        bot.startTurn(88);
        bot.startMove(90);
        while(bot.getSlideLevel() != 600) {
            bot.setSlideLevel(600);
            bot.elevateOpen();
            bot.update();
        }
        while(!isStopRequested() && isStarted()){
            telemetry.addLine("finished and waiting clock");
        }
//        bot.elevateOpen();
//        bot.update();
//        bot.startStrafe(20);
    }

    @Override
    public void runOpMode() throws InterruptedException {runOpMode(this);}
}
