package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Gripper {
    Servo gripperServo;
    public Gripper(HardwareMap hwMap) {
        gripperServo = hwMap.get(Servo.class, "gripperServo");
    }

    public void closeGripper() {
        gripperServo.setPosition(0);
    }

    public void openGripper() {
        gripperServo.setPosition(.3);
    }

    public void raiseGripper(){ gripperServo.setPosition(.6);}

    public void setGripperServo(double gripperAngle) {gripperServo.setPosition(gripperAngle);}

    public double getGripperRotation() {return gripperServo.getPosition();}
//    Note: the getPosition() method doesn't return the real position, only the set position

    //    The units for these functions are rotations. 1 rotation = 360 degrees

}

