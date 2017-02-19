package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Functions;
import org.firstinspires.ftc.teamcode.Gyroscope;
import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

/**
 * Created by 970098955 on 2/9/2017.
 */

public class ZTAutonomous extends LinearOpMode7582 {
    Gyroscope gyro;
    final float driveCompensation = 1;

    @Override
    public void runOpMode() {
        super.runOpMode();
        initialize();
        waitForStart();
        run();
    }

    public void run(){}

    public void initialize(){
        super.runOpMode();
        gyro = new Gyroscope(hardware);
        gyro.calibrate();
        hardware.ballBlocker.setPosition(0.175);
        hardware.buttonPusher.setPosition(0.5);

        hardware.color.enableLed(false);

        hardware.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    int driveDistance(double speed, double distance, Functions.Units unit) {
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
        return driveRotations(distance/divisor, speed);
    }

    private int driveRotations(double rotations, double speed){
        gyro.update();
        DcMotor.RunMode lrm = hardware.leftDrive.getMode();
        DcMotor.RunMode rrm = hardware.rightDrive.getMode();

        if (speed < 0){
            speed = -speed;
            rotations = -rotations;
        }

        int[] targets = new int[] {
                hardware.leftDrive.getCurrentPosition() + ((int) (rotations * driveCompensation * 1440)),
                hardware.rightDrive.getCurrentPosition() - ((int) (rotations * driveCompensation * 1440))
        };

        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //hardware.leftDrive.setMaxSpeed(((int)(maxSpeed * speed)));

        hardware.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //hardware.rightDrive.setMaxSpeed(((int)(maxSpeed * speed)));

        hardware.leftDrive.setTargetPosition(targets[0]);
        hardware.rightDrive.setTargetPosition(targets[1]);

        hardware.rightDrive.setPower(speed);
        hardware.leftDrive.setPower(speed);

        while (opModeIsActive()){
            if (!hardware.leftDrive.isBusy()) hardware.leftDrive.setPower(0);
            if (!hardware.rightDrive.isBusy()) hardware.rightDrive.setPower(0);
            if (hardware.leftDrive.getPower() == 0 && hardware.rightDrive.getPower() == 0) break;
            gyro.update();
            updateTelemetry(new Object[][] {
                    {"Target left, right", targets[0] + ", " + targets[1]},
                    {"Current left, right", hardware.leftDrive.getCurrentPosition() + ", " + hardware.rightDrive.getCurrentPosition()},
                    {"Power left, right", hardware.leftDrive.getPower() + ", " + hardware.rightDrive.getPower()}
            }, false);
            idle();
        }

        hardware.leftDrive.setPower(0);
        hardware.rightDrive.setPower(0);

        hardware.leftDrive.setMode(lrm);
        hardware.rightDrive.setMode(rrm);

        return 0;
    }

    double[] turn(float degrees, double speed){
        gyro.update();
        if (speed < 0){
            speed = -speed;
            degrees = -degrees;
        }

        double init = gyro.getHeading();
        double target = gyro.getHeading() + degrees;

        if (degrees > 0) {
            hardware.rightDrive.setPower(speed);
            hardware.leftDrive.setPower(speed);
            while (gyro.getHeading() < target && opModeIsActive()){
                gyro.update();
                updateTelemetry(new Object[][] {{"Target", target-init}, {"Current", gyro.getHeading()-init}} , false);
            }
        } else if (degrees < 0){
            hardware.rightDrive.setPower(-speed);
            hardware.leftDrive.setPower(-speed);
            while (gyro.getHeading() > target && opModeIsActive()){
                gyro.update();
                updateTelemetry(new Object[][] {{"Target", (target-init) * 3}, {"Current", gyro.getHeading()-init}} , false);
            }
        }

        hardware.rightDrive.setPower(0);
        hardware.leftDrive.setPower(0);

        return new double[] {init, target};
    }

    void delay(long milliseconds){
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() - time < milliseconds);
    }

    void updateTelemetry(Object[][] additionalValues, boolean defaults){
        for (Object[] s : additionalValues){
            if (!(s[0] instanceof String)) throw new IllegalArgumentException("First telemetry array value must be a string");
            telemetry.addData((String)s[0], s[1]);
        }

        if (defaults) {
            telemetry.addData("Time", (((int) runtime.seconds()) / 60) + ":" + (((int) runtime.seconds()) % 60));
            telemetry.addData("Heading", gyro.getHeading());
        }
        telemetry.update();
    }

}
