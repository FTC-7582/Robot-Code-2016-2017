package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;

/**
 * Created by 970098955 on 12/19/2016.
 */
@Autonomous(name="SensorTesting Testing")
public class SensorTesting extends IterativeOpMode7582{

    @Override
    public void init() {
        super.init();
        hardware.ballBlocker.setPosition(0.175);
        hardware.buttonPusher.setPosition(0.8);
        //hardware.color.enableLed(true);
        //hardware.light.enableLed(true);

    }

    @Override
    public void loop() {
        //telemetry.addData("Accel Conn", hardware.accel.getConnectionInfo());
        //telemetry.addData("Accel X", hardware.accel.getAcceleration().xAccel);
        //telemetry.addData("Accel Y", hardware.accel.getAcceleration().yAccel);
        //telemetry.addData("Accel Z", hardware.accel.getAcceleration().zAccel);
        //telemetry.addData("Accel Z", hardware.accel.getAcceleration().acquisitionTime);
        /*
        telemetry.addData("Color SensorTesting Conn", hardware.color.getConnectionInfo());
        telemetry.addData("Color sensor red", hardware.color.red());
        telemetry.addData("Color sensor green", hardware.color.green());
        telemetry.addData("Color sensor blue", hardware.color.blue());
        telemetry.addData("Color sensor alpha", hardware.color.alpha());
        telemetry.addData("Color SensorTesting Hex", Integer.toHexString(hardware.color.alpha()+(hardware.color.blue()<<8)+(hardware.color.green()<<16)+(hardware.color.red()<<24)));
        */
        //telemetry.addData("Light SensorTesting", hardware.light.getRawLightDetected() + "/" + hardware.light.getRawLightDetectedMax());
        //telemetry.update();
    }
}
