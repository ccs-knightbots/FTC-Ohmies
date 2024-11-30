package org.firstinspires.ftc.teamcode.auto_ops;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.legacy.years.auto_opmodes.UnivAuto;
import org.firstinspires.ftc.teamcode.legacy.years.mechanism.MainBoard;
import org.firstinspires.ftc.teamcode.legacy.years.mechanism.Traction;
import org.firstinspires.ftc.teamcode.utilities.RobotCore;

@Autonomous
public class UniversalAuto extends OpMode {
    enum State {
        FORWARD,
        RIGHT,
        STOP
    }
    State state = State.FORWARD;

    RobotCore robotCore;

    @Override
    public void init() {robotCore = new RobotCore(hardwareMap);}


    @Override
    public void start(){
        resetRuntime();
        State state = State.FORWARD;
        robotCore.claw.closeClaw();
    }

    public void loop(){
        telemetry.addData("Time", getRuntime());

        switch (state) {
            case FORWARD:
                robotCore.manualDrive.controllerDrive(.5,0,0);
                state = State.RIGHT;
                break;

            case RIGHT:
                if (getRuntime() >= .2) {
                    robotCore.manualDrive.controllerDrive(0,.5,0);
                    state = State.STOP;
                }
                break;

            case STOP:
                if (getRuntime() >= 5) {
                    robotCore.manualDrive.controllerDrive(0,0,0);
                }
                break;
        }

        telemetry.update();

    }
}
