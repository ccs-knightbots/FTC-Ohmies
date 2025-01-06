package org.firstinspires.ftc.teamcode.legacy.years.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.legacy.years.mechanism.MainBoard;
import org.firstinspires.ftc.teamcode.legacy.years.mechanism.Structures;
import org.firstinspires.ftc.teamcode.legacy.years.mechanism.Traction;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Disabled
public class MainBot extends OpMode {
    MainBoard board = new MainBoard();
    // This class directly controls each motor.
    Traction driveTrain = new Traction(board);
    // This is the class which does all the computations for each motor's speed.
    Structures codeStructures = new Structures();

    double axial;
    double lateral;
    double yaw;
    double tongueLocation = .9;
    double slowDown = .5;

    boolean alreadyPressed;
    boolean finalStage;

    @Override
    public void init() {
        board.init(hardwareMap);
//        This initializes the hardware map.

        telemetry.addLine("Initialized");
        telemetry.update();

//        By the way, variables defined in here don't extend outside.
    }

    @Override
    public void init_loop() {
       telemetryAprilTag();
    }

    @Override
    public void start() {
        board.stopStreaming();
    }

    @Override
    public void loop() {
//      This allows the driver to switch between normal and inverted. This is useful because sometimes the robot drives backwards.
        if (codeStructures.toggle_1(gamepad1.a)){
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
        driveTrain.controllerDrive(axial*slowDown, lateral*slowDown, yaw*slowDown);

//      This code block moves the tongueServo in and out.
        if (gamepad2.dpad_down) {
            tongueLocation = .7;
        } else if (gamepad2.dpad_up) {
            tongueLocation = 0;
//            .7 is fully withdrawn
        }
        board.setTongueServo(tongueLocation);

//        This code block runs the linear slides up and down.
        if (gamepad1.dpad_up) {
            board.runTo(2.05);
//            Full run is 2.15
//            Original is 1.7
        } else if (gamepad1.dpad_down){
            board.runBack();
        }

//        To stop the Linear Extenders from wasting battery and heating up, we signal for them to turn off when they reach near 0.
        boolean userInput = Math.abs(board.getLinearExtender1()) <= .1;
        if (userInput && !alreadyPressed) {
            if (finalStage) {
                board.runBack();
            } else {
                board.cancelRunTo();
            }
        }
        alreadyPressed = userInput;

//        This end stage code will be useful later. It runs the slides to a different position, and most importantly, stops the slides from disengaging
//        due to the anti-overheating mechanism
//        if (gamepad2.right_stick_button) {
//            finalStage = true;
//            board.runTo(.7);
//        }

//        This toggle block moves the claw servo.
        if (codeStructures.toggle_2(gamepad2.x)) {
            board.setClawServo(110);
        } else {
            board.setClawServo(0);
        }

//        This maps the game pad [-1, 1] to the servo [0, 135].
        board.setWristServo(270 * (-gamepad2.left_stick_y + 1) / 4);

//        Debugging information printed on the Driver hub.
        telemetry.addData("Claw rotation: ", board.getClawRotation());
        telemetry.addData("SlideServo: ", board.getTonguePosition());
        telemetry.addData("Wrist Rotation: ", board.getWristRotation());
        telemetry.addData("Linear Extender: ", board.getLinearExtender1());
        telemetry.addData("Linear Extender: ", board.getLinearExtender2());

        telemetry.update();

    }

    private void telemetryAprilTag() {
        List<AprilTagDetection> currentDetections = board.getAprilTagDetections();

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