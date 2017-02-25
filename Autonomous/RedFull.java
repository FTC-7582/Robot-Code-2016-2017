package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Functions;

@Autonomous(name="RA",group="2")
public class RedFull extends ZTAutonomous{

    @Override
    public void run(){
        //driveDistance is the function that uses the encoder Run To Position run mode
        //Drives 0.5 feet forward at 0.5 speed
        driveDistance(0.5, 0.35, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        //turns 45 degrees counter-clockwise
        turn(-47, 0.5);
        if (!opModeIsActive()) return;

        // Drives 3.7 feet forward at 0.5 (half) speed
        driveDistance(0.5, 3.7, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        // This runs the Pop Tart Accelerator for 2 seconds (2000 milliseconds; milli = 1/1000)
        runPopTartAccelerator(2000);
        if (!opModeIsActive()) return;

        // Drives 1.5 feet backwards at 0.5 (half) speed
        driveDistance(0.5, -1.5, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        // This turns 45 degrees counter-clockwise at 0.2 (one-fifth) speed
        turn(-47, 0.2);
        if (!opModeIsActive()) return;

        // Drives 4.7 feet forward at 0.5 (half) speed
        driveDistance(0.5, 3.4, Functions.Units.FEET);
        if (!opModeIsActive()) return;

        // This turns 50 degrees counter-clockwise at 0.2 (one-fifth) speed
        turn(-47, 0.2);
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
