package org.firstinspires.ftc.teamcode.auto_opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.mechanism.MainBoard;
import org.firstinspires.ftc.teamcode.mechanism.Traction;

@Autonomous
public class UniversalAuto extends OpMode {
    enum State {
        FORWARD,
        RIGHT,
        STOP
    }
    State state = State.FORWARD;


    MainBoard board = new MainBoard();
    Traction driveTrain = new Traction(board);

    @Override
    public void init() {
        board.init(hardwareMap);
    }

    @Override
    public void start(){
        resetRuntime();
        State state = State.FORWARD;
        board.setClawServo(0);
    }

    public void loop(){
        telemetry.addData("Time", getRuntime());

        switch (state) {
            case FORWARD:
                driveTrain.controllerDrive(.5, 0, 0);
                state = State.RIGHT;
                break;

            case RIGHT:
                if (getRuntime() >= .2) {
                    driveTrain.controllerDrive(0, .5, 0);
                    state = State.STOP;
                }
                break;

            case STOP:
                if (getRuntime() >= 5) {
                    driveTrain.controllerDrive(0, 0, 0);
                }
                break;
        }

        telemetry.update();

    }
}

