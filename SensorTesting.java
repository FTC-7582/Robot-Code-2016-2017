package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;


@Autonomous(name="Sensor Testing", group = "debug")
public class SensorTesting extends IterativeOpMode7582{

    CompReading compass = new CompReading(this);

    //Holds the accelerometer data
    Acceleration accelVal;
    //Holds the second value of
    double deltaTime;
    long lastTime = 0, time;
    //Arrays to hold positional vector information.
    //Index 0 is the x value. Index 1 is the y value
    double[] accelVector = new double[2];
    double[] lastVelocity = new double[] {0, 0};
    double[] pos = {0, 0};

    @Override
    public void init() {
        super.init();
        hardware.ballBlocker.setPosition(0.175);
        hardware.buttonPusher.setPosition(0.51);
        hardware.color.enableLed(false);
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        //Calculate position from accelerometer data
        //Get data
        accelVal = hardware.accel.getAcceleration().toUnit(DistanceUnit.INCH);

        //Calculate the time since last read
        time = accelVal.acquisitionTime;
        deltaTime = (time - lastTime) * 0.000000001;
        lastTime = time;

        //Calculate position
        accelVector[0] = accelVal.xAccel; accelVector[1] = accelVal.yAccel;
        pos[0] += (accelVector[0]/2 * deltaTime * deltaTime) + (lastVelocity[0] * deltaTime);
        pos[1] += (accelVector[1] * (time * 0.000000001) * (time * 0.000000001))/2;
        lastVelocity[0] += accelVal.xAccel * deltaTime;
        lastVelocity[1] += accelVal.yAccel * deltaTime;

        //Update Telemetry to show data
        telemetry.addData("Accel x, y", accelVector[0] + "in/s/s, " + accelVector[1] + "in/s/s");
        telemetry.addData("Accel Aq Time", deltaTime * 1000000000 + " nanoseconds");
        telemetry.addData("Position x, y", pos[0] + "in, " + pos[1] + "in");
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
