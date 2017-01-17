package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;
import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

/**
 * Created by 970098955 on 1/16/2017.
 */

public class CompReading {

    private float yaw, pitch, roll;

    OpMode opMode;

    public CompReading(LinearOpMode7582 opMode){
        this.opMode = opMode;
    }

    public CompReading(IterativeOpMode7582 opMode){
        this.opMode = opMode;
    }

    public void init() {

        try {
            SensorManager sensorManager;
            sensorManager = (SensorManager) opMode.hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
            sensorManager.registerListener(orientationListener, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        } catch (Exception e) {
            opMode.telemetry.addData("Exception", e.toString());
        }
    }

    public float[] getAngles(){
        return new float[] {yaw, pitch, roll};
    }

    public float getYaw() {return yaw;}
    public float getPitch() {return pitch;}
    public float getRoll() {return roll;}

    private SensorEventListener orientationListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            try {

                yaw = event.values[0];
                pitch = event.values[1];
                roll = event.values[2];

            } catch (Exception e) {
                opMode.telemetry.addData("Exception", e.toString());
            }
        }

        @Override
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {

        }
    };
}
