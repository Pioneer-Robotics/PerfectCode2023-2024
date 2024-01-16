package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class VoltageHandler {
    /**
     * Iterate over all of our voltageHandler sensor readings and return the lowest value
     * @param voltageSensors: collection of voltageHandler sensors
     * @return the lowest voltageHandler
     */
    public double getVoltage(HardwareMap.DeviceMapping<VoltageSensor> voltageSensors) {
        double lowestVoltage = Double.POSITIVE_INFINITY;
        for (VoltageSensor sensor : voltageSensors) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                lowestVoltage = Math.min(lowestVoltage, voltage);
            }
        }
        return lowestVoltage;
    }
}