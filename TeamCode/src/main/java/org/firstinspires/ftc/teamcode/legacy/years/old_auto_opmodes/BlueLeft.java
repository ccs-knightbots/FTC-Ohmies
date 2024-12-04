package org.firstinspires.ftc.teamcode.legacy.years.old_auto_opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.legacy.years.old_mechanism.ProgrammingBoard;
import org.firstinspires.ftc.teamcode.legacy.years.mechanism.Traction;

@Disabled
public class BlueLeft extends OpMode {

    enum State {
        LEFT,
        JOLT,
        BACK,
        STOP,
        FINISH
    }

    State state = State.LEFT;
    ProgrammingBoard Board = new ProgrammingBoard();
    Traction DriveTrain = new Traction(Board);
    @Override
    public void init() {
        Board.init(hardwareMap);
    }
    @Override
    public void start(){
        resetRuntime();
        State state = State.LEFT;

    }

    @Override
    public void loop(){
        telemetry.addData("Time", getRuntime());

        switch (state) {
            case LEFT:
                DriveTrain.controllerDrive(0, -.5, 0);
                state = State.JOLT;
                break;

            case JOLT:
                if (getRuntime() >= 2.25) {
                    DriveTrain.controllerDrive(.5, 0, 0);
                    state = State.BACK;
                    Board.setIntakePower(-1);
                }
                break;

            case BACK:
                if (getRuntime() >= 2.5) {
                    DriveTrain.controllerDrive(-.5, 0, 0);
                    state = State.STOP;
                }
                break;
            case STOP:
                if (getRuntime() >= 2.75) {
                    DriveTrain.controllerDrive(0, 0, 0);
                    state = State.FINISH;
                }

            case FINISH:
                if (getRuntime() >= 10) {
                    DriveTrain.controllerDrive(0, 0, 0);
                    Board.setIntakePower(0);
                }
                break;

        }
        
        telemetry.update();

    }
}
