package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;
import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;


@Autonomous(name="Gyro Turn Test Linear", group = "debug")

public class GyroTurnTestLinear extends LinearOpMode7582{

    CompReading compass = new CompReading(this);
    //AccelReading accel = new AccelReading(this);

    GyroSensor gyro;
    private double zeroOffset;

    private double angvel;
    private double gyroHeading = 0.0;
    private double deadband = 0.0;
    private long lastTime = System.currentTimeMillis();
    private double tvalue;
    private long tinc;
    double degrees = 60;
    int loopcnt = 0;

    @Override
    public void runOpMode() {
        super.runOpMode();

        gyro = hardwareMap.gyroSensor.get("Gyro");
        hardware.ballBlocker.setPosition(0.175);
        hardware.buttonPusher.setPosition(0.51);
        hardware.color.enableLed(false);
        hardware.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Functions.wait(this, 0.1);
        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        calibrateGyro();
        telemetry.addData("Calibrated TPD", zeroOffset);

        try {
            gyro.resetZAxisIntegrator();
        } catch (UnsupportedOperationException e){
            telemetry.addLine("Unable to use resetZAxisIntegrator");
        }

        telemetry.update();

        waitForStart();

        lastTime = System.currentTimeMillis();
        gyroHeading = 0;
        hardware.leftDrive.setPower(0.25);
        hardware.rightDrive.setPower(0.25);

        while (opModeIsActive() && (Math.abs(gyroHeading) <= Math.abs(degrees))){
            telemetry.addData("Gyro Heading", gyroHeading);
            integrateGyro();
            telemetry.addData("tvalue, tinc", tvalue + ", " + tinc);
            telemetry.addData("lastTime", lastTime);
            telemetry.addData("deadband", deadband);
            telemetry.addData("zeroOffset", zeroOffset);
            telemetry.update();
        }
        hardware.leftDrive.setPower(0);
        hardware.rightDrive.setPower(0);
        Functions.wait(this, 1);

        degrees = 0;

        hardware.leftDrive.setPower(-0.25);
        hardware.rightDrive.setPower(-0.25);
        while (opModeIsActive() && (Math.abs(gyroHeading) >= Math.abs(degrees) && gyroHeading > 0)){
            telemetry.addData("Gyro Heading", gyroHeading);
            integrateGyro();
            telemetry.addData("tvalue, tinc", tvalue + ", " + tinc);
            telemetry.addData("lastTime", lastTime);
            telemetry.addData("deadband", deadband);
            telemetry.addData("zeroOffset", zeroOffset);
            telemetry.update();
        }
        hardware.leftDrive.setPower(0);
        hardware.rightDrive.setPower(0);
        while(opModeIsActive()){
            telemetry.addData("Gyro Heading", gyroHeading);
            telemetry.addData("tvalue, tinc", tvalue + ", " + tinc);
            telemetry.addData("lastTime", lastTime);
            telemetry.addData("deadband", deadband);
            telemetry.addData("zeroOffset", zeroOffset);
            telemetry.update();
        }

    }

    // calculates the gyro reading with no accelaration
    public void calibrateGyro()
    {
        double sum = 0.0;
        double value = gyro.getRotationFraction();
        double minValue = value;
        double maxValue = value;

        for (int i = 0; i < 50; i++)
        {
            sum += gyro.getRotationFraction();
            if (value < minValue) minValue = value;
            if (value > maxValue) maxValue = value;
            Functions.wait(this, 0.02);
        }
        zeroOffset = sum/50.0;
        deadband = maxValue - minValue;
    }


    public void integrateGyro() {
        long currTime = System.currentTimeMillis();
        double value = (gyro.getRotationFraction() - zeroOffset) * 720;
        if (Math.abs(value) < deadband) value = 0.0;
        gyroHeading += value * (currTime - lastTime) / 1000.0;
        tinc = currTime - lastTime;
        lastTime = currTime;
        tvalue = value;
        Functions.wait(this, 0.005);
    }
}
