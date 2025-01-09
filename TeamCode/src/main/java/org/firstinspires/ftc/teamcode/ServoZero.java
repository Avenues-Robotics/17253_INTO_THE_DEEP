package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@Autonomous(name="Zero Servo Port 5")
public class ServoZero extends LinearOpMode {
    @Override
    public void runOpMode() {
        this.hardwareMap.get(Servo.class, "zero").setPosition(0);
    }
}
