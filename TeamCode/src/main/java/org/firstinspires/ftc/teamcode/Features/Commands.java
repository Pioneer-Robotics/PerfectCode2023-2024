package org.firstinspires.ftc.teamcode.Features;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Helpers.Counter;
import org.firstinspires.ftc.teamcode.Initializers.HardwareHelper;

public class Commands extends HardwareHelper {
    public void gearShift(Gamepad gamepad, Counter counter){counter.arithmetic(gamepad.right_bumper, gamepad.left_bumper, .1);}
}
