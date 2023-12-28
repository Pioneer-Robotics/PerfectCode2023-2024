package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.SelfDrivingAuto.Movement;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.PIDCoefficients;

/**
 * Contains all of our strings for servos and motors and any reusable constants
 */
public class Config {
    //Motor names
    public static final String motorLF = "LF";//Right Odometer
    public static final String motorLB = "LB";//Left Odometer
    public static final String motorRF = "RF";//Middle Odometer
    public static final String motorRB = "RB";
    public static final String odoLeft = "OdoLeft";
    public static final String odoRight = "OdoRight";
    public static final String odoMiddle = "OdoMiddle";
    public static final String[] odos = {"OdoLeft", "OdoRight", "OdoMiddle"};
    public static final String slideArm = "slideArm";
    public static final String collectorMotor = "collector";

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
    public static final double odoXOffset = 16.65; //distance in cm from center of robot to odometers on left/right side
    public static final double odoYOffset = 12.4; //distance in cm from center of robot to odometers on back side

    //ODO CALCULATIONS CONSTANTS
    public static final double yOdoDistanceFromCenterCM = 13.25; //distance in cm from center of robot to odometers on left/right side
    public static final double xOdoDistanceFromCenterCM = 7.45; //distance in cm from center of robot to odometers on back side
    public static final double yOdoAngleFromCenterRAD = Math.atan(13/8.5);
    public static final double xOdoAngleFromCenterRAD = Math.atan(1/15);
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

    //Speeds
    public static final double speed = 0.5;
//FTCGP-21
    //PID
    public static PIDCoefficients drive = new PIDCoefficients(0.5,0,0, 0);
    public static PIDCoefficients drive2 = new PIDCoefficients(3,0,0, 0);
    public static PIDCoefficients turn = new PIDCoefficients(1,0,0,0);
    public static Movement movement = new Movement(0,20,0, drive) {
        @Override
        public void runExtra() {}
    };

    //Pixel Servos
    public static final String leftDropServo = "PixelDropLeft";
    public static final String rightDropServo = "PixelDropRight";
    public static final double leftOpenPos = 0.6;
    public static final double rightOpenPos = .6;
    public static final double leftClosedPos = 0;
    public static final double rightClosedPos = 0.1;

    //Gripper Servos
    public static final String gripperServo = "gripperServo";
    public static final String wristServo = "elevateServo";
    public static final String intakeServoForCollector = "intakeServo";
    public static final String airplaneLauncher = "airplaneLauncher";
    public static final double airplaneReady = .25;
    public static final double airplaneLaunch = 1;
    public static final double intakeUp = 0.02;
    public static final double intakeDown = .4;
    public static final double gripperOpen = 0.65;
    public static final double gripperClosed = .83;
    public static final double WristVertical = .7;
    public static final double WristHorizontal = .45;
}
