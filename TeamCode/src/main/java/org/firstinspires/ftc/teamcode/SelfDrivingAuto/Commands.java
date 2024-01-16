package org.firstinspires.ftc.teamcode.SelfDrivingAuto;
import android.media.tv.AitInfo;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

/**
 * This class will contain our autonomous methods as well as gearShift
 */
public class Commands extends AbstractHardwareComponent {
    /**
     * Is used to "gear shift" between powers in teleOp
     * @param gamepad: usually gamepad1
     * @param counter: this is the object that will handle that math
     */
    public void gearShift(Gamepad gamepad, Counter counter){counter.arithmetic(gamepad.right_bumper, gamepad.left_bumper, .1);}
    public void pShift(Gamepad gamepad, Counter counter){counter.arithmetic(gamepad.dpad_left, gamepad.dpad_right, 0.05);}

    //Board side
    /**
     * This is the method that will run our board side auto based on whichever alliance side and team marker location
     */
    public void boardSideAuto(){
        int location = bot.getTeamMarkerLocation(); //get team marker location
        bot.gripperClosed();
        bot.intakeDown();
        bot.autoLights();

        //run program based on marker location
        if(location == 1){
            if (bot.isRed()) {
                runBoardSideRight();
            } else{
                runBoardSideLeft();
            }
        } else if(location == 3){
            if(bot.isRed()){
                runBoardSideLeft();
            } else {
                runBoardSideRight();
            }
        } else{
            runBoardSideMiddle();
        }

        //reset arm for TeleOp
        bot.setSlideLevel(0);
    }

    public void runBoardSideLeft(){
          bot.drive(AutoConfig.dropOffPixelLeft);
        dropPixelBasedOnAlliance();
        bot.drive(AutoConfig.goForwardForBoardLeft);
        bot.drive(AutoConfig.goToBoardLeft);
        closeGripperAndWait();
        AutoConfig.elapsedTime.reset();
        bot.drive(AutoConfig.strafeToAvoidTeammate);
    }

    public void runBoardSideMiddle(){
        bot.drive(AutoConfig.dropOffPixelMiddle);
        dropPixelBasedOnAlliance();
        bot.drive(AutoConfig.goToBoardMiddle);
        closeGripperAndWait();
        AutoConfig.elapsedTime.reset();
        bot.drive(AutoConfig.strafeToAvoidTeammate);
    }

    public void runBoardSideRight(){
        bot.drive(AutoConfig.dropOffPixelRight);
        dropPixelBasedOnAlliance();
        bot.drive(AutoConfig.goToBoardRight);
        closeGripperAndWait();
        AutoConfig.elapsedTime.reset();
        bot.drive(AutoConfig.strafeToAvoidTeammate);
    }

    //Audience side

    /**
     * This is the method that will run our audience side auto based on whichever alliance side and team marker location
     */
    public void audienceSideAuto(){

    }

    public void runAudienceSideLeft(){

    }

    //Side-less auto function(s)
    public void dropPixelBasedOnAlliance(){
        bot.timerSleep(1); //give some time to drop servo

        //drop correct servo
        if(bot.isRed()){
            bot.leftDropDown();
        } else{
            bot.rightDropDown();
        }
        bot.timerSleep(1); //give some time for pixel to fall

        //reset both servos
        bot.rightDropUp();
        bot.leftDropUp();
    }

    public void closeGripperAndWait(){
        bot.gripperOpen();
        bot.timerSleep(2);
        bot.setSlideLevel(Config.secondLinePos);
        bot.timerSleep(2);
    }
}
