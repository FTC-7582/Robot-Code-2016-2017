package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;


@Autonomous(name="Sensor Testing")
public class SensorTesting extends IterativeOpMode7582{

    CompReading compass = new CompReading(this);

    @Override
    public void init() {
        super.init();
        hardware.ballBlocker.setPosition(0.175);
        hardware.color.enableLed(false);
        compass.init();
        hardware.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Functions.wait(this, 0.1);
        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void start() {
        hardware.rightDrive.setPower(0.25);
        hardware.leftDrive.setPower(0.25);
    }

    @Override
    public void loop() {
        telemetry.addData("Left ", hardware.leftDrive.getCurrentPosition());
        telemetry.addData("Right", hardware.rightDrive.getCurrentPosition());
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
