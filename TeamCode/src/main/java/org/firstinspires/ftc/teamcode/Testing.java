package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.Ports;
import org.firstinspires.ftc.teamcode.hardware.Driver;

@Config
@Autonomous(name = "Test")
public class Testing extends LinearOpMode {

    Telemetry dashboardTelemetry;
    Ports ports;
    Ports.Builder portsBuilder;

    public static double grip;

    @Override
    public void runOpMode() {

        dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();

        portsBuilder = new Ports.Builder();
        portsBuilder.allActive = true;
        ports = new Ports(this, portsBuilder);

        waitForStart();

        ports.intakeClaw.setPosition(grip);
        sleep(100000);
    }
}
