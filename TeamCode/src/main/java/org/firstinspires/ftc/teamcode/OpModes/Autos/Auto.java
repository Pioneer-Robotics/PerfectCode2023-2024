package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@Autonomous
public class Auto extends OpScript {
    @Override
    public void run() {bot.drive(Config.turn90);}

    @Override
    public void initloop() {init_loop(bot);}

    @Override
    public void runOpMode() throws InterruptedException {runOpMode(this);}
}
