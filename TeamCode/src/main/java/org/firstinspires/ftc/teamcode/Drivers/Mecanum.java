package org.firstinspires.ftc.teamcode.Drivers;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Helpers.VelocityMath;
import org.firstinspires.ftc.teamcode.Initializers.HardwareHelper;

public class Mecanum extends HardwareHelper {
    private double leftDiagPower = 0d, rightDiagPower = 0d, leftRotatePower = 0d, rightRotatePower = 0d;
    private final double sq2 = VelocityMath.sq2;

    //POWERS
    //TODO get rid of this when velocities are correct
    public void coordinateLockMecanum(Gamepad gamepad, Counter scalePower){
        double imu_offset = Math.toRadians(bot.angleDEG());
        double angle = (Math.toRadians(bot.angleRAD()) - imu_offset);

        leftDiagPower = -(((-gamepad.left_stick_y - gamepad.left_stick_x) * Math.sin(angle) + ((gamepad.left_stick_y - gamepad.left_stick_x)) * Math.cos(angle)));
        rightDiagPower = -(((-(-gamepad.left_stick_y + gamepad.left_stick_x)) * Math.sin(angle) + ((gamepad.left_stick_y + gamepad.left_stick_x) * Math.cos(angle))));
        leftRotatePower = -gamepad.right_stick_x*0.8;
        rightRotatePower = gamepad.right_stick_x*0.8;

        bot.gearShift(scalePower);
        bot.setPowers(scalePower.getNum()*(leftDiagPower+leftRotatePower),
                scalePower.getNum()*(rightDiagPower+leftRotatePower),
                scalePower.getNum()*(rightDiagPower+rightRotatePower),
                scalePower.getNum()*(leftDiagPower+rightRotatePower) );
    }

    public void mecanumDrive(Gamepad gamepad, Counter scalePower){
        leftDiagPower = ((-gamepad.left_stick_y + gamepad.left_stick_x));
        rightDiagPower = ((-gamepad.left_stick_y - gamepad.left_stick_x));
        leftRotatePower =  gamepad.right_stick_x*0.8;
        rightRotatePower = -gamepad.right_stick_x*0.8;

        bot.gearShift(scalePower);
        bot.setPowers(scalePower.getNum() *(leftDiagPower+leftRotatePower),
                scalePower.getNum() *(rightDiagPower+leftRotatePower),
                scalePower.getNum() *(rightDiagPower+rightRotatePower),
                scalePower.getNum() *(leftDiagPower+rightRotatePower));
    }

    //Velocities
//    public void coordinateLockMecanum(Gamepad gamepad, Counter scalePower){
//        double imu_offset = Math.toRadians(bot.angleDEG());
//        double angle = (Math.toRadians(bot.angleRAD()) - imu_offset);
//
//        leftDiagPower = -(((-gamepad.left_stick_y - gamepad.left_stick_x) / sq2 * Math.sin(angle) + ((gamepad.left_stick_y - gamepad.left_stick_x) / sq2) * Math.cos(angle)));
//        rightDiagPower = -(((-(-gamepad.left_stick_y + gamepad.left_stick_x) / sq2) * Math.sin(angle) + ((gamepad.left_stick_y + gamepad.left_stick_x) / sq2 * Math.cos(angle))));
//        leftRotatePower = -gamepad.right_stick_x*0.8;
//        rightRotatePower = gamepad.right_stick_x*0.8;
//
//        bot.gearShift(scalePower);
//        bot.setVels(scalePower.getNum()*(leftDiagPower+leftRotatePower),
//                scalePower.getNum()*(rightDiagPower+leftRotatePower),
//                scalePower.getNum()*(rightDiagPower+rightRotatePower),
//                scalePower.getNum()*(leftDiagPower+rightRotatePower) );
//    }
//
//    public void mecanumDrive(Gamepad gamepad, Counter scalePower){
//        leftDiagPower = ((-gamepad.left_stick_y + gamepad.left_stick_x) / VelocityMath.sq2);
//        rightDiagPower = ((-gamepad.left_stick_y - gamepad.left_stick_x) / VelocityMath.sq2);
//        leftRotatePower =  gamepad.right_stick_x*0.8;
//        rightRotatePower = -gamepad.right_stick_x*0.8;
//
//        bot.gearShift(scalePower);
//        bot.setVels(scalePower.getNum() *(leftDiagPower+leftRotatePower),
//                scalePower.getNum() *(rightDiagPower+leftRotatePower),
//                scalePower.getNum() *(rightDiagPower+rightRotatePower),
//                scalePower.getNum() *(leftDiagPower+rightRotatePower));
//    }
}
