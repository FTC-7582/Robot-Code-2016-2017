package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

@Autonomous(name="Autonomous - Red Team", group = "main")
public class ZeroTurnAutoRed extends LinearOpMode7582 {

    CompReading compass = new CompReading(this);
    private int maxSpeed = 4320;
    private double[] turnValues;

    @Override
    public void runOpMode() {
        super.runOpMode();
        try {
            hardware.color.enableLed(false);

            hardware.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hardware.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            idle();

            hardware.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            hardware.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            compass.init();
            idle();

            hardware.ballBlocker.setPosition(0.175);

            while (!opModeIsActive() && !isStopRequested()) {
                telemetry.addLine("Waiting for start");
                telemetry.addData("Heading", compass.getYaw());
                telemetry.update();
            }
            //driveDistance(0.25, 2.0, Functions.Units.FEET);
            if (opModeIsActive()) driveDistance(1, 4, Functions.Units.FEET);

            if (opModeIsActive()) hardware.launcher.setPower(-1);
            if (opModeIsActive()) Functions.wait(this, 2);
            if (opModeIsActive()) hardware.launcher.setPower(0);

            if (opModeIsActive()) driveDistance(1, -1, Functions.Units.FEET);

            if (opModeIsActive()) turn(-60, 1);
            if (opModeIsActive()) driveDistance(1, 3, Functions.Units.FEET);
            //if (opModeIsActive()) turn (-45, 1);
            //if (opModeIsActive()) driveDistance(1, -1, Functions.Units.FEET);

                while (opModeIsActive() && hardware.collector.getPower() < 1){
                hardware.collector.setPower(Functions.clampMax(hardware.collector.getPower() + 0.05, 1));
                idle();
            }

            hardware.ballBlocker.setPosition(0.5);
            Functions.wait(this, 0.5);
            hardware.ballBlocker.setPosition(0.175);

            while (opModeIsActive() && hardware.collector.getPower() > -1) {
                hardware.collector.setPower(Functions.clampMin(hardware.collector.getPower() - 0.05, -1));
                idle();
            }
            if (opModeIsActive()) Functions.wait(this, 0.5);
            if (opModeIsActive()) hardware.ballBlocker.setPosition(0.5);
            if (opModeIsActive()) Functions.wait(this, 2);
            if (opModeIsActive()) hardware.ballBlocker.setPosition(0.175);
            if (opModeIsActive()) while (opModeIsActive() && hardware.collector.getPower() < 0) {
                hardware.collector.setPower(Functions.clampMax(hardware.collector.getPower() + 0.015, 0));
                idle();
            }

            if (opModeIsActive()) driveDistance(1, -4, Functions.Units.FEET);
            //if (opModeIsActive()) turnValues = turn(-220, 1);
            //if (opModeIsActive()) driveDistance(1, -5, Functions.Units.FEET);

            hardware.collector.setPower(0);
            hardware.leftDrive.setPower(0);
            hardware.rightDrive.setPower(0);
            idle();

        } catch (Exception e){
            hardware.collector.setPower(0);
            hardware.leftDrive.setPower(0);
            hardware.rightDrive.setPower(0);
            telemetry.addData("Error: ", e.getMessage());
            telemetry.update();
        }

        while (opModeIsActive()){updateTelemetry(new Object[][] {
                {"Initial", turnValues[0]},
                {"Change", turnValues[1]},
                {"Target", turnValues[2]},
                {"Position", turnValues[3]}
        });}


    }

    private int driveDistance(double speed, double distance, Functions.Units unit) {
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
        return driveRotations2(distance/divisor, speed);
    }

