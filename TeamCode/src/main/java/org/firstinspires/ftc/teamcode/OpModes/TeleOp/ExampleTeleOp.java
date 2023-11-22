package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@TeleOp
public class ExampleTeleOp extends OpScript {
    @Override
    public void initloop() {init_loop(bot);}

    @Override
    public void run() {bot.example();}

    @Override
    public void runOpMode() throws InterruptedException {runOpMode(this);}
}
