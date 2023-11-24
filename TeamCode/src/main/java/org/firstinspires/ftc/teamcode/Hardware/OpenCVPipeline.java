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
        Mat augmentedImage = new Mat(input, newImage);
        Mat augmentedImage2 = new Mat(input, newImage2);
        Mat augmentedImage3 = new Mat(input, newImage3);

        saturationLeft = meanSaturation(augmentedImage);
        saturationMiddle = meanSaturation(augmentedImage2);
        saturationRight = meanSaturation(augmentedImage3);

        Mat output = input.clone();
        Imgproc.rectangle(output, newImage.tl(), newImage.br(), new Scalar(0, 255, 0), 2);
        Imgproc.rectangle(output, newImage2.tl(), newImage2.br(), new Scalar(0, 255, 0), 2);
        Imgproc.rectangle(output, newImage3.tl(), newImage3.br(), new Scalar(0, 255, 0), 2);

        return output;
    }

    public double meanSaturation(Mat augmentedImage){
        Imgproc.cvtColor(augmentedImage, hsvImage, Imgproc.COLOR_BGR2HSV);
        Core.extractChannel(hsvImage, saturationChannel, 1);
        Scalar meanSaturation = Core.mean(saturationChannel);
        return meanSaturation.val[0];
    }
}
