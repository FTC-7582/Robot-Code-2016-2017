package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by 970098955 on 10/29/2016.
 */
public class HardwareZeroTurn {

    //Defining Constants
    public static final boolean ENCODERS = true, NO_ENCODERS = false;

    //Declaring the variables
    public final DcMotor leftDrive, rightDrive;
    public final DcMotor collector;
    public final Servo buttonPusher, ballBlocker;
    //public final AccelerationSensor accel;
    //public final ColorSensor color;
    //public final LightSensor light;

    public HardwareZeroTurn(HardwareMap hardwareMap, boolean encoders){
        //Assigns the hardware components to their corresponding variables
        leftDrive = hardwareMap.dcMotor.get("LeftDrive");
        rightDrive = hardwareMap.dcMotor.get("RightDrive");
        collector = hardwareMap.dcMotor.get("Collector");
        buttonPusher = hardwareMap.servo.get("Beacon");
        ballBlocker = hardwareMap.servo.get("Blocker");
        //accel = hardwareMap.accelerationSensor.get("Accel");
        //color = hardwareMap.colorSensor.get("Color");
        //light = hardwareMap.lightSensor.get("Light");



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
