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
    public void setPowers(double LFspeed, double LBspeed, double RFspeed, double RBspeed) {driveMotors.setPowers(LFspeed, LBspeed, RFspeed, RBspeed);}
    public double getPower(){return driveMotors.getPower();}
    public void setMotorPower(double power) {driveMotors.setMotorPower(power);}
    public void setMotorPowerStrafe(double power) {driveMotors.setMotorPowerStrafe(power);}
    public void setMotorPowerRight(double power) {driveMotors.setMotorPowerRight(power);}
    public void setMotorPowerLeft(double power) {driveMotors.setMotorPowerLeft(power);}
    public void setMotorPowerTurn(double power) {driveMotors.setMotorPower(-power, power);}
    public void setRunMode(DcMotorEx.RunMode mode) {driveMotors.setRunMode(mode);}
    public void setPowerBehavior(DcMotorEx.ZeroPowerBehavior Lbehavior, DcMotorEx.ZeroPowerBehavior Rbehavior) {driveMotors.setPowerBehavior(Lbehavior, Rbehavior);}
    public void brake() {driveMotors.brake();}
    public void gearShift(Counter counter) {commands.gearShift(opmode.gamepad1, counter);}

    //Odometers
    public DcMotorEx[] getRawOdos(){return pose.getRawOdoValues();}
    public void resetOdometers(){
        driveMotors.resetOdometers();}
    public double getX(){return pose.getX();}
    public double getY(){return pose.getY();}
    public void resetY(){pose.resetY();}
    public double getXX(){return pose.getXX();}
    public double getYY(){return pose.getYY();}
    public void updateOdos(){pose.updateOdo();}
    public void gruberOdos(){pose.gruberOdoCalculations();}
    public void newOdoCalc(){pose.newOdoCalc();}


    //Angle
    public double angleDEG(){return imu.getDegrees();}
    public double angleRAD(){return imu.getRadians();}
    public double angleDEGN(){return -imu.getDegrees();}
    public double angleRADN(){return -imu.getRadians();}
    public void resetIMU() {imu.resetYaw();}

    //RedAuto
    public void runAuto() {commands.boardSide();}
    public void runAuto2() {commands.audienceSide();}
    public boolean isRunning(){return !getInstance().isAuto() || opmode.runAuto;}
    public void openCamera(){cameraHandler.openCamera();}
    public int locationCamera(){return cameraHandler.locationCamera();}
    public String getSaturationHigh(){return cameraHandler.getSaturationHigh();}
    public void startMove(double distance){simpleDrive.moveForward(distance);}
    public void startTurn(double degree){simpleDrive.moveTurn(degree);}
    public void startStrafe(double distance){simpleDrive.moveStrafe(distance);}
    public void startStrafeNegative(double distance){simpleDrive.moveStrafe(distance);}

    //Features
    public double getVoltage(){return voltage.getVoltage(opmode.hardwareMap.voltageSensor);}

    //TeleOp
    public void TeleOp(){drivers.TeleOp(opmode.gamepad1, opmode.gamepad2);}
    public void example(){drivers.example();}

    //Driving movements
    public void coordinateLock(Counter counter) {mecanum.coordinateLockMecanum(opmode.gamepad1, counter);}
    public void regularMecanum(Counter counter) {mecanum.mecanumDrive(opmode.gamepad1, counter);}

    //Servos
    public void leftDropUp(){leftDropServo.servoOpen();}
    public void leftDropDown(){leftDropServo.servoClosed();}
    public void rightDropUp(){rightDropServo.servoOpen();}
    public void rightDropDown(){rightDropServo.servoClosed();}
    public void gripperOpen(){gripperServo.servoOpen();}
    public void gripperClosed(){gripperServo.servoClosed();}
    public void wristVertical(){wristServo.servoOpen();}
    public void wristHorizontal(){wristServo.servoClosed();}
    public void intakeUp(){intakeServo.servoOpen();}
    public void intakeDown(){intakeServo.servoClosed();}

    //SelfDriving
//    public void drive(Movement movement){selfDriving.drive(movement);}

    //Slide Arm
    public void setSlideLevel(int level){slideArmMotor.setLevel(level);}
    public void slideVelocity(double speed){slideArmMotor.setVelocity(speed);}
    public void resetSlideVelocity(double speed){slideArmMotor.setDefaultVelocity(speed);}
    public double getSlideLevel(){return slideArmMotor.getSlideLevel();}

    //Collector Arm
    public void moveCollectorBack(){collector.moveCollectorBack();}
    public void moveCollector(){collector.moveCollector();}
    public void stopCollector(){collector.stopCollector();}
    public void setCollectorVelocitySpeed(double speed){collector.setVelocity(speed);}

    //Telemetry and Bulkreader
    public void update(){opmode.update();}
    public void addLine(){opmode.telemetry.addLine();}
    public void addLine(String label){opmode.telemetry.addLine(label);}
    public void addData(String label, Object data){opmode.telemetry.addData(label, data);}
    public void telemetryUpdate(){opmode.telemetry.update();}
    public void clearCache(){bulkReader.clearCache();}
}