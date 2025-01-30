package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Driver;
import org.firstinspires.ftc.teamcode.hardware.Ports;

@Config
@Autonomous(name = "april tag auto")
public class AprilAutoOpMode extends LinearOpMode {

    Ports ports;
    Ports.Builder portsBuilder;

    @Override
    public void runOpMode() {
        portsBuilder = new Ports.Builder();
        portsBuilder.wheelsActive = true;
        ports = new Ports(this, portsBuilder);

        waitForStart();

        Driver.drive(this, ports, 1, 100, 155);
    }

}
