package org.firstinspires.ftc.teamcode.SelfDrivingAuto;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class AutoConfig extends AbstractHardwareComponent {
    public static boolean isRedAuto = false;
    public static boolean isAudience = false;

    public AutoConfig(boolean isAutoRed, boolean isAudience){
        this.isRedAuto = isAutoRed;
        this.isAudience = isAudience;

        if(isAudience){
            xPosForBoard =  boardSideXPos + extraXPosChangeBasedOnAudience;
            xPosStrafeToAvoidTeammate += extraXPosChangeBasedOnAudience;
        } else {
            xPosForBoard = boardSideXPos;
        }

        dropOffPixelMiddle = new Movement(0,yPosToDropOffPurplePixel, robotTurn90, dropOffPixelMiddlePID, turn90PID) {
            @Override
            public void doWhileMoving() {
                bot.setSlideLevel(Config.firstLinePos);
            }
        };

        dropOffPixelLeft = new Movement(-48,yPosToDropOffPurplePixel, 0, dropOffPixelLeftPID, turn90PID) {
            @Override // -40, 55 for potential new pixel left
            public void doWhileMoving() {
                bot.setSlideLevel(Config.firstLinePos);
            }
        };

        dropOffPixelRight = new Movement(5,yPosToDropOffPurplePixel,0, dropOffPixelRightPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {
                bot.setSlideLevel(Config.firstLinePos);
            }
        };

        goToBoard = new Movement(xPosForBoard,yPosForMiddleOfBoard, robotTurn90, goToBoardPID, turn90PID) {
            @Override
            public void doWhileMoving() {
                bot.setSlideLevel(Config.firstLinePos);
                bot.wristVertical();
            }
        };

        inPositionForPixel = new Movement(48, yPosToDropOffPurplePixel,0, inPositionForPixelPID, turn90PID) {
            @Override
            public void doWhileMoving() {
                bot.wristHorizontal();
                bot.setSlideLevel(0);
            }
        };

        collectExtraPixelFromStack = new Movement(48, 129, -90, collectPixelPID, turn90PID) {
            @Override
            public void doWhileMoving() {
                bot.gripperOpen();
                bot.wristHorizontal();
                bot.setSlideLevel(0);
            }
        };

        goForwardToPixel = new Movement(50.5, 129, -90, goForwardToPixelPID, smallAngleTurnPID ) {
            @Override
            public void doWhileMoving() {
                bot.setIntakeServoPos(Config.fifthPixelPos);
            }
        };

        passTruseAndNearBoard = new Movement(-180, collectExtraPixelFromStack.getdY(), -90, passTruseAndNearBoardPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {}
        };

        strafeToAvoidTeammate = new Movement(xPosStrafeToAvoidTeammate, 10, robotTurn90, strafeAvoidPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {
                if(elapsedTime.seconds() > 0.3) {
                    bot.setSlideLevel(0);
                    bot.wristHorizontal();
                    bot.intakeUp();
                }
            }
        };
    }

    //Constants
    public static final double speed = 0.3;
    public static final double drivingThresholdCM = 1.25;
    public static final double turningThresholdDEG = 1.25;

    //Movement and PID objects
    public static double boardSideXPos = -89;
    public static double xPosForBoard;
    public static double extraXPosChangeBasedOnAudience = -132;
    public static double yPosForLeftSideOfBoard = 52; // 1 and 4 on the board
    public static double yPosForMiddleOfBoard = 65;   // 2 and 5 on the board
    public static double yPosForRightSideOFBoard = 80; // 3 and 6 on the board
    public static double robotTurn90 = -90; //turn to fast the board
    public static double yPosToDropOffPurplePixel = 67;
    public static double xPosForLeftSide = 50;

    //Turn PID
    public static PIDCoefficients smallAngleTurnPID = new PIDCoefficients(0.15,0,0, 0);
    public static PIDCoefficients turn90PID = new PIDCoefficients(1.35,0.035,0,0);

    //middle board
    public static PIDCoefficients dropOffPixelMiddlePID = new PIDCoefficients(1.5,0.002,0, 0);
    public static PIDCoefficients goToBoardMiddlePID = new PIDCoefficients(2.3, 0.015,0,0);
    public static Movement dropOffPixelMiddle;

    //left board
    public static PIDCoefficients dropOffPixelLeftPID = new PIDCoefficients(0.8,0.002,0, 0);
    public static PIDCoefficients goToBoardLeftPID = new PIDCoefficients(1.1, 0.008,0,0);
    public static Movement dropOffPixelLeft;

    //right board
    public static PIDCoefficients dropOffPixelRightPID = new PIDCoefficients(0.65,0.01,0,0);
    public static PIDCoefficients goToBoardRightPID = new PIDCoefficients(2.25, 0.0185,0,0);
    public static Movement dropOffPixelRight;

    //Audience Side
    public static PIDCoefficients collectPixelPID           = new PIDCoefficients(1,0,0,0);
    public static PIDCoefficients passTruseAndNearBoardPID     = new PIDCoefficients(2,0.01,0,0);
    public static PIDCoefficients inPositionForPixelPID = new PIDCoefficients(1,0.005,0,0);
    public static PIDCoefficients goForwardToPixelPID = new PIDCoefficients(0.6,0,0,0);
    public static Movement collectExtraPixelFromStack;
    public static Movement passTruseAndNearBoard;
    public static Movement inPositionForPixel;
    public static Movement goForwardToPixel;

    //strafe over for teammate
    public static PIDCoefficients strafeAvoidPID = new PIDCoefficients(1,0,0,0);
    public static ElapsedTime elapsedTime = new ElapsedTime();
    public static Movement strafeToAvoidTeammate;
    public static double xPosStrafeToAvoidTeammate = -80;

    //Place pixel and go to board
    public static PIDCoefficients goToBoardPID = new PIDCoefficients(1.25,0.004,0,0);
    public static Movement goToBoard;
    public static PIDCoefficients reversedBlueLeftPID = new PIDCoefficients(1,0,0.01,0);
}
