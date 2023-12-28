package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@Autonomous
public class RedAuto extends OpScript {
    @Override
    public void run() {
        bot.redAuto();
    }

    @Override
    public void runOpMode() throws InterruptedException {runOpMode(this);}
}
