package org.firstinspires.ftc.teamcode.auto.pushbot;

import static org.firstinspires.ftc.teamcode.hardware.Driver.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Ports;

/*
 *
 * Pushbot: Autonomous Left
 *
 * Scores 5 points
 * In this OpMode the robot starts on the LEFT, scores a sample in the
 * NET ZONE, and parks in the OBSERVATION ZONE.
 *
 * The robot should start facing the NET ZONE with a SAMPLE touching
 * the front cross bar of the robot so it can push it straight into
 * the NET ZONE.
 */

//Create an opmode class
@Config
@Autonomous(name="noah auto pushbot left")
public class AutoPushbotLeft extends LinearOpMode {

    Ports ports;
    Ports.Builder builder;

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        builder.wheelsActive = true;
        ports = new Ports(this, builder);

        ports.fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ports.fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ports.br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ports.bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //wait for the game to start
        waitForStart();

        drive(this, ports, 0.4, 80, 0);
        drive(this, ports, 0.4, 300, 180);

    }
}
