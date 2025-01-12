package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@Autonomous(name="Zero Servo Port 5")
public class ServoZero extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();
        this.hardwareMap.get(Servo.class, "zero").setPosition(0);
        sleep(5000);
    }
} // This comment only has the purpose of ridiculing Milind, who will never see it because he never looks at the code, despite being a self-proclaimed programmer.