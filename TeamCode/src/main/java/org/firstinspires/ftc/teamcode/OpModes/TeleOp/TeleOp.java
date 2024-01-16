package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpScript {
    @Override
    public void run() {bot.teleOp();}

    @Override
    public void runOpMode() throws InterruptedException {runOpMode(this);}
}
