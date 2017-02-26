package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Functions;

/**
 * Created by 970098955 on 2/25/2017.
 */

@Disabled
@Autonomous(name="Match 1", group="main")
public class Match1 extends ZTAutonomous {

    @Override
    public void run() {
        driveDistance(0.5, 3, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        turn(-90, 0.2);
        if (!opModeIsActive()) return;

        driveDistance(0.5, 2, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        turn(-45, 0.2);
        if (!opModeIsActive()) return;

        driveDistance(0.5, 1, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        launchCorner(2000);
        if (!opModeIsActive()) return;

        driveDistance(0.5, -2, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        turn(180, 0.2);
        if (!opModeIsActive()) return;

        driveDistance(0.5, 3, Functions.Units.FEET);
        if (!opModeIsActive()) return;
    }
}
