package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo clawServo;
    public Claw(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "clawServo");
    }

    public void closeClaw() {
        clawServo.setPosition(0);
    }

    public void openClaw() {
        clawServo.setPosition(.5);
    }

    public void setClawServo(double clawAngle) {clawServo.setPosition(clawAngle);}

    public double getClawRotation() {return clawServo.getPosition();}
//    Note: the getPosition() method doesn't return the real position, only the set position

    //    The units for these functions are rotations. 1 rotation = 360 degrees

}
