package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanism.MainBoard;
import org.firstinspires.ftc.teamcode.mechanism.Structures;
import org.firstinspires.ftc.teamcode.mechanism.Traction;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp
public class MainBot extends OpMode {
    MainBoard board = new MainBoard();
    // This class directly controls each motor.
    Traction driveTrain = new Traction(board);
    // This is the class which does all the computations for each motor's speed.
    Structures codeStructures = new Structures();

    double axial;
    double lateral;
    double yaw;
    double slideLocation = .7;
    double slowDown = 1;

    boolean alreadyPressed;

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
        List<AprilTagDetection> currentDetections = board.getAprilTagDetections();
        StringBuilder idsFound = new StringBuilder();

        for (AprilTagDetection detection: currentDetections) {
            idsFound.append(detection.id);
            idsFound.append(" ");
        }

        telemetry.addData("April Tags", idsFound);
        telemetry.update();
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

//      This code block moves the slideServo in and out.
        if (gamepad1.dpad_left) {
            slideLocation = .7;
        } else if (gamepad1.dpad_right) {
            slideLocation = 0;
        }
        board.setSlideServo(slideLocation);

//        This code block runs the linear slides up and down.
        if (gamepad1.dpad_up) {
            board.runTo(1.7);
            telemetry.addLine("Working hard");

//            Full run is 2.15
        } else if (gamepad1.dpad_down){
            telemetry.addLine("Working hard");
            board.runBack();
        }

//        To stop the Linear Extenders from wasting battery and heating up, we signal for them to turn off when they reach near 0.
        boolean userInput = Math.abs(board.getLinearExtender1()) <= .1;
        if (userInput && !alreadyPressed) {
            board.cancelRunTo();
        }
        alreadyPressed = userInput;

//        This toggle block moves the claw servo.
        if (codeStructures.toggle_2(gamepad2.x)) {
            board.setClawServo(90);
        } else {
            board.setClawServo(0);
        }

//        Wrist servo only uses 135 degrees out of the available 270. The other stuff is to allow the [-1, 1,]
//        left_stick_x to take advantage of all the space.
        board.setWristServo(135*(-gamepad2.left_stick_y + 1) / 2);

//        Debugging information printed on the Driver hub.
        telemetry.addData("Claw rotation: ", board.getClawRotation());
        telemetry.addData("SlideServo: ", board.getSlidePosition());
        telemetry.addData("Wrist Rotation: ", board.getWristRotation());
        telemetry.addData("Linear Extender: ", board.getLinearExtender1());
        telemetry.addData("Linear Extender: ", board.getLinearExtender2());

        telemetry.update();

    }
}