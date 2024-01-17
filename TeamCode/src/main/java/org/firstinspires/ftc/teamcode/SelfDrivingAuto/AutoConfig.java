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

        dropOffPixelMiddle = new Movement(0,65, robotTurn90, dropOffPixelMiddlePID, turnPID) {
            @Override
            public void doWhileMoving() {}
        };

        goToBoardMiddle = new Movement(xPosForBoard,yPosForMiddleOfBoard, robotTurn90, goToBoardMiddlePID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {
                bot.setSlideLevel(Config.firstLinePos);
                bot.wristVertical();
            }
        };

        dropOffPixelLeft = new Movement(-20,40, robotTurn90, dropOffPixelLeftPID, turnPID) {
            @Override
            public void doWhileMoving() {}
        };

        goForwardForBoardLeft = new Movement(-70,dropOffPixelLeft.getdY(), robotTurn90, goToBoardLeftPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {
                bot.setSlideLevel(Config.firstLinePos);
                bot.wristVertical();
            }
        };

        goToBoardLeft = new Movement(xPosForBoard,yPosForLeftSideOfBoard, robotTurn90, goToBoardLeftPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {
                bot.setSlideLevel(Config.firstLinePos);
                bot.wristVertical();
            }
        };

        dropOffPixelRight = new Movement(5,67,0, dropOffPixelRightPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {}
        };

        goToBoardRight = new Movement(xPosForBoard,yPosForRightSideOFBoard, robotTurn90, goToBoardRightPID, turnPID) {
            @Override
            public void doWhileMoving() {
                bot.setSlideLevel(Config.firstLinePos);
                bot.wristVertical();
            }
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

        collectExtraPixelFromStack = new Movement(20, 90, -90, collectPixelPID, turnPID) {
            @Override
            public void doWhileMoving() {bot.setIntakeServoPos(Config.fifthPixelPos);}
        };

        passTruseAndNearBoard = new Movement(-180, 90, -90, passTruseAndNearBoardPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {}
        };
    }

    //Constants
    public static final double speed = 0.3;
    public static final double drivingThresholdCM = 1.5;
    public static final double turningThresholdDEG = 1.5;

    //Movement and PID objects
    public static double boardSideXPos = -88.75;
    public static double xPosForBoard;
    public static double extraXPosChangeBasedOnAudience = -140;
    public static double yPosForLeftSideOfBoard = 52; // 1 and 4 on the board
    public static double yPosForRightSideOFBoard = 85; // 3 and 6 on the board
    public static double yPosForMiddleOfBoard = 67;
    public static double robotTurn90 = -90; //turn to fast the board

    //Turn PID
    public static PIDCoefficients smallAngleTurnPID = new PIDCoefficients(0.25,0,0, 0);
    public static PIDCoefficients turnPID = new PIDCoefficients(1.4,0.04,0,0);

    //middle board
    public static PIDCoefficients dropOffPixelMiddlePID = new PIDCoefficients(1.75,0.009,0, 0);
    public static PIDCoefficients goToBoardMiddlePID = new PIDCoefficients(2.3, 0.015,0,0);
    public static Movement dropOffPixelMiddle;
    public static Movement goToBoardMiddle;

    //left board
    public static PIDCoefficients dropOffPixelLeftPID = new PIDCoefficients(1.1,0.0085,0, 0);
    public static PIDCoefficients goToBoardLeftPID = new PIDCoefficients(1.1, 0.008,0,0);
    public static Movement dropOffPixelLeft;
    public static Movement goForwardForBoardLeft;
    public static Movement goToBoardLeft;

    //right board
    public static PIDCoefficients dropOffPixelRightPID = new PIDCoefficients(0.75,0.013,0,0);
    public static PIDCoefficients goToBoardRightPID = new PIDCoefficients(2.25, 0.0185,0,0);
    public static Movement dropOffPixelRight;
    public static Movement goToBoardRight;

    //Audience Side
    public static PIDCoefficients collectPixelPID           = new PIDCoefficients(1,0,0,0);
    public static PIDCoefficients passTruseAndNearBoardPID     = new PIDCoefficients(1,0,0,0);
    public static Movement collectExtraPixelFromStack;
    public static Movement passTruseAndNearBoard;

    //strafe over for teammate
    public static PIDCoefficients strafeAvoidPID = new PIDCoefficients(1,0,0,0);
    public static ElapsedTime elapsedTime = new ElapsedTime();
    public static Movement strafeToAvoidTeammate;
    public static double xPosStrafeToAvoidTeammate = -80;
}
