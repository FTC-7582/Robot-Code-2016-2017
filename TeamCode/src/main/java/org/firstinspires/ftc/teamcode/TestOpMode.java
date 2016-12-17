package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

/**
 * Created by 970098955 on 11/22/2016.
 */

@Autonomous(name="TestOpMode")
public class TestOpMode extends LinearOpMode7582 {

    private ElapsedTime runtime = new ElapsedTime();
    HardwareZeroTurn hardware;

    int i = 0;

    @Override
    public void runOpMode(){
        hardware = new HardwareZeroTurn(hardwareMap, HardwareZeroTurn.ENCODERS);

        hardware.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Motor Start",  "Starting at %7d :%7d",
                hardware.leftDrive.getCurrentPosition(),
                hardware.rightDrive.getCurrentPosition());
        telemetry.update();

        waitForStart();
        telemetry.addData("PosLeft", hardware.leftDrive.getCurrentPosition());
        telemetry.addData("TarLeft", hardware.leftDrive.getTargetPosition());
        telemetry.addData("PosRight", hardware.rightDrive.getCurrentPosition());
        telemetry.addData("TarRight", hardware.rightDrive.getTargetPosition());
        telemetry.update();

        Functions.driveRotations(this, 0.33, 5, hardware.leftDrive, hardware.rightDrive);

        telemetry.addData("PosLeft", hardware.leftDrive.getCurrentPosition());
        telemetry.addData("TarLeft", hardware.leftDrive.getTargetPosition());
        telemetry.addData("PosRight", hardware.rightDrive.getCurrentPosition());
        telemetry.addData("TarRight", hardware.rightDrive.getTargetPosition());
        telemetry.update();

        while (opModeIsActive()){}
    }

}
