package org.firstinspires.ftc.teamcode;

import android.provider.ContactsContract;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;
import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

import static org.firstinspires.ftc.teamcode.Functions.Algorithms.DAMPENED_CIRCLE;

/**
 * Created by 970098955 on 11/22/2016.
 */
public class Functions {

    public static final boolean RIGHT = true, LEFT = false;

    public enum Algorithms {
        SQUARE_ROOT, CIRCLE, REVERSE_SQUARE_ROOT, DAMPENED_CIRCLE, COSINE, INVERSE_COSINE
    }

    public static double getMappedMotorPower(double input, Algorithms algorithm){
        //Graphical view of algorithms: https://www.desmos.com/calculator/wuo2ngstq1
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
            case COSINE:
                if (input < 0){
                    input = -input;
                    return -(0.5*(1-Math.cos(Math.PI*input)));
                }
                return 0.5*(1-Math.cos(Math.PI*input));
            case INVERSE_COSINE:
                if (input < 0){
                    input = -input;
                    return -((1/Math.PI) * Math.acos(1-(2*input)));
                }
                return (1/Math.PI) * Math.acos(1-(2*input));
            default:
                return input;
        }
    }
    public static double getMappedMotorPower(double input, Algorithms algorithm, double dampening) {
        //Graphical view of algorithms: https://www.desmos.com/calculator/ync9ayg4dx
        if (algorithm == DAMPENED_CIRCLE) {
            if (input < 0){
                return -(1-Math.sqrt(Range.clip(1-Math.pow(-input, dampening), 0.0d, 1.0d)));
            } else {
                return 1-Math.sqrt(Range.clip(1-Math.pow(input, dampening), 0.0d, 1.0d));
            }
        } else return getMappedMotorPower(input, algorithm);
    }

    public enum Units{
        INCHES, CENTIMETERS, FEET, METERS, ROTATIONS
    }

    public static void wait(OpMode opMode, double seconds){
        if (opMode instanceof LinearOpMode7582) {
            double start = ((LinearOpMode7582) opMode).runtime.seconds();
            while (((LinearOpMode7582) opMode).opModeIsActive() && (((LinearOpMode7582) opMode).runtime.seconds() - start <= seconds));
        }
        else if (opMode instanceof IterativeOpMode7582){
            double start = ((IterativeOpMode7582) opMode).runtime.seconds();
            while (((IterativeOpMode7582) opMode).runtime.seconds() - start <= seconds);
        }
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
