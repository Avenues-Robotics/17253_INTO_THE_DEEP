package org.firstinspires.ftc.teamcode.auto.pushbot;

import static org.firstinspires.ftc.teamcode.hardware.Driver.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Ports;

/*
 *
 * Pushbot: Autonomous Right
 *
 * Scores 5 points
 * In this OpMode the robot starts on the RIGHT, scores a sample in the
 * NET ZONE, and parks in the OBSERVATION ZONE while avoiding a stationary
 * alliance partner robot.
 *
 * The robot should start on the right with the forward end pointed left
 */

//Create an opmode class
@Config
@Autonomous(name="noah auto pushbot right")
public class AutoPushbotRight extends LinearOpMode {

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

        drive(this, ports, 0.4, 84.853, 45);
        drive(this, ports, 0.4, 120, 0);
        rotate(this, ports, 0.4, -45);
        drive(this, ports, 0.4, 70, 0);
        drive(this, ports, 0.4, 70, 180);
        rotate(this, ports, 0.4, 45);
        drive(this, ports, 0.4, 200, 180);
        drive(this, ports, 0.4, 70, -135);

    }
}
