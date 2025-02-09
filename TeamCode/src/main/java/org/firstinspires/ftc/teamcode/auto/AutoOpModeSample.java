package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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
@Autonomous(name="Tritonics Auto Sample")
public class AutoOpModeSample extends LinearOpMode {

    Ports ports;
    Ports.Builder builder;
    Telemetry dashboardTelemetry;

    PIDController lsv_lController;
    PIDController lsv_rController;
    PIDController lsh_lController;
    PIDController lsh_rController;

    public static int waitTime;

    public static double speed1 = 0.2;
    public static double dist1 = 40;
    public static double deg1 = 255;
    public static int target1 = 3300;

    public static double speed2 = 1;
    public static double dist2 = 35;
    public static double deg2 = 0;

    public static double speed3 = 1;
    public static double deg3 = -45;

    public static double speed4 = 0.7;
    public static double dist4 = 30;
    public static double deg4 = 180;

    public static double speed5 = 1;
    public static double dist5 = 80;
    public static double deg5 = 0;
    public static int target5 = 2000;

    public static int target6 = 300;

    public static double speed7 = 1;
    public static double deg7 = 45;

    public static double speed8 = 1;
    public static double dist8 = 45;
    public static double deg8 = 180;

    public static double speed9 = 0.5;
    public static double dist9 = 120;
    public static double deg9 = 340;

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
        ports.outtakeClaw.setPosition(1);
        ports.intakeClaw.setPosition(1);
        Driver.driveSlides(this, ports, speed1, dist1, deg1, lsv_lController, lsv_rController, target1);
        ports.outtakePitchLL.setPosition(0);
        ports.outtakePitchLR.setPosition(1);
        ports.outtakePitchRR.setPosition(1);
        ports.outtakePitchRL.setPosition(0);
        sleep(1000);
        ports.outtakeClaw.setPosition(0);
        ports.outtakePitchLL.setPosition(1);
        ports.outtakePitchLR.setPosition(0);
        ports.outtakePitchRR.setPosition(0);
        ports.outtakePitchRL.setPosition(1);
        Driver.driveSlides(this, ports, speed2, dist2, deg2, lsv_lController, lsv_rController, 0);
        Driver.rotate(this, ports, speed3, deg3);
        Driver.driveSlides(this, ports, speed4, dist4, deg4, lsv_lController, lsv_rController, 0);
        Driver.driveSlides(this, ports, speed5, dist5, deg5, lsh_lController, lsh_rController, target5);
        ports.intakePitch.setPosition(0.3);
        sleep(300);
        ports.intakeClaw.setPosition(0);
        sleep(500);
        ports.intakeClaw.setPosition(0.78);
        sleep(500);
        ports.intakePitch.setPosition(0.66);
        Driver.linearSlides(lsh_lController, lsh_rController, 0);
        ports.outtakeClaw.setPosition(1);
        sleep(500);
        ports.intakeClaw.setPosition(0);
        Driver.linearSlides(lsh_lController, lsh_rController, target6);
        Driver.rotate(this, ports, speed7, deg7);
        Driver.driveSlides(this, ports, speed8, dist8, deg8, lsv_lController, lsv_rController, target1);
        Driver.linearSlides(lsv_lController, lsv_rController, target1);
        ports.outtakePitchLL.setPosition(0);
        ports.outtakePitchLR.setPosition(1);
        ports.outtakePitchRR.setPosition(1);
        ports.outtakePitchRL.setPosition(0);
        sleep(1000);
        ports.outtakeClaw.setPosition(0);
        ports.outtakePitchLL.setPosition(1);
        ports.outtakePitchLR.setPosition(0);
        ports.outtakePitchRR.setPosition(0);
        ports.outtakePitchRL.setPosition(1);
        Driver.driveSlides(this, ports, speed9, dist9, deg9, lsv_lController, lsv_rController, 0);
    }
}