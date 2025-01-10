package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Gripper {
    Servo gripperServo;
    public Gripper(HardwareMap hwMap) {
        gripperServo = hwMap.get(Servo.class, "gripperServo");
    }

    public void closeClaw() {
        gripperServo.setPosition(0);
    }

    public void openClaw() {
        gripperServo.setPosition(.5);
    }

    public void setClawServo(double clawAngle) {gripperServo.setPosition(clawAngle);}

    public double getClawRotation() {return gripperServo.getPosition();}
//    Note: the getPosition() method doesn't return the real position, only the set position

    //    The units for these functions are rotations. 1 rotation = 360 degrees

}

