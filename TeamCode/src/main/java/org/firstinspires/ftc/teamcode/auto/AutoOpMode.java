package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.classes.PIDController;
import org.firstinspires.ftc.teamcode.hardware.Driver;
import org.firstinspires.ftc.teamcode.hardware.Ports;

/*
 * USAGE GUIDE:
 *
 * Here is the big function: all of the code in here is the code that will run on the day of the
 * competition. Everything before the waitForStart() command will be run as initialization after the
 * robot gets turned on but before the match starts. Everything after waitForStart() will be run
 * during the match.
 */

//Create an opmode class
@Config
@Autonomous(name="Tritonics Auto")
public class AutoOpMode extends LinearOpMode {

    Ports ports;
    Ports.Builder builder;
    Telemetry dashboardTelemetry;

    PIDController lsv_lController;
    PIDController lsv_rController;
    PIDController lsh_lController;
    PIDController lsh_rController;

    public static boolean isSpecimen;
    public static int waitTime;

    public static double speed1 = 0.15;
    public static double dist1 = 50;
    public static double deg1 = 235;
    public static int target1 = 3650;

    public static double speed2 = 0.15;
    public static double dist2 = 30;
    public static double deg2 = 0;
    public static int target2 = 0;

    public static double speed3 = 1;
    public static double dist3 = 50;
    public static double deg3 = 180;
    public static int target3;

    public static double speed4 = 1;
    public static double deg4 = -45;

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        builder = new Ports.Builder();
        builder.allActive = true;
        ports = new Ports(this, builder);
        dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();
        lsv_lController = new PIDController(0.0127, 0.0004, 0.000001, 0.1, 20, ports.lsv_l);
        lsv_rController = new PIDController(0.0127, 0.0004, 0.000001, 0.1, 20, ports.lsv_r);
        lsh_lController = new PIDController(0.0127, 0.0004, 0.000001, 0, 20, ports.lsh_l);
        lsh_rController = new PIDController(0.0127, 0.0004, 0.000001, 0, 20, ports.lsh_r);

        ports.lsv_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsv_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsh_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsh_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsv_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.lsv_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.lsh_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.lsh_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //wait for the game to start
        waitForStart();

        sleep(waitTime);
        ports.specimenClaw.setPosition(1);
        ports.outtakePitchLL.setPosition(0.9);
        ports.outtakePitchLR.setPosition(0.1);
        ports.outtakePitchRR.setPosition(0.1);
        ports.outtakePitchRL.setPosition(0.9);
        Driver.driveSlides(this, ports, 0.35, 85, 160, lsv_lController, lsv_rController, 2300);
        Driver.driveSlides(this, ports, 0.75, 20, 235, lsv_lController, lsv_rController, 1000);
        Driver.driveSlides(this, ports, 0.6, 40, 0, lsv_lController, lsv_rController, 0);
        ports.specimenClaw.setPosition(0);
        Driver.rotate(this, ports, 0.75, -90);
        ports.outtakePitchLL.setPosition(0.95);
        ports.outtakePitchLR.setPosition(0.05);
        ports.outtakePitchRR.setPosition(0.05);
        ports.outtakePitchRL.setPosition(0.95);
        Driver.drive(this, ports, 1, 75, 0);
        Driver.rotate(this, ports, 0.75, -90);
        Driver.drive(this, ports, 1, 75, 0);
        Driver.drive(this, ports, 1, 44, 90);
        Driver.drive(this, ports, 0.8, 130, 160);
        Driver.drive(this, ports, 1, 20, 0);
        Driver.drive(this, ports, 1, 40, 270);
        Driver.drive(this, ports, 1, 30, 180);
        Driver.drive(this, ports, 0.3, 20, 180);
        ports.specimenClaw.setPosition(1);
        sleep(750);
        ports.outtakePitchLL.setPosition(0.9);
        ports.outtakePitchLR.setPosition(0.1);
        ports.outtakePitchRR.setPosition(0.1);
        ports.outtakePitchRL.setPosition(0.9);
        Driver.driveSlides(this, ports, 1, 110, 290, lsv_lController, lsv_rController, 2300);
        Driver.rotate(this, ports, 1, 160);
        Driver.driveSlides(this, ports, 1, 110, 150, lsv_lController, lsv_rController, 2300);
        Driver.driveSlides(this, ports, 0.8, 40, 240, lsv_lController, lsv_rController, 0);
        Driver.drive(this, ports, 1, 40, 0);
        ports.specimenClaw.setPosition(0);
        ports.outtakePitchLL.setPosition(0.95);
        ports.outtakePitchLR.setPosition(0.05);
        ports.outtakePitchRR.setPosition(0.05);
        ports.outtakePitchRL.setPosition(0.95);
        Driver.rotate(this, ports, 1, -90);
        Driver.driveSlides(this, ports, 1, 100, 0, lsv_lController, lsv_rController, 0);
        Driver.rotate(this, ports, 1, -90);
        Driver.drive(this, ports, 1, 30, 180);
        Driver.drive(this, ports, 0.3, 20, 180);
        ports.specimenClaw.setPosition(1);
        sleep(750);
        ports.outtakePitchLL.setPosition(0.9);
        ports.outtakePitchLR.setPosition(0.1);
        ports.outtakePitchRR.setPosition(0.1);
        ports.outtakePitchRL.setPosition(0.9);
        Driver.driveSlides(this, ports, 1, 110, 290, lsv_lController, lsv_rController, 2300);
        Driver.rotate(this, ports, 1, 160);
        Driver.drive(this, ports, 1, 110, 150);
        Driver.driveSlides(this, ports, 1, 20, 270, lsv_lController, lsv_rController, 0);
        Driver.drive(this, ports, 1, 10, 0);
        Driver.driveSlides(this, ports, 1, 150, 300, lsv_lController, lsv_rController, 0);
        ports.specimenClaw.setPosition(0);
    }
}