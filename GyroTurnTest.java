package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;


@Autonomous(name="Gyro Turn Test", group = "debug")

public class GyroTurnTest extends IterativeOpMode7582{

    Gyroscope gyro;
//    private double zeroOffset;

    @Override
    public void init() {
        super.init();
        gyro = new Gyroscope(hardware);
        hardware.ballBlocker.setPosition(0.175);
        hardware.buttonPusher.setPosition(0.51);
        hardware.color.enableLed(false);
        hardware.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Functions.wait(this, 0.1);
        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hardware.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        gyro.calibrate();
        telemetry.addData("Calibrated: ", gyro.getBias() + ", " + gyro.getDeadband());
        telemetry.update();
    }



    @Override
    public void start() {
        /*lastTime = System.currentTimeMillis();
        gyroHeading = 0;
        hardware.rightDrive.setPower(0.25);
        hardware.leftDrive.setPower(0.25);*/

    }

    /*private double angvel;
    private double gyroHeading = 0.0;
    private double deadband = 0.0;
    private long lastTime = System.currentTimeMillis();
    private double tvalue;
    private long tinc;
    double degrees = 240;
    int loopcnt = 0;*/

    @Override
    public void loop() {
        /*if (loopcnt == 0) {lastTime = System.currentTimeMillis();};
        loopcnt += 1;

        if (Math.abs(gyroHeading) <= Math.abs(degrees) ) {
            telemetry.addData("Gyro Heading", gyroHeading);
            integrateGyro();
            telemetry.addData("tvalue, tinc", tvalue + ", " + tinc);
            telemetry.addData("lastTime", lastTime);
            telemetry.addData("deadband", deadband);
            telemetry.addData("zeroOffset", zeroOffset);
            telemetry.update();
        } else {
            hardware.rightDrive.setPower(0);
            hardware.leftDrive.setPower(0);
        }*/

        gyro.update();
        telemetry.addData("Heading", gyro.getHeading());
        telemetry.addData("Delta Time", gyro.getDeltaTime());


    }

    @Override
    public void stop() {
        super.stop();
    }

    // calculates the gyro reading with no accelaration
/*    public void calibrateGyro()
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
    }*/


    /*public void integrateGyro() {
        long currTime = System.currentTimeMillis();
        double value = (gyro.getRotationFraction() - zeroOffset) * 720;
        if (Math.abs(value) < deadband) value = 0.0;
        gyroHeading += value * (currTime - lastTime) / 1000.0;
        tinc = currTime - lastTime;
        lastTime = currTime;
        tvalue = value;
        Functions.wait(this, 0.005);
    }*/
}
