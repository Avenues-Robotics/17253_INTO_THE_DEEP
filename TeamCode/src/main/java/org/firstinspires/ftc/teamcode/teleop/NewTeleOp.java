package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.classes.Localization;
import org.firstinspires.ftc.teamcode.classes.PIDController;
import org.firstinspires.ftc.teamcode.hardware.Driver;
import org.firstinspires.ftc.teamcode.hardware.Ports;
import org.firstinspires.ftc.teamcode.hardware.Telem;


@TeleOp(name = "TeleOp Tritonics State")
public class NewTeleOp extends LinearOpMode {

    Ports ports;
    Ports.Builder builder;

    Gamepad prevGamepad1;
    Gamepad prevGamepad2;
    Gamepad currGamepad1;
    Gamepad currGamepad2;

    public static double servoRangeTime = 1;

    boolean intakeInverse = false;
    int handoffStep = 0;

    boolean buttonPressed = false;

    ElapsedTime elapsedTime = new ElapsedTime();

    PIDController lsv_lController;
    PIDController lsv_rController;

    PIDController lsh_lController;
    PIDController lsh_rController;

    Localization localizer;


    public static Position startingPosition = new Position(DistanceUnit.INCH, 0, 0, 0, 0);
    public static YawPitchRollAngles startingRotation = new YawPitchRollAngles(AngleUnit.DEGREES, 0, 0, 0, 0);


    double max;
    double tolerance = 30;

    // STATE MACHINE VARIABLES
    private String vSlideState = "MANUAL";
    private double lsv_lPower= 0;
    private double lsv_rPower = 0;


