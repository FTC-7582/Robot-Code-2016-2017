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

    public static final int SQUARE_ROOT = 0, CIRCLE = 1, INVERSE_SQUARE_ROOT = 2, SQUARE_ROOT_AND_THREE_HALVES_ROOT_CIRCLE = 3;

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor mLeft;
    DcMotor mRight;
    Servo sBeacon;

    double sPos;

    @Override
    public void init() {
        mLeft = hardwareMap.dcMotor.get("LeftDrive");
        mRight = hardwareMap.dcMotor.get("RightDrive");
        sBeacon = hardwareMap.servo.get("Beacon");

        sPos = 0.8;

        mLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {runtime.reset();}

        @Override
    public void loop() {
            telemetry.addData("Status", "Running: " + runtime.toString());

            mLeft.setPower(getMappedMotorPower(gamepad1.left_stick_y, SQUARE_ROOT));
            mRight.setPower(getMappedMotorPower(gamepad1.right_stick_y, SQUARE_ROOT));

            sPos += (gamepad2.right_trigger-gamepad2.left_trigger)/50;
            sPos = Range.clip(sPos, 0.7, 0.9);
            sBeacon.setPosition(sPos);
            telemetry.addData("Position", sBeacon.getPosition());

            telemetry.addData("Left Motor Power", mLeft.getPower());
            telemetry.addData("Right Motor Power", mRight.getPower());
            telemetry.addData("Servo Position", sBeacon.getPosition() * 180);
    }

    @Override
    public void stop() {
    }

    private double getMappedMotorPower(double input, int algorithm){
        switch (algorithm){
            case SQUARE_ROOT:
                if (input < 0){
                    return Math.sqrt(Range.clip(-input, 0.0d, 1.0d));
                } else {
                    return -Math.sqrt(Range.clip(input, 0.0d, 1.0d));
                }
            case CIRCLE:
                if (input < 0){
                    return Math.sqrt(Range.clip(((-input) * 2) - Math.pow(Range.clip(-input, 0.0d, 1.0d), 2), 0.0d, 1.0d));
                } else {
                    return -Math.sqrt(Range.clip(((input) * 2) - Math.pow(Range.clip(input, 0.0d, 1.0d), 2), 0.0d, 1.0d));
                }
            case INVERSE_SQUARE_ROOT:
                input = -input;
                return Range.clip(input * (2 - input), -1.0d, 1.0d);
            case SQUARE_ROOT_AND_THREE_HALVES_ROOT_CIRCLE:
                if (input < 0){
                    return Math.sqrt(Range.clip(Math.sqrt(-input) * Math.sqrt(Range.clip(((input) * 2) - Math.pow(Range.clip(input, 0.0d, 1.0d), 2), 0.0d, 1.0d)), 0.0d, 1.0d));
                } else {
                    return -Math.sqrt(Range.clip(Math.sqrt(input) * Math.sqrt(Range.clip(((input) * 2) - Math.pow(Range.clip(input, 0.0d, 1.0d), 2), 0.0d, 1.0d)), 0.0d, 1.0d));
                }
            default:
                return -input;
            }
        }
    }

