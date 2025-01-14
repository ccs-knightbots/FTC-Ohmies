package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.statemachines.TongueSM;

public class Tongue {

    public DcMotor extender;

    public TongueSM tongueSM;

    double targetPosition = 0;
    int tickPerRotation;

    public Tongue(HardwareMap hwMap) {
        extender = hwMap.get(DcMotor.class, "tongueMotor");

        extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        extender.setDirection(DcMotor.Direction.FORWARD);

        extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        tongueSM = new TongueSM(this);
//      By invoking the constructor SlidesSM(), we pass the SlidesSM object the specific slides we are using for that state machine.
//      We are giving it "this", which is a pointer that redirects to the constructor of the current file we are in (the Slides file.
//      Thereby, we are giving it the slides which we have just built.

        tickPerRotation = (int) extender.getMotorType().getTicksPerRev();
    }

    public void setTargetPosition(double targetPosition) {
        this.targetPosition = targetPosition;
        extender.setTargetPosition((int) targetPosition);
    }

    public int getTargetPosition() {
        return (int) targetPosition;
    }

    public void setPower(double power) {
        extender.setPower(power);
    }

    public void setManualPower(double power){
        extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extender.setPower(power);
    }

    public double getPosition(){
      return extender.getCurrentPosition();
    }

    public void goUp(double location) {
        setPower(1);
        setTargetPosition((int) (location*(.5*tickPerRotation)));
    }

    public void goDown() {
        setPower(1);
        setTargetPosition(0);
    }

    public void cancelRunTo() {
       setPower(0);
    }

    public double getExtender() {return extender.getCurrentPosition()/(.5*tickPerRotation);}
    public double getLinearExtender2() {return extender.getCurrentPosition()/(.5*tickPerRotation);}
//    For some reason, the FTC ticksPerRotation is 2x what it should be.
}
