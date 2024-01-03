package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

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
    public ElapsedTime autoTimer;

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
        autoTimer = new ElapsedTime();
        autoTimer.reset();
        if(bot.isAuto()) {bot.openCamera();}
        while (!opScript.opModeIsActive() && !opScript.isStarted()  && !opScript.isStopRequested()) {opScript.initloop();}
        setForTeleOp();
        while (opScript.opModeIsActive() && opScript.isStarted() && !opScript.isStopRequested()) {
            if(bot.isRunning()) {
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

    public void setForTeleOp(){
        bot.setSlideLevel(0);
    }

    /**
     * Used to loop the cameraHandler in auto and telemetry pre-run
     */
    public void initloop() {
        opScript.runAuto = true;
        bot.addLine(welcomeText);
        if(bot.isAuto()){
            location = bot.locationCamera2();
            bot.addData("CameraHandler", bot.getSaturationHigh());
            bot.addData("test cam", location);
        }
        bot.addData("Angle: ", -bot.angleDEG());
        bot.addData("VoltageHandler", bot.getVoltage());
        bot.update();
    }

    public void update() {
        bot.clearCache();
        opScript.telemetry.update();
    }
}
