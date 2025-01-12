package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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

    public static double speedOne;
    public static double cmOne;
    public static double degreesOne;

    public static double deg;

    public static double speedTwo;
    public static double cmTwo;
    public static double degreesTwo;

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        builder = new Ports.Builder();
        builder.allActive = true;
        ports = new Ports(this, builder);
        dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();

        //wait for the game to start
        waitForStart();

        Driver.drive(this, ports, 0.75, 85, 165);
        Driver.drive(this, ports, 0.75, 20, 235);
        Driver.drive(this, ports, 1, 20 , 0);
        Driver.rotate(this, ports, 0.75, 195);
        Driver.drive(this, ports, 1, 150, 90);
        Driver.drive(this, ports, 1, 40, 180);
        sleep(1000);
        Driver.drive(this, ports, 1, 15, 180);
        Driver.drive(this, ports, 1, 130, 290);
        Driver.rotate(this, ports, 1, 155);
        Driver.drive(this, ports, 1, 90, 150);
    }
}