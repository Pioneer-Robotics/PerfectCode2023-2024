package org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.teamcode.Bot;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

class OpenCVPipeline extends OpenCvPipeline {
    public double x = 30;
    public double y = 300;
    public double width = 386;
    public double height = 360;

    public static double saturationLeft;
    public static double saturationMiddle;
    public static double saturationRight;

    Rect newImage =  new Rect((int) x, (int) y, (int) width, (int) height);
    Rect newImage2 =  new Rect((int) x + 416, (int) y, (int) width, (int) height);
    Rect newImage3 =  new Rect((int) x + 832, (int) y, (int) width, (int) height);

    Mat saturationChannel = new Mat();
    Mat hsvImage = new Mat();


    public OpenCVPipeline(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public Mat processFrame(Mat input) {
        saturationLeft = meanSaturation(input.submat(newImage));
        saturationMiddle = meanSaturation(input.submat(newImage2));
        saturationRight = meanSaturation(input.submat(newImage3));

        Imgproc.rectangle(input, newImage.tl(), newImage.br(), new Scalar(0, 255, 0), 2);
        Imgproc.rectangle(input, newImage2.tl(), newImage2.br(), new Scalar(0, 255, 0), 2);
        Imgproc.rectangle(input, newImage3.tl(), newImage3.br(), new Scalar(0, 255, 0), 2);

        return input;
    }

    public double meanSaturation(Mat augmentedImage){
        Imgproc.cvtColor(augmentedImage, hsvImage, Imgproc.COLOR_BGR2HSV);
        Core.extractChannel(hsvImage, saturationChannel, 1);
        Scalar meanSaturation = Core.mean(saturationChannel);
        return meanSaturation.val[0];
    }
}
