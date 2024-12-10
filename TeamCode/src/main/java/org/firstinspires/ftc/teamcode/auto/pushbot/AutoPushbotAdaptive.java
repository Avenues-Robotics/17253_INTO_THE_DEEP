package org.firstinspires.ftc.teamcode.auto.pushbot;

import static org.firstinspires.ftc.teamcode.hardware.Driver.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Ports;

/*
 *
 * Pushbot: Autonomous Adaptive
 *
 * Scores 5 points
 * In this OpMode the driver selects the starting position using the game
 * pad.
 *
 * If the robot is in the LEFT starting position it should be set-up with
 * the front facing the basket and a sample on the left of the front
 * If the robot is in the RIGHT starting position it should be set-up with
 * the front facing the basket and a sample on the left of the front
 */

//Create an opmode class
@Config
@Autonomous(name="noah auto pushbot adaptive")
public class AutoPushbotAdaptive extends LinearOpMode {

    // this gamepad object will measure the current gamepad state
    Gamepad currentGamepad1;

    // this gamepad object will measure the previous gamepad state
    Gamepad prevGamepad1;

    // this will only return true once the op mode has been configured
    private Boolean isConfigured;

    // This object class will store the starting position that has been entered for the robot
    private enum StartingPosition {
        LEFT,
        RIGHT
    }

    private final double speed = 0.4;

    private int delay = 0;
    private Boolean delaySet = false;

    // This object will store the starting position that has been entered for the robot
    private StartingPosition startingPosition;

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

        // Initialize the gampepad objects
        prevGamepad1 = new Gamepad();
        currentGamepad1 = new Gamepad();

        // requires isConfigured variable to start false (figure out why yourself)
        isConfigured = false;

        // init the starting pos var as null
        startingPosition = null;

        // This loop will only end once the op mode has been configured or when it's been stopped
        while(!isStopRequested() && !isConfigured && !delaySet){

            // Set the value of prevGamepad1 to the value of from the previous loop
            prevGamepad1.copy(currentGamepad1);

            // Set the value of currentGamepad1 to the current state of the gamepad
            currentGamepad1.copy(gamepad1);

            telemetry.addLine("CONFIGURE AUTONOMOUS");
            telemetry.addLine("--------------------");
            telemetry.addLine("");

            // If there isn't a starting position, as for one to be inputted and record it
            if(startingPosition == null){
                telemetry.addLine("Select a starting position");
                telemetry.addLine("Press (X/□) for LEFT, (B/○) for RIGHT");

                // if the x key has been pressed, set the position as left
                if(currentGamepad1.x && !prevGamepad1.x){
                    startingPosition = StartingPosition.LEFT;
                } else if (currentGamepad1.b && !prevGamepad1.b) {
                    startingPosition = StartingPosition.RIGHT;
                }
            } else{
                // If there is a starting position, say that the opmode has been configured
                isConfigured = true;
            }
            telemetry.update();

            if(!delaySet && isConfigured) {
                telemetry.addLine("DELAY SETTING");
                telemetry.addLine("-------------");
                telemetry.addLine("");
                telemetry.addLine("Select a Delay using the d-pad UP and DOWN buttons");
                telemetry.addData("Current Delay (s)", delay);

                if(currentGamepad1.dpad_up && !prevGamepad1.dpad_up) {
                    delay++;
                } else if (currentGamepad1.dpad_down && !prevGamepad1.dpad_down) {
                    delay--;
                }

                if(currentGamepad1.a && !prevGamepad1.a){
                    delaySet = true;
                }
            }
            telemetry.update();
        }

        // inform the driver that the setup is complete
        telemetry.addLine("CONFIGURATION COMPLETE");
        telemetry.addLine("----------------------");
        telemetry.addData("Starting Position", startingPosition);
        telemetry.addData("Delay", delay);
        telemetry.update();

        //wait for the game to start
        waitForStart();

        this.sleep((long)1000*delay);

        if(startingPosition == StartingPosition.LEFT){
            drive(this, ports, speed, 80, 0);
            drive(this, ports, speed, 300, 180);
        } else if (startingPosition == StartingPosition.RIGHT) {
            drive(this, ports, speed, 84.853, 45);
            drive(this, ports, speed, 120, 0);
            rotate(this, ports, speed, -45);
            drive(this, ports, speed, 70, 0);
            drive(this, ports, speed, 70, 180);
            rotate(this, ports, speed, 45);
            drive(this, ports, speed, 200, 180);
            drive(this, ports, speed, 70, -135);
        }
    }
}