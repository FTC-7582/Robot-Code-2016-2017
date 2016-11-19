package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by 970098955 on 11/19/2016.
 */
@TeleOp(name="Servo")
public class ServoTest extends OpMode{

    Servo beacon;

    double pos = 0.8;

    @Override
    public void init(){
        beacon = hardwareMap.servo.get("Beacon");
        beacon.setPosition(pos);
    }

    @Override
    public void loop(){

        pos += (gamepad1.right_trigger-gamepad1.left_trigger)/50;
        pos = Range.clip(pos, 0.7, 0.9);
        beacon.setPosition(pos);
        telemetry.addData("Position", beacon.getPosition());
    }

}
