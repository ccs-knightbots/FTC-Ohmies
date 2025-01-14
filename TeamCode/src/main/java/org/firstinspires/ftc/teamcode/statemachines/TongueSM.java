package org.firstinspires.ftc.teamcode.statemachines;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.Tongue;

public class TongueSM {

    Tongue tongue;
    STATE currentState = STATE.MANUAL_CONTROL;

    public enum STATE {
        MANUAL_CONTROL,
        RUN_TO_POSITION
    }

    public enum EVENT {
        ENABLE_MANUAL,
        ENABLE_RTP
    }

    public TongueSM(Tongue tongue) {
        this.tongue = tongue;
    }

    public STATE getState() {
        return currentState;
    }

    public void transition(EVENT event) {
        switch (event) {
            case ENABLE_MANUAL:
                tongue.extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                currentState = STATE.MANUAL_CONTROL;
                break;
            case ENABLE_RTP:
                tongue.extender.setTargetPosition(tongue.getTargetPosition());
                tongue.extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                currentState = STATE.RUN_TO_POSITION;
                break;
        }
    }

}
