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
    private Vector lastVelocity = new Vector();
    private double xAccel, yAccel, zAccel;
    private double xPos = 0, yPos = 0, zPos = 0;
    private double lastXVel, lastYVel, lastZVel;
    private ElapsedTime runtime = new ElapsedTime();
    private double lastTime, deltaTime;

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
    }

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
    public Vector getLastVelocity() {return lastVelocity;}

    private SensorEventListener accelerationListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            deltaTime = runtime.seconds()-lastTime;
            lastTime = runtime.seconds();
            acceleration.setX(event.values[0]);
            acceleration.setY(event.values[1]);
            acceleration.setZ(event.values[2]);

            position.setX(position.getX() + (acceleration.getX()/2 * deltaTime * deltaTime) + (lastVelocity.getX() * deltaTime));
            position.setY(position.getY() + (acceleration.getY()/2 * deltaTime * deltaTime) + (lastVelocity.getY() * deltaTime));
            position.setZ(position.getZ() + (acceleration.getZ()/2 * deltaTime * deltaTime) + (lastVelocity.getZ() * deltaTime));

            lastVelocity.setX(lastVelocity.getX() + (acceleration.getX() * deltaTime));
            lastVelocity.setY(lastVelocity.getY() + (acceleration.getY() * deltaTime));
            lastVelocity.setZ(lastVelocity.getZ() + (acceleration.getZ() * deltaTime));

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
