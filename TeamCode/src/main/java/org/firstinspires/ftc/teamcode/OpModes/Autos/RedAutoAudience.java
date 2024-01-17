package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@Autonomous
public class RedAutoAudience extends OpScript {
    @Override
    public void run() {
        bot.runAuto();
    }

    @Override
    public void runOpMode() throws InterruptedException {runOpMode(this);}
}
