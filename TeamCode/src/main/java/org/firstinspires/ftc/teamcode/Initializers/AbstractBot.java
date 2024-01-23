package org.firstinspires.ftc.teamcode.Initializers;

import android.annotation.SuppressLint;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Drivers.Drivers;
import org.firstinspires.ftc.teamcode.Drivers.Mecanum;
import org.firstinspires.ftc.teamcode.Hardware.HangingMotor;
import org.firstinspires.ftc.teamcode.Hardware.LED;
import org.firstinspires.ftc.teamcode.Helpers.Timer;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.AutoConfig;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.Commands;
import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Hardware.VoltageHandler;
import org.firstinspires.ftc.teamcode.Hardware.BotIMU;
import org.firstinspires.ftc.teamcode.Hardware.CameraHandler;
import org.firstinspires.ftc.teamcode.Hardware.Collector;
import org.firstinspires.ftc.teamcode.Hardware.SlideArmMotor;
import org.firstinspires.ftc.teamcode.Hardware.UniServo;
import org.firstinspires.ftc.teamcode.Hardware.DriveMotors;
import org.firstinspires.ftc.teamcode.Helpers.BulkReader;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.Pose;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.SelfDriving;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.SimpleDrive;

/**
 * Abstract class that initializes all of our classes used for the robot
 * Holds method "init" that we use to init everything
 */
public abstract class AbstractBot {
    //All used objects
    public OpScript opmode;//inits a single OpMode
    public BotIMU imu;
    public Commands commands;
    public DriveMotors driveMotors;
    public BulkReader bulkReader;
    public Mecanum mecanum;
    public Drivers drivers;
    public UniServo gripperServo, wristServo, intakeServo, leftDropServo, rightDropServo, airplaneLauncher, hangServo;
    public VoltageHandler voltageHandler;
    public SelfDriving selfDriving;
    public CameraHandler cameraHandler;
    public SlideArmMotor slideArmMotor;
    public Pose pose;
    public Collector collector;
    public SimpleDrive simpleDrive;
    public Timer timer;
    public AutoConfig autoConfig;
    public HangingMotor hangingMotor;
    public LED led;

    /**
     * Method used to create all instantiated objects
     * @param opMode: inputs one OpMode to run
     */
    protected void init(OpScript opMode) {
        opmode              = opMode;
        bulkReader          = new BulkReader(opMode.hardwareMap);
        imu                 = new BotIMU(opMode.hardwareMap.get(IMU.class, "imu"));
        imu.resetYaw();
        cameraHandler       = new CameraHandler(opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                                                opMode.hardwareMap.appContext.getPackageName()),
                                                opMode.hardwareMap.get(WebcamName.class, "Webcam 1"),
                                                opmode.getClass().getName().contains("Red"));
        autoConfig          = new AutoConfig(opmode.getClass().getName().contains("Red"), opmode.getClass().getName().contains("Audience"));
        slideArmMotor       = new SlideArmMotor(opMode.hardwareMap.get(DcMotorEx.class, Config.slideArm));
        collector           = new Collector(opMode.hardwareMap.get(DcMotorEx.class, Config.collectorMotor));
        leftDropServo       = new UniServo(opMode.hardwareMap.get(Servo.class, Config.leftDropServo), Config.leftClosedPos, Config.leftOpenPos);
        rightDropServo      = new UniServo(opMode.hardwareMap.get(Servo.class, Config.rightDropServo), Config.rightClosedPos , Config.rightOpenPos);
        airplaneLauncher    = new UniServo(opMode.hardwareMap.get(Servo.class, Config.airplaneLauncher), Config.airplaneReady, Config.airplaneLaunch);
        gripperServo        = new UniServo(opMode.hardwareMap.get(Servo.class, Config.gripperServo), Config.gripperClosed, Config.gripperOpen);
        intakeServo         = new UniServo(opMode.hardwareMap.get(Servo.class, Config.intakeServoForCollector), Config.intakeUp, Config.intakeDown);
        wristServo          = new UniServo(opMode.hardwareMap.get(Servo.class, Config.wristServo), Config.WristVertical, Config.WristHorizontal);
        hangServo           = new UniServo(opMode.hardwareMap.get(Servo.class, Config.hangServo), Config.hangReady, Config.hangLaunch);
        led                 = new LED(opMode.hardwareMap.get(RevBlinkinLedDriver.class, Config.LED));
        commands            = new Commands();
        driveMotors         = new DriveMotors(opMode.hardwareMap.get(DcMotorEx.class, Config.motorLF),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorRF),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorLB),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorRB));
        pose                = new Pose(0,0,0,
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.odoLeft),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.odoMiddle),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.odoRight));
        simpleDrive         = new SimpleDrive(.6, 0.002, 0, 0);
        hangingMotor        = new HangingMotor(opMode.hardwareMap.get(DcMotorEx.class, Config.hangingMotor));
        mecanum             = new Mecanum();
        drivers             = new Drivers();
        voltageHandler      = new VoltageHandler();
        selfDriving         = new SelfDriving(pose);
        timer               = new Timer();
    }
}