package org.firstinspires.ftc.teamcode.statemachines;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.Slides;

public class SlidesSM {

    Slides slides;
    STATE currentState = STATE.MANUAL_CONTROL;

    public enum STATE {
        MANUAL_CONTROL,
        RUN_TO_POSITION
    }

    public enum EVENT {
        ENABLE_MANUAL,
        ENABLE_RTP
    }

    public SlidesSM(Slides slides) {
        this.slides = slides;
    }

    public STATE getState() {
        return currentState;
    }

    public void transition(EVENT event) {
        switch (event) {
            case ENABLE_MANUAL:
                slides.linearExtender1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                slides.linearExtender2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                currentState = STATE.MANUAL_CONTROL;
                break;
            case ENABLE_RTP:
                slides.linearExtender1.setTargetPosition(slides.getTargetPosition());
                slides.linearExtender2.setTargetPosition(slides.getTargetPosition());
                slides.linearExtender1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slides.linearExtender2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                currentState = STATE.RUN_TO_POSITION;
                break;
        }
    }

}
