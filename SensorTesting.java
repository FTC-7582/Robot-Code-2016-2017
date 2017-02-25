package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;

@Disabled
@Autonomous(name="Sensor Testing", group = "debug")
public class SensorTesting extends IterativeOpMode7582{

    GyroSensor gyro;
    private double zeroOffset;


    @Override
    public void init() {
        super.init();
        gyro = hardwareMap.gyroSensor.get("Gyro");
        hardware.ballBlocker.setPosition(0.175);
        hardware.buttonPusher.setPosition(0.51);
        hardware.color.enableLed(false);

        //accel.init();
        calibrateGyro();
        telemetry.addData("Calibrated TPD", zeroOffset);

        try {
            gyro.resetZAxisIntegrator();
        } catch (UnsupportedOperationException e){
            telemetry.addLine("Unable to use resetZAxisIntegrator");
        }

        telemetry.update();
    }



    @Override
    public void start() {
        lastTime = System.currentTimeMillis();
        gyroHeading = 0;

    }

    private double angvel;
    private double gyroHeading = 0.0;
    private double deadband = 0.0;
    private long lastTime = System.currentTimeMillis();
    private double tvalue;
    private long tinc;
    double degrees = 30;

    @Override
    public void loop() {


 /*       try {
            telemetry.addData("Heading", gyro.getHeading());
        } catch (UnsupportedOperationException e){
            telemetry.addLine("Unable to use getHeading");
        }

        try {
            telemetry.addData("X", gyro.rawX());
        } catch (UnsupportedOperationException e){
            telemetry.addLine("Unable to use rawX");
        }

        try {
            telemetry.addData("Y", gyro.rawY());
        } catch (UnsupportedOperationException e){
            telemetry.addLine("Unable to use rawY");
        }

        try {
            telemetry.addData("Z", gyro.rawZ());
        } catch (UnsupportedOperationException e){
            telemetry.addLine("Unable to use rawZ");
        }

        try {
            telemetry.addData("Fraction", gyro.getRotationFraction());
        } catch (UnsupportedOperationException e){
            telemetry.addLine("Unable to use getRotationFraction");
        }
        angvel = gyro.getRotationFraction() - zeroOffset;
        // print the angular velocity
        telemetry.addData("Ang Vel", angvel);
        telemetry.update();
*/
//        while (Math.abs(gyroHeading) <= Math.abs(degrees) ) {
            telemetry.addData("Gyro Heading", gyroHeading);
            integrateGyro();
            telemetry.addData("tvalue, tinc", tvalue + ", " + tinc);
            telemetry.addData("lastTime", lastTime);
            telemetry.addData("deadband", deadband);
            telemetry.addData("zeroOffset", zeroOffset);
            telemetry.update();
 //      }


    }

    @Override
    public void stop() {
        super.stop();
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
        double value = (gyro.getRotationFraction() - zeroOffset) * 360;
        if (Math.abs(value) < deadband) value = 0.0;
        gyroHeading += value * (currTime - lastTime) / 1000.0;
        tinc = currTime - lastTime;
        lastTime = currTime;
        tvalue = value;
        Functions.wait(this, 0.005);
    }
}
