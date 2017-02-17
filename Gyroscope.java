package org.firstinspires.ftc.teamcode;

public class Gyroscope {
    private final HardwareZeroTurn hardware;

    private double heading;
    private double deadband;
    private double bias;
    private double lastTime;

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
        double velocity = (hardware.gyro.getRotationFraction() - bias);
        if (Math.abs(velocity) < deadband) velocity = 0.0;
        else velocity *= maxDPS;
        heading += velocity * (currTime - lastTime) / 1000.0;
        long time = System.currentTimeMillis();
        lastTime = currTime;
        while (System.currentTimeMillis() - time < 5);
    }

    public double getHeading(){return heading;}

}
