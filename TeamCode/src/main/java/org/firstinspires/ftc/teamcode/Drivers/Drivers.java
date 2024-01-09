package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Annotations.DriverAnnotations;
import org.firstinspires.ftc.teamcode.Config;
import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

import java.lang.reflect.Method;

/**
 * / This class will hold three methods that will run driver 1, 2, and the telemetry.
 * It uses Toggle and Counter
 */
public class Drivers extends AbstractHardwareComponent {
    //Toggles
    private final Toggle mecanumToggle = new Toggle(true);
    private final Toggle collectorToggle = new Toggle(false);
    private final Toggle grabberToggle = new Toggle(true);
    private final Toggle intakeToggle = new Toggle(true);
    private final Toggle airplaneLaunch = new Toggle(false);

    //Counter
    private final Counter scalePower = new Counter(.3, .2, 0.8);
    private final Counter intakePos = new Counter(0, 0, 4);

    @DriverAnnotations.Driver1(name = "Seth")
    public void driver1(Gamepad gamepad) {
        mecanumToggle.toggle(gamepad.y);//toggles between regular and coordinate lock mecanum

        if (mecanumToggle.getBool()) {
            bot.teleOpFieldCentric(scalePower);
        } else {
            bot.regularMecanum(scalePower);
        }

        if (gamepad.x) {//resets IMU/Odometry just in case robot is not facing directly north
            bot.resetIMU();
            //bot.resetOdometers();
        }

        if(gamepad.left_stick_button){
            collectorToggle.set(false);
        }

        airplaneLaunch.toggle(gamepad.a);
        if (airplaneLaunch.getBool()) {
            bot.launchAirplane();
        } else {
            bot.holdAirplane();
        }
    }

    @DriverAnnotations.Driver2(name = "Henry")
    public void driver2(Gamepad gamepad) {
        intakePos.arithmetic(gamepad.dpad_right, gamepad.dpad_left);

        if(gamepad.right_trigger > 0.074) {
            bot.setHangSpeed(gamepad.right_trigger);
        }else{
            bot.setHangSpeed(0);
        }


        //ensures pixel drop servos are up
        bot.rightDropUp();
        bot.leftDropUp();
        grabberToggle.toggle(gamepad.left_bumper);
        if(bot.getSlideLevelTarg() == 0){
            grabberToggle.set(false);
        }

        if (grabberToggle.getBool() && (bot.getSlideLevel() <= 99 || bot.getSlideLevel() >= 900)) {
            bot.gripperClosed();
        } else if((bot.getSlideLevel() <= 99 || bot.getSlideLevel() >= 900)){
            bot.gripperOpen();
        }

//        if((bot.getSlideLevel() >= 300 && bot.getSlideLevel() <= 900) && bot.getSlideLevelTarg() > 100){
//            bot.gripperClosed();
//        }

        if (gamepad.right_bumper && bot.getSlideLevelTarg() != 0 && (bot.getSlideLevel() <= 99 || bot.getSlideLevel() >= 900)) {
            bot.wristVertical();
        } else if(bot.getSlideLevelTarg() < 100){
            bot.wristHorizontal();
        }
        else if(bot.getSlideLevelTarg() > 100) {
            bot.setWrist(Config.WristCloseDoor);
        }

        intakeToggle.toggle(gamepad.a);
        if (intakeToggle.getBool()) {
            bot.intakeCounter(intakePos.getNum());
        } else {
            bot.intakeDown();
        }

        if (gamepad.x) {
            bot.setSlideLevel(1200);
        } else if (gamepad.y) {
            bot.setSlideLevel(2200);
        } else if (gamepad.b) {
            bot.setSlideLevel(0);
            bot.wristHorizontal();
        }

        collectorToggle.toggle(gamepad.dpad_down);
        if(bot.getSlideLevelTarg() > 0){
            collectorToggle.set(false);
        }

        if (gamepad.dpad_up) {
            bot.moveCollectorBack();
        } else {
            if (collectorToggle.getBool()) {
                bot.moveCollector();
            } else {
                bot.stopCollector();
            }
        }
    }

    @DriverAnnotations.Coach(name = "Christian/Owen")
    public void coach(){
        //this is where we keep track of who is the coach
    }

    public void telemetry(){
        bot.updateOdos();
        telemetry.addData("Scale Power", scalePower.getNum());
        telemetry.addData("Coordinate Lock Mecanum:", mecanumToggle.getBool());
        telemetry.addData("Angle:", -bot.angleDEG());
        telemetry.addData("arm pos", bot.getSlideLevel());
        telemetry.addData("X in cm", bot.getXX());
        telemetry.addData("Y in cm", bot.getYY());
        telemetry.addData("left", bot.getRawOdos()[0].getCurrentPosition());
        telemetry.addData("right", -bot.getRawOdos()[1].getCurrentPosition());
        telemetry.addData("middle", bot.getRawOdos()[2].getCurrentPosition());
    }

    public void teleOp(Gamepad gamepad1, Gamepad gamepad2) {
        driver1(gamepad1);
        driver2(gamepad2);
        telemetry();
    }

    public String getWelcomeText() {
        Class myClass = Drivers.class;
        StringBuilder text = new StringBuilder();
        String name1 = "";
        String name2 = "";
        String name3 = "";
        try {
            Method[] methods = myClass.getMethods();
            Method method = null;
            Method method2 = null;
            Method method3 = null;
            for (Method m : methods) {
                if (m.getName().equals("driver1")) {
                    method = m;
                } else if (m.getName().equals("driver2")) {
                    method2 = m;
                } else if (m.getName().equals("coach")) {
                    method3 = m;
                }
            }
            assert method != null;
            DriverAnnotations.Driver1 anno = method.getAnnotation(DriverAnnotations.Driver1.class);
            name1 = anno.name();
            assert method2 != null;
            DriverAnnotations.Driver2 anno2 = method2.getAnnotation(DriverAnnotations.Driver2.class);
            name2 = anno2.name();
            assert method3 != null;
            DriverAnnotations.Coach anno3 = method3.getAnnotation(DriverAnnotations.Coach.class);
            name3 = anno3.name();
        }
        catch (Exception e){
            //telemetry();
        }
        return "Welcome, " + name1 + ", " + name2 + ", and " + name3 + ".\nHave a great second tournament.";
    }

    public void example(){
        //bot.closeCam();
        telemetry.addLine(bot.isAuto() ? "Example auto succeeded" : "Example tele succeeded");
    }
}