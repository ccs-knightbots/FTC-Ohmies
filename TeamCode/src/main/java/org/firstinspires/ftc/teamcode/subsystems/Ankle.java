package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Ankle {
    Servo ankleServo;

    public Ankle(HardwareMap hwMap) {ankleServo = hwMap.get(Servo.class, "ankleServo");}

    public void setAnkleServo(double ankleAngle) {ankleServo.setPosition(ankleAngle);}

    public void raiseAnkle() {
        ankleServo.setPosition(.6);
    }

    public void lowerAnkle() {
        ankleServo.setPosition(.82);
    }

    public double getAnklePosition() {return ankleServo.getPosition();}
    //    Note: the getPosition() method doesn't return the real position, only the set position

    //    The units for this function is rotations. 1 rotation = 360 degrees
}
