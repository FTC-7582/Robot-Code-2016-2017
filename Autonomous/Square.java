package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Functions;

@Autonomous(name="Square", group="2")
public class Square extends ZTAutonomous {

    @Override
    public void run() {
        driveDistance(0.5, 4.5, Functions.Units.FEET);
        turn(90, 0.2);
        driveDistance(0.5, 2.5, Functions.Units.FEET);
        turn(90, 0.2);
        driveDistance(0.5, 4.5, Functions.Units.FEET);
        turn(-180, 0.2);
        driveDistance(0.5, 4.5, Functions.Units.FEET);
    }
}
