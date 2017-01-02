package org.firstinspires.ftc.teamcode.optemplates;

import com.qualcomm.hardware.HardwareFactory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareZeroTurn;

/**
 * Created by 970098955 on 12/17/2016.
 */
public class IterativeOpMode7582 extends OpMode{
    public HardwareZeroTurn hardware;
    public final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init(){
        hardware = new HardwareZeroTurn(hardwareMap, HardwareZeroTurn.NO_ENCODERS);
    }

    @Override
    public void start() {runtime.reset();}

    @Override
    public void loop(){

    }

}
