package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;

/**
 * Handles IMU initializing and simplifies getting the orientation
 */
public class BotIMU extends AbstractHardwareComponent {
    private final IMU imu;

    public BotIMU(IMU imu){
        this.imu = imu;
        this.imu.initialize(new com.qualcomm.robotcore.hardware.IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        this.imu.resetYaw();
    }

    public double getDegrees(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public double getRadians(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public void resetYaw(){
        imu.resetYaw();
    }
}
