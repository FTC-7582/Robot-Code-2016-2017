package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Functions;

//Blue
@Autonomous(name="Match 5", group="main")
public class Match5 extends ZTAutonomous {

    @Override
    public void run() {
        delay(10000);
        if (!opModeIsActive()) return;

        driveDistance(0.5, 3.5, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        runPopTartAccelerator(2000);
        if (!opModeIsActive()) return;

        driveDistance(0.5, -1, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        turn(-180, 0.35);
        if (!opModeIsActive()) return;

        driveDistance(0.5, -2, Functions.Units.FEET);
        if (!opModeIsActive()) return;
    }
}
