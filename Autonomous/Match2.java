package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Functions;

//Blue
@Disabled
@Autonomous(name="Match 2", group="main")
public class Match2 extends ZTAutonomous {

    @Override
    public void run() {
        driveDistance(0.5, 0.4, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        turn(45, 0.2);
        if (!opModeIsActive()) return;

        // Drives 4.7 feet forward at 0.5 (half) speed
        driveDistance(0.5, 3.7, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        // This runs the Pop Tart Accelerator for 2 seconds (2000 milliseconds; milli = 1/1000)
        runPopTartAccelerator(2000);
        if (!opModeIsActive()) return;

        // Drives 1.5 feet backwards at 0.5 (half) speed
        driveDistance(0.5, -1.5, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        // This turns 45 degrees clockwise at 0.2 (one-fifth) speed
        turn(45, 0.2);
        if (!opModeIsActive()) return;

        // Drives 4.7 feet forward at 0.5 (half) speed
        driveDistance(0.5, 3.4, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        // This turns 50 degrees clockwise at 0.2 (one-fifth) speed
        turn(45, 0.2);
        if (!opModeIsActive()) return;

        // This will run the collector backwards to ensure that there is not a jam, then it
        // will run it forward for 2 second (2000 milliseconds)
        launchCorner(2000);
        if (!opModeIsActive()) return;

        // Drives 4.7 feet forward at 0.5 (half) speed
        driveDistance(0.5, -4.0, Functions.Units.FEET);
        if (!opModeIsActive()) return;
    }
}
