package org.firstinspires.ftc.teamcode.SelfDrivingAuto;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    /**
     * Depending on if red or blue side and what the cameraHandler sees, this method will make the robot go there
     */
    public void boardSide() {
        try {
            ElapsedTime time = new ElapsedTime();
            boolean isBlue = bot.opmode.getClass().getName().contains("Blue");
            //int leftMiddleRight = bot.locationCamera();//left is one, middle is two, and right is three
            //int leftMiddleRight = bot.locationCamera2();
            int leftMiddleRight = bot.opmode.location;
            if (leftMiddleRight == 3) {
                moveLeft = true;
            } else if (leftMiddleRight == 2) {
                moveMiddle = true;
            } else {
                moveRight = true;
            }

            //flipping turn based on if we are on red or blue side
            if(!isBlue){
                turn = -turn;
            }

            //not going to left side
            if (!moveLeft) {
                //make sure all motors and servos are in correct position
                bot.rightDropUp();
                bot.gripperClosed();

                bot.startMove(58);//move forward one square
                sleep(1000);//wait one second
                if (moveRight) {
                    //drop pixel if we are going to the right
                    bot.rightDropDown();
                }
                bot.update();

                sleep(1000);//one second
                bot.startTurn(turn);//turn baseed on if red or blue side
                sleep(1000);//wait one second

                if (moveMiddle) {
                    //drop the pixel if in middle position
                    bot.rightDropDown();
                }
                sleep(1000);//wait
                bot.startMove(85.5);//drive to the board
                if (moveRight) {
                    //strafe to the right
                    bot.startStrafe(40);
                }
                if (moveMiddle) {
                    //strafe to the right but less for middle slot
                    bot.startStrafe(20);
                }
                sleep(1000);//wait
                while (bot.getSlideLevel() != 1600) {
                    //shoots the linear slide up and moves wrist servo
                    bot.setSlideLevel(1600);
                    bot.wristVertical();
                    bot.update();
                }
                time.reset();
                while (time.milliseconds() < 2000) {
                    //opens gripper and waits
                    bot.gripperOpen();
                    bot.update();
                }
                time.reset();
                while (time.milliseconds() < 2000) {
                    //resets arm back to resetig position
                    bot.wristHorizontal();
                    bot.gripperClosed();
                    bot.setSlideLevel(0);
                    bot.update();
                }
            } else {
                //ONLY LEFT SIDE
                //reset arms and servos
                bot.rightDropUp();
                bot.gripperClosed();
                bot.startMove(23);//move foward half a square
                sleep(1000);
                bot.startTurn(turn);//turn based on red or blue
                sleep(1000);
                bot.startMove(13);
                sleep(1000);
                bot.rightDropDown();//drop pixel
                sleep(1000);
                bot.startMove(70.5);//drive to board
                sleep(1000);
                bot.startStrafe(36);//strafe right but a little over
                sleep(1000);
                while (bot.getSlideLevel() != 1600) {
                    //shoots arm up
                    bot.setSlideLevel(1600);
                    bot.wristVertical();
                    bot.update();
                }
                sleep(1000);
                time.reset();
                while (time.milliseconds() < 2000) {
                    //opens gripper
                    bot.gripperOpen();
                    bot.update();
                }
                time.reset();
                while (time.milliseconds() < 2000) {
                    //reset arm
                    bot.wristHorizontal();
                    bot.gripperClosed();
                    bot.setSlideLevel(0);
                    bot.update();
                }
            }
        }
        catch (Exception e){
            telemetry.addLine("failed");
        }
    }

    public void redAuto(){
        try {
            ElapsedTime time = new ElapsedTime();
            boolean isBlue = bot.opmode.getClass().getName().contains("Blue");
            //int leftMiddleRight = bot.locationCamera();//left is one, middle is two, and right is three
            int leftMiddleRight = bot.opmode.location;
            if (leftMiddleRight == 3) {
                moveLeft = true;
            } else if (leftMiddleRight == 2) {
                moveMiddle = true;
            } else {
                moveRight = true;
            }

            turn = -turn;
            //flipping turn based on if we are on red or blue side

            //not going to left side
            if (!moveLeft) {
                //make sure all motors and servos are in correct position
                bot.leftDropUp();
                bot.gripperClosed();

                bot.startMove(50);//move forward one square
                sleep(1000);//wait one second
                if (moveRight) {
                    //drop pixel if we are going to the right
                    bot.leftDropDown();
                }
                bot.update();

                sleep(1000);//one second
                bot.startTurn(turn);//turn baseed on if red or blue side
                sleep(1000);//wait one second

                if (moveMiddle) {
                    //drop the pixel if in middle position
                    bot.leftDropDown();
                }
                sleep(1000);//wait
                bot.startMove(65);//drive to the board
                if (moveRight) {
                    //strafe to the right
                    bot.startStrafeNegative(-35);
                }
                if (moveMiddle) {
                    //strafe to the right but less for middle slot
                    bot.startStrafeNegative(-20);
                }
                sleep(1000);//wait
                while (bot.getSlideLevel() != 1600) {
                    //shoots the linear slide up and moves wrist servo
                    bot.setSlideLevel(1600);
                    bot.wristVertical();
                    bot.update();
                }
                time.reset();
                while (time.milliseconds() < 2000) {
                    //opens gripper and waits
                    bot.gripperOpen();
                    bot.update();
                }
                time.reset();
                while (time.milliseconds() < 2000) {
                    //resets arm back to resetig position
                    bot.wristHorizontal();
                    bot.gripperClosed();
                    bot.setSlideLevel(0);
                    bot.update();
                }
                bot.startStrafe(50);
            } else {
                //ONLY LEFT SIDE
                //reset arms and servos
                bot.rightDropUp();
                bot.gripperClosed();
                bot.startMove(30);//move foward half a square
                sleep(1000);
                bot.startTurn(turn);//turn based on red or blue
                sleep(1000);
                bot.startMove(13);
                sleep(1000);
                bot.leftDropDown();//drop pixel
                sleep(1000);
                bot.startMove(70);//drive to board
                sleep(1000);
                bot.startStrafeNegative(-36);//strafe right but a little over
                sleep(1000);
                while (bot.getSlideLevel() != 1600) {
                    //shoots arm up
                    bot.setSlideLevel(1600);
                    bot.wristVertical();
                    bot.update();
                }
                sleep(1000);
                time.reset();
                while (time.milliseconds() < 2000) {
                    //opens gripper
                    bot.gripperOpen();
                    bot.update();
                }
                time.reset();
                while (time.milliseconds() < 2000) {
                    //reset arm
                    bot.wristHorizontal();
                    bot.gripperClosed();
                    bot.setSlideLevel(0);
                    bot.update();
                }
                bot.startStrafe(50);
            }
        }
        catch (Exception e){
            telemetry.addLine("failed");
        }
    }


    public void audienceSide(){
        ElapsedTime time = new ElapsedTime();
        try {
            bot.leftDropUp();
            bot.gripperClosed();
            bot.startMove(58);
            sleep(1000);
            if(moveLeft){
                bot.startStrafeNegative(-15);
                sleep(1000);
                time.reset();
                while (time.milliseconds() < 2000) {
                    bot.leftDropDown();
                    bot.update();
                }
            }
            else{
                bot.startMove(-90);
                if(moveMiddle){
                    bot.startMove(10);
                    while (time.milliseconds() < 2000) {
                        bot.leftDropDown();
                        bot.update();
                    }
                }
                else{
                    bot.startMove(30);
                    while (time.milliseconds() < 2000) {
                        bot.leftDropDown();
                        bot.update();
                    }
                }
            }
        }
        catch (Exception e){
            telemetry.addLine("failed");
        }

    }
}
