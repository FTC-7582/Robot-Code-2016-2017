package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Testing", group="2")
public class Testing extends ZTAutonomous {
    @Override
    public void run() {
        //turn(360*5, 0.2);
        //delay(1000);
        double[] t = turn(-360*5, 0.2);

        while (opModeIsActive()) {
            telemetry.addData("Bias, Deadband", gyro.getBias() + ", " + gyro.getDeadband());
            telemetry.addData("Target, Current",
                    ((int)(t[1]*1000))/1000.0f + ", " + ((int)(gyro.getHeading() * 1000))/1000.0f);
            telemetry.update();
        }

    }
}
