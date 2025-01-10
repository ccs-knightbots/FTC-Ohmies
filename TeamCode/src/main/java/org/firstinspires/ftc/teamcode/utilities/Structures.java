package org.firstinspires.ftc.teamcode.utilities;

public class Structures {

    boolean alreadyPressed;
    boolean alreadyPressed2;
    boolean alreadyPressed3;
    boolean alreadyPressed4;

    boolean state;
    boolean state2;
    boolean state3;
    boolean state4;

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
        if (userInput && !alreadyPressed2) {
            state2 = !state2;
        }
        alreadyPressed2 = userInput;
        return !state2;
    }

    public boolean toggle_3(boolean userInput) {
        if (userInput && !alreadyPressed3) {
            state3 = !state3;
        }
        alreadyPressed3 = userInput;
        return !state3;
    }

    public boolean toggle_4(boolean userInput) {
        if (userInput && !alreadyPressed4) {
            state4 = !state4;
        }
        alreadyPressed4 = userInput;
        return !state4;
    }


}
