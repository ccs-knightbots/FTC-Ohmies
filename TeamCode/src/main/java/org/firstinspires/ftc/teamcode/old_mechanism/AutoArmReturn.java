package org.firstinspires.ftc.teamcode.old_mechanism;

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
                // Change the number above to change the angle. It is the same as the angle displayed on the Driver Hub.
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
