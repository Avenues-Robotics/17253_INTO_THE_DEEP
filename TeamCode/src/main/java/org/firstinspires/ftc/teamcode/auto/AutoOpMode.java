package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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

        //wait for the game to start
        waitForStart();

        sleep(waitTime);
        ports.specimenClaw.setPosition(1);
        ports.outtakePitchRR.setPosition(1);
        ports.outtakePitchRL.setPosition(0);
        ports.outtakePitchLR.setPosition(0);
        ports.outtakePitchLL.setPosition(1);
        Driver.driveSlides(this, ports, 0.2, 85, 165, lsv_lController, lsv_rController, 850);
        ports.specimenClaw.setPosition(0);
        Driver.drive(this, ports, 0.75, 20, 235);
        ports.outtakePitchRR.setPosition(1);
        ports.outtakePitchRL.setPosition(0);
        ports.outtakePitchLR.setPosition(0);
        ports.outtakePitchLL.setPosition(1);

        Driver.driveSlides(this, ports, 0.2, 20, 0, lsv_lController, lsv_rController, 500);
        Driver.rotate(this, ports, 0.75, 195);
        Driver.drive(this, ports, 1, 150, 90);
        Driver.drive(this, ports, 1, 40, 180);
        sleep(1000);
        Driver.drive(this, ports, 1, 15, 180);
        ports.specimenClaw.setPosition(1);
        sleep(750);
        Driver.driveSlides(this, ports, 1, 130, 290, lsv_lController, lsv_rController, 850);
        ports.outtakePitchRR.setPosition(0);
        ports.outtakePitchRL.setPosition(1);
        ports.outtakePitchLR.setPosition(0);
        ports.outtakePitchLL.setPosition(1);
        Driver.rotate(this, ports, 1, 155);
        Driver.drive(this, ports, 1, 90, 150);
        ports.specimenClaw.setPosition(0);
        Driver.driveSlides(this, ports, 1, 130, 310, lsv_lController, lsv_rController, 0);
        ports.intakePitch.setPosition(0.4);
        Driver.driveSlides(this, ports, 0.15, 75, 270, lsh_lController, lsh_rController, -30);

    }
}