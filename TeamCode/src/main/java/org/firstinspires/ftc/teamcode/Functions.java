package org.firstinspires.ftc.teamcode;

import android.provider.ContactsContract;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
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
        int target = motor.getCurrentPosition()+((int)(rotations * 1400));
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

            int target = motor.getCurrentPosition() + ((int) (rotations * 1400));
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

    public static void driveRotations2(LinearOpMode7582 opMode, double speed, double rotations, DcMotor left, DcMotor right){
        int target = left.getCurrentPosition()+((int)(rotations * 1440));

        opMode.runtime.reset();

        left.setPower(speed);
        right.setPower(-speed);

        while (left.getCurrentPosition() < (target - 10)){
            opMode.telemetry.addData("Time", opMode.runtime.seconds());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("PosLeft", opMode.hardware.leftDrive.getCurrentPosition());
            opMode.telemetry.addData("PosRight", opMode.hardware.rightDrive.getCurrentPosition());
            opMode.telemetry.update();
        }

        left.setPower(0);
        right.setPower(0);
    }

    public static void driveRotations(LinearOpMode7582 opMode, double speed, double rotations, DcMotor left, DcMotor right){
        int leftTarget = left.getCurrentPosition()+((int)(rotations * 1400));
        int rightTarget = left.getCurrentPosition()-((int)(rotations * 1400));
        double millis;
        left.setTargetPosition(leftTarget);
        right.setTargetPosition(rightTarget);

        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        opMode.idle();
        opMode.runtime.reset();

        millis = opMode.runtime.milliseconds();
        right.setPower(Math.abs(speed));
        left.setPower(Math.abs(speed));

        while (opMode.opModeIsActive() && (left.isBusy() || right.isBusy())){
            opMode.telemetry.addData("Time", millis);
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

    public static void turn2(LinearOpMode7582 opMode, double speed, double degrees, DcMotor left, DcMotor right){
        int target = left.getCurrentPosition() + ((int)((6000*1.4875)*(degrees/360)));

        left.setPower(speed);
        right.setPower(speed);

        while (left.getCurrentPosition() < (target-10));

        left.setPower(0);
        right.setPower(0);

    }

    public static void turn(LinearOpMode7582 opMode, double speed, double degrees, DcMotor left, DcMotor right){

        int target = ((int)((6000*1.4875)*(degrees/360)));
        opMode.telemetry.addData("TTarget", target);
        opMode.telemetry.update();
        left.setTargetPosition(left.getCurrentPosition()+target);
        right.setTargetPosition(right.getCurrentPosition()+target);

        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        opMode.idle();
        left.setPower(Math.abs(speed));
        right.setPower(Math.abs(speed));

        while (opMode.opModeIsActive() && (left.isBusy() || right.isBusy()));

        left.setPower(0);
        right.setPower(0);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public static void driveInches(LinearOpMode7582 opMode, double speed, double distance, DcMotor left, DcMotor right) {
        driveRotations2(opMode, speed, distance/(5*Math.PI), left, right);
    }

    public static void driveDistance(LinearOpMode7582 opMode, double speed, double distance, Units unit, DcMotor left, DcMotor right) {
        double divisor;
        switch (unit){
            case INCHES:
                divisor = 5*Math.PI;
                break;
            case CENTIMETERS:
                divisor = 2.54*5*Math.PI;
                break;
            case FEET:
                divisor = (5*Math.PI)/12;
                break;
            case METERS:
                divisor = 0.0254*2.5*Math.PI;
                break;
            case ROTATIONS:
                divisor = 1;
                break;
            default:
                divisor = 1;
                break;
        }
        driveRotations2(opMode, speed, distance/divisor, left, right);
    }

    public enum Units{
        INCHES, CENTIMETERS, FEET, METERS, ROTATIONS
    }

    public static void wait(LinearOpMode7582 opMode, double seconds){
        double start = opMode.runtime.seconds();
        while (opMode.opModeIsActive() && (opMode.runtime.seconds()-start <= seconds));
    }

    public static double clamp(double value, double max, double min){
        return (value > max) ? max : ((value < min) ? min : value);
    }

    public static double clampMax(double value, double max){
        return (value > max) ? max : value;
    }

    public static double clampMin(double value, double min){
        return (value < min) ? min : value;
    }

}
