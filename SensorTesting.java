package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.GyroSensor;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;


@Autonomous(name="Sensor Testing", group = "debug")
public class SensorTesting extends IterativeOpMode7582{

    CompReading compass = new CompReading(this);
    //AccelReading accel = new AccelReading(this);

    GyroSensor gyro;



    @Override
    public void init() {
        super.init();
        gyro = hardwareMap.gyroSensor.get("Gyro");
        hardware.ballBlocker.setPosition(0.175);
        hardware.buttonPusher.setPosition(0.51);
        hardware.color.enableLed(false);
        //accel.init();
        gyro.calibrate();
        while (gyro.isCalibrating());
        telemetry.addLine("Calibrated");
        telemetry.update();
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

        try {
            telemetry.addData("Heading", gyro.getHeading());
        } catch (UnsupportedOperationException e){
            telemetry.addLine("Unable to use getHeading");
        }

        try {
            telemetry.addData("X", gyro.rawX());
        } catch (UnsupportedOperationException e){
            telemetry.addLine("Unable to use raw x");
        }

        try {
            telemetry.addData("Y", gyro.rawY());
        } catch (UnsupportedOperationException e){
            telemetry.addLine("Unable to use raw y");
        }

        try {
            telemetry.addData("Z", gyro.rawZ());
        } catch (UnsupportedOperationException e){
            telemetry.addLine("Unable to use raw z");
        }

        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
