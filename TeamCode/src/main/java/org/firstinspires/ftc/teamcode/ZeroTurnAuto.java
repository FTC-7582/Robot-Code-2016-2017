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

    CompReading compass = new CompReading(this);
    float startHeading;

    @Override
    public void runOpMode(){
        super.runOpMode();

        hardware.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        compass.init();
        startHeading = compass.getYaw();

        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();

        driveDistance(this, 0.33, 3.0, Functions.Units.FEET, hardware.leftDrive, hardware.rightDrive);
        turnToRelativeHeading(90);


        //Functions.turn2(this, 0.33, 180, hardware.leftDrive, hardware.rightDrive);
        //Functions.turn(this, 0.33, -180, hardware.leftDrive, hardware.rightDrive);


        while (opModeIsActive()){}
    }

    void pressButton(){
        //if (hardware.color.red() < 255 && hardware.color.red()> 127) hardware.buttonPusher.setPosition(0.9);
        //else if (hardware.color.blue() <255 && hardware.color.blue() > 127) hardware.buttonPusher.setPosition(0.7);
    }

    public void driveDistance(LinearOpMode7582 opMode, double speed, double distance, Functions.Units unit, DcMotor left, DcMotor right) {
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
        driveRotations(opMode, speed, distance/divisor, left, right);
    }

    public void driveRotations(LinearOpMode7582 opMode, double speed, double rotations, DcMotor left, DcMotor right){
        int target = left.getCurrentPosition()+((int)((rotations*0.74) * 1440));
        idle();
        float heading = compass.getYaw();
        idle();
        opMode.runtime.reset();



        left.setPower(speed);
        right.setPower(-speed);

        while (left.getCurrentPosition() < (target - 10)){

            if (heading - compass.getYaw() > 1){
                left.setPower(speed);
                right.setPower(-speed*0.8);
            } else if (heading - compass.getYaw() < -1){
                left.setPower(speed*0.8);
                right.setPower(-speed);
            } else {
                left.setPower(speed);
                right.setPower(-speed);
            }

            opMode.telemetry.addData("Time", opMode.runtime.seconds());
            opMode.telemetry.addData("Target", target);
            opMode.telemetry.addData("PosLeft", opMode.hardware.leftDrive.getCurrentPosition());
            opMode.telemetry.addData("PosRight", opMode.hardware.rightDrive.getCurrentPosition());
            opMode.telemetry.update();
        }

        left.setPower(0);
        right.setPower(0);
    }

    void turnToHeading(float heading){
        float deltaHeading = (compass.getYaw()-heading);
        //This turns a robot to a specific absolute heading. This has no basis on the robot's starting rotation
        if (deltaHeading > 180 || deltaHeading < 0){
            hardware.leftDrive.setPower(0.6);
            hardware.rightDrive.setPower(0.6);
            while (compass.getYaw() < heading-1);
            hardware.leftDrive.setPower(0);
            hardware.rightDrive.setPower(0);
        } else {
            hardware.leftDrive.setPower(-0.6);
            hardware.rightDrive.setPower(-0.6);
            while (compass.getYaw() > heading+1);
            hardware.leftDrive.setPower(0);
            hardware.rightDrive.setPower(0);
        }
    }

    void turnToRelativeHeading(float heading){
        //Turns the robot to a heading based on the robots heading at the time of function call
        //For example, if the robot starts facing 90 degrees and the user passes turnToRelativeHeading
        //      45 degrees, the robot will turn to the absolute heading of 135.

        turnToHeading((compass.getYaw()+heading)%360);
    }

}
