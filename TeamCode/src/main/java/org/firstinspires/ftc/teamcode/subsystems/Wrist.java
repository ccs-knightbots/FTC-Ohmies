package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Wrist {
    Servo wristServo;

    public Wrist(HardwareMap hwMap) {wristServo = hwMap.get(Servo.class, "wristServo");}

    public void setWristServo(double wristAngle) {wristServo.setPosition(wristAngle);}

    public double getWristRotation() {return wristServo.getPosition();}
    //    Note: the getPosition() method doesn't return the real position, only the set position

    //    The units for this function is rotations. 1 rotation = 360 degrees
}
