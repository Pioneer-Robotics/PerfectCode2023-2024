package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@Autonomous
public class Auto extends OpScript {
    @Override
    public void run() {
        bot.startMove(100);
        bot.startTurn(90);
        bot.startMove(100);
        bot.startStrafe(20);
    }

    @Override
    public void runOpMode() throws InterruptedException {runOpMode(this);}
}
