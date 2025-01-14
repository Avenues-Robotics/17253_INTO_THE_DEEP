package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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

    public static boolean isSpecimen = true;

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        builder = new Ports.Builder();
        builder.allActive = true;
        ports = new Ports(this, builder);
        dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();
        lsv_lController = new PIDController(0.0127, 0.0004, 0.000001, 0.06, 20, ports.lsv_l);
        lsv_rController = new PIDController(0.0127, 0.01, 0.000001, 0.06, 20, ports.lsv_r);

        //wait for the game to start
        waitForStart();

        if(isSpecimen) {
            ports.specimenClaw.setPosition(1);
            ports.outtakePitchR.setPosition(0);
            ports.outtakePitchL.setPosition(1);
            Driver.driveSlides(this, ports, 0.2, 85, 165, lsv_lController, lsv_rController, 500);
            ports.specimenClaw.setPosition(0);
            Driver.drive(this, ports, 0.75, 20, 235);
            ports.outtakePitchR.setPosition(1);
            ports.outtakePitchL.setPosition(0);
            Driver.driveSlides(this, ports, 1, 20, 0, lsv_lController, lsv_rController, 150);
            Driver.rotate(this, ports, 0.75, 195);
            Driver.drive(this, ports, 1, 150, 90);
            Driver.drive(this, ports, 1, 40, 180);
            sleep(1000);
            Driver.drive(this, ports, 1, 25, 180);
            ports.specimenClaw.setPosition(1);
            Driver.driveSlides(this, ports, 1, 145, 290, lsv_lController, lsv_rController, 500);
            ports.outtakePitchR.setPosition(0);
            ports.outtakePitchL.setPosition(1);
            Driver.rotate(this, ports, 1, 155);
            Driver.drive(this, ports, 1, 90, 150);
            ports.specimenClaw.setPosition(0);
            Driver.driveSlides(this, ports, 1, 170, 310, lsv_lController, lsv_rController, 2000);
        } else {

        }
    }
}