package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Annotations.DriverAnnotations;
import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Helpers.Toggle;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

import java.lang.annotation.Annotation;
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

    //Counter
    private final Counter scalePower = new Counter(.3, .2, 0.8);

    @DriverAnnotations.Driver1.Seth
    public void driver1(Gamepad gamepad) {
        mecanumToggle.toggle(gamepad.y);//toggles between regular and coordinate lock mecanum

        if (mecanumToggle.getBool()) {
            bot.coordinateLock(scalePower);
        } else {
            bot.regularMecanum(scalePower);
        }

        if (gamepad.x) {//resets IMU/Odometry just in case robot is not facing directly north
            bot.resetIMU();
            //bot.resetOdometers();
        }

        if (gamepad.right_bumper) {
            bot.launchAirplane();
        } else {
            bot.holdAirplane();
        }
    }

    @DriverAnnotations.Driver2.Henry
    public void driver2(Gamepad gamepad) {
        //ensures pixel drop servos are up
        bot.rightDropUp();
        bot.leftDropUp();
        grabberToggle.toggle(gamepad.left_bumper);
        if (grabberToggle.getBool()) {
            bot.gripperOpen();
        } else {
            bot.gripperClosed();
        }

        if (gamepad.right_bumper) {
            bot.wristVertical();
        } else {
            bot.wristHorizontal();
        }

        intakeToggle.toggle(gamepad.dpad_left);
        if (intakeToggle.getBool()) {
            bot.intakeUp();
        } else {
            bot.intakeDown();
        }

        if (gamepad.x) {
            bot.setSlideLevel(1200);
        } else if (gamepad.y) {
            bot.setSlideLevel(2200);
        } else if (gamepad.b) {
            bot.setSlideLevel(0);
        }
        collectorToggle.toggle(gamepad.dpad_down);

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

    @DriverAnnotations.Coach.Christian
    public void coach(){
        //this is where we keep track of who is the coach
    }

    public void telemetry(){
        bot.newOdoCalc();
        telemetry.addData("Scale Power", scalePower.getNum());
        telemetry.addData("Coordinate Lock Mecanum:", mecanumToggle.getBool());
        telemetry.addData("Angle:", bot.angleDEG());
        telemetry.addData("arm pos", bot.getSlideLevel());
        telemetry.addData("X in cm", bot.getXX());
        telemetry.addData("Y in cm", bot.getYY());
        telemetry.addData("left", bot.getRawOdos()[0].getCurrentPosition());
        telemetry.addData("right", -bot.getRawOdos()[1].getCurrentPosition());
        telemetry.addData("middle", bot.getRawOdos()[2].getCurrentPosition());
    }

    public void teleOp(Gamepad gamepad1, Gamepad gamepad2) {
        driver1(gamepad1);
        driver1(gamepad2);
        telemetry();
    }

    public String getWelcomeText(){
        Class<Drivers> myClass = Drivers.class;
        StringBuilder text = new StringBuilder();
        try {
            Method method = myClass.getDeclaredMethod("driver1", Gamepad.class);
            Method method2 = myClass.getDeclaredMethod("driver2", Gamepad.class);
            Method method3 = myClass.getDeclaredMethod("coach");
            // Check if the method has any annotations
            Annotation[] annotations = method.getAnnotations();
            Annotation[] annotations2 = method2.getAnnotations();
            Annotation[] annotations3 = method3.getAnnotations();

            // Iterate through the annotations and print their names
            for (Annotation annotation : annotations) {
                text.append(annotation.annotationType().getSimpleName());
            }
            for (Annotation annotation : annotations2) {
                text.append(", ").append(annotation.annotationType().getSimpleName());
            }
            for (Annotation annotation : annotations3) {
                text.append(", and ").append(annotation.annotationType().getSimpleName());
            }
        }
        catch (Exception e){
            telemetry();
        }
        return "Welcome, " + text.toString() + ".\nHave a great first tournament.";
    }

    public void example(){
        telemetry.addLine(bot.isAuto() ? "Example auto succeeded" : "Example tele succeeded");
    }
}