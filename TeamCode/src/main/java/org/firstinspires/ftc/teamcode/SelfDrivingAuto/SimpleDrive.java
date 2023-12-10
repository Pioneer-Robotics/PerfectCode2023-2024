package org.firstinspires.ftc.teamcode.SelfDrivingAuto;

import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

public class SimpleDrive extends AbstractHardwareComponent {
    private final double kp;
    private final double ki;
    private final double kd;
    private final double kf;

    private double setpoint;
    private double integral;
    private double previousError;

    public SimpleDrive(double kp, double ki, double kd, double kf) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.kf = kf;
    }

    //forward only
    public void moveForward(double distance) {
        setpoint = bot.getX() + distance;

        bot.setMotorPower(.06);
        while (bot.getX() < setpoint - 1 && Math.abs(bot.getPower()) > 0.05 && bot.opmode.opModeIsActive() && bot.opmode.isStarted()) {
            double error = (setpoint - bot.getX()) / distance;
            double proportional = kp * error;

            integral += error;

            double derivative = error - previousError;
            double feedforward = kf * distance;
            double output = proportional + ki * integral + kd * derivative + feedforward;

            bot.setMotorPower(output);

            bot.addData("distance", setpoint);
            bot.addData("cur", bot.getX());
            bot.update();

            previousError = error;
        }
        bot.setMotorPower(0);
    }

    //turn only
    public void moveTurn(double degree) {
        double kiTurn = .0005;
        double kpTurn = 1.15;
        setpoint = degree;

        bot.setMotorPower(.06);
        while(Math.abs(setpoint) - Math.abs(bot.angleDEG()) > 2 && Math.abs(bot.getPower()) > 0.05 && bot.opmode.opModeIsActive() && bot.opmode.isStarted()){
            double error = (Math.abs(setpoint) - Math.abs(bot.angleDEG())) / degree;
            double proportional = kpTurn * error;

            integral += error;

            double derivative = error - previousError;
            double feedforward = kf * degree;
            double output = proportional + kiTurn * integral + kd * .2 + feedforward;

            bot.setMotorPowerTurn(output);

            bot.addData("distance", setpoint);
            bot.addData("cur", bot.angleDEG());
            bot.update();

            previousError = error;
        }
        bot.setMotorPower(0);
    }

    //strafe only
    public void moveStrafe(double distance) {
        bot.resetY();
        setpoint = bot.getY() + distance;

        bot.setMotorPower(.06);
        while (bot.getY() < setpoint - 1 && Math.abs(bot.getPower()) > 0.05 && bot.opmode.opModeIsActive() && bot.opmode.isStarted()) {
            double error = (setpoint - bot.getY()) / distance;
            double proportional = kp * error;

            integral += error;

            double derivative = error - previousError;
            double feedforward = kf * distance;
            double output = proportional + ki * integral + kd * derivative + feedforward;

            bot.setMotorPowerStrafe(output);

            bot.addData("distance", setpoint);
            bot.addData("cur", bot.getY());
            bot.update();

            previousError = error;
        }
        bot.setMotorPower(0);
    }

    //strafe only
    public void moveStrafeNegative(double distance) {
        bot.resetY();
        setpoint = bot.getY() + distance;

        bot.setMotorPower(.06);
        while (bot.getY() > setpoint + 1 && Math.abs(bot.getPower()) > 0.05 && bot.opmode.opModeIsActive() && bot.opmode.isStarted()) {
            double error = (setpoint - bot.getY()) / distance;
            double proportional = kp * error;

            integral += error;

            double derivative = error - previousError;
            double feedforward = kf * distance;
            double output = proportional + ki * integral + kd * derivative + feedforward;

            bot.setMotorPowerStrafe(-output);

            bot.addData("distance", setpoint);
            bot.addData("cur", bot.getY());
            bot.update();

            previousError = error;
        }
        bot.setMotorPower(0);
    }
}
