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
        compass.init();

    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        telemetry.addData("Color sensor Conn", hardware.color.getConnectionInfo());
        telemetry.addData("Color sensor argb", Integer.toHexString(hardware.color.argb()));
        telemetry.addData("Color sensor red", hardware.color.red());
        telemetry.addData("Color sensor green", hardware.color.green());
        telemetry.addData("Color sensor blue", hardware.color.blue());
    }

    @Override
    public void stop() {
        super.stop();
    }
}
