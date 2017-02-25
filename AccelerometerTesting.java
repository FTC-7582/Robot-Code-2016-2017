package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.AccelReading;
import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;

@Disabled
@Autonomous(name="Accelerometer", group = "debug")
public class AccelerometerTesting extends IterativeOpMode7582 {
    AccelReading accel = new AccelReading(this);

    @Override
    public void init() {
        super.init();
        accel.init();
        while (accel.isCalibrating()){
            telemetry.addData("Calibrating", accel.initLoop + "/" + accel.numLoops);
            telemetry.update();
        }
    }

    @Override
    public void init_loop() {
        super.init_loop();
        telemetry.addData("Calibrated", accel.initLoop);
        telemetry.addData("Bias", accel.bias.getX() + " | " + accel.bias.getY() + " | " + accel.bias.getZ() + " | ");
        telemetry.addData("Deadband", accel.deadband.getX() + " | " + accel.deadband.getY() + " | " + accel.deadband.getZ() + " | ");
        telemetry.update();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
        telemetry.addData("Acceleration", accel.getAcceleration().getX() + "ft/s/s, "
        + accel.getAcceleration().getY() + "ft/s/s, " + accel.getAcceleration().getZ() + "ft/s/s");
        telemetry.addData("Velocity", accel.getVelocity().getX() + "ft/s, "
        + accel.getVelocity().getY() + "ft/s, " + accel.getVelocity().getZ() + "ft/s");
        telemetry.addData("Acceleration", accel.getPosition().getX() + "ft/s/s, "
        + accel.getPosition().getY() + "ft, " + accel.getPosition().getZ() + "ft");
        telemetry.update();

    }
}
