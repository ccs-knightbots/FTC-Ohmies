package org.firstinspires.ftc.teamcode.New_System.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Vision {

    public AprilTagProcessor aprilTagProcessor;
    public VisionPortal visionPortal;

    public Vision(HardwareMap hwMap) {
        WebcamName webcamName = hwMap.get(WebcamName.class, "Webcam");
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        visionPortal = VisionPortal.easyCreateWithDefaults(webcamName, aprilTagProcessor);
    }

    public List<AprilTagDetection> getAprilTagDetections() {
        return aprilTagProcessor.getDetections();
    }

    public void stopStreaming() {
        visionPortal.stopStreaming();
    }

}
