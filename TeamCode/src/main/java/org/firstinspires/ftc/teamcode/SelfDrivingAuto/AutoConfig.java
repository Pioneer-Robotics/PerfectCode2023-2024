package org.firstinspires.ftc.teamcode.SelfDrivingAuto;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class AutoConfig extends AbstractHardwareComponent {
    public static boolean isRedAuto = false;
    public static boolean isAudience = false;

    public AutoConfig(boolean isAutoRed, boolean isAudience){
        isRedAuto = isAutoRed;
        this.isAudience = isAudience;

        if(isAudience){
            xPosForBoard =  boardSideXPos + extraXPosChangeBasedOnAudience;
            xPosStrafeToAvoidTeammate =  strafeXPos + extraXPosChangeBasedOnAudience;
        } else {
            xPosForBoard = boardSideXPos;
            xPosStrafeToAvoidTeammate = strafeXPos;
        }

        dropOffPixelMiddle = new Movement(-11,yPosToDropOffPurplePixel, robotTurn90, dropOffPixelMiddlePID, turn90PID) {
            @Override
            public void doWhileMoving() {
            }
        };

        dropOffPixelLeft = new Movement(-53.5,yPosToDropOffPurplePixel, 0, dropOffPixelLeftPID, turn90PID) {
            @Override // -40, 55 for potential new pixel left
            public void doWhileMoving() {
            }
        };

        dropOffPixelRight = new Movement(0,yPosToDropOffPurplePixel,0, dropOffPixelRightPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {
            }
        };

        goToBoard = new Movement(xPosForBoard,yPosForMiddleOfBoard, robotTurn90, goToBoardPID, turn90PID) {
            @Override
            public void doWhileMoving() {
                bot.setSlideLevel(Config.firstLinePos);
                bot.wristVertical();
                bot.stopCollector();
            }
        };

        inPositionForPixel = new Movement(53.5, yPosToDropOffPurplePixel,0, inPositionForPixelPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {
            }
        };

        collectExtraPixelFromStack = new Movement(53.5, 129, 0, collectPixelPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {
                bot.gripperOpen();
            }
        };

        backUpSoWeDontHitPixelStack = new Movement(39.5, 129, -90, collectPixelPID, turn90PID) {
            @Override
            public void doWhileMoving() {

            }
        };

        goForwardToPixel = new Movement(56.5, 129, -90, goForwardToPixelPID, smallAngleTurnPID ) {
            @Override
            public void doWhileMoving() {
                bot.gripperOpen();
                bot.setIntakeServoPos(Config.fifthPixelPos);
            }
        };

        passTruseAndNearBoard = new Movement(-180, collectExtraPixelFromStack.getdY(), -90, passTruseAndNearBoardPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {
                bot.moveCollectorBack();
            }
        };

        strafeToAvoidTeammate = new Movement(xPosStrafeToAvoidTeammate, 10, robotTurn90, strafeAvoidPID, smallAngleTurnPID) {
            @Override
            public void doWhileMoving() {
                bot.intakeUp();
                if(elapsedTime.seconds() > 0.3) {
                    bot.setSlideLevel(0);
                    bot.wristHorizontal();
                }
            }
        };
    }

    //Constants
    public static final double speed = 0.425;
    public static final double drivingThresholdCM = 1.25;
    public static final double turningThresholdDEG = 1.25;

    //Movement and PID objects
    public static double boardSideXPos = -97;
    public static double strafeXPos = -84;
    public static double xPosForBoard;
    public static double extraXPosChangeBasedOnAudience = -120;
    public static double yPosForLeftSideOfBoard = 52; // 1 and 4 on the board
    public static double yPosForMiddleOfBoard = 67; // 2 and 5 on the board
    public static double yPosForRightSideOFBoard = 84; // 3 and 6 on the board
    public static double robotTurn90 = -90; //turn to fast the board
    public static double yPosToDropOffPurplePixel = 65;
    public static double xPosForLeftSide = 50;

    //Turn PID
    public static PIDCoefficients smallAngleTurnPID =   new PIDCoefficients(0.15,0,0.01, 0);
    public static PIDCoefficients turn90PID = new PIDCoefficients(1.35,0.035,0,0);

    //middle board
    public static PIDCoefficients dropOffPixelMiddlePID = new PIDCoefficients(1,0,0.02, 0);
    public static Movement dropOffPixelMiddle;

    //left board
    public static PIDCoefficients dropOffPixelLeftPID = new PIDCoefficients(1,0.001,0.01, 0);
    public static Movement dropOffPixelLeft;

    //right board
    public static PIDCoefficients dropOffPixelRightPID = new PIDCoefficients(0.75,0.005,0,0);
    public static Movement dropOffPixelRight;

    //Audience Side
    public static PIDCoefficients collectPixelPID           = new PIDCoefficients(0.75,0.005,0,0);
    public static PIDCoefficients passTruseAndNearBoardPID     = new PIDCoefficients(2.5,0.011,0,0);
    public static PIDCoefficients inPositionForPixelPID = new PIDCoefficients(1,0,0,0);
    public static PIDCoefficients goForwardToPixelPID = new PIDCoefficients(0.6,0,0,0);
    public static Movement collectExtraPixelFromStack;
    public static Movement backUpSoWeDontHitPixelStack;
    public static Movement passTruseAndNearBoard;
    public static Movement inPositionForPixel;
    public static Movement goForwardToPixel;

    //strafe over for teammate
    public static PIDCoefficients strafeAvoidPID = new PIDCoefficients(1,0,0,0);
    public static ElapsedTime elapsedTime = new ElapsedTime();
    public static Movement strafeToAvoidTeammate;
    public static double xPosStrafeToAvoidTeammate = -80;

    //Place pixel and go to board
    public static PIDCoefficients goToBoardPID = new PIDCoefficients(1,0.0015,0,0);
    public static Movement goToBoard;
    public static PIDCoefficients reversedBlueLeftPID = new PIDCoefficients(1,0,0.01,0);

    public static PIDCoefficients testPId = new PIDCoefficients(1, 0,0.015,0);
    public static Movement backToNormal = new Movement(-45,131, -90, testPId, smallAngleTurnPID ) {
        @Override
        public void doWhileMoving() {
             bot.setIntakeServoPos(Config.secondPixelPos);
            bot.setSlideLevel(0);
            bot.wristHorizontal();
        }
    };

    public static Movement pixelpixel = new Movement(170, 131, -90, testPId, turn90PID) {
        @Override
        public void doWhileMoving() {
            bot.setIntakeServoPos(Config.fifthPixelPos);
            bot.moveCollector();
        }
    };

    //NEW 2+3 audience
    public static PIDCoefficients uniPID = new PIDCoefficients(1.25,0,0,0);
    public static Movement audPixelLeft = new Movement(0, 65, 0, uniPID, smallAngleTurnPID) {
        @Override
        public void doWhileMoving() {

        }
    };
    public static Movement audPixelMiddle = new Movement(0,129, -90, uniPID, turn90PID) {
        @Override
        public void doWhileMoving() {
        }
    };

    public static Movement audPixelRight = new Movement(30, 129, -90, uniPID, turn90PID) {
        @Override
        public void doWhileMoving() {

        }
    };
}
