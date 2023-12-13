package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

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
        while (!opScript.opModeIsActive() && !opScript.isStarted()) {opScript.initloop();}
        while (opScript.opModeIsActive() && opScript.isStarted() && bot.isRunning()) {
            opScript.run();
            opScript.runAuto = false;
            cycleNumber++;
            bot.clearCache();
            opScript.telemetry.update();
        }
    }

    /**
     * Used to loop the cameraHandler in auto and telemetry pre-run
     */
    public void initloop() {
        opScript.runAuto = true;
        bot.addLine(welcomeText);
        if(bot.isAuto()){
            bot.openCamera();
            location = bot.locationCamera();
            bot.addData("CameraHandler", bot.getSaturationHigh());
        }
        bot.addData("VoltageHandler", bot.getVoltage());
        bot.update();
    }

    public void update() {
        bot.clearCache();
        opScript.telemetry.update();
    }
}
