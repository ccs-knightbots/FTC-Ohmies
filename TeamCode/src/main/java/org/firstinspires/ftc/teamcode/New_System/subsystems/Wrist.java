package org.firstinspires.ftc.teamcode.New_System.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Wrist {
    Servo wristServo;

    public Wrist(HardwareMap hwMap) {wristServo = hwMap.get(Servo.class, "clawServo");}

    public void setWristServo(double wristAngle) {wristServo.setPosition(wristAngle/270);}
    public double getWristRotation() {return wristServo.getPosition()*270;}
    //    Note: the getPosition() method doesn't return the real position, only the set position


}
