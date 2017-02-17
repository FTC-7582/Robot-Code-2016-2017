package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 970098955 on 2/6/2017.
 */

public class Gyroscope {
    private final HardwareZeroTurn hardware;

    private double heading;
    private double deadband;
    private double bias;
    private double deltaTime;
    private double lastTime;
    private double velocity;

    private final float maxDPS = 360;

    public Gyroscope(HardwareZeroTurn hardware){this.hardware = hardware;}

    public void calibrate(){
        double value = hardware.gyro.getRotationFraction();
        double minValue = value;
        double maxValue = value;
        bias = 0.0;

        for (int i = 0; i < 50; i++)
        {
            bias += hardware.gyro.getRotationFraction();
            if (value < minValue) minValue = value;
            if (value > maxValue) maxValue = value;
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < 20);
        }
        bias /= 50.0;
        deadband = maxValue - minValue;
    }

    public void update(){
        long currTime = System.currentTimeMillis();
        velocity = (hardware.gyro.getRotationFraction() - bias) * maxDPS;
        deltaTime = currTime - lastTime;
        if (Math.abs(velocity) < deadband) velocity = 0.0;
        heading += velocity * (currTime - lastTime) / 1000.0;
        long time = System.currentTimeMillis();
        lastTime = currTime;
        while (System.currentTimeMillis() - time < 5);
    }

    public double getHeading(){return heading;}

}
