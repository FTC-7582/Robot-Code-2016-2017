package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Functions;

@Autonomous(name="RA",group="2")
public class Red extends ZTAutonomous{

    @Override
    public void run(){
        /*
        driveDistance(0.5f, 4.5, Functions.Units.FEET);
        hardware.launcher.setPower(-1);
        delay(2000);
        hardware.launcher.setPower(0);
        driveDistance(0.5f, -1, Functions.Units.FEET);
        turn(-45, 0.5f);
        */

        driveDistance(0.5f, 1, Functions.Units.FEET);
        turn(90, 0.2);
        driveDistance(0.5f, 1, Functions.Units.FEET);
        turn(90, 0.2);
        driveDistance(0.5f, 1, Functions.Units.FEET);
        turn(90, 0.2);
        driveDistance(0.5f, 1, Functions.Units.FEET);
        turn(90, 0.2);

        while(opModeIsActive()){
            gyro.update();
            updateTelemetry(new Object[][] {
                    {"Velocity", gyro.getVelocity()},
                    {"Velocity Degrees", gyro.getVelocity() * 1080},
                    {"Deadband Degrees", ((int)(gyro.getDeadband() * 1000000))/1000.0f},
                    //{"Heading Offset", gyro.getHeading() - t[1]}
            }, true);
        }
    }
}
