package org.firstinspires.ftc.teamcode.Holonomic;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by 970098955 on 2/9/2017.
 */

public class HolonomicTele extends OpMode {

    private double x, y;
    private HardwareHolonomic hardware = new HardwareHolonomic(this.hardwareMap);

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        x = gamepad1.right_stick_x;
        y = gamepad1.right_stick_y;
        double angle = Math.atan2(y, x);
        angle = (angle > 1) ? 1 : ((angle < -1) ? -1 : angle);
        double netSpeed = Math.sqrt((x*x)+(y*y));
        //Setup:
        //  0   1
        //  2   3
        double[] speed = new double[4];
        if (angle >= 0 && angle <= Math.PI/2){
            speed[0] = netSpeed;
            speed[1] = netSpeed * ((2*x)-1);
            speed[2] = -netSpeed * ((2*x)-1);
            speed[3] = -netSpeed;
        } else if (angle >= Math.PI && angle <= Math.PI){
            speed[0] = netSpeed * ((2*y)-1);
            speed[1] = -netSpeed;
            speed[2] = netSpeed;
            speed[3] = -netSpeed * ((2*y)-1);
        } else if (angle <= -Math.PI && angle >= -Math.PI/2){
            speed[0] = -netSpeed;
            speed[1] = -netSpeed * ((2*x)-1);
            speed[2] = netSpeed * ((2*x)-1);
            speed[3] = netSpeed;
        } else if (angle <= -Math.PI/2 && angle >= 0){
            speed[0] = -netSpeed * ((2*y)-1);
            speed[1] = netSpeed;
            speed[2] = -netSpeed;
            speed[3] = netSpeed * ((2*y)-1);
        }

        for (int i = 0; i < 4; i++) hardware.drive[i].setPower(speed[i]);

    }
}
