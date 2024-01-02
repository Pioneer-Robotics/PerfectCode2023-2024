package org.firstinspires.ftc.teamcode.SelfDrivingAuto;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

/**
 * This class will contain our autonomous methods as well as gearShift
 */
public class Commands extends AbstractHardwareComponent {
    private double turn = 90d;//depending on red or blue side, this turn will be flipped

    //Based on booleans, will move to certain parts of the board
    boolean moveLeft = false;
    boolean moveRight = false;
    boolean moveMiddle = false;

    /**
     * Is used to "gear shift" between powers in teleOp
     * @param gamepad: usually gamepad1
     * @param counter: this is the object that will handle that math
     */
    public void gearShift(Gamepad gamepad, Counter counter){counter.arithmetic(gamepad.right_bumper, gamepad.left_bumper, .1);}
    public void pShift(Gamepad gamepad, Counter counter){counter.arithmetic(gamepad.dpad_left, gamepad.dpad_right, 0.05);}

    public void runAuto(){

    }

    public void runMiddle(){
        bot.drive(Config.dropOffPixelMiddle);
        bot.timerSleep(1);
        bot.rightDropDown();
        bot.timerSleep(1);
        bot.drive(Config.goToBoardMiddle);
        bot.timerSleep(2);
        bot.gripperOpen();
        bot.drive(Config.strafeToAvoidTeammate);
    }

    public void runLeft(){
        bot.drive(Config.dropOffPixelLeft);
        bot.timerSleep(1);
        bot.rightDropDown();
        bot.timerSleep(1);
        bot.drive(Config.goForwardForBoardLeft);
        bot.drive(Config.goToBoardLeft);
        bot.timerSleep(2);
        bot.gripperOpen();
        bot.drive(Config.strafeToAvoidTeammate);
    }

    public void runRight(){
        bot.drive(Config.dropOffPixelRight);
        bot.timerSleep(1);
        bot.rightDropDown();
        bot.timerSleep(1);
        bot.drive(Config.goToBoardRight);
        bot.timerSleep(2);
        bot.gripperOpen();
        bot.drive(Config.strafeToAvoidTeammate);
    }
}
