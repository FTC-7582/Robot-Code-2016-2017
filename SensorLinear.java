package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

/**
 * Created by 970098955 on 12/19/2016.
 */
@Disabled
@Autonomous(name="Sensing Linear")
public class SensorLinear extends LinearOpMode7582{

    @Override
    public void runOpMode() {
        super.runOpMode();
        hardware.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
        hardware.leftDrive.setPower(0);

        waitForStart();

        hardware.leftDrive.setTargetPosition(1400);
        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.leftDrive.setPower(0.1);
        while (hardware.leftDrive.isBusy()){
            telemetry.addData("Current", hardware.leftDrive.getCurrentPosition());
            telemetry.update();
        }

        while (opModeIsActive()){
//            telemetry.addData("Current", hardware.leftDrive.getCurrentPosition());
//            telemetry.update();
        }

//        while (opModeIsActive()){
//            hardware.leftDrive.setTargetPosition(pos+=20);
//            telemetry.addData("Target", hardware.leftDrive.getTargetPosition());
//            telemetry.addData("Current", hardware.leftDrive.getCurrentPosition());
//            telemetry.update();
//            hardware.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            idle();
//            hardware.leftDrive.setPower(0.1);
//            while (opModeIsActive() && hardware.leftDrive.isBusy()){
//                telemetry.addData("Target", hardware.leftDrive.getTargetPosition());
//                telemetry.addData("Current", hardware.leftDrive.getCurrentPosition());
//                telemetry.update();
//            }
//
//            //delay(125);
//        }
    }

    public void delay(double milliseconds){
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() <= milliseconds)){
            telemetry.addData("Runtime", runtime.milliseconds());
            telemetry.addData("Target", hardware.leftDrive.getTargetPosition());
            telemetry.addData("Current", hardware.leftDrive.getCurrentPosition());
            telemetry.update();
        }
    }
}
