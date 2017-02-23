// This is a comment. The phone will not try to run this line
//Or this one.
                    // Or anything on a given line after "//"

//Don't worry about these three lines. They are unimportant for your job
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Functions;

// This defines this OpMode as an autonomous op mode.
// The text between the quotation marks is the name that will show up on the phone. It will
//     need to be different in every program
//Don't worry about group for now.
@Autonomous(name="RA",group="2")
// RedFull is the name of this file in the code. You wll need to change this. Call the
//     mirror BlueFull, and come up with your own for the other program
public class RedFull extends ZTAutonomous{

    @Override
    public void run(){
        // Drives 4.7 feet forward at 0.5 (half) speed
        driveDistance(0.5, 4.7, Functions.Units.FEET);

        // This runs the Pop Tart Accelerator for 2 seconds (2000 milliseconds; milli = 1/1000)
        runPopTartAccelerator(2000);

        // Drives 1.5 feet backwards at 0.5 (half) speed
        driveDistance(0.5, -1.5, Functions.Units.FEET);

        // This turns 45 degrees counter-clockwise at 0.2 (one-fifth) speed
        turn(-45, 0.2);

        // Drives 4.7 feet forward at 0.5 (half) speed
        driveDistance(0.5, 3.4, Functions.Units.FEET);

        // This turns 50 degrees counter-clockwise at 0.2 (one-fifth) speed
        turn(-45, 0.2);

        // This will run the collector backwards to ensure that there is not a jam, then it
        // will run it forward for 2 second (2000 milliseconds)
        launchCorner(2000);

        // Drives 4.7 feet forward at 0.5 (half) speed
        driveDistance(0.5, -4.2, Functions.Units.FEET);
    }
}
