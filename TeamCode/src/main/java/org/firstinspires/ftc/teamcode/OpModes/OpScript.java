package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.AutoConfig;
import org.firstinspires.ftc.teamcode.SelfDrivingAuto.Movement;

/**
 * Abstract class which should be the inheritance class for every OpMode
 * Run(): input whatever you want to loop during the opMode
 * initloop(): loops the init (basically for cameraHandler and pre-run)
 */
public abstract class OpScript extends LinearOpMode {
    public static Bot bot;//inits bot
    public boolean runAuto;//if auto or not
    public OpScript opScript;//inits itself
    public static long cycleNumber;//how many cycles ran
    public int location;//team marker location 1-3
    public String welcomeText;//Welcome test for start of match.

    public abstract void run();//method where you put wherever needs to be looped

    /**
     * runs the opMode
     * @param opmode: input the OpScript object to run
     */
    public void runOpMode(OpScript opmode) {
        opScript = opmode;
        bot = Bot.getInstance(opScript);
        AbstractHardwareComponent.init(Bot.getInstance(), telemetry);
        welcomeText = bot.getWelcomeText();
        if(bot.isAuto()) {bot.openCamera();}
        while (!opScript.opModeIsActive() && !opScript.isStarted()  && !opScript.isStopRequested()) {opScript.initloop();}
        while (opScript.opModeIsActive() && opScript.isStarted() && !opScript.isStopRequested()) {
            if(bot.isRunning()) {
                bot.autoLights();
                opScript.run();
            }
            opScript.runAuto = false;
            cycleNumber++;
            bot.closeCam();
            bot.clearCache();
            opScript.telemetry.update();
        }
        opScript.stop();
    }

    /**
     * Used to loop the cameraHandler in auto and telemetry pre-run
     */
    public void initloop() {
        if(bot.getVoltage() > 12) {
            bot.initLights();
        }
        else{
            bot.lightsOn(RevBlinkinLedDriver.BlinkinPattern.STROBE_RED);
            bot.addLine("BATTERY LOW");
        }
        opScript.runAuto = true;
        bot.addLine(welcomeText);
        bot.addLine("Please remember to restart robot prior to running auto. Thanks!");
        if(bot.isAuto()){
//            location = bot.locationCamera2();
//            bot.addData("CameraHandler", bot.getSaturationHigh());
            location = bot.getMarkerLocation();
            bot.addData("CameraHandler", bot.getMarkerLocation());
        }
        bot.addData("Angle: ", -bot.angleDEG());
        bot.addData("VoltageHandler", bot.getVoltage());
        bot.update();
    }

    public void update() {
        if(opScript.opModeIsActive()) {bot.autoLights();}
        bot.clearCache();
        opScript.telemetry.update();
    }
}