package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanism.MainBoard;
import org.firstinspires.ftc.teamcode.mechanism.Structures;
import org.firstinspires.ftc.teamcode.old_mechanism.ProgrammingBoard;
import org.firstinspires.ftc.teamcode.mechanism.Traction;

@TeleOp
public class MainBot extends OpMode {
    MainBoard Board = new MainBoard();
    // This class directly controls each motor.
    Traction DriveTrain = new Traction(Board);
    // This is the class which does all the computations for each motor's speed.
    Structures CodeStructures = new Structures();

    double axial;
    double lateral;
    double yaw;
    double height;
    double slideLocation = 0;
    boolean alreadyPressed;
    boolean alreadyPressed1;
    boolean state;
    boolean state1;
    double slowDown = 1;


    @Override
    public void init() {
        Board.init(hardwareMap);
// This initializes the hardware map.

        telemetry.addLine("Initialized");
        telemetry.update();

        // By the way, variables defined in here don't extend outside.
    }

    @Override
    public void loop() {
//      This allows the driver to switch between normal and inverted. This is useful because sometimes the robot drives backwards.
        if (CodeStructures.toggle_1(gamepad1.a)){
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
        DriveTrain.controllerDrive(axial*slowDown, lateral*slowDown, yaw*slowDown);

//      This code block moves the slideServo in and out.
        if (gamepad1.dpad_left) {
            slideLocation = .7;
        } else if (gamepad1.dpad_right) {
            slideLocation = 0;
        }
        Board.setSlideServo(slideLocation);

//        This code block runs the linear slides up and down.
        if (gamepad1.dpad_up) {
            Board.runTo(2.15);
        } else if (gamepad1.dpad_down){
            Board.runBack();
        }

//        This toggle block moves the claw servo.
        if (CodeStructures.toggle_2(gamepad2.x)) {
            Board.setClawServo(90);
        } else {
            Board.setClawServo(0);
        }

//        Wrist servo only uses 135 degrees out of the available 270. The other stuff is to allow the [-1, 1,]
//        left_stick_x to take advantage of all the space.
        Board.setWristServo(135*(-gamepad2.left_stick_y + 1) / 2);

//        Debugging information printed on the Driver hub.
        telemetry.addData("Claw rotation: ", Board.getClawRotation());
        telemetry.addData("SlideServo: ", Board.getSlidePosition());
        telemetry.addData("Wrist Rotation: ", Board.getWristRotation());
        telemetry.addData("Linear Extender: ", Board.getLinearExtender1());
        telemetry.addData("Linear Extender: ", Board.getLinearExtender2());

        telemetry.update();




    }
}