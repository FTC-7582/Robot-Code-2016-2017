package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by 970098955 on 10/29/2016.
 */

@TeleOp(name = "ZeroTurn")
public class ZeroTurnOp extends OpMode{

    //Important: Code commented with //++ are planned code additions. If you see code like this, please try to acquaint yourself
    //     with the updated code. Planned code removals will start and end with //--. Code changes prepared in such a manner
    //     are likely already functional or near functional, but their implementation is being delayed.

    private ElapsedTime runtime = new ElapsedTime();

    HardwareZeroTurn hardware;

    DcMotor mLeft, mRight;
    DcMotor mCollector;
    Servo sBeacon;

    double sPos;

    @Override
    public void init() {
        hardware = new HardwareZeroTurn(hardwareMap, HardwareZeroTurn.NO_ENCODERS);

        sPos = 0.8;
    }

    @Override
    public void start() {runtime.reset();}

        @Override
    public void loop() {
            //Prints Runtime
            telemetry.addData("Status", "Running: " + runtime.toString());

            //Drive
            hardware.leftDrive.setPower(Functions.getMappedMotorPower(-gamepad1.left_stick_y, Functions.DAMPENED_CIRCLE)/3);
            hardware.rightDrive.setPower(Functions.getMappedMotorPower(-gamepad1.right_stick_y, Functions.DAMPENED_CIRCLE)/3);

            //Collector
            hardware.collector.setPower(((gamepad2.a) ? 1 : 0) - ((gamepad2.b) ? 1 : 0));

            //Beacon
            sPos += (gamepad2.right_trigger-gamepad2.left_trigger)/50;
            sPos = Range.clip(sPos, 0.7, 0.9);
            hardware.buttonPusher.setPosition(sPos);

            //Telemetry
            telemetry.addData("Left Joystick", -gamepad1.left_stick_y);
            telemetry.addData("Left Motor Power", hardware.leftDrive.getPower());
            telemetry.addData("Left Joystick", -gamepad1.right_stick_y);
            telemetry.addData("Right Motor Power", hardware.rightDrive.getPower());
            telemetry.addData("Servo Position", hardware.buttonPusher.getPosition() * 180);
    }
}

