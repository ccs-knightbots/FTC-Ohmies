package org.firstinspires.ftc.teamcode.auto_ops;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.statemachines.SlidesSM;
import org.firstinspires.ftc.teamcode.utilities.RobotCore;

@Autonomous
public class ClipAuto extends OpMode {
    enum State {
        FORWARD,
        RIGHT,
        STOP,
        FINISH
    }
    State state = State.FORWARD;

    RobotCore robotCore;

    @Override
    public void init() {
        robotCore = new RobotCore(hardwareMap);
        robotCore.slides.slidesSM.transition(SlidesSM.EVENT.ENABLE_RTP);
    }


    @Override
    public void start(){
        resetRuntime();
        State state = State.FORWARD;
        robotCore.claw.openClaw();
    }

    public void loop(){
        telemetry.addData("Time", getRuntime());

        switch (state) {
            case FORWARD:
                robotCore.manualDrive.controllerDrive(0,0,0);
                state = State.RIGHT;
                break;

            case RIGHT:
                if (getRuntime() >= 1) {
                    robotCore.manualDrive.controllerDrive(.7,0,0);
                    robotCore.slides.goUp(.5449);
                    robotCore.wrist.setWristServo(1);
                    state = State.STOP;
                }
                break;

            case STOP:
                if (getRuntime() >= 3) {
                    robotCore.manualDrive.controllerDrive(0,0,0);
                    robotCore.wrist.setWristServo(0.5);
                    robotCore.slides.goUp(.2);

                }
                break;
        }

        telemetry.update();

    }
}
