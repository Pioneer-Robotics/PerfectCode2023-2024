package org.firstinspires.ftc.teamcode.OpModes.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Hardware.CameraHandler;
import org.firstinspires.ftc.teamcode.Helpers.BulkReader;

@Autonomous
public class opmodetest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        BulkReader bulkReader = new BulkReader(hardwareMap);
        CameraHandler cameraHandler = new CameraHandler(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()), hardwareMap.get(WebcamName.class, "Webcam 1"), false);
        cameraHandler.openCamera();
        while (!opModeIsActive() && !isStarted()  && !isStopRequested()) {
            //telemetry.addLine(Integer.toString(cameraHandler.doTheMath()));
            telemetry.addLine("hi");
            telemetry.update();
        }
        while (opModeIsActive() && isStarted() && !isStopRequested()) {
            telemetry.addLine("hi");
            telemetry.update();
        }
    }
}