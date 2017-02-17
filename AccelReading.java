 package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;
import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

/**
 * Created by 970098955 on 1/16/2017.
 */

public class AccelReading {

    private Vector acceleration = new Vector();
    private Vector position = new Vector();
    private Vector velocity = new Vector();
    private Vector lastVelocity = new Vector();
    private double xAccel, yAccel, zAccel;
    private double xPos = 0, yPos = 0, zPos = 0;
    private double lastXVel, lastYVel, lastZVel;
    private ElapsedTime runtime = new ElapsedTime();
    private double lastTime, deltaTime;

    private boolean initializing = false;
    int numLoops = 200;
    int initLoop = 0;
    Vector bias = new Vector();
    Vector deadMin = new Vector();
    Vector deadMax = new Vector();
    Vector deadband = new Vector();

    OpMode opMode;

    public AccelReading(LinearOpMode7582 opMode){this.opMode = opMode;}
    public AccelReading(IterativeOpMode7582 opMode){this.opMode = opMode;}

    public void init() {
        try {
            SensorManager sensorManager;
            sensorManager = (SensorManager) opMode.hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
            sensorManager.registerListener(accelerationListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        } catch (Exception e) {
            opMode.telemetry.addData("Exception", e.toString());
        }
        lastTime = runtime.seconds();
        initializing = true;
    }

    public boolean isCalibrating() {return initializing;}

    public class Vector {
        private double x, y, z;

        public Vector(){
            this.x = 0;
            this.y = 0;
            this.z = 0;
        }

        public Vector (double x, double y, double z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double getX() {return x;}
        public double getY() {return y;}
        public double getZ() {return z;}
        public void setX(double x) {this.x = x;}
        public void setY(double y) {this.y = y;}
        public void setZ(double z) {this.z = z;}
    }

    public Vector getAcceleration() {return acceleration;}
    public Vector getPosition() {return position;}
    public Vector getVelocity() {return velocity;}
    public Vector getLastVelocity() {return lastVelocity;}

    private SensorEventListener accelerationListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (initializing){
                if (initLoop >= numLoops){
                    bias.setX(bias.getX()/50);
                    bias.setY(bias.getY()/50);
                    bias.setZ(bias.getZ()/50);
                    deadband.setX(deadMax.getX()-deadMin.getX());
                    deadband.setY(deadMax.getY()-deadMin.getY());
                    deadband.setZ(deadMax.getZ()-deadMin.getZ());
                    initializing = false;
                } else {
                    double time = runtime.seconds();
                    bias.setX(bias.getX() + event.values[0]);
                    bias.setY(bias.getY() + event.values[1]);
                    bias.setZ(bias.getZ() + event.values[2]);
                    if (initLoop == 0){
                        deadMin.setX(event.values[0]);
                        deadMax.setX(event.values[0]);
                        deadMin.setY(event.values[1]);
                        deadMax.setY(event.values[1]);
                        deadMin.setZ(event.values[2]);
                        deadMax.setZ(event.values[2]);
                    } else {
                        if (event.values[0] < deadMin.getX()) deadMin.setX(event.values[0]);
                        if (event.values[0] > deadMax.getX()) deadMax.setX(event.values[0]);
                        if (event.values[1] < deadMin.getY()) deadMin.setY(event.values[1]);
                        if (event.values[1] > deadMax.getY()) deadMax.setY(event.values[1]);
                        if (event.values[2] < deadMin.getZ()) deadMin.setZ(event.values[2]);
                        if (event.values[2] > deadMax.getZ()) deadMax.setZ(event.values[2]);
                    }
                    initLoop++;
                }

            } else {
                deltaTime = runtime.seconds() - lastTime;
                lastTime = runtime.seconds();
                acceleration.setX((Math.abs(event.values[0]) < deadband.getX()) ? 0 : event.values[0] - bias.getX());
                acceleration.setY((Math.abs(event.values[1]) < deadband.getY()) ? 0 : event.values[1] - bias.getY());
                acceleration.setZ((Math.abs(event.values[2]) < deadband.getZ()) ? 0 : event.values[2] - bias.getZ());

                velocity.setX((acceleration.getX() * deltaTime) + lastVelocity.getX());
                velocity.setY((acceleration.getY() * deltaTime) + lastVelocity.getY());
                velocity.setZ((acceleration.getZ() * deltaTime) + lastVelocity.getZ());

                position.setX(position.getX() + (acceleration.getX() / 2 * deltaTime * deltaTime) + (lastVelocity.getX() * deltaTime));
                position.setY(position.getY() + (acceleration.getY() / 2 * deltaTime * deltaTime) + (lastVelocity.getY() * deltaTime));
                position.setZ(position.getZ() + (acceleration.getZ() / 2 * deltaTime * deltaTime) + (lastVelocity.getZ() * deltaTime));

                lastVelocity.setX(lastVelocity.getX() + (acceleration.getX() * deltaTime));
                lastVelocity.setY(lastVelocity.getY() + (acceleration.getY() * deltaTime));
                lastVelocity.setZ(lastVelocity.getZ() + (acceleration.getZ() * deltaTime));
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
