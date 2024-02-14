package org.firstinspires.ftc.teamcode;

/**
 * Contains all of our strings for servos and motors and any reusable constants
 */
public class Config {
    //Motor names
    public static final String motorLF = "LF";// old Right Odometer
    public static final String motorLB = "LB";// old Left Odometer
    public static final String motorRF = "RF";// old Middle Odometer
    public static final String motorRB = "RB";
    public static final String odoLeft = "OdoLeft";
    public static final String odoRight = "collector";
    public static final String odoMiddle = "hang";
    public static final String slideArm = "slideArm";
    public static final String collectorMotor = "collector";
    public static final String hangingMotor = "hang";

    //LED
    public static final String LED = "led";

    //Odometers
    public static final int odoTicksPerRotation = 2000;
    public static final double odoDiameterIn = 1.811;
    public static final double odoDiameterCM = odoDiameterIn * 2.54;
    public static final double odoRadiusCM = 2.54;
    public static final double odoCircumferenceCM = odoDiameterCM * Math.PI;
    public static final double odoTicksToCm = (odoCircumferenceCM / odoTicksPerRotation);
    public static final double goBuildaOdoDiameter = 4.8;
    public static final double goBuildaOdoCircumferenceCM = goBuildaOdoDiameter * Math.PI;
    public static final double goBuildaOdoTicksToCm = goBuildaOdoCircumferenceCM / 2000;

    //ODO CALCULATIONS CONSTANTS
    public static final double yOdoDistanceFromCenterCM = 13.25; //distance in cm from center of robot to odometers on left/right side
    public static final double xOdoDistanceFromCenterCM = 7.45; //distance in cm from center of robot to odometers on back side
    public static final double yOdoAngleFromCenterRAD = Math.atan(13/8.5);
    public static final double xOdoAngleFromCenterRAD = Math.atan((double) 1 /15);
    public static final double newyOdoDistanceFromCenterCM = Math.sqrt( (13*13) + (9*9) );
    public static final double newxOdoDistanceFromCenterCM = Math.sqrt( (1) + (15.5*15.5) );
    public static final double xRotationOdosInTicksDiv20pi = 120593 / (20 * Math.PI);
    public static final double leftRotationOdosInTicksDiv20pi = 119563 / (20 * Math.PI);
    public static final double rightRotationOdosInTicksDiv20pi = 102635 / (20 * Math.PI);
    public static final double x15FullRotationOdosInTicksDiv30pi = 176760 / (30 * Math.PI);
    public static final double left15FullRotationOdosInTicksDiv30pi = 174117 / (30 * Math.PI);
    public static final double right15FullRotationOdosInTicksDiv30pi = 159455 / (30 * Math.PI);
    public static final double x20FullRotationOdosInTicksDiv40pi = 238278 / (40 * Math.PI);
    public static final double left20FullRotationOdosInTicksDiv40pi = 238269 / (40 * Math.PI);
    public static final double right20FullRotationOdosInTicksDiv40pi = 206129 / (40 * Math.PI);

    //Motor encoder
    public static final double encoderRatio = 2800d;
    public static final double triggerDeadzone = 0.074;

    //Pixel Servos
    public static final String leftDropServo = "PixelDropLeft";
    public static final String rightDropServo = "PixelDropRight";
    public static final double leftOpenPos = 1;
    public static final double rightOpenPos = 1;
    public static final double leftClosedPos = 0;
    public static final double rightClosedPos = 0;

    //Gripper Servos
    public static final String gripperServo = "gripperServo";
    public static final String wristServo = "wristServo";
    public static final String intakeServoForCollector = "intakeServo";
    public static final String airplaneLauncher = "airplaneLauncher";
    public static final String hangServo = "hangServo";
    public static final double hangReady = 0.05;
    public static final double hangLaunch = 0.45;
    public static final double airplaneReady = .175;
    public static final double airplaneLaunch = 1;
    public static final double intakeUp = 0.02;
    public static final double intakeDown = .4;
    public static final double gripperOpen = .2;
    public static final double gripperClosed = .5;
    public static final double WristVertical = .885;
    public static final double WristHorizontal = .6;
    public static final double WristCloseDoor = .15;

    //Auto Arm Positions
    public static final int secondLinePos = -2300;
    public static final int firstLinePos = -1350;
    public static final int lowPosTele = -1800;
    public static final int highPosTele = -2900;
    public static final int endgameHeight = -2000;
    //

    //Pixel Stack Intake Servo Positions
    public static final double pixelStackIncrementPos = 0.04;
    public static final double secondPixelPos = intakeUp + pixelStackIncrementPos;
    public static final double thirdPixelPos = secondPixelPos + pixelStackIncrementPos;
    public static final double fourthPixelPos = thirdPixelPos + pixelStackIncrementPos;
    public static final double fifthPixelPos = fourthPixelPos + 0.02;
    public static final double[] pixelStackServoPosArr = {intakeDown, Config.secondPixelPos, Config.thirdPixelPos, Config.fourthPixelPos, Config.fifthPixelPos};
}
