package org.firstinspires.ftc.teamcode.auto_ops;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner_otos.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.statemachines.SlidesSM;
import org.firstinspires.ftc.teamcode.statemachines.TongueSM;
import org.firstinspires.ftc.teamcode.utilities.RobotCore;

@Disabled
public class RR_Auto extends OpMode {

    RobotCore robotCore;
    Pose2d beginPose = new Pose2d(12, -60, Math.PI/2);
    SparkFunOTOSDrive drive;

    @Override
    public void init() {
        robotCore = new RobotCore(hardwareMap);
        drive = new SparkFunOTOSDrive(hardwareMap, beginPose);
    }

    public void start() {
        robotCore.slides.slidesSM.transition(SlidesSM.EVENT.ENABLE_RTP);
        robotCore.tongue.tongueSM.transition(TongueSM.EVENT.ENABLE_MANUAL);
        robotCore.ankle.lowerAnkle();
    }

    @Override
    public void loop() {

        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .strafeTo(new Vector2d(50,-36))
                        .strafeTo(new Vector2d(50, 15))
                        .strafeTo(new Vector2d(54, 15))
                        .strafeTo(new Vector2d(48, -54))
                        .strafeTo(new Vector2d(54, 15))
                        .strafeTo(new Vector2d(58, 15))
                        .strafeTo(new Vector2d(58, -54))
                        .strafeTo(new Vector2d(58, 15))
                        .strafeTo(new Vector2d(62, 15))
                        .strafeTo(new Vector2d(62, -54))
                        .build());
        }

    }