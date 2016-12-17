package org.firstinspires.ftc.teamcode.optemplates;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareZeroTurn;

/**
 * Created by 970098955 on 12/17/2016.
 */
public class LinearOpMode7582 extends LinearOpMode {
    public HardwareZeroTurn hardware;

    @Override
    public void runOpMode(){
        hardware = new HardwareZeroTurn(hardwareMap, HardwareZeroTurn.ENCODERS);
    }

}
