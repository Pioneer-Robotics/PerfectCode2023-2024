package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Initializers.AbstractBot;
import org.firstinspires.ftc.teamcode.OpModes.OpScript;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.AutoConfig;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.Movement;

/**
 * Singleton class that contains all of the methods we are using
 */
public class Bot extends AbstractBot {
    private Bot(OpScript op){super.init(op);}//inits bot that creates our OpMode
    public static Bot getInstance(OpScript op) {return new Bot(op);}//returns a new instance of itself
    public static Bot getInstance() {return OpScript.bot;}//gets itself
    public boolean isAuto() {return opmode.getClass().isAnnotationPresent(Autonomous.class);}//used to see if we are in auto or not
    public boolean isRed(){return opmode.getClass().getName().contains("Red");}

    //Motors
    public void setVelocity(double LFspeed, double LBspeed, double RFspeed, double RBspeed) {driveMotors.setVel(LFspeed, LBspeed, RFspeed, RBspeed);}
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
    public void pShift(Counter counter){commands.pShift(opmode.gamepad1, counter);}

    //Odometers
    public DcMotorEx[] getRawOdos(){return pose.getRawOdoValues();}
    public void resetOdometers(){driveMotors.resetOdometers();}
    public double getX(){return pose.getX();}
    public double getY(){return pose.getY();}
    public void resetY(){pose.resetY();}
    public double getXX(){return pose.getXX();}
    public double getYY(){return pose.getYY();}
    public void updateOdos(){pose.updateOdos();}
    public double subY(Movement movement){return selfDriving.subY(movement);}
    public double subX(Movement movement){return selfDriving.subX(movement);}
    public double getPID(double error, double target, double speed){return AutoConfig.dropOffPixelMiddle.getCoefficients().PID(error, target, speed);}

    //Angle
    public double angleDEG(){return imu.getDegrees();}
    public double angleRAD(){return imu.getRadians();}
    public double angleDEGN(){return -imu.getDegrees();}
    public double angleRADN(){return -imu.getRadians();}
    public void resetIMU() {imu.resetYaw();}

    //RedAuto
    public void runAuto() {commands.boardSideAuto();}
    public boolean isRunning(){return !getInstance().isAuto() || opmode.runAuto;}
    public void openCamera(){cameraHandler.openCamera();}
    public int locationCamera(){return cameraHandler.locationCamera();}
    public int locationCamera2(){return cameraHandler.doTheMath();}
    public int getTeamMarkerLocation(){return opmode.location;}
    public void closeCam(){cameraHandler.closeCam();}
    public String getSaturationHigh(){return cameraHandler.getSaturationHigh();}
    public void startMove(double distance){simpleDrive.moveForward(distance);}
    public void startTurn(double degree){simpleDrive.moveTurn(degree);}
    public void startStrafe(double distance){simpleDrive.moveStrafe(distance);}
    public void startStrafeNegative(double distance){simpleDrive.moveStrafeNegative(distance);}
    public void drive(Movement movement){selfDriving.drive(movement);}

    //Features
    public double getVoltage(){return voltageHandler.getVoltage(opmode.hardwareMap.voltageSensor);}
    public void setHangSpeed(double speed){hangingMotor.setSpeed(speed);}

    //TeleOp
    public void teleOp(){drivers.teleOp(opmode.gamepad1, opmode.gamepad2);}
    public void example(){drivers.example();}
    public String getWelcomeText(){return drivers.getWelcomeText();}

    //Driving movements
    public void autoFieldCentricDriving(double x, double y, double rx){mecanum.autoFieldCentricDriving(x, y, rx);}
    public void regularMecanum(Counter counter) {mecanum.mecanumDrive(opmode.gamepad1, counter);}
    public void teleOpFieldCentric(Counter counter){mecanum.teleOpFieldCentricMecanum(opmode.gamepad1, counter);}

    //Servos
    public void leftDropUp(){leftDropServo.servoOpen();}
    public void leftDropDown(){leftDropServo.servoClosed();}
    public void rightDropUp(){rightDropServo.servoOpen();}
    public void rightDropDown(){rightDropServo.servoClosed();}
    public void gripperClosed(){gripperServo.servoOpen();}
    public void gripperOpen(){gripperServo.servoClosed();}
    public void wristVertical(){wristServo.servoOpen();}
    public void wristHorizontal(){wristServo.servoClosed();}
    public void setWrist(double pos){wristServo.setServo(pos);}
    public void intakeCounter(double pos){intakeServo.servoCounter(pos);}
    public void intakeUp(){intakeServo.servoOpen();}
    public void intakeDown(){intakeServo.servoClosed();}
    public void launchAirplane(){airplaneLauncher.servoOpen();}
    public void holdAirplane(){airplaneLauncher.servoClosed();}

    //SelfDriving
//    public void drive(Movement movement){selfDriving.drive(movement);}

    //Slide Arm
    public void setSlideLevel(int level){slideArmMotor.setLevel(level);}
    public void slideVelocity(double speed){slideArmMotor.setVelocity(speed);}
    public void resetSlideVelocity(double speed){slideArmMotor.setDefaultVelocity(speed);}
    public double getSlideLevel() {
        return slideArmMotor.getSlideLevel();
    }

    public double getSlideLevelTarg() {
        return slideArmMotor.getTarg();
    }

    //Collector Arm
    public void moveCollectorBack(){collector.moveCollectorBack();}
    public void moveCollector(){collector.moveCollector();}
    public void stopCollector(){collector.stopCollector();}
    public void setCollectorVelocitySpeed(double speed){collector.setVelocity(speed);}

    //timer
    public void timerSleep(long seconds){timer.sleep(seconds);}

    //Telemetry and Bulkreader
    public void update(){opmode.update();}
    public void addLine(){opmode.telemetry.addLine();}
    public void addLine(String label){opmode.telemetry.addLine(label);}
    public void addData(String label, Object data){opmode.telemetry.addData(label, data);}
    public void telemetryUpdate(){opmode.telemetry.update();}
    public void clearCache(){bulkReader.clearCache();}
}