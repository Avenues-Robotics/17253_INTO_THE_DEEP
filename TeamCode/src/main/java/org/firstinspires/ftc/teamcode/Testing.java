package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.classes.AprilTagLocalizer;
import org.firstinspires.ftc.teamcode.classes.PIDController;
import org.firstinspires.ftc.teamcode.hardware.Ports;
import org.firstinspires.ftc.teamcode.hardware.Driver;

@Config
@Autonomous(name = "Test")
public class Testing extends LinearOpMode {

    Telemetry dashboardTelemetry;
    Ports ports;
    Ports.Builder portsBuilder;

    public static double grip;
    public static int desiredposition;

    PIDController lsh_lController;
    PIDController lsh_rController;

    AprilTagLocalizer aprilTagLocalizer;

    @Override
    public void runOpMode() {

        dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();

        portsBuilder = new Ports.Builder();
        portsBuilder.allActive = true;
        portsBuilder.lsh_lActive = true;
        ports = new Ports(this, portsBuilder);

        lsh_lController = new PIDController(0.0127, 0.0004, 0.000001, 0, 20, ports.lsh_l);
        lsh_rController = new PIDController(0.0127, 0.0004, 0.000001, 0, 20, ports.lsh_r);


        //aprilTagLocalizer = new AprilTagLocalizer(this);


        waitForStart();

        while(opModeIsActive()) {
            //aprilTagLocalizer.run();


            lsh_rController.setup(desiredposition-ports.lsh_r.getCurrentPosition());
            lsh_lController.setup(desiredposition-ports.lsh_l.getCurrentPosition());

            ports.lsh_r.setPower(lsh_rController.evaluate(desiredposition-ports.lsh_r.getCurrentPosition()));
            ports.lsh_l.setPower(lsh_rController.evaluate(desiredposition-ports.lsh_l.getCurrentPosition()));

            telemetry.addData("lsh_l position", ports.lsh_l.getCurrentPosition());
            telemetry.addData("lsh_r position", ports.lsh_r.getCurrentPosition());
            telemetry.update();

        }
    }
}
