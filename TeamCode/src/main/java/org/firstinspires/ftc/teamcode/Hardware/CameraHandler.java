package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class CameraHandler {
    int cameraMonitorViewId;
    OpenCvCamera camera;
    WebcamName webcamName;
    ColorDetectionPipeline openCVPipeline;

    public CameraHandler(int camera, WebcamName name, boolean isRed){
        cameraMonitorViewId = camera;
        this.camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        webcamName = name;
        this.camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        //openCVPipeline = new OpenCVPipeline(30, 30, 386, 660);
        openCVPipeline = new ColorDetectionPipeline(isRed);
    }

    public void openCamera(){
        camera.setPipeline(openCVPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode) {
                //bot.addLine("CameraHandler Failed.");
            }
        });
    }

    public int locationCamera(){
        camera.setPipeline(openCVPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode) {
                //bot.addLine("CameraHandler Failed.");
            }
        });
        String satHigh = getSaturationHigh();
        return satHigh.equals("Left") ? 1 : satHigh.equals("Right") ? 3 : 2;
    }

    public void closeCam(){
        camera.closeCameraDevice();
    }

    public String getSaturationHigh(){
        return OpenCVPipeline.saturationLeft > OpenCVPipeline.saturationMiddle ?  (OpenCVPipeline.saturationLeft > OpenCVPipeline.saturationRight ? "Left" : "Right") : OpenCVPipeline.saturationMiddle > OpenCVPipeline.saturationRight ? "Middle" : "Right";
    }

    public int doTheMath(){
        String sat = getSaturationHigh();
        return sat.equals("Left") ? 1 : (sat.equals("Middle") ? 2 : 3);
    }
}