package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Functions;

@Autonomous(name="RA",group="2")
public class Red extends ZTAutonomous{

    @Override
    public void run(){
        driveDistance(0.5f, 3, Functions.Units.FEET);
//        driveDistance(0.5f, -3, Functions.Units.FEET);
        double[] t = turn(90, 0.5f);
        delay(500);
        turn(-90, 0.5f);

        /*while (opModeIsActive()){
            updateTelemetry(new Object[][] {
                    {"Target Heading", t[1]},
                    {"Initial Heading", t[0]},
                    {"Current Heading", gyro.getHeading()}
            }, false);
        }*/
    }
}
