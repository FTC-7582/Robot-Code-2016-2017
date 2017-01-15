package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;

/**
 * Created by 970098955 on 10/29/2016.
 */

@TeleOp(name = "ZeroTurn")
public class ZeroTurnTele extends IterativeOpMode7582{

    //Important: Code commented with //++ are planned code additions. If you see code like this, please try to acquaint yourself
    //     with the updated code. Planned code removals will start and end with //--. Code changes prepared in such a manner
    //     are likely already functional or near functional, but their implementation is being delayed.

    double sPos;

    @Override
    public void init() {
        super.init();
        sPos = 0.8;
        hardware.buttonPusher.setPosition(sPos);
        hardware.ballBlocker.setPosition(0.5);
    }

    @Override
    public void loop() {
        try {
            //Prints Runtime
            telemetry.addData("Status", "Running: " + runtime.toString());

            //Drive
            hardware.leftDrive.setPower(Functions.getMappedMotorPower(-gamepad1.left_stick_y, Functions.DAMPENED_CIRCLE) / 3);
            hardware.rightDrive.setPower(-Functions.getMappedMotorPower(-gamepad1.right_stick_y, Functions.DAMPENED_CIRCLE) / 3);

            //Collector
            if (gamepad2.a) {
                hardware.collector.setPower(Functions.clampMax(hardware.collector.getPower() + 0.05, 1));
            } else if (gamepad2.y) {
                hardware.collector.setPower(Functions.clampMin(hardware.collector.getPower() - 0.05, -1));
            } else {
                if (hardware.collector.getPower() > 0) {
                    hardware.collector.setPower(Functions.clampMin(hardware.collector.getPower() - 0.015, 0));
                } else if (hardware.collector.getPower() < 0) {
                    hardware.collector.setPower(Functions.clampMax(hardware.collector.getPower() + 0.015, 0));
                }
            }


            //Ball Blocker
            if (hardware.collector.getPower() == 1 || hardware.collector.getPower() == -1) hardware.ballBlocker.setPosition(0.5);
            else hardware.ballBlocker.setPosition(0.1);

            //Beacon
            sPos += (gamepad2.right_trigger - gamepad2.left_trigger) / 50;
            sPos = Range.clip(sPos, 0.7, 0.9);
            hardware.buttonPusher.setPosition(sPos);

            //Telemetry
            telemetry.addData("Left Joystick", -gamepad1.left_stick_y);
            telemetry.addData("Left Motor Power", hardware.leftDrive.getPower());
            telemetry.addData("Right Joystick", -gamepad1.right_stick_y);
            telemetry.addData("Right Motor Power", -hardware.rightDrive.getPower());
            telemetry.addData("Servo Position", hardware.buttonPusher.getPosition() * 180);
        } catch (Exception e){}
    }
}