    @Override
    public void runOpMode() {

        builder = new Ports.Builder();
        builder.wheelsActive = true;
        builder.slidesActive = true;
        builder.servosActive = true;
        ports = new Ports(this, builder);

        ports.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsv_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsv_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsh_r.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.lsh_l.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ports.fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.lsv_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.lsv_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.lsh_r.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ports.lsh_l.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lsv_lController = new PIDController(0.0127, 0.0004, 0.000001, 0.06, 20, ports.lsv_l);
        lsv_rController = new PIDController(0.0127, 0.0004, 0.000001, 0.06, 20, ports.lsv_r);

        lsh_lController = new PIDController(0.0127, 0.0004, 0.000001, 0, 20, ports.lsh_l);
        lsh_rController = new PIDController(0.0127, 0.0004, 0.000001, 0, 20, ports.lsh_r);

        prevGamepad1 = new Gamepad();
        prevGamepad2 = new Gamepad();
        currGamepad1 = new Gamepad();
        currGamepad2 = new Gamepad();


        ElapsedTime handoffElapsedTime = new ElapsedTime();

        localizer = new Localization(this, ports, new Pose3D(startingPosition, startingRotation));

        waitForStart();

        elapsedTime.reset();

        while (opModeIsActive()) {
            localizer.loop();

            prevGamepad1.copy(currGamepad1);
            prevGamepad2.copy(currGamepad2);

            currGamepad1.copy(gamepad1);
            currGamepad2.copy(gamepad2);


            // **** VERTICAL SLIDES ****
            // USE CONTROLLER TO SET STATE
            if(currGamepad2.left_stick_y >= 0.05){
                vSlideState = "MANUAL";
            } else if(currGamepad1.a && !prevGamepad1.a){
                vSlideState = "HIGH CHAMBER";
            } else if(currGamepad1.b && !prevGamepad1.b){
                vSlideState = "HIGH BASKET";
            }

            // USE STATE TO CALCULATE POWER
            if(vSlideState == "MANUAL"){
                double vertical = -currGamepad2.left_stick_y;

                if(ports.lsv_l.getCurrentPosition() < 4330 || vertical < 0) {
                    lsv_lPower = vertical + 0.06;
                    lsv_rPower = vertical + 0.06;
                } else {
                    lsv_lPower = 0.06;
                    lsv_rPower = 0.06;
                }
            } else if(vSlideState == "HIGH CHAMBER"){
                lsv_lPower = lsv_lController.evaluate(500 - ports.lsv_l.getCurrentPosition());
                lsv_rPower = lsv_rController.evaluate(500 - ports.lsv_l.getCurrentPosition());
            } else if(vSlideState == "HIGH BASKET"){
                lsv_lPower = lsv_lController.evaluate(3650 - ports.lsv_l.getCurrentPosition());
                lsv_rPower = lsv_rController.evaluate(3650 - ports.lsv_l.getCurrentPosition());
            }

            // SET POWER
            ports.lsv_l.setPower(lsv_lPower);
            ports.lsv_r.setPower(lsv_rPower);


            // **** HORIZONTAL SLIDES ****
            double horizontal = currGamepad2.right_stick_x;

            if(ports.lsh_r.getCurrentPosition() < 2220 && ports.lsh_l.getCurrentPosition() < 2220) {
                ports.lsh_r.setPower(horizontal);
                ports.lsh_l.setPower(horizontal);
            } else {
                ports.lsh_r.setPower(0);
                ports.lsh_l.setPower(0);
            }

            // X = SQUARE
            // Y = TRIANGLE
            // A = CROSS
            // B = CIRCLE

            // **** MECANUM WHEELS ****

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double drive = -currGamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double yaw = currGamepad1.right_stick_x;

            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            double fr = (drive - strafe - yaw) * Math.abs(drive - strafe - yaw);
            double fl = (drive + strafe + yaw) * Math.abs(drive + strafe + yaw);
            double br = (drive + strafe - yaw) * Math.abs(drive + strafe - yaw);
            double bl = (drive - strafe + yaw) * Math.abs(drive - strafe + yaw);

            // Normalize the values so no wheel power exceeds 100%
            max = Math.max(Math.max(Math.max(Math.abs(fl), Math.abs(fr)), Math.abs(bl)), Math.abs(br));

            if (max > 1.0) {
                fl /= max;
                fr /= max;
                bl /= max;
                br /= max;
            }

            if(currGamepad2.y && !prevGamepad2.y) {
                intakeInverse = !intakeInverse;
            }

            ports.fl.setPower(fl);
            ports.fr.setPower(fr);
            ports.bl.setPower(bl);
            ports.br.setPower(br);

            // **** SERVOS ****
            // flip outtake claw from inside to outside robot
            if(currGamepad2.dpad_up){
                ports.outtakePitchL.setPosition(1);
                ports.outtakePitchR.setPosition(0);
            } else if(currGamepad2.dpad_down){
                ports.outtakePitchL.setPosition(0);
                ports.outtakePitchR.setPosition(1);
            }
            // opens and closes outtake claw
            if(currGamepad2.dpad_right){
                ports.outtakeClaw.setPosition(0.25);
            } else if(currGamepad2.dpad_left){
                ports.outtakeClaw.setPosition(0);
            }
            // open and closes intake claw
            if(currGamepad2.x && !prevGamepad2.x){
                if(intakeInverse) {
                    ports.intakeClaw.setPosition(0.85); // open
                    intakeInverse = false;
                } else {
                    ports.intakeClaw.setPosition(0.05); // closed
                }
            }
            // rotates intake claw up and down
            if(currGamepad2.a && !prevGamepad2.a){
                if(intakeInverse){
                    ports.intakePitch.setPosition(0.5);
                    intakeInverse = false;
                } else {
                    ports.intakePitch.setPosition(0.1);
                }
            }

            // horizontal rotates intake claw
            if(currGamepad2.b) {
                if(intakeInverse){
                    ports.intakeRoll.setPosition(ports.intakeRoll.getPosition() + elapsedTime.seconds());
                } else {
                    ports.intakeRoll.setPosition(ports.intakeRoll.getPosition() - elapsedTime.seconds());
                }
            }

            // specimen claw open and close
            if(currGamepad2.right_bumper && !prevGamepad2.right_bumper){
                ports.specimenClaw.setPosition(0.8);
            }
            if(currGamepad2.left_bumper && !prevGamepad2.left_bumper){
                ports.specimenClaw.setPosition(0);
            }

            // **** AUTOMATED HANDOFF ****
            if(currGamepad1.dpad_up && !prevGamepad1.dpad_up){
                handoffStep = 1;
            }
            if(handoffStep == 1) { // open claws and setup slides
                ports.fr.setPower(0);
                ports.br.setPower(0);
                ports.fl.setPower(0);
                ports.bl.setPower(0);

                ports.intakePitch.setPosition(0.4);
                ports.outtakeClaw.setPosition(0);
                lsv_lController.setup(-ports.lsv_l.getCurrentPosition());
                lsv_rController.setup(-ports.lsv_l.getCurrentPosition());
                handoffStep = 2;
            }

            if(handoffStep == 2) { // bring vertical slides fully down
                ports.lsv_l.setPower(lsv_lController.evaluate(-ports.lsv_l.getCurrentPosition())); // error: between 0 and current pos
                ports.lsv_r.setPower(lsv_rController.evaluate(-ports.lsv_l.getCurrentPosition()));

                if(ports.lsv_l.getCurrentPosition() < 10) {
                    handoffStep = 3;
                }
            }

            if(handoffStep == 3) { //  setup horizontal slides
                lsh_lController.setup(100-ports.lsh_l.getCurrentPosition());
                lsh_rController.setup(100-ports.lsh_r.getCurrentPosition());
                handoffStep = 4;
            }

            if(handoffStep == 4) { // bring horizontal slides in
                ports.lsh_l.setPower(lsh_lController.evaluate(100-ports.lsh_l.getCurrentPosition()));
                ports.lsh_r.setPower(lsh_rController.evaluate(100-ports.lsh_r.getCurrentPosition()));

                if(!(Math.abs(ports.lsh_l.getCurrentPosition()-100) > 10 || Math.abs(ports.lsh_r.getCurrentPosition()-100) > 10)){
                    handoffStep = 5;
                }
            }

            if(handoffStep == 6) { // close outtake claw around sample
                ports.outtakeClaw.setPosition(1);
                handoffElapsedTime.reset();
                handoffStep = 7;
            }

            if(handoffStep == 7 && handoffElapsedTime.seconds() >= 0.3) { // open intake claw and release sample
                ports.intakeClaw.setPosition(1);
                handoffStep = 8;
            }

            if(handoffStep == 8) { // setup horizontal slides
                lsh_lController.setup(250-ports.lsh_l.getCurrentPosition());
                lsh_rController.setup(250-ports.lsh_r.getCurrentPosition());
                handoffStep = 9;
            }

            if(handoffStep == 9) { // bring horizontal slides back out, everything finished
                ports.lsh_l.setPower(lsh_lController.evaluate(250-ports.lsh_l.getCurrentPosition()));
                ports.lsh_r.setPower(lsh_rController.evaluate(250-ports.lsh_r.getCurrentPosition()));

                if(!(Math.abs(ports.lsh_l.getCurrentPosition()-250) < 10 || Math.abs(ports.lsh_r.getCurrentPosition()-250) < 10)){
                    handoffStep = 0;
                }
            }

            //TELEMETRY
            telemetry.addLine("Intake Inverse: " + intakeInverse);
            telemetry.addData("Front Left Pow", ports.fl.getPower());
            telemetry.addData("Back Left Pow", ports.bl.getPower());
            telemetry.addData("Front Right Pow", ports.fr.getPower());
            telemetry.addData("Back Right Pow", ports.br.getPower());
            telemetry.addData("Intake Claw Position", ports.intakeClaw.getPosition());
            telemetry.addData("Outtake Claw Position", ports.outtakeClaw.getPosition());
            telemetry.addData("Linear Slide Vertical Right Position", ports.lsv_r.getCurrentPosition());
            telemetry.addData("Linear Slide Vertical Left Position", ports.lsv_l.getCurrentPosition());
            telemetry.addData("Linear Slide Horizontal Right Position", ports.lsh_r.getCurrentPosition());
            telemetry.addData("Linear Slide Horizontal Left Position", ports.lsh_l.getCurrentPosition());
            telemetry.addData("Location", localizer.getRobotPosition());
            telemetry.addData("Handoff step: ", handoffStep);
            Telem.update(this);

            elapsedTime.reset();
        }
    }
}