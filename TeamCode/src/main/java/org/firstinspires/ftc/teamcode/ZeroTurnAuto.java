package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

/**
 * Created by 970098955 on 11/22/2016.
 */

@Autonomous(name="ZeroTurnAuto", group = "main")
public class ZeroTurnAuto extends LinearOpMode7582 {

    CompReading compass = new CompReading(this);
    int target;

    @Override
    public void runOpMode(){
        super.runOpMode();

        hardware.color.enableLed(false);


        hardware.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        compass.init();
        idle();

        hardware.ballBlocker.setPosition(0.175);

        while (!opModeIsActive() && !isStopRequested()){
            telemetry.addLine("Waiting for start");
            telemetry.addData("Heading", compass.getYaw());
            telemetry.update();
        }

        //driveDistance(0.25, 2.0, Functions.Units.FEET);
        target = driveDistance(0.5, 4, Functions.Units.FEET);
        runtime.reset();
        while (runtime.seconds() < 0.5);
        target = driveDistance(0.5, -1, Functions.Units.FEET);
        turn(-90, 0.5);

        //turnToRelativeHeading(90);
        //Functions.wait(this, 1);
        //turnToRelativeHeading(270);
        //Functions.wait(this, 1);
        //turnToRelativeHeading(360);


        //Functions.turn2(this, 0.33, 180, hardware.leftDrive, hardware.rightDrive);
        //Functions.turn(this, 0.33, -180, hardware.leftDrive, hardware.rightDrive);


        while (opModeIsActive()){updateTelemetry(new Object[][] {
                {"Target Position", target},
                {"Current Position", hardware.leftDrive.getCurrentPosition()}
        });}
    }

    void pressButton(){
        //if (hardware.color.red() < 255 && hardware.color.red()> 127) hardware.buttonPusher.setPosition(0.9);
        //else if (hardware.color.blue() <255 && hardware.color.blue() > 127) hardware.buttonPusher.setPosition(0.7);
    }

    public int driveDistance(double speed, double distance, Functions.Units unit) {
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
        return driveRotations((distance/divisor)*(36.0/50), speed);
    }

    public int driveRotations(double rotations, double speed){
        int target;
        if (rotations < 0){
            speed = -speed;
            rotations = -rotations;
        }
        if (speed < 0) target = hardware.leftDrive.getCurrentPosition()-((int)(rotations * 1400));
        else target = hardware.leftDrive.getCurrentPosition()+((int)(rotations * 1400));


        if (target < 0){
            hardware.rightDrive.setPower(speed*0.75);
            hardware.leftDrive.setPower(-speed);
        } else {
            hardware.rightDrive.setPower(-speed*0.75);
            hardware.leftDrive.setPower(speed);
        }

        while (opModeIsActive() && hardware.leftDrive.getCurrentPosition() < target) updateTelemetry(new Object[][] {
                {"Target Position", target},
                {"Current Position", hardware.leftDrive.getCurrentPosition()}
        });

        hardware.rightDrive.setPower(0);
        hardware.leftDrive.setPower(0);

        return target;
    }

    void turn(float degrees, double speed){
        if (speed < 0){
            speed = -speed;
            degrees = -degrees;
        }
        float compensation = 1;
        int target = hardware.leftDrive.getCurrentPosition() + ((int)((1400 * 3.2 * compensation)*(degrees/360)));

        float tarHeading;
        if (degrees < 0 && compass.getYaw()+degrees < 0) tarHeading = 360+(compass.getYaw()+degrees);
        else tarHeading = (compass.getYaw()+degrees) % 360;

        if (degrees < 0) hardware.leftDrive.setPower(-speed);
        else hardware.leftDrive.setPower(speed);

        if (degrees < 0) while (opModeIsActive() && hardware.leftDrive.getCurrentPosition() > (target+5)) updateTelemetry(new Object[][] {
                {"Degrees", degrees},
                {"Target Enc Position", target},
                {"Current Enc Position", hardware.leftDrive.getCurrentPosition()},
                {"Target Heading", tarHeading},
                {"Current Heading", compass.getYaw()}
        });
        else while (opModeIsActive() && hardware.leftDrive.getCurrentPosition() < (target-5)) updateTelemetry(new Object[][] {
                {"Degrees", degrees},
                {"Target Enc Position", target},
                {"Current Enc Position", hardware.leftDrive.getCurrentPosition()},
                {"Target Heading", tarHeading},
                {"Current Heading", compass.getYaw()}
        });

        hardware.leftDrive.setPower(0);

        float deltaH;

        /*if (tarHeading-compass.getYaw() < -180)
            deltaH = 360+(tarHeading-compass.getYaw());
        else if (tarHeading-compass.getYaw() > 180)
            deltaH = -(360-(tarHeading-compass.getYaw()));
        else*/ deltaH = tarHeading - compass.getYaw();

        //if (deltaH > 7 || deltaH < -7) turnToHeading(tarHeading, speed*0.5);
    }