    private int driveRotations2(double rotations, double speed){

        DcMotor.RunMode lrm = hardware.leftDrive.getMode();
        DcMotor.RunMode rrm = hardware.rightDrive.getMode();

        if (speed < 0){
            speed = -speed;
            rotations = -rotations;
        }

        float compensation = 1;

        int[] targets = new int[] {
                hardware.leftDrive.getCurrentPosition() + ((int) (rotations * compensation * 1440)),
                hardware.rightDrive.getCurrentPosition() - ((int) (rotations * compensation * 1440))
        };

        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //hardware.leftDrive.setMaxSpeed(((int)(maxSpeed * speed)));

        hardware.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //hardware.rightDrive.setMaxSpeed(((int)(maxSpeed * speed)));

        hardware.leftDrive.setTargetPosition(targets[0]);
        hardware.rightDrive.setTargetPosition(targets[1]);

        hardware.rightDrive.setPower(speed);
        hardware.leftDrive.setPower(speed);

        while (opModeIsActive() && hardware.leftDrive.isBusy() && hardware.rightDrive.isBusy()){
            updateTelemetry(new Object[][] {
                    {"Target left, right", targets[0] + ", " + targets[1]},
                    {"Current left, right", hardware.leftDrive.getCurrentPosition() + ", " + hardware.rightDrive.getCurrentPosition()},
                    {"Power left, right", hardware.leftDrive.getPower() + ", " + hardware.rightDrive.getPower()}
            });
            idle();
        }

        hardware.leftDrive.setPower(0);
        hardware.rightDrive.setPower(0);

        hardware.leftDrive.setMode(lrm);
        hardware.rightDrive.setMode(rrm);

        return 0;
    }

    private double[] turn(float degrees, double speed){
        DcMotor.RunMode lrm = hardware.leftDrive.getMode();
        DcMotor.RunMode rrm = hardware.rightDrive.getMode();

        float compensation = 2.85f;

        if (speed < 0){
            speed = -speed;
            degrees = -degrees;
        }
        int target = ((int)((1440 * 3.2 * compensation)*(degrees/360)));

        int change = target;
        int initial;

        if (degrees < 0) {
            initial = hardware.rightDrive.getCurrentPosition();
            hardware.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //hardware.rightDrive.setMaxSpeed((int)(speed * maxSpeed));
            target += hardware.rightDrive.getCurrentPosition();
            hardware.rightDrive.setTargetPosition(target);
            hardware.rightDrive.setPower(speed);
        } else {
            initial = hardware.leftDrive.getCurrentPosition();
            hardware.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //hardware.leftDrive.setMaxSpeed((int)(speed * maxSpeed));
            target += hardware.leftDrive.getCurrentPosition();
            hardware.leftDrive.setTargetPosition(target);
            hardware.leftDrive.setPower(speed);
        }

        runtime.reset();

        while (opModeIsActive() && runtime.seconds() < 2 && (hardware.leftDrive.isBusy() || hardware.rightDrive.isBusy())){
            updateTelemetry(new Object[][] {
                    {"Busy left, right", hardware.leftDrive.isBusy() + ", " + hardware.rightDrive.isBusy()}
            });
            idle();
        }

        hardware.leftDrive.setPower(0);
        hardware.rightDrive.setPower(0);

        hardware.leftDrive.setMode(lrm);
        hardware.rightDrive.setMode(rrm);

        return new double[] {initial, change, target,
                (degrees < 0) ? hardware.rightDrive.getCurrentPosition() : hardware.leftDrive.getCurrentPosition()};

    }

    private void updateTelemetry(){
        telemetry.addData("Time", runtime.seconds());
        //telemetry.addData("Starting Heading", target);
        telemetry.addData("Heading", compass.getYaw());
        telemetry.addData("Encoder Position", hardware.leftDrive.getCurrentPosition());
        telemetry.update();
    }

    private void updateTelemetry(Object[][] additionalValues){
        for (Object[] s : additionalValues){
            if (!(s[0] instanceof String)) throw new IllegalArgumentException("First telemetry array value must be a string");
            telemetry.addData((String)s[0], s[1]);
        }

        telemetry.addData("Time", (((int)runtime.seconds())/60) + ":" + (((int)runtime.seconds()) % 60));
        telemetry.addData("Heading", compass.getYaw());
        telemetry.update();
    }

}
