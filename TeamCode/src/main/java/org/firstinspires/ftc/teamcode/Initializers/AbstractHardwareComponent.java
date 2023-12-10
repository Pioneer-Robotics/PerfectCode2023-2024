package org.firstinspires.ftc.teamcode.Initializers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Bot;

/**
 * Holds two static references: Bot and Telemetry.
 * Sub-classes can extend this which makes it easier to utilizes a single instance of bot
 * rather than instantiating multiple objects or working around it
 */
public abstract class AbstractHardwareComponent {
    public static Bot bot;
    public static Telemetry telemetry;

    /**
     * Must be static to initialize inside o
     * @param bot
     * @param telemetry
     */
    public static void init(Bot bot, Telemetry telemetry){
        AbstractHardwareComponent.bot = bot;
        AbstractHardwareComponent.telemetry = telemetry;
    }
}
