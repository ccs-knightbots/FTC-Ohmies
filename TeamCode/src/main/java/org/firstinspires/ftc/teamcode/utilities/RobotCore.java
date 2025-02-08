package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner_otos.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.subsystems.*;

public class RobotCore {

    public Claw claw;
    public Wrist wrist;
    public Gripper gripper;
    public Slides slides;
    public ManualDrive manualDrive;
    public Vision vision;
    public Tongue tongue;
    public Ankle ankle;

    public RobotCore(HardwareMap hwMap) {
        claw = new Claw(hwMap);
        wrist = new Wrist(hwMap);
        gripper = new Gripper(hwMap);
        slides = new Slides(hwMap);
        manualDrive = new ManualDrive(hwMap);
        vision = new Vision(hwMap);
        tongue = new Tongue(hwMap);
        ankle = new Ankle(hwMap);
    }
}
