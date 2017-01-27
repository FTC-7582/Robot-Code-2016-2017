package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.optemplates.IterativeOpMode7582;

/**
 * Created by 970098955 on 1/25/2017.
 */

@Disabled
@TeleOp(name = "Servo Setting")
public class ServoSetting extends IterativeOpMode7582{

    boolean prevLeft = false, prevRight = false;

    @Override
    public void init() {
        super.init();
        hardware.buttonPusher.setPosition(0.5);
    }

    @Override
    public void loop() {
        if (gamepad1.left_bumper){
            if (!prevLeft) {
                hardware.buttonPusher.setPosition(hardware.buttonPusher.getPosition() + 0.01);
                prevLeft = true;
                prevRight = false;
            }
        } else if (gamepad1.right_bumper){
            if (!prevRight) {
                hardware.buttonPusher.setPosition(hardware.buttonPusher.getPosition() - 0.01);
                prevLeft = false;
                prevRight = true;
            }
        } else {
            prevLeft = false;
            prevRight = false;
        }

        telemetry.addData("Servo Pos", hardware.buttonPusher.getPosition());
        telemetry.update();
    }
}
