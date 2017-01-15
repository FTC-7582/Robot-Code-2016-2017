package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

/**
 * Created by 970098955 on 11/22/2016.
 */

@Autonomous(name="ZeroTurnAuto")
public class ZeroTurnAuto extends LinearOpMode7582 {

    @Override
    public void runOpMode(){
        super.runOpMode();

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
        //Functions.driveDistance(this, 0.33, 3.0, Functions.Units.FEET, hardware.leftDrive, hardware.rightDrive);
        Functions.turn2(this, 0.33, 180, hardware.leftDrive, hardware.rightDrive);
        //Functions.turn(this, 0.33, -180, hardware.leftDrive, hardware.rightDrive);


        while (opModeIsActive()){}
    }

    void pressButton(){
        if (hardware.color.red() < 255 && hardware.color.red()> 127) hardware.buttonPusher.setPosition(0.9);
        else if (hardware.color.blue() <255 && hardware.color.blue() > 127) hardware.buttonPusher.setPosition(0.7);
    }

}
