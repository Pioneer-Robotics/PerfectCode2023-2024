package org.firstinspires.ftc.teamcode.Hardware;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class OpenCVPipeline extends OpenCvPipeline {
    public double x = 30;
    public double y = 30;
    public double width = 386;
    public double height =660;
    public double count = 0;

    public static double saturationLeft;
    public static double saturationMiddle;
    public static double saturationRight;

    private static float[] midPos = {4f/8f+0f/8f, 4f/8f+0f/8f};
    private static float[] leftPos = {2f/8f+0f/8f, 4f/8f+0f/8f};
    private static float[] rightPos = {6f/8f+0f/8f, 4f/8f+0f/8f};

    public static double[] cameraLocationValues = new double[3];

    public static String confidence = "";

    Rect newImage =  new Rect((int) x, (int) y, (int) width, (int) height);
    Rect newImage2 =  new Rect((int) x + 416, (int) y, (int) width, (int) height);
    Rect newImage3 =  new Rect((int) x + 832, (int) y, (int) width, (int) height);

    Mat saturationChannel = new Mat();
    Mat weirdColorMat = new Mat();
    Mat imlostmat = new Mat();
    Mat all = new Mat();

    Mat mask = new Mat();
    List<MatOfPoint> contours = new ArrayList<>();
    Mat newMat = new Mat();

    Scalar lowerRed = new Scalar(0, 100, 100, 0); // Adjust as needed for light pink
    Scalar upperRed = new Scalar(0, 250, 250, 0); // Adjust as needed for dark red

//        Scalar lowerRed = new Scalar(75, 0, 0); // Adjust as needed for light blue
//        Scalar upperRed = new Scalar(100, 150, 100); // Adjust as needed for dark blue


    public OpenCVPipeline(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public Mat processFrame(Mat input) {
//        saturationLeft = meanSaturation(input.submat(newImage));
//        saturationMiddle = meanSaturation(input.submat(newImage2));
//        saturationRight = meanSaturation(input.submat(newImage3));
//
//        cameraLocationValues[0] = saturationLeft;
//        cameraLocationValues[1] = saturationMiddle;
//        cameraLocationValues[2] = saturationRight;

        //colorBasedObjectDetection(input);
        objectDetectTest(input);

        Imgproc.rectangle(input, newImage.tl(), newImage.br(), new Scalar(0, 255, 0), 2);
        Imgproc.rectangle(input, newImage2.tl(), newImage2.br(), new Scalar(0, 255, 0), 2);
        Imgproc.rectangle(input, newImage3.tl(), newImage3.br(), new Scalar(0, 255, 0), 2);

        System.gc();
        return input;
    }

    public double meanSaturation(Mat augmentedImage){
        Imgproc.cvtColor(augmentedImage, augmentedImage, Imgproc.COLOR_RGB2YCrCb);
        //Core.extractChannel(hsvImage, saturationChannel, 1);
        //Scalar meanSaturation = Core.mean(saturationChannel);
        Scalar meanSaturation = Core.mean(augmentedImage);
        saturationChannel.release();
        augmentedImage.release();
        return meanSaturation.val[0];
    }

    public void objectDetectTest(Mat mat){
        Imgproc.blur(mat, mat, new Size(10, 10));

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2HSV);

        Core.inRange(mat, lowerRed, upperRed, mask);

        Imgproc.erode(mask, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12)));
        Imgproc.erode(mask, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12)));

        Imgproc.dilate(mask, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24)));
        Imgproc.dilate(mask, mat, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24)));

        Imgproc.findContours(mat, contours, newMat, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
        if(newMat.size().height > 0 && newMat.size().width > 0){
            for(int idx = 0; idx >= 0; idx = (int) newMat.get(0, idx)[0]){
                Imgproc.drawContours(mat, contours, idx, new Scalar(255, 0, 0));
            }
        }
    }

    private void colorBasedObjectDetection(Mat frameMat) {
        contours.clear();
        Imgproc.cvtColor(frameMat, weirdColorMat, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(weirdColorMat, weirdColorMat, 2);

        Imgproc.threshold(weirdColorMat, imlostmat, 102, 255, Imgproc.THRESH_BINARY_INV);

        // Create a binary mask based on the red color range
        //Core.inRange(frameMat, lowerRed, upperRed, mask);

        // Find contours in the binary mask
        //Imgproc.findContours(mask, contours, newMat, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.findContours(imlostmat, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        weirdColorMat.copyTo(all);
        Imgproc.drawContours(frameMat, contours, -1, new Scalar(255, 0, 0), 3, 8);

        double valLeft = calcAvgColor(imlostmat.submat(newImage));
        double valMid = calcAvgColor(imlostmat.submat(newImage2));
        double valRight = calcAvgColor(imlostmat.submat(newImage3));

//        double[] pixMid = imlostmat.get((int)(frameMat.rows()*midPos[1]), (int)(frameMat.cols()*midPos[0]));
//        double valLeft = (int) pixMid[0];
//
//        double[] pixLeft = imlostmat.get((int)(frameMat.rows()*leftPos[1]), (int)(frameMat.cols()*leftPos[0]));
//        double valMid = (int) pixLeft[0];
//
//        double[] pixRight = imlostmat.get((int)(frameMat.rows()*rightPos[1]), (int)(frameMat.cols()*rightPos[0]));
//        double valRight = (int) pixRight[0];

        Rect box4 = new Rect(500, 300, 10, 10);

//        Imgproc.putText(frameMat, Double.toString(frameMat.get(540, 300)[0]), newImage.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 255, 0), 2);
//        Imgproc.putText(frameMat, Double.toString(frameMat.get(540, 300)[1]), newImage2.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 255, 0), 2);
//        Imgproc.putText(frameMat, Double.toString(frameMat.get(540, 300)[2]), newImage3.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 255, 0), 2);
        Imgproc.rectangle(frameMat, box4, new Scalar(0, 0, 255));
        Imgproc.putText(frameMat, Double.toString(valLeft), newImage.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 255, 0), 2);
        Imgproc.putText(frameMat, Double.toString(valMid), newImage2.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 255, 0), 2);
        Imgproc.putText(frameMat, Double.toString(valRight), newImage3.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, new Scalar(0, 255, 0), 2);

        // Draw contours on the original frame
