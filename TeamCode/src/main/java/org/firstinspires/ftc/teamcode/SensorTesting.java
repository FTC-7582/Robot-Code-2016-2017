package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;


@Autonomous(name="Sensor Testing")
public class SensorTesting extends IterativeOpMode7582{

    CompReading compass = new CompReading(this);

    @Override
    public void init() {
        super.init();
        hardware.ballBlocker.setPosition(0.175);
        hardware.color.enableLed(true);
        hardware.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Functions.wait(this, 0.1);
        hardware.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        compass.init();

    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        //telemetry.addData("Accel Conn", hardware.accel.getConnectionInfo());
        //telemetry.addData("Accel X", hardware.accel.getAcceleration().xAccel);
        //telemetry.addData("Accel Y", hardware.accel.getAcceleration().yAccel);
        //telemetry.addData("Accel Z", hardware.accel.getAcceleration().zAccel);
        //telemetry.addData("Accel Z", hardware.accel.getAcceleration().acquisitionTime);

        telemetry.addData("Encoder", hardware.leftDrive.getCurrentPosition());

        /*telemetry.addData("LMotor Encoder", hardware.leftDrive.getCurrentPosition());
        telemetry.addData("RMotor Encoder", hardware.rightDrive.getCurrentPosition());
        telemetry.addLine();
        telemetry.addData("Color sensor Conn", hardware.color.getConnectionInfo());
        telemetry.addData("Color sensor argb", Integer.toHexString(hardware.color.argb()));
        telemetry.addData("Color sensor red", hardware.color.red());
        telemetry.addData("Color sensor green", hardware.color.green());
        telemetry.addData("Color sensor blue", hardware.color.blue());
        */

        //telemetry.addData("Light SensorTesting", hardware.light.getRawLightDetected() + "/" + hardware.light.getRawLightDetectedMax());
        //telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
