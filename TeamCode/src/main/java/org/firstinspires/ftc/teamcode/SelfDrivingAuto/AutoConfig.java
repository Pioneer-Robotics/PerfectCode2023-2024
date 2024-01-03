package org.firstinspires.ftc.teamcode.SelfDrivingAuto;

import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class AutoConfig extends AbstractHardwareComponent {

//    public AutoConfig(){
//        if(bot.isRed()){ //change turn based on color side
//            robotTurn       *= -1;
//            xPosForBoard    *= -1;
//
//        }
//    }
    //Constants
    public static final double speed = 0.3;
    public static final double drivingThresholdCM = 2;
    public static final double turningThresholdDEG = 1.5;

    //Movement and PID objects
    public static double xPosForBoard = -90;
    public static double yPosForLeftSideOfBoard = 52;
    public static double yPosForRightSideOFBoard = 87;
    public static double robotTurn = -90;

    //Turn PID
    public static PIDCoefficients smallAngleTestTurn = new PIDCoefficients(0.5,0,0, 0);
    public static PIDCoefficients pidTurn = new PIDCoefficients(1.4,0.03,0,0);

    //middle board
    public static PIDCoefficients dropOffPixelMiddlePID = new PIDCoefficients(1.75,0.009,0, 0);
    public static PIDCoefficients goToBoardMiddlePID = new PIDCoefficients(2.3, 0.015,0,0);
    public static Movement dropOffPixelMiddle = new Movement(0,67, robotTurn, dropOffPixelMiddlePID, pidTurn) {
        @Override
        public void doWhileMoving() {}
    };
    public static Movement goToBoardMiddle = new Movement(xPosForBoard,67, robotTurn, goToBoardMiddlePID, smallAngleTestTurn) {
        @Override
        public void doWhileMoving() {
            bot.setSlideLevel(2000);
            bot.wristVertical();
        }
    };

    //left board
    public static PIDCoefficients dropOffPixelLeftPID = new PIDCoefficients(1.1,0.0085,0, 0);
    public static PIDCoefficients goToBoardLeftPID = new PIDCoefficients(1.35, 0.0105,0,0);
    public static Movement dropOffPixelLeft = new Movement(-20,40, robotTurn, dropOffPixelLeftPID, pidTurn) {
        @Override
        public void doWhileMoving() {}
    };
    public static Movement goForwardForBoardLeft = new Movement(-70,dropOffPixelLeft.getdY(),robotTurn, goToBoardLeftPID, smallAngleTestTurn) {
        @Override
        public void doWhileMoving() {
            bot.setSlideLevel(2000);
            bot.wristVertical();
        }
    };
    public static Movement goToBoardLeft = new Movement(xPosForBoard,52,robotTurn, goToBoardLeftPID, smallAngleTestTurn) {
        @Override
        public void doWhileMoving() {
            bot.setSlideLevel(2000);
            bot.wristVertical();
        }
    };

    //right board
    public static PIDCoefficients dropOffPixelRightPID = new PIDCoefficients(0.75,0.013,0,0);
    public static PIDCoefficients goToBoardRightPID = new PIDCoefficients(2.5, 0.0185,0,0);
    public static Movement dropOffPixelRight = new Movement(5,67,0, dropOffPixelRightPID, smallAngleTestTurn) {
        @Override
        public void doWhileMoving() {}
    };
    public static Movement goToBoardRight = new Movement(xPosForBoard,85,robotTurn, goToBoardRightPID, pidTurn) {
        @Override
        public void doWhileMoving() {
            bot.setSlideLevel(2000);
            bot.wristVertical();
        }
    };

    //strafe over for teammate
    public static PIDCoefficients strafeAvoidPID = new PIDCoefficients(1,0.0075,0,0);
    public static Movement strafeToAvoidTeammate = new Movement(-85, 10,robotTurn, strafeAvoidPID, smallAngleTestTurn) {
        @Override
        public void doWhileMoving() {
            bot.setSlideLevel(1250);
            bot.wristHorizontal();
        }
    };
}
