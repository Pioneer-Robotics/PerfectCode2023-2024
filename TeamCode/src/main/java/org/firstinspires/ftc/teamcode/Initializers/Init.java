package org.firstinspires.ftc.teamcode.Initializers;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Drivers.Drivers;
import org.firstinspires.ftc.teamcode.Drivers.Mecanum;
import org.firstinspires.ftc.teamcode.Features.Commands;
import org.firstinspires.ftc.teamcode.Features.Config;
import org.firstinspires.ftc.teamcode.Features.Voltage;
import org.firstinspires.ftc.teamcode.Hardware.MotorData;
import org.firstinspires.ftc.teamcode.Hardware.PixelDropServo;
import org.firstinspires.ftc.teamcode.Hardware.SlideArm;
import org.firstinspires.ftc.teamcode.Helpers.BulkReader;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;
import org.firstinspires.ftc.teamcode.SelfDriving.SelfDriving;

public abstract class Init {
    protected static OpScript opmode;
    public static Telemetry telemetry;
    public static long cycleNumber = 0;
    public static boolean runAuto;
    public static BulkReader bulkReader;
    public static IMU imu;
    public static Commands commands;
    public static MotorData motorData;
    public static Mecanum mecanum;
    public static Drivers drivers;
    public static PixelDropServo pixelDropServo;
    public static Voltage voltage;
    public static SelfDriving selfDriving;
    public static SlideArm slideArm;

    protected void init(OpScript opMode) {
        opmode              = opMode;
        telemetry           = opMode.telemetry;
        opMode.bulkReader   = new BulkReader(opMode.hardwareMap);
        bulkReader          = opMode.bulkReader;
        runAuto             = opMode.runAuto;
        imu = opMode.hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT)));
        imu.resetYaw();
        Motors.setMotors(
                opMode.hardwareMap.get(DcMotorEx.class, Config.motorLF),
                opMode.hardwareMap.get(DcMotorEx.class, Config.motorRF),
                opMode.hardwareMap.get(DcMotorEx.class, Config.motorLB),
                opMode.hardwareMap.get(DcMotorEx.class, Config.motorRB),
                opMode.hardwareMap.get(DcMotorEx.class, Config.odoLeft),
                opMode.hardwareMap.get(DcMotorEx.class, Config.odoRight),
                opMode.hardwareMap.get(DcMotorEx.class, Config.odoMiddle)
        );
        slideArm            = new SlideArm(opMode.hardwareMap.get(DcMotorEx.class, Config.slideArm));
        pixelDropServo      = new PixelDropServo(opMode.hardwareMap.get(Servo.class, Config.pixelDropServo));
        commands            = new Commands();
        motorData           = new MotorData();
        mecanum             = new Mecanum();
        drivers             = new Drivers();
        voltage             = new Voltage();
        selfDriving         = new SelfDriving();
    }

    public static class Motors {
        public static DcMotorEx leftFront;
        public static DcMotorEx rightFront;
        public static DcMotorEx leftBack;
        public static DcMotorEx rightBack;
        public static DcMotorEx[] encoders = new DcMotorEx[3];

        public static void setMotors(DcMotorEx lFront, DcMotorEx rFront, DcMotorEx lBack, DcMotorEx rBack, DcMotorEx odoLeft, DcMotorEx odoRight, DcMotorEx odoMiddle) {
            leftFront = lFront;
            rightFront = rFront;
            leftBack = lBack;
            rightBack = rBack;
            Motors.encoders[0] = odoLeft;
            Motors.encoders[1] = odoRight;
            Motors.encoders[2] = odoMiddle;
        }
    }
}