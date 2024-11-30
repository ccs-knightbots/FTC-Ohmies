package org.firstinspires.ftc.teamcode.mechanism;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class MainBoard extends AbstractBoard{
    private static DcMotor leftFrontMotor_0;
    private static DcMotor leftBackMotor_1;
    private static DcMotor rightBackMotor_2;
    private static DcMotor rightFrontMotor_3;

    private static DcMotor linearExtender1;
    private static DcMotor linearExtender2;

    private static Servo clawServo;
    private static Servo tongueServo;
    private static Servo wristServo;

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    // Defines the motors.

    int tickPerRotation;
    final static double TICKS_PER_SLIDE_MOTOR = 537.7; // From GoBilda 19.2:1 motor

    public void init(HardwareMap hwMap) {
        // Function runs during init phase of robot.

        leftFrontMotor_0 = hwMap.get(DcMotor.class, "leftFrontMotor_0");
        leftBackMotor_1 = hwMap.get(DcMotor.class, "leftBackMotor_1");
        rightBackMotor_2 = hwMap.get(DcMotor.class, "rightBackMotor_2");
        rightFrontMotor_3 = hwMap.get(DcMotor.class, "rightFrontMotor_3");
        // Maps motors.

        leftFrontMotor_0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor_3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Sets the mode. *See official FTC guide for more options.

        leftFrontMotor_0.setDirection(DcMotor.Direction.FORWARD);
        leftBackMotor_1.setDirection(DcMotor.Direction.REVERSE);
        rightBackMotor_2.setDirection(DcMotor.Direction.FORWARD);
        rightFrontMotor_3.setDirection(DcMotor.Direction.REVERSE);
        // Sets the direction. You might have to change this if hardware team screws up. `-`

        linearExtender1 = hwMap.get(DcMotor.class, "linearExtender1");
        linearExtender2 = hwMap.get(DcMotor.class, "linearExtender2");
        linearExtender1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearExtender2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearExtender1.setDirection(DcMotor.Direction.FORWARD);
        linearExtender2.setDirection(DcMotor.Direction.FORWARD);
        linearExtender1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearExtender2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearExtender1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        linearExtender2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        linearExtender1.setTargetPosition(0);
        linearExtender2.setTargetPosition(0);
        tickPerRotation = (int) linearExtender1.getMotorType().getTicksPerRev();

        clawServo = hwMap.get(Servo.class, "clawServo");
        tongueServo = hwMap.get(Servo.class, "tongueServo");
        wristServo = hwMap.get(Servo.class, "wristServo");

        WebcamName webcamName = hwMap.get(WebcamName.class, "Webcam");
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        visionPortal = VisionPortal.easyCreateWithDefaults(webcamName, aprilTagProcessor);
    }

    public void setDCMotorPower(double leftFrontPower, double leftBackPower, double rightFrontPower, double rightBackPower){
        leftFrontMotor_0.setPower(leftFrontPower);
        leftBackMotor_1.setPower(leftBackPower);
        rightBackMotor_2.setPower(rightBackPower);
        rightFrontMotor_3.setPower(rightFrontPower);
        // Method allows for other classes to set the speed of each motor.
        // If hardware team mixes up the cord (as always), comment out the above commands to see which one is which.
    }

    public void setLinear(double linearPower){
        linearExtender1.setPower(-linearPower);
        linearExtender2.setPower(linearPower);
    }

    public void setClawServo(double clawAngle) {clawServo.setPosition(clawAngle/270);}
    public double getClawRotation() {return clawServo.getPosition()*270;}
//    Note: the getPosition() method doesn't return the real position, only the set position

    public void setTongueServo(double tongueMeasure) {tongueServo.setPosition(tongueMeasure);}
    public double getTonguePosition() {return tongueServo.getPosition();}

    public void setWristServo(double wristAngle) {wristServo.setPosition(wristAngle/270);}
    public double getWristRotation() {return wristServo.getPosition()*270;}

    public double getLinearExtender1() {return linearExtender1.getCurrentPosition()/(.5*tickPerRotation);}
    public double getLinearExtender2() {return linearExtender2.getCurrentPosition()/(.5*tickPerRotation);}
//    For some reason, the FTC ticksPerRotation is 2x what it should be.

    public void runTo(double location) {
        linearExtender1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearExtender2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearExtender1.setPower(1);
        linearExtender2.setPower(1);
        linearExtender1.setTargetPosition((int) (location*(.5*tickPerRotation)));
        linearExtender2.setTargetPosition((int) (-location*(.5*tickPerRotation)));
    }

    public void runBack() {
        linearExtender1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearExtender2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearExtender1.setPower(1);
        linearExtender2.setPower(1);
        linearExtender1.setTargetPosition(0);
        linearExtender2.setTargetPosition(0);
    }

    public void cancelRunTo() {
        linearExtender1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearExtender2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearExtender1.setPower(0);
        linearExtender2.setPower(0);
    }

    public List<AprilTagDetection> getAprilTagDetections() {
        return aprilTagProcessor.getDetections();
    }

    public void stopStreaming() {
        visionPortal.stopStreaming();
    }
}

