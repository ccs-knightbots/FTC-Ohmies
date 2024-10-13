package org.firstinspires.ftc.teamcode.mechanism;

public class Structures {

    boolean alreadyPressed;
    boolean alreadyPressed1;

    boolean state;
    boolean state1;

//    The below "if" block is a toggle. It passes the first gate upon the press of A, but then it changes alreadyPressed, which prevents
//    any further changing of state. In the new state, it redefines the variables to be opposite.

    public boolean toggle_1(boolean userInput) {
        if (userInput && !alreadyPressed) {
            state = !state;
        }
        alreadyPressed = userInput;
        return !state;
    }

    public boolean toggle_2(boolean userInput) {
        if (userInput && !alreadyPressed1) {
            state1 = !state1;
        }
        alreadyPressed1 = userInput;
        return !state1;
    }


}
