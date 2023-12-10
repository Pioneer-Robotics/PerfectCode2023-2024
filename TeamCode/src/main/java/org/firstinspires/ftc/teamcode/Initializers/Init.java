package org.firstinspires.ftc.teamcode.Initializers;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Drivers.Drivers;
import org.firstinspires.ftc.teamcode.Drivers.Mecanum;
import org.firstinspires.ftc.teamcode.Features.Commands;
import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.Features.Voltage;
import org.firstinspires.ftc.teamcode.Hardware.BotIMU;
import org.firstinspires.ftc.teamcode.Hardware.CameraHandler;
import org.firstinspires.ftc.teamcode.Hardware.Collector;
import org.firstinspires.ftc.teamcode.Hardware.SlideArmMotor;
import org.firstinspires.ftc.teamcode.Hardware.UniServo;
import org.firstinspires.ftc.teamcode.Hardware.DriveMotors;
import org.firstinspires.ftc.teamcode.Helpers.BulkReader;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;
import org.firstinspires.ftc.teamcode.SelfDriving.Pose;
import org.firstinspires.ftc.teamcode.SelfDriving.SelfDriving;
import org.firstinspires.ftc.teamcode.SelfDriving.SimpleDrive;

/**
 * Abstract class that initializes all of our classes used for the robot
 * Holds method "init" that we use to init everything
 */
public abstract class Init {
    //All used objects
    public OpScript opmode;//inits a single OpMode
    public BotIMU imu;
    public Commands commands;
    public DriveMotors driveMotors;
    public BulkReader bulkReader;
    public Mecanum mecanum;
    public Drivers drivers;
    public UniServo gripperServo, wristServo, intakeServo, leftDropServo, rightDropServo;
    public Voltage voltage;
    public SelfDriving selfDriving;
    public CameraHandler cameraHandler;
    public SlideArmMotor slideArmMotor;
    public Pose pose;
    public Collector collector;
    public SimpleDrive simpleDrive;

    /**
     * Method used to create all instantiated objects
     * @param opMode: inputs one OpMode to run
     */
    protected void init(OpScript opMode) {
        opmode              = opMode;
        bulkReader          = new BulkReader(opMode.hardwareMap);
        imu                 = new BotIMU(opMode.hardwareMap.get(IMU.class, "imu"));
        imu.resetYaw();
        cameraHandler = new CameraHandler(opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName()), opMode.hardwareMap.get(WebcamName.class, "Webcam 1"));
        slideArmMotor = new SlideArmMotor(opMode.hardwareMap.get(DcMotorEx.class, Config.slideArm));
        collector           = new Collector(opMode.hardwareMap.get(DcMotorEx.class, Config.collectorMotor));
        leftDropServo       = new UniServo(opMode.hardwareMap.get(Servo.class, Config.leftDropServo), Config.leftClosedPos, Config.leftOpenPos);
        rightDropServo      = new UniServo(opMode.hardwareMap.get(Servo.class, Config.rightDropServo),Config.rightClosedPos , Config.rightOpenPos);
        gripperServo        = new UniServo(opMode.hardwareMap.get(Servo.class, Config.gripperServo), Config.gripperOpen, Config.gripperClosed);
        intakeServo         = new UniServo(opMode.hardwareMap.get(Servo.class, Config.intakeServoForCollector), Config.intakeUp, Config.intakeDown);
        wristServo          = new UniServo(opMode.hardwareMap.get(Servo.class, Config.wristServo), Config.WristVertical, Config.WristHorizontal);
        commands            = new Commands();
        driveMotors = new DriveMotors(opMode.hardwareMap.get(DcMotorEx.class, Config.motorLF),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorRF),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorLB),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorRB));
        pose                = new Pose(0,0,0,
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorLB),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorLF),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorRF));
        simpleDrive         = new SimpleDrive(.6, 0.002, 0, 0);
        mecanum             = new Mecanum();
        drivers             = new Drivers();
        voltage             = new Voltage();
        selfDriving         = new SelfDriving(pose);
    }
}