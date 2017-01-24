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
    boolean prevFP = false, reverse = false, prevRP = false;
    float multiplier = 1;

    @Override
    public void init() {
        super.init();
        sPos = 0.8;
        hardware.buttonPusher.setPosition(sPos);
        hardware.ballBlocker.setPosition(0.175);
    }

    @Override
    public void loop() {
        try {
            //Prints Runtime
            telemetry.addData("Status", "Running: " + runtime.toString());

            if (gamepad1.left_bumper && !prevRP){
                reverse = !reverse;
                prevRP = true;
            } else if (!gamepad1.left_bumper) prevRP = false;

            telemetry.addData("Direction", (reverse) ? "Reverse" : "Forward");
            telemetry.addData("Speed", (gamepad1.right_bumper) ? "Half" : "Full");

            //Drive
            if (gamepad1.right_bumper) multiplier = 0.5f;
            else multiplier = 1;
            if (reverse) {
                if ((-gamepad1.left_stick_y > 0 && -gamepad1.right_stick_y < 0) || (-gamepad1.left_stick_y < 0 && -gamepad1.right_stick_y > 0)) {
                    hardware.leftDrive.setPower(multiplier * -Functions.getMappedMotorPower(-gamepad1.right_stick_y, Functions.Algorithms.COSINE));
                    hardware.rightDrive.setPower(multiplier * Functions.getMappedMotorPower(-gamepad1.left_stick_y, Functions.Algorithms.COSINE));
                } else {
                    hardware.leftDrive.setPower(multiplier * -Functions.getMappedMotorPower(-gamepad1.right_stick_y, Functions.Algorithms.COSINE) / 3);
                    hardware.rightDrive.setPower(multiplier * Functions.getMappedMotorPower(-gamepad1.left_stick_y, Functions.Algorithms.COSINE) / 3);
                }
            } else {
                if ((-gamepad1.left_stick_y > 0 && -gamepad1.right_stick_y < 0) || (-gamepad1.left_stick_y < 0 && -gamepad1.right_stick_y > 0)) {
                    hardware.leftDrive.setPower(multiplier * Functions.getMappedMotorPower(-gamepad1.left_stick_y, Functions.Algorithms.COSINE));
                    hardware.rightDrive.setPower(multiplier * -Functions.getMappedMotorPower(-gamepad1.right_stick_y, Functions.Algorithms.COSINE));
                } else {
                    hardware.leftDrive.setPower(multiplier * Functions.getMappedMotorPower(-gamepad1.left_stick_y, Functions.Algorithms.COSINE) / 3);
                    hardware.rightDrive.setPower(multiplier * -Functions.getMappedMotorPower(-gamepad1.right_stick_y, Functions.Algorithms.COSINE) / 3);
                }
            }

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
            if (hardware.collector.getPower() == -1){
                if (!prevFP){
                    runtime.reset();
                    prevFP = !prevFP;
                } else {
                    if (runtime.seconds() < 1);
                    else hardware.ballBlocker.setPosition(0.5);
                }
            }
            else if (hardware.collector.getPower() == 1){
                hardware.ballBlocker.setPosition(0.5);
            } else {
                hardware.ballBlocker.setPosition(0.175);
                prevFP = false;
            }
            telemetry.addData("Servo Pos", hardware.ballBlocker.getPosition());

            if (gamepad2.x){
                hardware.launcher.setPower(-1);
            } else {
                hardware.launcher.setPower(0);
            }

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

