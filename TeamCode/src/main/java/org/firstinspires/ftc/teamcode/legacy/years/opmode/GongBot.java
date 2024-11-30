package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanism.GongBoard;
import org.firstinspires.ftc.teamcode.mechanism.Structures;
import org.firstinspires.ftc.teamcode.mechanism.Traction;

@TeleOp
public class GongBot extends OpMode{
    GongBoard board = new GongBoard();
    Traction driveTrain = new Traction(board);
    Structures codeStructures = new Structures();

    double axial;
    double lateral;
    double yaw;

    double armPower;

    @Override
    public void init() {
        board.init(hardwareMap);
        telemetry.addLine("Initialized");
        telemetry.update();
    }
    @Override
    public void loop() {
        if (codeStructures.toggle_1(gamepad1.a)){
            axial = -gamepad1.left_stick_y;
            lateral = gamepad1.left_stick_x;
        } else {
            axial = gamepad1.left_stick_y;
            lateral = -gamepad1.left_stick_x;        }
        yaw = gamepad1.right_stick_x;

        armPower = gamepad2.left_stick_x;
        board.setArmMotor(armPower);
    }
}
