package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by 970098955 on 10/29/2016.
 */
public class HardwareZeroTurn {

    public final DcMotor leftDrive, rightDrive;
    public final DcMotor collector;
    public final Servo buttonPusher;

    public HardwareZeroTurn(HardwareMap hardwareMap){
        //Assigns the hardware components to their corresponding variables
        leftDrive = hardwareMap.dcMotor.get("LeftDrive");
        rightDrive = hardwareMap.dcMotor.get("RightDrive");
        collector = hardwareMap.dcMotor.get("Collector");
        buttonPusher = hardwareMap.servo.get("Beacon");

        //Sets the run mode for the motors
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        collector.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}
