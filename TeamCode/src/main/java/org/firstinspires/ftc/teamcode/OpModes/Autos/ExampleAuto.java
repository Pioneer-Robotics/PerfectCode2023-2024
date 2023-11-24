package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@Autonomous
public class ExampleAuto extends OpScript {
    @Override
    public void run() {bot.example();}

    @Override
    public void runOpMode() throws InterruptedException {runOpMode(this);}
}
