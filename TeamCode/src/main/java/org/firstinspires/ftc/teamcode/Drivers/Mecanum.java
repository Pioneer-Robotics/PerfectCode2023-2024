package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

/**
 * Holds two methods: coordinateLock and regularMecanum that are our two modes of driving
 */
public class Mecanum extends AbstractHardwareComponent {
    private double leftDiagPower = 0d, rightDiagPower = 0d, leftRotatePower = 0d, rightRotatePower = 0d;

    /**
     * Based on the zero angle, it will drive depending on that
     * @param gamepad: input gamepad
     * @param scalePower: ranging from 0-1
     */
    public void teleOpFieldCentricMecanum(Gamepad gamepad, Counter scalePower){
        double botHeading = bot.angleRAD();

        double x = gamepad.left_stick_x;
        double y = -gamepad.left_stick_y;
        double rx = gamepad.right_stick_x;

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.2;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        bot.gearShift(scalePower);
        bot.setVelocity(
                scalePower.getNum() * frontLeftPower,
                scalePower.getNum() * backLeftPower,
                scalePower.getNum() * frontRightPower,
                scalePower.getNum() * backRightPower );
    }

    public void autoFieldCentricDriving(double x, double y, double rx){
        double botHeading = bot.angleRAD();

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 2.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        bot.setVelocity(
                frontLeftPower,
                backLeftPower,
                frontRightPower,
                backRightPower);
    }

    public void mecanumDrive(Gamepad gamepad, Counter scalePower){
        leftDiagPower = ((-gamepad.left_stick_y + gamepad.left_stick_x));
        rightDiagPower = ((-gamepad.left_stick_y - gamepad.left_stick_x));
        leftRotatePower =  gamepad.right_stick_x;
        rightRotatePower = -gamepad.right_stick_x;

        bot.gearShift(scalePower);
        bot.setVelocity(scalePower.getNum() * (leftDiagPower+leftRotatePower),
                scalePower.getNum() * (rightDiagPower+leftRotatePower),
                scalePower.getNum() * (rightDiagPower+rightRotatePower),
                scalePower.getNum() * (leftDiagPower+rightRotatePower));
    }
}
