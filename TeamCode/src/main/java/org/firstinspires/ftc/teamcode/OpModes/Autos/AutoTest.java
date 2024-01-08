package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.OpModes.OpScript;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.AutoConfig;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.Movement;

@Autonomous
public class AutoTest extends OpScript {
    @Override
    public void run() {
        bot.drive(new Movement(0,50,5, AutoConfig.dropOffPixelMiddlePID, AutoConfig.smallAngleTurnPID) {
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
