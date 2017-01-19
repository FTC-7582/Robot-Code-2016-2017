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

        hardware.ballBlocker.setPosition(0.175);

        while (!opModeIsActive()){
            telemetry.addLine("Waiting for start");
            telemetry.addData("Heading", compass.getYaw());
            telemetry.update();
        }

        driveDistance(0.33, 3.0, Functions.Units.FEET, hardware.leftDrive, hardware.rightDrive);
        turnToRelativeHeading(90);
        //Functions.wait(this, 1);
        //turnToRelativeHeading(270);
        //Functions.wait(this, 1);
        //turnToRelativeHeading(360);


        //Functions.turn2(this, 0.33, 180, hardware.leftDrive, hardware.rightDrive);
        //Functions.turn(this, 0.33, -180, hardware.leftDrive, hardware.rightDrive);


        while (opModeIsActive()){updateTelemetry();}
    }

    void pressButton(){
        //if (hardware.color.red() < 255 && hardware.color.red()> 127) hardware.buttonPusher.setPosition(0.9);
        //else if (hardware.color.blue() <255 && hardware.color.blue() > 127) hardware.buttonPusher.setPosition(0.7);
    }

    public void driveDistance(double speed, double distance, Functions.Units unit, DcMotor left, DcMotor right) {
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
        driveRotationsMaintainHeading(distance/divisor, (float)speed);
    }

    public void driveRotations(double speed, double rotations, DcMotor left, DcMotor right){
        int target = left.getCurrentPosition()+((int)((rotations*0.74) * 1440));
        idle();
        float heading = compass.getYaw();
        idle();
        runtime.reset();

        left.setPower(speed);
        right.setPower(-speed);

        while ((left.getCurrentPosition() < (target - 10)) && opModeIsActive()){
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
            updateTelemetry();
        }

        left.setPower(0);
        right.setPower(0);
    }

    public void driveRotationsMaintainHeading(double rotations, float speed){
        int target = hardware.leftDrive.getCurrentPosition()+((int)((rotations*0.74) * 1440));

        compass.driveWithMaintainedHeading(speed, new DcMotor[] {hardware.leftDrive, hardware.rightDrive});
        while (hardware.leftDrive.getCurrentPosition() < target - 10) updateTelemetry();
        compass.stopHeadingMaintainence();

    }

    void turnToHeading(float heading){
        float initHeading = compass.getYaw();
        float deltaHeading = (compass.getYaw()-heading);
        //This turns a robot to a specific absolute heading. This has no basis on the robot's starting rotation
        if (deltaHeading > 180 || deltaHeading < 0){
            hardware.leftDrive.setPower(1);
            //hardware.rightDrive.setPower(0.6);
            compass.runToHeading(heading, new DcMotor[] {hardware.leftDrive, hardware.rightDrive});
            while (compass.areMotorsBusy() && opModeIsActive())
                try {updateTelemetry(new Object[][]{{"Initial Heading", initHeading}, {"Target Heading", heading}, {"Delta Heading", deltaHeading}});}
                catch (IllegalArgumentException e) {updateTelemetry();}
            if (compass.areMotorsBusy()) compass.deactivateStopAtTarget();
            else if (compass.getYaw() > heading + 1 || compass.getYaw() < heading - 1) turnToHeading(heading, 0.75f, true);
        } else {
            //hardware.leftDrive.setPower(-0.6);
            hardware.rightDrive.setPower(-1);
            compass.runToHeading(heading, new DcMotor[] {hardware.leftDrive, hardware.rightDrive});
            while (compass.areMotorsBusy() && opModeIsActive())
                try {updateTelemetry(new Object[][]{{"Initial Heading", initHeading}, {"Target Heading", heading}, {"Delta Heading", deltaHeading}});}
                catch (IllegalArgumentException e) {updateTelemetry();}
            if (compass.areMotorsBusy()) compass.deactivateStopAtTarget();
            else if (compass.getYaw() > heading + 1 || compass.getYaw() < heading - 1) turnToHeading(heading, 0.75f, false);
        }
    }

    void turnToHeading(float heading, float speed, boolean wheel){
        float initHeading = compass.getYaw();
        float deltaHeading = (compass.getYaw()-heading);
        //This turns a robot to a specific absolute heading. This has no basis on the robot's starting rotation
        if (deltaHeading > 180 || deltaHeading < 0){
            if (wheel) hardware.leftDrive.setPower(speed);
            else hardware.rightDrive.setPower(speed);
            compass.runToHeading(heading, new DcMotor[] {hardware.leftDrive, hardware.rightDrive});
            while (compass.areMotorsBusy() && opModeIsActive())
                try {updateTelemetry(new Object[][]{{"Initial Heading", initHeading}, {"Target Heading", heading}, {"Delta Heading", deltaHeading}});}
                catch (IllegalArgumentException e) {updateTelemetry();}
            if (compass.areMotorsBusy()) compass.deactivateStopAtTarget();
            else if (compass.getYaw() > heading + 1 || compass.getYaw() < heading - 1) turnToHeading(heading, speed, wheel);
        } else {
            if (wheel) hardware.leftDrive.setPower(-speed);
            else hardware.rightDrive.setPower(-speed);
            compass.runToHeading(heading, new DcMotor[] {hardware.leftDrive, hardware.rightDrive});
            while (compass.areMotorsBusy() && opModeIsActive())
                try {updateTelemetry(new Object[][]{{"Initial Heading", initHeading}, {"Target Heading", heading}, {"Delta Heading", deltaHeading}});}
                catch (IllegalArgumentException e) {updateTelemetry();}
            if (compass.areMotorsBusy()) compass.deactivateStopAtTarget();
            else if (compass.getYaw() > heading + 2 || compass.getYaw() < heading - 2) turnToHeading(heading, speed, wheel);
        }

        Functions.wait(this,0.5);
        if (compass.getYaw() > heading + 1 || compass.getYaw() < heading - 1)
            turnToHeading(heading, (speed > 0.15) ? speed * 0.75f : 0.15f, wheel);
    }

    void updateTelemetry(){
        telemetry.addData("Time", runtime.seconds());
        telemetry.addData("Starting Heading", startHeading);
        telemetry.addData("Heading", compass.getYaw());
        telemetry.addData("Encoder Position", hardware.leftDrive.getCurrentPosition());
        telemetry.update();
    }

    void updateTelemetry(Object[][] additionalValues){
        for (Object[] s : additionalValues){
            if (!(s[0] instanceof String)) throw new IllegalArgumentException("First telemetry array value must be a string");
            telemetry.addData((String)s[0], s[1]);
        }

        telemetry.addData("Time", runtime.seconds());
        telemetry.addData("Heading", compass.getYaw());
        telemetry.addData("Encoder Position", hardware.leftDrive.getCurrentPosition());
        telemetry.update();
    }

    void turnToRelativeHeading(float heading){
        //Turns the robot to a heading based on the robots heading at the time of function call
        //For example, if the robot starts facing 90 degrees and the user passes turnToRelativeHeading
        //      45 degrees, the robot will turn to the absolute heading of 135.

        turnToHeading((compass.getYaw()+heading)%360);
    }

}
