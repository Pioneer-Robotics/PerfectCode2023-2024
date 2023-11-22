package org.firstinspires.ftc.teamcode.Initializers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Bot;

public abstract class HardwareHelper {
    public static Bot bot;
    public static Telemetry telemetry;

    public static void init(Bot bot, Telemetry telemetry){
        HardwareHelper.bot = bot;
        HardwareHelper.telemetry = telemetry;
    }
}
