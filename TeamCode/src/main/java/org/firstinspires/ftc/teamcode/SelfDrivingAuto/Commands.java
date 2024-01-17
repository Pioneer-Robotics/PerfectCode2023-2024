package org.firstinspires.ftc.teamcode.SelfDrivingAuto;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

/**
 * This class will contain our autonomous methods as well as gearShift
 */
public class Commands extends AbstractHardwareComponent {

    /**
     * This is the method that will run for both autos auto based on whichever alliance side and team marker location
     */
    public void runAuto(){
        //reset for auto
        bot.gripperClosed();
        bot.intakeDown();

        //dropping off pixel is standard for all
        dropOffPixelAll(bot.getTeamMarkerLocation());

        //collect pixel and near the board to place two pixels
        if(bot.isAudienceSide()){
            grabExtraPixelAndNearBoard();
        }

        //drive to the board and drop off pixel at the board.
        placePixelAndStrafeOver();
        closeGripperAndWait();

        //Strafe to avoid teammate
        AutoConfig.elapsedTime.reset();
        bot.drive(AutoConfig.strafeToAvoidTeammate);
    }

    /**
     * Is used to "gear shift" between powers in teleOp
     * @param gamepad: usually gamepad1
     * @param counter: this is the object that will handle that math
     */
    public void gearShift(Gamepad gamepad, Counter counter){counter.arithmetic(gamepad.right_bumper, gamepad.left_bumper, .1);}
    public void pShift(Gamepad gamepad, Counter counter){counter.arithmetic(gamepad.dpad_left, gamepad.dpad_right, 0.05);}

    //Side-less auto function(s)
    public void dropPixelBasedOnAlliance(){
        //drop correct servo
        if(bot.isAudienceSide()){
            if(bot.isRed()){
                bot.rightDropDown();
            } else{
                bot.leftDropDown();
            }
        } else{
            if(bot.isRed()){
                bot.leftDropDown();
            } else{
                bot.rightDropDown();
            }
        }

        bot.timerSleep(1); //give some time for pixel to fall

        //reset both servos
        bot.rightDropUp();
        bot.leftDropUp();
    }

    public void dropOffPixelAll(int location){
        if(location == 1){
            if(bot.isAudienceSide()){
                if(bot.isRed()){
                    bot.drive(AutoConfig.dropOffPixelLeft);
                } else{
                    bot.drive(AutoConfig.dropOffPixelRight);
                }
            } else{
                if (bot.isRed()) {
                    bot.drive(AutoConfig.dropOffPixelRight);
                } else{
                    bot.drive(AutoConfig.dropOffPixelLeft);
                }
            }
        } else if(location == 3){
            if(bot.isAudienceSide()){
                //audience
                if(bot.isRed()){
                    bot.drive(AutoConfig.dropOffPixelRight);
                } else {
                    bot.drive(AutoConfig.dropOffPixelLeft);
                }
            } else{
                //board side
                if(bot.isRed()){
                    bot.drive(AutoConfig.dropOffPixelLeft);
                } else {
                    bot.drive(AutoConfig.dropOffPixelRight);
                }
            }
        } else{
            bot.drive(AutoConfig.dropOffPixelMiddle);
        }
        dropPixelBasedOnAlliance(); //drop off pixel
    }

    public void placePixelAndStrafeOver(){
        if(bot.getTeamMarkerLocation() == 1){
            AutoConfig.goToBoard.setdY(AutoConfig.yPosForLeftSideOfBoard);
        } else if(bot.getTeamMarkerLocation() == 3){
            AutoConfig.goToBoard.setdY(AutoConfig.yPosForRightSideOFBoard);
        }
    }

    public void grabExtraPixelAndNearBoard(){
        bot.drive(AutoConfig.collectExtraPixelFromStack);

        bot.moveCollector();
        bot.timerSleep(2);
        bot.stopCollector();

        bot.drive(AutoConfig.passTruseAndNearBoard);
    }

    public void closeGripperAndWait(){
        bot.gripperOpen();
        bot.timerSleep(2);
        bot.setSlideLevel(Config.secondLinePos);
        bot.timerSleep(2);
    }
}
