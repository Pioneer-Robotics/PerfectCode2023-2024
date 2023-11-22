package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpScript {
    @Override
    public void initloop() {init_loop(bot);}

    @Override
    public void run() {
        bot.Driver1();
        bot.Driver2();
        bot.Telemetry();
    }

    @Override
    public void runOpMode() throws InterruptedException {runOpMode(this);}
}
