package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;

/**
 * Created by 970098955 on 12/19/2016.
 */
@Autonomous(name="Sensor Testing")
public class Sensor extends IterativeOpMode7582{

    @Override
    public void init() {
        super.init();
        hardware.ballBlocker.setPosition(0.5);
        hardware.buttonPusher.setPosition(0.8);
        hardware.color.enableLed(true);
        hardware.light.enableLed(false);

    }

    @Override
    public void loop() {
        telemetry.addData("Touch sensor pressed", hardware.touch.isPressed());
        telemetry.addData("Color Sensor Conn", hardware.color.getConnectionInfo());
        telemetry.addData("Color sensor red", hardware.color.red());
        telemetry.addData("Color sensor green", hardware.color.green());
        telemetry.addData("Color sensor blue", hardware.color.blue());
        telemetry.addData("Color sensor alpha", hardware.color.alpha());
        telemetry.addData("Color Sensor Hex", Integer.toHexString(hardware.color.alpha()+(hardware.color.blue()<<8)+(hardware.color.green()<<16)+(hardware.color.red()<<24)));
        telemetry.addData("Light Sensor", hardware.light.getRawLightDetected() + "/" + hardware.light.getRawLightDetectedMax());
        telemetry.update();
    }
}
