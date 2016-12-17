package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by 970098955 on 10/29/2016.
 */
public class HardwareZeroTurn {

    //Defining Constants
    public static final boolean ENCODERS = true, NO_ENCODERS = false;

    //Declaring the variables
    public final DcMotor leftDrive, rightDrive;
    public final DcMotor collector;
    public final Servo buttonPusher;

    public HardwareZeroTurn(HardwareMap hardwareMap, boolean encoders){
        //Assigns the hardware components to their corresponding variables
        leftDrive = hardwareMap.dcMotor.get("LeftDrive");
        rightDrive = hardwareMap.dcMotor.get("RightDrive");
        collector = hardwareMap.dcMotor.get("Collector");
        buttonPusher = hardwareMap.servo.get("Beacon");

        //Sets the run mode for the motors
        if (encoders) {
            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        collector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}
