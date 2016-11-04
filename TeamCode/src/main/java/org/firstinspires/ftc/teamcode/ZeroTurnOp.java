package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.Iterator;

/**
 * Created by 970098955 on 10/29/2016.
 */

@TeleOp(name = "ZeroTurn")
public class ZeroTurnOp extends OpMode{

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor mLeft;
    DcMotor mRight;

    @Override
    public void init() {
        mLeft = hardwareMap.dcMotor.get("M1");
        mRight = hardwareMap.dcMotor.get("M2");
        telemetry.addData("Status", "Initialized");

        mLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {runtime.reset();}

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());

        mLeft.setPower(((gamepad1.left_stick_y < 0) ? -Math.sqrt(Range.clip(-gamepad1.left_stick_y, 0.0d, 1.0d)) : Math.sqrt(Range.clip(gamepad1.left_stick_y, 0.0d, 1.0d))) / 3);
        mRight.setPower(((gamepad1.right_stick_y < 0) ? Math.sqrt(Range.clip(-gamepad1.right_stick_y, 0.0d, 1.0d)) : -Math.sqrt(Range.clip(gamepad1.right_stick_y, 0.0d, 1.0d))) / 3);

        //telemetry.addData("Left Motor Power", Math.sqrt(Range.clip(gamepad1.left_stick_y, -1.0d, 1.0d)));
        //telemetry.addData("Right Motor Power", Math.sqrt(Range.clip(gamepad1.right_stick_y, -1.0d, 1.0d)));
        telemetry.addData("Left Motor Power", mLeft.getPower());
        telemetry.addData("Right Motor Power", mRight.getPower());
        telemetry.addData("Left Control Stick", -gamepad1.left_stick_y);
        telemetry.addData("Right Control Stick", -gamepad1.right_stick_y);

        mLeft.setPower((mLeft.getPower() > 0) ? Range.clip(mLeft.getPower() - 0.01, 0, mLeft.getPower()) : Range.clip(mLeft.getPower() + 0.01, 0, mLeft.getPower()));
        mRight.setPower((mRight.getPower() > 0) ? Range.clip(mRight.getPower() - 0.01, 0, mRight.getPower()) : Range.clip(mRight.getPower() + 0.01, 0, mRight.getPower()));
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
