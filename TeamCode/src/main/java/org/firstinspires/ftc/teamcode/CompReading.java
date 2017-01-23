package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;
import org.firstinspires.ftc.teamcode.optemplates.LinearOpMode7582;

/**
 * Created by 970098955 on 1/16/2017.
 */

public class CompReading {

    private float yaw, pitch, roll;
    private boolean stopAtHeading = false, maintainHeading = false;
    private float heading, speed;
    private DcMotor[] motors = new DcMotor[2];

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

    public void setHeading(float heading){
        this.heading = heading;
    }

    public void activateStopAtTarget(){
        stopAtHeading = true;
    }

    public void deactivateStopAtTarget(){
        stopAtHeading = false;
        for (DcMotor motor : motors) motor.setPower(0);
        motors = new DcMotor[] {};
    }

    public boolean areMotorsBusy(){
        return stopAtHeading || maintainHeading;
    }

    public void runToHeading(float heading, DcMotor[] motors){
        this.motors = motors;
        setHeading(heading);
        activateStopAtTarget();
    }

    public void beginHeadingMaintainence(){
        maintainHeading = true;
    }

    public void driveWithMaintainedHeading(float speed, DcMotor[] motors, ZeroTurnAuto opMode){
        this.motors = motors;
        this.speed = speed;
        this.heading = yaw;
        beginHeadingMaintainence();
    }

    public void stopHeadingMaintainence(){
        maintainHeading = false;
        for (DcMotor motor : motors) motor.setPower(0);
        motors =  new DcMotor[] {};
    }

    private SensorEventListener orientationListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            try {

                //if (opMode instanceof ZeroTurnAuto) ((ZeroTurnAuto) opMode).runtime.reset();

                yaw = event.values[0];
                pitch = event.values[1];
                roll = event.values[2];

                if (stopAtHeading){
                    if (event.values[0] > heading + 3 || event.values[0] < heading - 3){
                        for (DcMotor motor : motors) motor.setPower(0);
                        deactivateStopAtTarget();
                        motors = new DcMotor[] {};
                    } else {
                        for (DcMotor motor : motors){
                            motor.setPower(motor.getPower() * Functions.clampMin(((heading-yaw < 0) ? -(heading-yaw) : heading-yaw)/360.0d, 0.15d));
                        }
                    }
                }/* else if (maintainHeading){
                    if (motors.length > 2)  throw new IllegalArgumentException("There cannot be more than two drive motors on the robot. Please only pass an array with two motors");
                    float deltaHeading = (event.values[0]-heading);

                    //if (opMode instanceof ZeroTurnAuto) ((ZeroTurnAuto) opMode).updateTelemetry(new Object[][]{{"Delta Heading", deltaHeading}, {"Speed", speed}});

                    //This turns a robot to a specific absolute heading. This has no basis on the robot's starting rotation
                    if (deltaHeading > 2 || deltaHeading < -2) {
                        if (deltaHeading > 180 || deltaHeading < 0) {
                            motors[0].setPower(speed);
                            motors[1].setPower(speed*0.75);
                        } else if (deltaHeading <= 180 && deltaHeading >= 0) {
                            motors[0].setPower(speed*0.75);
                            motors[1].setPower(speed);
                        }
                    } else {
                        for (DcMotor motor : motors){
                            motor.setPower(speed);
                        }
                    }
                }*/
                //if (opMode instanceof ZeroTurnAuto) ((ZeroTurnAuto) opMode).updateTelemetry(new Object[][]{{"Runtime", ((ZeroTurnAuto) opMode).runtime.seconds()}, {"Turning", stopAtHeading}, {"Holding", maintainHeading}});
            } catch (Exception e) {
                opMode.telemetry.addData("Exception", e.toString());
            }
        }

        @Override
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {

        }
    };
}
