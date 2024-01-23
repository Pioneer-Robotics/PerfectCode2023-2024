package org.firstinspires.ftc.teamcode.Hardware;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class ColorDetectionPipeline extends OpenCvPipeline {

    Scalar lowerBlue = new Scalar(93, 118, 67); // Adjust as needed for light blue
    Scalar upperBlue = new Scalar(180, 177, 107); // Adjust as needed for dark blue
    Scalar lowerRed = new Scalar(0, 82, 117); // Adjust as needed for light blue
    Scalar upperRed = new Scalar(51, 216, 193); // Adjust as needed for dark blue

    public static int markerLocation = -1;

    public static String confidence = "";

    public boolean isRed;

    Mat blurredImage = new Mat();
    Mat hsvImage = new Mat();
    Mat mask = new Mat();
    Mat morphOutput = new Mat();

    public double x = 30;
    public double y = 300;
    public double width = 386;
    public double height = 360;

    Rect newImage =  new Rect((int) x, (int) y, (int) width, (int) height);
    Rect newImage2 =  new Rect((int) x + 416, (int) y, (int) width, (int) height);
    Rect newImage3 =  new Rect((int) x + 832, (int) y, (int) width, (int) height);

    static List<MatOfPoint> contours = new ArrayList<>();

    public ColorDetectionPipeline(boolean isRed){
        this.isRed = isRed;
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.rectangle(input, newImage.tl(), newImage.br(), new Scalar(0, 255, 0), 2);
        Imgproc.rectangle(input, newImage2.tl(), newImage2.br(), new Scalar(0, 255, 0), 2);
        Imgproc.rectangle(input, newImage3.tl(), newImage3.br(), new Scalar(0, 255, 0), 2);

        contours.clear();

        Imgproc.blur(input, blurredImage, new Size(15, 15));

        Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_RGB2HSV);

        if(isRed) {Core.inRange(hsvImage, lowerRed, upperRed, mask);}
        else{Core.inRange(hsvImage, lowerBlue, upperBlue, mask);}

        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));

        Imgproc.erode(mask, morphOutput, erodeElement);
        Imgproc.erode(morphOutput, morphOutput, erodeElement);

        Imgproc.dilate(morphOutput, morphOutput, dilateElement);
        Imgproc.dilate(morphOutput, morphOutput, dilateElement);

        double minContourArea = 5000.0;

        //Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.findContours(morphOutput, contours, new Mat(), Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        for (int i = 0; i < contours.size(); i++) {
            MatOfPoint contour = contours.get(i);
            double contourArea = Imgproc.contourArea(contour);

            if (contourArea > minContourArea) {
                // This contour is worth showing, draw it on the frameMat
                Imgproc.drawContours(input, contours, i, new Scalar(250, 0, 0));
            }
        }

        Rect mostConfidentBox = null;
        double highestConfidence = Double.MIN_VALUE;

        for (int i = 0; i < contours.size(); i++) {
            Rect boundingBox = Imgproc.boundingRect(contours.get(i));

            // Calculate the area of the bounding box
            double area = boundingBox.area();

            if (area > highestConfidence) {
                highestConfidence = area;
                mostConfidentBox = boundingBox;
            }
        }

        if (mostConfidentBox != null) {
            if (isPartiallyWithin(mostConfidentBox, newImage)) {
                confidence = "Box 1";
                markerLocation = 1;
            }
            else if (isPartiallyWithin(mostConfidentBox, newImage2)) {
                confidence = "Box 2";
                markerLocation = 2;
            }
            else if (isPartiallyWithin(mostConfidentBox, newImage3)) {
                confidence = "Box 3";
                markerLocation = 3;
            }

            for (int i = 0; i < contours.size(); i++) {
                MatOfPoint contour = contours.get(i);
                double contourArea = Imgproc.contourArea(contour);

                if (contourArea > minContourArea) {
                    Imgproc.drawContours(blurredImage, contours, i, new Scalar(250, 0, 0));
                }
            }

            Imgproc.putText(blurredImage, confidence, newImage.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 10.0, new Scalar(0, 255, 0), 4);
        }

        return blurredImage;
    }

    boolean contains(Rect container, Rect contained) {
        return container.contains(contained.tl()) && container.contains(contained.br());
    }

    private static boolean isPartiallyWithin(Rect boxToCheck, Rect referenceBox) {
        return (boxToCheck.x < (referenceBox.x + referenceBox.width) &&
                (boxToCheck.x + boxToCheck.width) > referenceBox.x &&
                boxToCheck.y < (referenceBox.y + referenceBox.height) &&
                (boxToCheck.y + boxToCheck.height) > referenceBox.y);
    }
}