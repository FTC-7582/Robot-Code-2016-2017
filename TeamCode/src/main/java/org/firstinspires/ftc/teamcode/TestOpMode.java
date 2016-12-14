package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by 970098955 on 11/22/2016.
 */

@TeleOp(name="TestOpMode")
public class TestOpMode extends OpMode{

    private ElapsedTime runtime = new ElapsedTime();
    HardwareZeroTurn hardware;

    int i = 0;

    @Override
    public void init(){
        hardware = new HardwareZeroTurn(hardwareMap, HardwareZeroTurn.ENCODERS);
    }

    @Override
    public void start() {
        runtime.reset();
        //hardware.leftDrive.resetDeviceConfigurationForOpMode();
        //hardware.leftDrive.setTargetPosition(i);
        //while (hardware.leftDrive.isBusy());
    }

    @Override
    public void loop(){

        hardware.leftDrive.setPower(-gamepad1.left_stick_y);
        telemetry.addData("Left", hardware.leftDrive.getCurrentPosition());
        telemetry.addData("Max Speed", hardware.leftDrive.getMaxSpeed());
        //telemetry.addData("Motor 2", hardware.rightDrive.getCurrentPosition());

        //hardware.leftDrive.setTargetPosition(hardware.leftDrive.getTargetPosition() - gamepad1.left_stick_y);

        //hardware.leftDrive.setPower(-gamepad1.left_stick_y);
        //hardware.rightDrive.setPower(-gamepad1.right_stick_y);

        /*
        telemetry.addData("Left Motor", hardware.leftDrive.getPower());
        telemetry.addData("Left Encoder", hardware.leftDrive.getTargetPosition());
        telemetry.addData("Left Stick", -gamepad1.left_stick_y);
        telemetry.addData("Right Motor", hardware.rightDrive.getPower());
        telemetry.addData("Right Encoder", hardware.rightDrive.getTargetPosition());
        telemetry.addData("Right Stick", -gamepad1.right_stick_y);
        */
    }

}
