package org.firstinspires.ftc.teamcode.auto_ops;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner_otos.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.statemachines.SlidesSM;
import org.firstinspires.ftc.teamcode.utilities.RobotCore;

@Autonomous
public class ClipAuto extends OpMode {
    enum State {
        WAIT,
        FORWARD,
        BACK,
        END,
        RR
    }
    State state = State.WAIT;

    RobotCore robotCore;
    Pose2d beginPose = new Pose2d(12, -60, Math.PI/2);
    SparkFunOTOSDrive drive;

    @Override
    public void init() {
        robotCore = new RobotCore(hardwareMap);
        robotCore.slides.slidesSM.transition(SlidesSM.EVENT.ENABLE_RTP);
        drive = new SparkFunOTOSDrive(hardwareMap, beginPose);

    }


    @Override
    public void start(){
        resetRuntime();
        State state = State.FORWARD;
    }

    public void loop(){
        telemetry.addData("Time", getRuntime());

        switch (state) {
            case WAIT:
                state = State.FORWARD;
                robotCore.claw.openClaw();
                robotCore.ankle.raiseAnkle();
                break;

            case FORWARD:
                if (getRuntime() >= 1) {
                    robotCore.manualDrive.controllerDrive(.7,0,0);
                    robotCore.slides.goUp(.54);
                    robotCore.wrist.setWristServo(1);
                    state = State.BACK;
                }
                break;

            case BACK:
                if (getRuntime() >= 3) {
                    robotCore.manualDrive.controllerDrive(-.3,0,0);
                    robotCore.claw.closeClaw();
                    robotCore.wrist.setWristServo(0.48);
                    state = State.END;
                }
                break;

            case END:
                if (getRuntime() >= 5) {
                    robotCore.manualDrive.controllerDrive(0,0,0);
                    robotCore.slides.goDown();
                    state = State.RR;
                }
                break;

            case RR:
                if (getRuntime() >= 6) {
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .strafeTo(new Vector2d(36,-36))
                                    .strafeTo(new Vector2d(48, 0))
                                    .strafeTo(new Vector2d(54, 0))
                                    .strafeTo(new Vector2d(48, -54))
                                    .strafeTo(new Vector2d(54, 0))
                                    .strafeTo(new Vector2d(58, 0))
                                    .strafeTo(new Vector2d(58, -54))
                                    .strafeTo(new Vector2d(58, 0))
                                    .strafeTo(new Vector2d(62, 0))
                                    .strafeTo(new Vector2d(62, -54))

                                    .build());
                }
                break;

        }

        telemetry.update();

    }
}
