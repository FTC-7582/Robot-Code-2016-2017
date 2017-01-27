package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

import java.util.IllegalFormatCodePointException;

/**
 * Created by 970098955 on 11/22/2016.
 */

@Autonomous(name="ZeroTurnAuto", group = "main")
public class ZeroTurnAuto extends LinearOpMode7582 {

    CompReading compass = new CompReading(this);
    int target;
    float startHeading;

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

        startHeading = compass.getYaw();

        while (!opModeIsActive() && !isStopRequested()){
            telemetry.addLine("Waiting for start");
            telemetry.addData("Heading", compass.getYaw());
            telemetry.update();
        }

        //driveDistance(0.25, 2.0, Functions.Units.FEET);
        target = driveDistance(1, 3, Functions.Units.FEET);
        runtime.reset();
        while (runtime.seconds() < 0.5);
        target = driveDistance(1, -1, Functions.Units.FEET);
        float[] vals = turnToHeading2(270, 1);

        //turnToRelativeHeading(90);
        //Functions.wait(this, 1);
        //turnToRelativeHeading(270);
        //Functions.wait(this, 1);
        //turnToRelativeHeading(360);


        //Functions.turn2(this, 0.33, 180, hardware.leftDrive, hardware.rightDrive);
        //Functions.turn(this, 0.33, -180, hardware.leftDrive, hardware.rightDrive);


        while (opModeIsActive()){updateTelemetry(new Object[][] {
                {"Start Heading", startHeading},
                {"Absolute Offset", vals[4]},
                {"Relative Offset", vals[1]},
                {"Absolute Target", vals[3]},
                {"Degrees (0 to 360)", vals[0]},
                {"Degrees (-180 to 180)", vals[2]}
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
        return driveRotations2((distance/divisor)*(50/50), speed);
    }

    public int driveRotations2(double rotations, double speed){

        if (speed < 0){
            speed = -speed;
            rotations = -rotations;
        }

        int[] targets = new int[] {
                hardware.leftDrive.getCurrentPosition() + ((int) (rotations * 1400)),
                hardware.rightDrive.getCurrentPosition() - ((int) (rotations * 1440))
        };

        int maxSpeed = 1440;

        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.leftDrive.setMaxSpeed(((int)(maxSpeed * speed)));

        hardware.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hardware.rightDrive.setMaxSpeed(((int)(maxSpeed * speed)));

        hardware.leftDrive.setTargetPosition(targets[0]);
        hardware.rightDrive.setTargetPosition(targets[1]);

        hardware.rightDrive.setPower(1);
        hardware.leftDrive.setPower(1);

        while (hardware.leftDrive.isBusy() && hardware.rightDrive.isBusy() && opModeIsActive()){
            updateTelemetry(new Object[][] {
                    {"Target left, right", targets[0] + ", " + targets[1]},
                    {"Current left, right", hardware.leftDrive.getCurrentPosition() + ", " + hardware.rightDrive.getCurrentPosition()},
                    {"Power left, right", hardware.leftDrive.getPower() + ", " + hardware.rightDrive.getPower()}
            });
        }

        hardware.leftDrive.setPower(0);
        hardware.rightDrive.setPower(0);

        return 0;
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
            hardware.rightDrive.setPower(speed);
            hardware.leftDrive.setPower(-speed);
        } else {
            hardware.rightDrive.setPower(-speed);
            hardware.leftDrive.setPower(speed);
        }

        while (opModeIsActive() && hardware.leftDrive.getCurrentPosition() < target-10) updateTelemetry(new Object[][] {
                {"Target Position", target},
                {"Current Position", hardware.leftDrive.getCurrentPosition()}
        });

        hardware.rightDrive.setPower(0);
        hardware.leftDrive.setPower(0);

        return target;
    }

    float[] turnToHeading2(float heading, double speed){
        //This is relative to the robot's starting position.

        if (speed < 0) throw new IllegalArgumentException("Speed must be positive");

        float iFacing, iDegrees, iAbsolute, iHeading;

        iAbsolute = (heading + startHeading) % 360;
        iHeading = compass.getYaw();

        float facing = compass.getYaw()-startHeading;
        if (facing < 0) facing += 360;
        iFacing = facing;

        float degrees = heading-facing;
        if (degrees > 180) degrees -= 360;
        else if (degrees < -180) degrees += 360;
        iDegrees = degrees;

        if (degrees > 3 || degrees < -3) {

            int encoderTicks = 28;

            int[] targets = {
                    hardware.leftDrive.getCurrentPosition() + ((int) (degrees * encoderTicks)),
                    hardware.rightDrive.getCurrentPosition() + ((int) (degrees * encoderTicks))
            };

            int maxSpeed = 1440;

            float multiplier = 1;
            if (degrees < 45 && degrees > -45){
                if (degrees < 0){
                    multiplier = -degrees/45;
                } else {
                    multiplier = degrees/45;
                }
            }

            hardware.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.leftDrive.setMaxSpeed(((int)(maxSpeed * speed * multiplier)));

            hardware.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.rightDrive.setMaxSpeed(((int)(maxSpeed * speed * multiplier)));

            hardware.leftDrive.setTargetPosition(targets[0]);
            hardware.rightDrive.setTargetPosition(targets[1]);

            hardware.rightDrive.setPower(1);
            hardware.leftDrive.setPower(1);

            while (hardware.leftDrive.isBusy() && hardware.rightDrive.isBusy() && opModeIsActive()){
                updateTelemetry(new Object[][] {
                        {"Target left, right", targets[0] + ", " + targets[1]},
                        {"Current left, right", hardware.leftDrive.getCurrentPosition() + ", " + hardware.rightDrive.getCurrentPosition()},
                        {"Power left, right", hardware.leftDrive.getPower() + ", " + hardware.rightDrive.getPower()}
                });
            }

            hardware.leftDrive.setPower(0);
            hardware.rightDrive.setPower(0);

            /*while (hardware.leftDrive.getCurrentPosition() < target) {
                updateTelemetry(new Object[][]{
                        {"Target Heading", heading},
                        {"Current Heading", compass.getYaw()},
                        {"Degrees"}
                });
            }

            hardware.leftDrive.setPower(0);*/

            turnToHeading2(heading, speed);
        }
        return new float[] {heading, iFacing, iDegrees, iAbsolute, iHeading};
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
                //{"Target Enc Position", target},
                //{"Current Enc Position", hardware.leftDrive.getCurrentPosition()},
                {"Target Heading", tarHeading},
                {"Current Heading", compass.getYaw()}
        });
        else while (opModeIsActive() && hardware.leftDrive.getCurrentPosition() < (target-5)) updateTelemetry(new Object[][] {
                {"Degrees", degrees},
                //{"Target Enc Position", target},
                //{"Current Enc Position", hardware.leftDrive.getCurrentPosition()},
                {"Target Heading", tarHeading},
                {"Current Heading", compass.getYaw()}
        });

