package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Initializers.AbstractHardwareComponent;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class Camera extends AbstractHardwareComponent {
    private final Object lock = new Object();
    int cameraMonitorViewId;
    OpenCvCamera camera;
    WebcamName webcamName;
    OpenCVPipeline openCVPipeline;

    public Camera(int camera, WebcamName name){
        cameraMonitorViewId = camera;
        this.camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        webcamName = name;
        this.camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        openCVPipeline = new OpenCVPipeline(30, 30, 386, 660);
    }

    public void openCamera(){
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                streamCamera(openCVPipeline);
                telemetry.addData("Camera Sat", getSaturationHigh());
            }
            @Override
            public void onError(int errorCode) {
                bot.addLine("Camera Failed.");
            }
        });
    }

    public int locationCamera(){
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                streamCamera(openCVPipeline);
                telemetry.addData("Camera Sat", getSaturationHigh());
            }
            @Override
            public void onError(int errorCode) {
                bot.addLine("Camera Failed.");
            }
        });
        String satHigh = getSaturationHigh();
        return satHigh.equals("Left") ? 1 : satHigh.equals("Right") ? 3 : 2;
    }

    public void streamCamera(OpenCVPipeline openCVPipeline) {
        synchronized (lock) {
            camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            camera.setPipeline(openCVPipeline);

            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getSaturationHigh(){
        return OpenCVPipeline.saturationLeft > OpenCVPipeline.saturationMiddle ?  (OpenCVPipeline.saturationLeft > OpenCVPipeline.saturationRight ? "Left" : "Right") : OpenCVPipeline.saturationMiddle > OpenCVPipeline.saturationRight ? "Middle" : "Right";
    }
}
