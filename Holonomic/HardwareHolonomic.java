package org.firstinspires.ftc.teamcode.Holonomic;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 970098955 on 2/9/2017.
 */

public class HardwareHolonomic {
    public HardwareHolonomic(){}

    public DcMotor[] drive;

    public HardwareHolonomic(HardwareMap hardwareMap){
        drive = new DcMotor[] {
                hardwareMap.dcMotor.get("Drive1"),
                hardwareMap.dcMotor.get("Drive2"),
                hardwareMap.dcMotor.get("Drive3"),
                hardwareMap.dcMotor.get("Drive4")
        };
    }
}
