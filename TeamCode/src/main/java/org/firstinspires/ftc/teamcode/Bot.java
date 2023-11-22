package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Features.Voltage;
import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Initializers.Init;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;
import org.firstinspires.ftc.teamcode.SelfDriving.Movement;

public class Bot extends Init {
    private Bot(OpScript op){super.init(op);}
    public static Bot getInstance(OpScript op) {return new Bot(op);}
    public static Bot getInstance() {return opmode.bot;}
    public boolean opmodeIsActive() {return opmode.opModeIsActive();}
    public boolean isAuto() {return opmode.getClass().isAnnotationPresent(Autonomous.class);}

    //Motors
    public void setVels(double LFspeed, double LBspeed, double RFspeed, double RBspeed) {motorData.setVel(LFspeed, LBspeed, RFspeed, RBspeed);}
    public void setPowers(double LFspeed, double LBspeed, double RFspeed, double RBspeed) {motorData.setPowers(LFspeed, LBspeed, RFspeed, RBspeed);}
    public void setMotorPower(double power) {motorData.setMotorPower(power);}
    public void setMotorPowerRight(double power) {motorData.setMotorPowerRight(power);}
    public void setMotorPowerLeft(double power) {motorData.setMotorPowerLeft(power);}
    public void setMotorPowerTurn(double power) {motorData.setMotorPower(-power, power);}
    public void setRunMode(DcMotorEx.RunMode mode) {motorData.setRunMode(mode);}
    public void setPowerBehavior(DcMotorEx.ZeroPowerBehavior Lbehavior, DcMotorEx.ZeroPowerBehavior Rbehavior) {motorData.setPowerBehavior(Lbehavior, Rbehavior);}
    public void brake() {motorData.brake();}
    public void gearShift(Counter counter) {commands.gearShift(opmode.gamepad1, counter);}

    //Odometers
    public void resetOdometers(){motorData.resetOdometers();}
    public int[] getOdos(){return motorData.odoPos();}
    public DcMotorEx[] encoders(){return motorData.encoders();}
    //Angle
    public double angleDEG(){return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);}
    public double angleRAD(){return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);}
    public void resetIMU() {imu.resetYaw();}

    //Auto
    public boolean isRunning(){return !getInstance().isAuto() || Bot.runAuto;}

    //Features
    public double getVoltage(){return Voltage.getVoltage(opmode.hardwareMap.voltageSensor);}

    //TeleOp
    public void Driver1() {drivers.Driver1(opmode.gamepad1);}
    public void Driver2() {drivers.Driver2(opmode.gamepad2);}
    public void Telemetry() {drivers.telemetry();}
    public void example(){drivers.example();}

    //Driving movements
    public void coordinateLock(Counter counter) {mecanum.coordinateLockMecanum(opmode.gamepad1, counter);}
    public void regularMecanum(Counter counter) {mecanum.mecanumDrive(opmode.gamepad1, counter);}

    //Servos
    public void pixelDropOpen(){pixelDropServo.servoOpen();}
    public void pixelDropClosed(){pixelDropServo.servoClosed();}

    //SelfDriving
    public void drive(Movement movement){selfDriving.drive(movement);}
    public double[] updateOdometry(){return selfDriving.updateOdometry();}

    //Slide Arm
    public void setSlideLevel(int level){slideArm.setLevel(level);}
    public void slideVelocity(double speed){slideArm.setVelocity(speed);}
    public void resetSlideVelocity(double speed){slideArm.resetVelocity(speed);}
    public double getSlideLevel(){return slideArm.getSlideLevel();}
}
