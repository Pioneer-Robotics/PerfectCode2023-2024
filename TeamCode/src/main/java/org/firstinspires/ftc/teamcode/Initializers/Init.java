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
import org.firstinspires.ftc.teamcode.Hardware.Camera;
import org.firstinspires.ftc.teamcode.Hardware.Collector;
import org.firstinspires.ftc.teamcode.Hardware.GripperServo;
import org.firstinspires.ftc.teamcode.Hardware.MotorData;
import org.firstinspires.ftc.teamcode.Hardware.PixelDropServos;
import org.firstinspires.ftc.teamcode.Hardware.SlideArm;
import org.firstinspires.ftc.teamcode.Helpers.BulkReader;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;
import org.firstinspires.ftc.teamcode.SelfDriving.Pose;
import org.firstinspires.ftc.teamcode.SelfDriving.SelfDriving;
import org.firstinspires.ftc.teamcode.SelfDriving.SimpleDrive;

public abstract class Init {
    public OpScript opmode;
    public BotIMU imu;
    public Commands commands;
    public MotorData motorData;
    public BulkReader bulkReader;
    public Mecanum mecanum;
    public Drivers drivers;
    public PixelDropServos leftDropServo, rightDropServo;
    public GripperServo gripperServo, wristServo, intakeServo;
    public Voltage voltage;
    public SelfDriving selfDriving;
    public Camera camera;
    public SlideArm slideArm;
    public Pose pose;
    public Collector collector;
    public SimpleDrive simpleDrive;

    protected void init(OpScript opMode) {
        opmode              = opMode;
        bulkReader          = new BulkReader(opMode.hardwareMap);
        imu                 = new BotIMU(opMode.hardwareMap.get(IMU.class, "imu"));
        camera              = new Camera(opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName()), opMode.hardwareMap.get(WebcamName.class, "Webcam 1"));
        slideArm            = new SlideArm(opMode.hardwareMap.get(DcMotorEx.class, Config.slideArm));
        collector           = new Collector(opMode.hardwareMap.get(DcMotorEx.class, Config.collectorMotor));
        leftDropServo       = new PixelDropServos(opMode.hardwareMap.get(Servo.class, Config.leftDropServo), Config.leftOpenPos, Config.leftClosedPos);
        rightDropServo      = new PixelDropServos(opMode.hardwareMap.get(Servo.class, Config.rightDropServo), Config.rightOpenPos, Config.rightClosedPos);
        gripperServo        = new GripperServo(opMode.hardwareMap.get(Servo.class, Config.gripperServo), Config.gripperOpen, Config.gripperClosed);
        intakeServo         = new GripperServo(opMode.hardwareMap.get(Servo.class, Config.intakeServoForCollector), Config.intakeUp, Config.intakeDown);
        wristServo        = new GripperServo(opMode.hardwareMap.get(Servo.class, Config.wristServo), Config.WristVertical, Config.WristHorizontal);
        commands            = new Commands();
        motorData           = new MotorData(opMode.hardwareMap.get(DcMotorEx.class, Config.motorLF),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorRF),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorLB),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorRB));
        pose                = new Pose(0,0,0,
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorLB),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorLF),
                                            opMode.hardwareMap.get(DcMotorEx.class, Config.motorRF));
        simpleDrive         = new SimpleDrive(1, 0, 0.125, 0);
        mecanum             = new Mecanum();
        drivers             = new Drivers();
        voltage             = new Voltage();
        selfDriving         = new SelfDriving(pose);
    }
}