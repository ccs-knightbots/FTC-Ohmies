package org.firstinspires.ftc.teamcode.auto_ops;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.utilities.RobotCore;

@Autonomous
public class AutoRR extends OpMode {

    RobotCore robotCore;
    @Override
    public void init() {
        robotCore = new RobotCore(hardwareMap);
    }

    @Override
    public void loop() {

//        Add in our commands here

    }
}
