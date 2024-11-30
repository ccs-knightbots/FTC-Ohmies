package org.firstinspires.ftc.teamcode.old_opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.old_mechanism.Robot2Board;
import org.firstinspires.ftc.teamcode.mechanism.Traction;

@Disabled
public class TrainingBot extends OpMode {
    Robot2Board Board = new Robot2Board();
    Traction DriveTrain = new Traction(Board);
    double axial, lateral, yaw;
    @Override
    public void init(){
        Board.init(hardwareMap);
    }
    @Override
    public void loop(){
        axial = gamepad1.left_stick_y;
        lateral = -gamepad1.left_stick_x;
        yaw = -gamepad1.right_stick_x;

        DriveTrain.controllerDrive(axial, lateral, yaw);
    }
}
