package org.firstinspires.ftc.teamcode.vision;

import android.graphics.Canvas;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.opencv.core.Mat;

public class BasicVisionProcessor implements org.firstinspires.ftc.vision.VisionProcessor {

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}
