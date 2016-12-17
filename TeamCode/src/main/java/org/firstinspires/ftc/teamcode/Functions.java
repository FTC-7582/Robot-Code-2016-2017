package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

/**
 * Created by 970098955 on 11/22/2016.
 */
public class Functions {

    public static final int SQUARE_ROOT = 0, CIRCLE = 1, REVERSE_SQUARE_ROOT = 2, DAMPENED_CIRCLE = 3;

    public static double getMappedMotorPower(double input, int algorithm){
        //Graphical view of algorithms: https://www.desmos.com/calculator/ync9ayg4dx
        switch (algorithm){
            case SQUARE_ROOT:
                if (input < 0){
                    return -Math.sqrt(Range.clip(-input, 0.0d, 1.0d));
                } else {
                    return Math.sqrt(Range.clip(input, 0.0d, 1.0d));
                }
            case CIRCLE:
                if (input < 0){
                    return -Math.sqrt(Range.clip(((-input) * 2) - Math.pow(Range.clip(-input, 0.0d, 1.0d), 2), 0.0d, 1.0d));
                } else {
                    return Math.sqrt(Range.clip(((input) * 2) - Math.pow(Range.clip(input, 0.0d, 1.0d), 2), 0.0d, 1.0d));
                }
            case REVERSE_SQUARE_ROOT:
                return Range.clip(input * (2 - input), -1.0d, 1.0d);
            case DAMPENED_CIRCLE:
                if (input < 0){
                    return -(1-Math.sqrt(Range.clip(1-Math.pow(-input, 0.8), 0.0d, 1.0d)));
                    //return Math.sqrt(Range.clip(Math.sqrt(-input) * Math.sqrt(Range.clip(((input) * 2) - Math.pow(Range.clip(input, 0.0d, 1.0d), 2), 0.0d, 1.0d)), 0.0d, 1.0d));
                } else {
                    return 1-Math.sqrt(Range.clip(1-Math.pow(input, 0.8), 0.0d, 1.0d));
                    //return -Math.sqrt(Range.clip(Math.sqrt(input) * Math.sqrt(Range.clip(((input) * 2) - Math.pow(Range.clip(input, 0.0d, 1.0d), 2), 0.0d, 1.0d)), 0.0d, 1.0d));
                }
            default:
                return input;
        }
    }
    public static double getMappedMotorPower(double input, int algorithm, double dampening) {
        //Graphical view of algorithms: https://www.desmos.com/calculator/ync9ayg4dx
        if (algorithm == DAMPENED_CIRCLE) {
            if (input < 0){
                return -(1-Math.sqrt(Range.clip(1-Math.pow(-input, dampening), 0.0d, 1.0d)));
            } else {
                return 1-Math.sqrt(Range.clip(1-Math.pow(input, dampening), 0.0d, 1.0d));
            }
        } else return getMappedMotorPower(input, algorithm);
    }

    public static void moveRotations(double speed, double rotations, DcMotor motor){
        int target = motor.getCurrentPosition()+((int)(rotations * 1440));
        motor.setTargetPosition(target);

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(Math.abs(speed));

        while (motor.isBusy());

        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static void moveRotations(double speed, double rotations, DcMotor[] motors){
        boolean motorBusy = false;

        for (DcMotor motor : motors) {

            int target = motor.getCurrentPosition() + ((int) (rotations * 1440));
            motor.setTargetPosition(target);

            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(Math.abs(speed));
        }

        do {
            motorBusy = false;
            for (DcMotor motor : motors)
                if (motor.isBusy()) {
                    motorBusy = true;
                    break;
                }
        } while (motorBusy);

        for (DcMotor motor : motors) {
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public static void driveRotations(LinearOpMode7582 opMode, double speed, double rotations, DcMotor left, DcMotor right){
        int leftTarget = left.getCurrentPosition()+((int)(rotations * 1440));
        int rightTarget = left.getCurrentPosition()-((int)(rotations * 1440));
        left.setTargetPosition(leftTarget);
        right.setTargetPosition(rightTarget);

        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setPower(Math.abs(speed));
        right.setPower(Math.abs(speed));

        while (opMode.opModeIsActive() && (left.isBusy() || right.isBusy())){
            opMode.telemetry.addData("PosLeft", opMode.hardware.leftDrive.getCurrentPosition());
            opMode.telemetry.addData("TarLeft", opMode.hardware.leftDrive.getTargetPosition());
            opMode.telemetry.addData("PosRight", opMode.hardware.rightDrive.getCurrentPosition());
            opMode.telemetry.addData("TarRight", opMode.hardware.rightDrive.getTargetPosition());
            opMode.telemetry.update();
        }

        left.setPower(0);
        right.setPower(0);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
