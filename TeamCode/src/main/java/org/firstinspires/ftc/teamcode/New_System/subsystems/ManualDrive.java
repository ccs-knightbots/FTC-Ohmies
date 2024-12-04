package org.firstinspires.ftc.teamcode.New_System.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ManualDrive {
    public DcMotor leftFrontMotor_0;
    public DcMotor leftBackMotor_1;
    public DcMotor rightBackMotor_2;
    public DcMotor rightFrontMotor_3;

    public ManualDrive(HardwareMap hwMap) {
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
    }

    public void setDCMotorPower(double leftFrontPower, double leftBackPower, double rightFrontPower, double rightBackPower){
        leftFrontMotor_0.setPower(leftFrontPower);
        leftBackMotor_1.setPower(leftBackPower);
        rightBackMotor_2.setPower(rightBackPower);
        rightFrontMotor_3.setPower(rightFrontPower);
        // Method allows for other classes to set the speed of each motor.
        // If hardware team mixes up the cord (as always), comment out the above commands to see which one is which.
    }

    public void controllerDrive(double axial, double lateral, double yaw){
        // Function takes in desired directions as parameters.

        double leftFrontPower  = axial + lateral + yaw;
        double leftBackPower   = axial + lateral - yaw;
        double rightFrontPower = axial - lateral - yaw;
        double rightBackPower  = axial - lateral + yaw;
        // Calculates necessary motor speeds.
        // These calculations are dependent on this orientation:      \ /
        // Look up a mecanum diagram to see how this works.           / \

        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(rightBackPower));
        max = Math.max(max, Math.abs(leftBackPower));
        // Finds max value.


        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            rightBackPower   /= max;
            leftBackPower  /= max;
        }
        // Readjusts for max value to preserve the intention of the controller.

        setDCMotorPower(leftFrontPower, rightBackPower, rightFrontPower, leftBackPower);
        // Sends the desired inputs over to the Board class.

    }
}