//        Imgproc.drawContours(frameMat, contours, -1, new Scalar(255, 0, 0), 2);
//        drawBoxes(contours, frameMat);
    }

    public int calcAvgColor(Mat mat){
        Scalar sum = Core.sumElems(mat);
        int totalPixels = mat.rows() * mat.cols();
        return (int) (sum.val[0] / totalPixels);
    }

    private void drawBoxes(List<MatOfPoint> contours, Mat frameMat) {
        Scalar colorGreen = new Scalar(0, 255, 0);

        // Variables to track the box with the highest confidence
        Rect mostConfidentBox = null;
        double highestConfidence = Double.MIN_VALUE;

        // Iterate through the contours to find the box with the detected object
        for (int i = 0; i < contours.size(); i++) {
            Rect boundingBox = Imgproc.boundingRect(contours.get(i));

            // Calculate the perimeter of the bounding box
            double perimeter = Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()), true);

            // Choose the box with the highest perimeter as the most confident box
            if (perimeter > highestConfidence) {
                highestConfidence = perimeter;
                mostConfidentBox = boundingBox;
            }
        }
        // Draw the most confident box
        if (mostConfidentBox != null) {
            if (contains(newImage, mostConfidentBox)) {
                confidence = "Box 1";
            }
            // Check if the bounding box intersects with Box 2
            else if (contains(newImage2, mostConfidentBox)) {
                confidence = "Box 2";
                //Imgproc.putText(frameMat, "Box 2", box1.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, colorGreen, 2);
            }
            // Check if the bounding box intersects with Box 3
            else if (contains(newImage3, mostConfidentBox)) {
                confidence = "Box 3";
                // Imgproc.putText(frameMat, "Box 3", box1.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, colorGreen, 2);
            }
        }
        Imgproc.putText(frameMat, confidence, newImage.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 1.0, colorGreen, 2);
    }

    boolean contains(Rect container, Rect contained) {
        // Check if the rectangle "contained" is completely contained within the rectangle "container"
        return container.contains(contained.tl()) && container.contains(contained.br());
    }
}