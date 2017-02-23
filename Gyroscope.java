package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Gyroscope {
    private final HardwareZeroTurn hardware;
    ElapsedTime runtime = new ElapsedTime();

    private double heading;
    private double deadband;
    private double bias;
    private double lastTime;
    private double deltaTime;
    private double raw;
    private double velocity;

    public static final float maxDPS = 1010;

    public Gyroscope(HardwareZeroTurn hardware){this.hardware = hardware;}

    public void calibrate(){
        heading = raw = 0;
        double value = hardware.gyro.getRotationFraction();
        double minValue = value;
        double maxValue = value;
        bias = 0.0;

        for (int i = 0; i < 50; i++)
        {
            value = hardware.gyro.getRotationFraction();
            bias += value;
            if (value < minValue) minValue = value;
            if (value > maxValue) maxValue = value;
            double time = runtime.milliseconds();
            while (runtime.milliseconds() - time < 20);
        }
        bias /= 50.0;
        deadband = maxValue - minValue;
    }

    public void update(){
        double currTime = runtime.milliseconds();
        deltaTime = currTime-lastTime;
        velocity = (hardware.gyro.getRotationFraction() - bias);
        if (Math.abs(velocity) < deadband) velocity = 0.0;
        raw += velocity * (deltaTime/1000.0);
        heading += velocity * maxDPS * (deltaTime/1000.0);
        lastTime = currTime;
        double time = runtime.milliseconds();
        while (runtime.milliseconds() - time < 5);
    }

    public double getHeading(){return heading;}
    public double getRaw(){return raw;}
    public double getVelocity(){return velocity*maxDPS;}
    public double getBias(){return bias*maxDPS;}
    public double getDeadband(){return deadband*maxDPS;}
    public double getDeltaTime() {return deltaTime;}

}
