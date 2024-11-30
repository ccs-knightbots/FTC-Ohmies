package org.firstinspires.ftc.teamcode.old_opmodes;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.old_mechanism.AutoArmReturn;
import org.firstinspires.ftc.teamcode.old_mechanism.ProgrammingBoard;
import org.firstinspires.ftc.teamcode.mechanism.Traction;

@Disabled
public class CompetitionCode extends OpMode {

    ProgrammingBoard Board = new ProgrammingBoard();
// This class directly controls each motor.
    Traction DriveTrain = new Traction(Board);
    // This is the class which does all the computations for each motor's speed.
    AutoArmReturn armReturn = new AutoArmReturn(Board);
    double axial;
    double lateral;
    double yaw;
    double intakePower;
    double armPower;
    double droneAngle;
    boolean alreadyPressed, alreadyPressed2;
    boolean state, state2;



    @Override
    public void init(){
        Board.init(hardwareMap);
// This initializes the hardware map.

        telemetry.addLine("Initialized");
        telemetry.update();

        // By the way, variables defined in here don't extend outside.
    }

    @Override
    public void loop(){

//         The below "if" block is a toggle. It passes the first gate upon the press of A, but then it changes alreadyPressed, which prevents
//         any further changing of state. In the new state, it redefines the variables to be opposite.

        if (gamepad1.a && !alreadyPressed) {
            state = !state;
        }
        alreadyPressed = gamepad1.a;

        if (!state) {
            axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            lateral =  gamepad1.left_stick_x;
        } else {
            axial   = gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            lateral = -gamepad1.left_stick_x;
        }

        yaw = gamepad1.right_stick_x;
        intakePower = -gamepad1.left_trigger + gamepad1.right_trigger;



        if (gamepad2.x) {
            Board.setClawServo(0);
            telemetry.addLine("x pressed");
            // Claw tightens
        } else if (gamepad2.b) {
            Board.setClawServo(1);
            telemetry.addLine("b pressed");
            // Claw releases
        }

        if (gamepad2.dpad_down) {
            Board.setWristServo(.35);
            // Wrist down
        } else if (gamepad2.dpad_up) {
            Board.setWristServo(0);
            // Wrist up
        }

        if (gamepad2.left_stick_button && gamepad2.right_stick_button) {
            droneAngle = .5;
        }
        // Pressing both sticks shoots the launcher


        // Below is a toggle block. When OFF, it uses normal arm functionality. When ON, it returns arm to pan. You may need
        // to comment it out if it is glitching out.
        if (gamepad2.a && !alreadyPressed2) {
            state2 = !state2;
        }
        alreadyPressed2 = gamepad2.a;

        if (!state2) {
            Board.undoArmLock();
            resetRuntime();

//  Normally this sets the arm power, unless we switch it to the auto-arm mode.
            if (gamepad2.right_bumper && gamepad2.left_bumper) {
                armPower = 1;
            } else {
                armPower = -gamepad2.right_stick_y/2;
            }
        } else {
//          This is the auto-arm mode.
            armReturn.armReturn(getRuntime());
            telemetry.addData("Time", getRuntime());
        }

        // Set arm and intake power based off variables defined above.
        Board.setIntakePower(intakePower);
        Board.setArmPower(armPower);
        telemetry.addData("Arm Position", Board.getArmRotation());
        Board.setDroneServo(droneAngle);

// This function sends the game pad inputs to the Traction class.
        DriveTrain.controllerDrive(axial, lateral, yaw);

    }
    }