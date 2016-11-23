package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.Range;

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
}
