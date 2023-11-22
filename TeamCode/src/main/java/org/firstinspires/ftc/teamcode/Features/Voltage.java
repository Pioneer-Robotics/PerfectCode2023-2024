package org.firstinspires.ftc.teamcode.Features;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

//changes this year were to just give out random errors but no matter the voltage it will always run
public class Voltage {
    public static double getVoltage(HardwareMap.DeviceMapping<VoltageSensor> volt) {
        double result = Double.POSITIVE_INFINITY;
        for (VoltageSensor sensor : volt) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                result = Math.min(result, voltage);
            }
        }
        return result;
    }
}