        hardware.leftDrive.setPower(0);

        //if (deltaH > 7 || deltaH < -7) turnToHeading(tarHeading, speed*0.5);
        if ((compass.getYaw() > tarHeading+3 && (tarHeading <= 3 && compass.getYaw() < 360-tarHeading))||
                (compass.getYaw() < tarHeading-3 && (tarHeading+7 > 360 && compass.getYaw() > (tarHeading+7)%360)))
            turnToHeading(tarHeading, speed*0.5);
    }

    void turnToHeading(float heading, double speed){
        float degrees = heading-compass.getYaw();
        if (degrees < -180) {
            degrees = (360-compass.getYaw())+heading;
        } else if (degrees > 180) {
            degrees = -((360-heading)+compass.getYaw());
        }

        float compensation = 2;
        int target = hardware.leftDrive.getCurrentPosition() + ((int)((1400 * 3.2 * compensation)*(degrees/360)));

        //if (degrees < 0 && compass.getYaw()+degrees < 0) heading = 360+(compass.getYaw()+degrees);
        //else heading = (compass.getYaw()+degrees) % 360;

        if (degrees < 0) hardware.leftDrive.setPower(-speed);
        else hardware.leftDrive.setPower(speed);

        if (degrees < 0) while (opModeIsActive() && hardware.leftDrive.getCurrentPosition() > (target+5)) updateTelemetry(new Object[][] {
                {"Degrees", degrees},
                //{"Target Enc Position", target},
                //{"Current Enc Position", hardware.leftDrive.getCurrentPosition()},
                {"Target Heading", heading},
                {"Current Heading", compass.getYaw()}
        });
        else while (opModeIsActive() && hardware.leftDrive.getCurrentPosition() < (target-5)) updateTelemetry(new Object[][] {
                {"Degrees", degrees},
                //{"Target Enc Position", target},
                //{"Current Enc Position", hardware.leftDrive.getCurrentPosition()},
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

        if ((compass.getYaw() > heading+3 && (heading <= 3 && compass.getYaw() < 360-heading))||
                (compass.getYaw() < heading-3 && (heading+7 > 360 && compass.getYaw() > (heading+7)%360)))
            turnToHeading(heading, speed);
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

        telemetry.addData("Time", new String((((int)runtime.seconds())/60) + ":" + (((int)runtime.seconds()) % 60)));
        telemetry.addData("Heading", compass.getYaw());
        telemetry.update();
    }

}