    void turnToHeading(float heading, double speed){
        float degrees;
        float deltaHeading = heading-compass.getYaw();
        /*if (deltaHeading < -180) {
            degrees = -(360+deltaHeading);
        } else if (deltaHeading > 180) {
            degrees = 360-deltaHeading;
        } else {
            degrees = deltaHeading;
        }*/

        degrees = deltaHeading;

        float compensation = 2;
        int target = hardware.leftDrive.getCurrentPosition() + ((int)((1400 * 3.2 * compensation)*(degrees/360)));

        //if (degrees < 0 && compass.getYaw()+degrees < 0) heading = 360+(compass.getYaw()+degrees);
        //else heading = (compass.getYaw()+degrees) % 360;

        if (degrees < 0) hardware.leftDrive.setPower(-speed);
        else hardware.leftDrive.setPower(speed);

        if (degrees < 0) while (opModeIsActive() && hardware.leftDrive.getCurrentPosition() > (target+5)) updateTelemetry(new Object[][] {
                {"Degrees", degrees},
                {"Target Enc Position", target},
                {"Current Enc Position", hardware.leftDrive.getCurrentPosition()},
                {"Target Heading", heading},
                {"Current Heading", compass.getYaw()}
        });
        else while (opModeIsActive() && hardware.leftDrive.getCurrentPosition() < (target-5)) updateTelemetry(new Object[][] {
                {"Degrees", degrees},
                {"Target Enc Position", target},
                {"Current Enc Position", hardware.leftDrive.getCurrentPosition()},
                {"Target Heading", heading},
                {"Current Heading", compass.getYaw()}
        });

        hardware.leftDrive.setPower(0);

        runtime.reset();
        while (runtime.seconds() < 2) updateTelemetry(new Object[][] {
                {"Degrees", degrees},
                {"Target Enc Position", target},
                {"Current Enc Position", hardware.leftDrive.getCurrentPosition()},
                {"Target Heading", heading},
                {"Current Heading", compass.getYaw()}
        });

        float deltaH;
        /*if (heading-compass.getYaw() < -180)
            deltaH = 360+(heading-compass.getYaw());
        else if (heading-compass.getYaw() > 180)
            deltaH = -(360-(heading-compass.getYaw()));
        else*/ deltaH = heading - compass.getYaw();

        if (deltaH > 3 || deltaH < -3) turnToHeading(heading, speed);
    }


    void turnToHeading(float heading){
        float initHeading = compass.getYaw();
        float deltaHeading = (compass.getYaw()-heading);
        //This turns a robot to a specific absolute heading. This has no basis on the robot's starting rotation
        if (deltaHeading > 180 || deltaHeading < 0){
            hardware.leftDrive.setPower(0.5);
            //hardware.rightDrive.setPower(0.6);
            compass.runToHeading(heading, new DcMotor[] {hardware.leftDrive, hardware.rightDrive});
            while (compass.areMotorsBusy() && opModeIsActive())
                try {updateTelemetry(new Object[][]{{"Initial Heading", initHeading}, {"Target Heading", heading}, {"Delta Heading", deltaHeading}});}
                catch (IllegalArgumentException e) {updateTelemetry();}
            if (compass.areMotorsBusy()) compass.deactivateStopAtTarget();
            else if (compass.getYaw() > heading + 1 || compass.getYaw() < heading - 1) turnToHeading(heading, 0.5f, true);
        } else {
            //hardware.leftDrive.setPower(-0.6);
            hardware.rightDrive.setPower(-0.5);
            compass.runToHeading(heading, new DcMotor[] {hardware.leftDrive, hardware.rightDrive});
            while (compass.areMotorsBusy() && opModeIsActive())
                try {updateTelemetry(new Object[][]{{"Initial Heading", initHeading}, {"Target Heading", heading}, {"Delta Heading", deltaHeading}});}
                catch (IllegalArgumentException e) {updateTelemetry();}
            if (compass.areMotorsBusy()) compass.deactivateStopAtTarget();
            else if (compass.getYaw() > heading + 3 || compass.getYaw() < heading - 3) turnToHeading(heading, 0.5f, false);
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
            //else if (compass.getYaw() > heading + 1 || compass.getYaw() < heading - 1) turnToHeading(heading, speed, wheel);
        } else {
            if (wheel) hardware.leftDrive.setPower(-speed);
            else hardware.rightDrive.setPower(-speed);
            compass.runToHeading(heading, new DcMotor[] {hardware.leftDrive, hardware.rightDrive});
            while (compass.areMotorsBusy() && opModeIsActive())
                try {updateTelemetry(new Object[][]{{"Initial Heading", initHeading}, {"Target Heading", heading}, {"Delta Heading", deltaHeading}});}
                catch (IllegalArgumentException e) {updateTelemetry();}
            if (compass.areMotorsBusy()) compass.deactivateStopAtTarget();
            //else if (compass.getYaw() > heading + 3 || compass.getYaw() < heading - 3) turnToHeading(heading, speed, wheel);
        }

        Functions.wait(this,0.5);
        if (compass.getYaw() > heading + 1 || compass.getYaw() < heading - 1)
            turnToHeading(heading, (speed > 0.15) ? speed * 0.75f : 0.15f, wheel);
    }

    void updateTelemetry(){
        telemetry.addData("Time", runtime.seconds());
        //telemetry.addData("Starting Heading", target);
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

}
