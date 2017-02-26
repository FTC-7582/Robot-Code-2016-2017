package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Functions;

//Red
@Disabled
@Autonomous(name="Match 3", group="main")
public class Match3 extends ZTAutonomous {

    @Override
    public void run() {
        driveDistance(0.5, 1.5, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        turn(-92, 0.2);
        if (!opModeIsActive()) return;

        driveDistance(0.5, 4.5, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        turn(-50, 0.2);
        if (!opModeIsActive()) return;

        driveDistance(0.5, 0.5, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        launchCorner(2000);
        if (!opModeIsActive()) return;

        /*driveDistance(0.5, -2, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        turn(180, 0.2);
        if (!opModeIsActive()) return;

        driveDistance(0.5, 3, Functions.Units.FEET);
        if (!opModeIsActive()) return;*/
    }
}
