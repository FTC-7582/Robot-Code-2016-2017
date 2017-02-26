package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Functions;

//Blue
@Autonomous(name="Match 6", group="main")
public class Match6 extends ZTAutonomous {

    @Override
    public void run() {
        driveDistance(0.5, 3, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        turn(90, 0.2);
        if (!opModeIsActive()) return;

        driveDistance(0.5, 1, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        turn(45, 0.3);
        if (!opModeIsActive()) return;

        launchCorner(2000);
        if (!opModeIsActive()) return;

        turn(180, 0.2);
        if (!opModeIsActive()) return;

        driveDistance(0.5, 2.5, Functions.Units.FEET);
        if (!opModeIsActive()) return;
    }
}
