package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Initializers.Init;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;

public class Bot extends Init {
    private Bot(OpScript op){super.init(op);}
    public static Bot getInstance(OpScript op) {return new Bot(op);}
    public static Bot getInstance() {return OpScript.bot;}
    public boolean isAuto() {return opmode.getClass().isAnnotationPresent(Autonomous.class);}

    //Motors
    public void setPowers(double LFspeed, double LBspeed, double RFspeed, double RBspeed) {motorData.setPowers(LFspeed, LBspeed, RFspeed, RBspeed);}
    public double getPower(){return motorData.getPower();}
    public void setMotorPower(double power) {motorData.setMotorPower(power);}
    public void setMotorPowerStrafe(double power) {motorData.setMotorPowerStrafe(power);}
    public void setMotorPowerRight(double power) {motorData.setMotorPowerRight(power);}
    public void setMotorPowerLeft(double power) {motorData.setMotorPowerLeft(power);}
    public void setMotorPowerTurn(double power) {motorData.setMotorPower(-power, power);}
    public void setRunMode(DcMotorEx.RunMode mode) {motorData.setRunMode(mode);}
    public void setPowerBehavior(DcMotorEx.ZeroPowerBehavior Lbehavior, DcMotorEx.ZeroPowerBehavior Rbehavior) {motorData.setPowerBehavior(Lbehavior, Rbehavior);}
    public void brake() {motorData.brake();}
    public void gearShift(Counter counter) {commands.gearShift(opmode.gamepad1, counter);}

    //Odometers
    public DcMotorEx[] getRawOdos(){return pose.getRawOdoValues();}
    public void resetOdometers(){motorData.resetOdometers();}
    public double getX(){return pose.getX();}
    public double getY(){return pose.getY();}
    public void resetY(){pose.resetY();}
    public double getXX(){return pose.getXX();}
    public double getYY(){return pose.getYY();}
    public void updateOdos(){pose.updateOdo();}


    //Angle
    public double angleDEG(){return -imu.getDegrees();}
    public double angleRAD(){return -imu.getRadians();}
    public void resetIMU() {imu.resetYaw();}

    //Auto
    public boolean isRunning(){return !getInstance().isAuto() || opmode.runAuto;}
    public void openCamera(){camera.openCamera();}
    public String getSaturationHigh(){return camera.getSaturationHigh();}
    public void startMove(double distance){simpleDrive.moveForward(distance);}
    public void startTurn(double degree){simpleDrive.moveTurn(degree);}
    public void startStrafe(double distance){simpleDrive.moveStrafe(distance);}

    //Features
    public double getVoltage(){return voltage.getVoltage(opmode.hardwareMap.voltageSensor);}

    //TeleOp
    public void TeleOp(){drivers.TeleOp(opmode.gamepad1, opmode.gamepad2);}
    public void example(){drivers.example();}

    //Driving movements
    public void coordinateLock(Counter counter) {mecanum.coordinateLockMecanum(opmode.gamepad1, counter);}
    public void regularMecanum(Counter counter) {mecanum.mecanumDrive(opmode.gamepad1, counter);}

    //Servos
    public void leftDropUp(){leftDropServo.servoUp();}
    public void leftDropDown(){leftDropServo.servoDown();}
    public void rightDropUp(){rightDropServo.servoUp();}
    public void rightDropDown(){rightDropServo.servoDown();}
    public void gripperOpen(){gripperServo.servoOpen();}
    public void gripperClosed(){gripperServo.servoClosed();}
    public void elevateOpen(){wristServo.servoOpen();}
    public void elevateClosed(){wristServo.servoClosed();}
    public void intakeUp(){intakeServo.servoOpen();}
    public void intakeDown(){intakeServo.servoClosed();}

    //SelfDriving
//    public void drive(Movement movement){selfDriving.drive(movement);}

    //Slide Arm
    public void setSlideLevel(int level){slideArm.setLevel(level);}
    public void slideVelocity(double speed){slideArm.setVelocity(speed);}
    public void resetSlideVelocity(double speed){slideArm.resetVelocity(speed);}
    public double getSlideLevel(){return slideArm.getSlideLevel();}

    //Collector Arm
    public void moveCollector(){collector.moveCollector();}
    public void stopCollector(){collector.stopCollector();}
    public void setCollectorVelocitySpeed(double speed){collector.setVelocitySpeed(speed);}

    //Telemetry and Bulkreader
    public void update(){opmode.update();}
    public void addLine(){opmode.telemetry.addLine();}
    public void addLine(String label){opmode.telemetry.addLine(label);}
    public void addData(String label, Object data){opmode.telemetry.addData(label, data);}
    public void telemetryUpdate(){opmode.telemetry.update();}
    public void clearCache(){bulkReader.clearCache();}
}