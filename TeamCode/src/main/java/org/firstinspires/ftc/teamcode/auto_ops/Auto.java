package org.firstinspires.ftc.teamcode.auto_ops;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.statemachines.SlidesSM;
import org.firstinspires.ftc.teamcode.statemachines.TongueSM;
import org.firstinspires.ftc.teamcode.utilities.RobotCore;

@Autonomous
public class Auto extends OpMode {

    RobotCore robotCore;

    @Override
    public void init() {
        robotCore = new RobotCore(hardwareMap);
    }

    public void start() {
        robotCore.manualDrive.controllerDrive(0, .5, 0);

        robotCore.slides.slidesSM.transition(SlidesSM.EVENT.ENABLE_RTP);
        robotCore.tongue.tongueSM.transition(TongueSM.EVENT.ENABLE_MANUAL);
        robotCore.ankle.lowerAnkle();
    }

    @Override
    public void loop() {
        if (getRuntime() > 2) {
            robotCore.manualDrive.controllerDrive(0, 0, 0);
        }



    }
}
