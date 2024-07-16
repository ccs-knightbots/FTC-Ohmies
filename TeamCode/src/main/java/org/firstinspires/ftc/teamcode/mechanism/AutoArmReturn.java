package org.firstinspires.ftc.teamcode.mechanism;

import org.firstinspires.ftc.teamcode.opmode.BlueLeft;

public class AutoArmReturn {
    enum State {
        RETURN,
        LOWER,
        BACK,
        STOP,
        FINISH
    }
    ProgrammingBoard localBoard;
    public AutoArmReturn(ProgrammingBoard activeBoard) {
        localBoard = activeBoard;
    }

    State state = State.RETURN;
    ProgrammingBoard Board = new ProgrammingBoard();

    public void armReturn(double runTime){

        switch (state) {
            case RETURN:
                Board.setClawServo(0);
                Board.setWristServo(0);
                Board.setArmAngle(.011486);
                state = State.LOWER;
                break;
            case LOWER:
                if (runTime >= 3) {
                    Board.setWristServo(.35);
                }
                break;
        }

    }
}
