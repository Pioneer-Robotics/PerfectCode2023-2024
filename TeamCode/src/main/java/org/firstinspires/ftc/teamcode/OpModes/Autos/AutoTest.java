package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.Movement;

@Autonomous
public class AutoTest extends OpScript {
    @Override
    public void run() {
        bot.drive(new Movement(0,50,5, Config.dropOffPixelMiddlePID, Config.smallAngleTestTurn) {
            @Override
            public void doWhileMoving() {

            }
        });
    }

    @Override
    public void runOpMode() throws InterruptedException {
        runOpMode(this);
    }
}
