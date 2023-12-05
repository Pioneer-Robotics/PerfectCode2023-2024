package org.firstinspires.ftc.teamcode.Features;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class Voltage {
    public double getVoltage(HardwareMap.DeviceMapping<VoltageSensor> volt) {
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