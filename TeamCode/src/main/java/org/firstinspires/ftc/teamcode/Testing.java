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

    @Override
    public void runOpMode() {

        dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();

        portsBuilder = new Ports.Builder();
        portsBuilder.allActive = true;
        ports = new Ports(this, portsBuilder);

        ports.lsv_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsv_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        ports.lsv_l.setPower(0.251897438);
        ports.lsv_r.setPower(0.251897438);
        while(opModeIsActive()) {
            dashboardTelemetry.addData("left", ports.lsv_l.getCurrentPosition());
            dashboardTelemetry.addData("right", ports.lsv_r.getCurrentPosition());
            dashboardTelemetry.update();
        }
    }
}
