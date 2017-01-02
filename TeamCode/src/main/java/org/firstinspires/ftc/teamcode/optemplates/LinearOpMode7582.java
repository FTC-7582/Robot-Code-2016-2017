package org.firstinspires.ftc.teamcode.optemplates;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareZeroTurn;

/**
 * Created by 970098955 on 12/17/2016.
 */
public class LinearOpMode7582 extends LinearOpMode {
    public HardwareZeroTurn hardware;
    public final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        hardware = new HardwareZeroTurn(hardwareMap, HardwareZeroTurn.ENCODERS);
    }

}
