package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;

@Autonomous
public class AutoTest extends OpScript {
    @Override
    public void run() {
        bot.drive(Config.dropOffPixelMiddle);
        bot.timerSleep(2);
        bot.rightDropDown();
        bot.timerSleep(1);
        bot.drive(Config.goToBoardMiddle);
        bot.setSlideLevel(2000);
        bot.wristVertical();
        bot.timerSleep(2);
        bot.gripperOpen();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        runOpMode(this);
    }
}
