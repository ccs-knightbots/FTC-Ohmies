package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner_otos.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.subsystems.*;

public class RobotCore {

    public Claw claw;
    public Wrist wrist;
    public Slides slides;
    public ManualDrive manualDrive;
    public Vision vision;
    public Otos otos;

    public RobotCore(HardwareMap hwMap) {
        claw = new Claw(hwMap);
        wrist = new Wrist(hwMap);
        slides = new Slides(hwMap);
        manualDrive = new ManualDrive(hwMap);
        vision = new Vision(hwMap);
        otos = new Otos(hwMap);
    }
}
