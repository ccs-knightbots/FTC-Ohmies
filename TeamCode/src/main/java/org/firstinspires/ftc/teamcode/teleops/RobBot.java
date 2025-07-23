package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.statemachines.TongueSM;
import org.firstinspires.ftc.teamcode.utilities.*;
import org.firstinspires.ftc.teamcode.statemachines.SlidesSM;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp
public class RobBot extends OpMode {

    RobotCore robotCore;
    Structures structures;

    double axial, lateral, yaw;
    double slowDown = .5;
    double tongueOut, tongueIn;
    boolean alreadyDown;
    boolean finalStage;

    double gripperAngle;
    boolean alreadyPressed1,alreadyPressed2;

    @Override
    public void init() {
        robotCore = new RobotCore(hardwareMap);
        structures = new Structures();
    }

    @Override
    public void init_loop() {telemetryAprilTag();}

    @Override
    public void start() {
        robotCore.vision.stopStreaming();
        robotCore.slides.slidesSM.transition(SlidesSM.EVENT.ENABLE_RTP);
        robotCore.tongue.tongueSM.transition(TongueSM.EVENT.ENABLE_MANUAL);
        robotCore.ankle.lowerAnkle();
    }

    @Override
    public void loop() {

//      This allows the driver to switch between normal and inverted. This is useful because sometimes the robot drives backwards.
        if (structures.toggle_1(gamepad1.a)) {
            axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            lateral = gamepad1.left_stick_x;
        } else {
            axial = gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            lateral = -gamepad1.left_stick_x;
        }
//      Rotation doesn't change even if you invert it.
        yaw = gamepad1.right_stick_x;

//      This can be helpful for precise maneuvering.
        if (gamepad1.right_stick_button) {
            slowDown = 0.5;
        } else if (gamepad1.left_stick_button) {
            slowDown = 1;
        }

//      This function sends the game pad inputs to the Traction class.
        robotCore.manualDrive.controllerDrive(axial * slowDown, lateral * slowDown, yaw * slowDown);

//        This code block runs the linear slides up and down.
        if (gamepad1.dpad_up) {
            robotCore.slides.goUp(.5449);
//            Full run is 2.15
       } else if (gamepad1.dpad_down) {
            robotCore.slides.goDown();
        }

        if (gamepad2.left_stick_button) {
            robotCore.slides.goUp(1.1);
        } else if (gamepad2.right_stick_button) {
//            finalStage = true;
            robotCore.slides.goUp(1.6726);
        }
//        This final stage variable may be useful later. It runs the slides to a different position, and most importantly, stops the slides from disengaging
//        due to the anti-overheating mechanism

        //        To stop the Linear Extenders from wasting battery and heating up, we signal for them to turn off when they reach near 0.
        boolean approximatelyDown = Math.abs(robotCore.slides.getLinearExtenderPos1()) <= .1;
        if (approximatelyDown && !alreadyDown) {
            if (finalStage) {
                robotCore.slides.goDown();
            } else {
                robotCore.slides.cancelRunTo();
            }
        }
        alreadyDown = approximatelyDown;

        //      This toggle block moves the claw servo.
        if (structures.toggle_2(gamepad2.x)) {
            robotCore.claw.openClaw();
        } else {
            robotCore.claw.closeClaw();
        }

        //      This toggle block moves the claw servo.
        if (structures.toggle_4(gamepad2.y)) {
            robotCore.gripper.openGripper();
        } else {
            robotCore.gripper.closeGripper();
        }

        //      This toggle block moves the claw servo.
        if (gamepad2.dpad_up) {
            robotCore.ankle.lowerAnkle();
        } else if (gamepad2.dpad_down) {
            robotCore.ankle.raiseAnkle();
        }

//      This maps the game pad [-1, 1] to the servo [0, .5]. Measured in rotations.
        robotCore.wrist.setWristServo((-gamepad2.left_stick_y + 1) / 4);

        if (robotCore.tongue.getPosition() < -2300) {
            tongueOut = 0;
        } else {
            tongueOut = gamepad2.left_trigger;
        }

        if (robotCore.tongue.getPosition() > -35) {
            tongueIn = 0;
        } else {
            tongueIn = gamepad2.right_trigger;
        }
        robotCore.tongue.setManualPower(tongueIn-tongueOut);

        telemetry.addData("State: ", robotCore.slides.slidesSM.getState());
        telemetry.addData("Slides: ", robotCore.slides.getLinearExtenderPos1());
        telemetry.addData("Claw Rotation: ", robotCore.claw.getClawRotation());
        telemetry.addData("Wrist Rotation: ", robotCore.wrist.getWristRotation());
        telemetry.addData("Gripper Rotation: ", robotCore.gripper.getGripperRotation());
        telemetry.addData("Tongue Distance: ", robotCore.tongue.getPosition());
        telemetry.addData("Ankle Distance: ", robotCore.ankle.getAnklePosition());

    }




// Vision Telemetry defined outside of the loop.

    private void telemetryAprilTag() {
        List<AprilTagDetection> currentDetections = robotCore.vision.getAprilTagDetections();

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");

        telemetry.update();
    }   // end method telemetryAprilTag()


}
