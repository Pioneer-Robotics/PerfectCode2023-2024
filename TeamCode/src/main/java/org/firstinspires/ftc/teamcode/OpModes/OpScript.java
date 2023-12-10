package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

/**
 * Abstract class which should be the inheritance class for every OpMode
 * Run(): input whatever you want to loop during the opMode
 * initloop(): loops the init (basically for camera and pre-run)
 */
public abstract class OpScript extends LinearOpMode {
    public static Bot bot;
    public boolean runAuto;
    public OpScript opScript;
    public static long cycleNumber;
    public int location;

    public abstract void run();//method where you put wherever needs to be looped

    /**
     * runs the opMode
     * @param opmode: input the OpScript object to run
     */
    public void runOpMode(OpScript opmode) {
        opScript = opmode;
        bot = Bot.getInstance(opScript);
        AbstractHardwareComponent.init(Bot.getInstance(), telemetry);
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
     * Used to loop the camera in auto and telemetry pre-run
     */
    public void initloop() {
        opScript.runAuto = true;
        if(bot.isAuto()){
            bot.openCamera();
            location = bot.locationCamera();
            bot.addData("Camera", bot.getSaturationHigh());
        }
        bot.addData("Voltage", bot.getVoltage());
        bot.update();
    }

    public void update() {
        bot.clearCache();
        opScript.telemetry.update();
    }
}
