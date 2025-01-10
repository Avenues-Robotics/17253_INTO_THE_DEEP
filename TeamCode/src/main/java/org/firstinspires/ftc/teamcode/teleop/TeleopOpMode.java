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
import org.firstinspires.ftc.teamcode.hardware.Ports;
import org.firstinspires.ftc.teamcode.hardware.Telem;


@TeleOp(name = "Teleop Tritonics")
public class TeleopOpMode extends LinearOpMode {

    Ports ports;
    Ports.Builder builder;

    Gamepad prevGamepad1;
    Gamepad prevGamepad2;
    Gamepad currGamepad1;
    Gamepad currGamepad2;

    public static double servoRangeTime = 1;

    boolean intakeInverse = false;
    int handoffStep = 0;

    ElapsedTime elapsedTime = new ElapsedTime();

    PIDController lsv_lController;
    PIDController lsv_rController;

    Localization localizer;

    public static Position startingPosition = new Position(DistanceUnit.INCH, 0, 0, 0, 0);
    public static YawPitchRollAngles startingRotation = new YawPitchRollAngles(AngleUnit.DEGREES, 0, 0, 0, 0);

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

        prevGamepad1 = new Gamepad();
        prevGamepad2 = new Gamepad();
        currGamepad1 = new Gamepad();
        currGamepad2 = new Gamepad();

        localizer = new Localization(this, ports, new Pose3D(startingPosition, startingRotation));

        double max;

        waitForStart();

        elapsedTime.reset();

        while (opModeIsActive()) {
            localizer.loop();

            prevGamepad1.copy(currGamepad1);
            prevGamepad2.copy(currGamepad2);

            currGamepad1.copy(gamepad1);
            currGamepad2.copy(gamepad2);

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double drive = -currGamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double yaw = currGamepad1.right_stick_x;
            double vertical = -currGamepad2.left_stick_y;
            double horizontal = currGamepad2.right_stick_x;

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
            if(ports.lsv_r.getCurrentPosition() < 4330 && ports.lsv_l.getCurrentPosition() < 4330 || vertical < 0) {
                ports.lsv_r.setPower(vertical + 0.06);
                ports.lsv_l.setPower(vertical + 0.06);
            } else {
                ports.lsv_r.setPower(0.06);
                ports.lsv_l.setPower(0.06);
            }
            if(ports.lsh_r.getCurrentPosition() < 2220 && ports.lsh_l.getCurrentPosition() < 2220 || horizontal < 0) {
                ports.lsh_r.setPower(horizontal);
                ports.lsh_l.setPower(horizontal);
            } else {
                ports.lsh_r.setPower(0);
                ports.lsh_l.setPower(0);
            }
            // FLIPS THE OUTTAKE CLAW OVER THE ROBOT FROM OUTSIDE TO INSIDE
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
                    ports.intakePitch.setPosition(0.4);
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

            // set points

            // Comments from Mr. Carpenter below.
            // Overall comment, why are all the positions negative?
            // It's confusing, can you flip the motor directions for the
            // vertical slides?

            // high chamber
            if(currGamepad2.right_bumper && !prevGamepad2.right_bumper){
                // Is the math right here?  Seems to me that if the slides current
                // position is 1000, you would want the error in .setup() to be 1230 - 1000 = 230
                // Is my logic wrong?
                // Or if the set point is supposed to be negative (-1230), then
                // if the current position is -1000, should it be
                // -1230 - (-1000) = -230
                // Right? - SC
                lsv_lController.setup(1230-ports.lsv_l.getCurrentPosition());
                lsv_rController.setup(1230-ports.lsv_r.getCurrentPosition());
            }
            if(currGamepad2.right_bumper){
                // Same comment as above. -SC
                ports.lsv_l.setPower(lsv_lController.evaluate(1230-ports.lsv_l.getCurrentPosition()));
                ports.lsv_r.setPower(lsv_rController.evaluate(1230-ports.lsv_r.getCurrentPosition()));
            }
            // high basket
            if(currGamepad2.left_bumper && !prevGamepad2.left_bumper){
                lsv_lController.runTo(-3125);
                lsv_rController.runTo(-3125);
            }
            // low basket
            // If you are going to use the trigger, use > 0.1 or something
            // like that, otherwise noise could make this conditional true -SC
            if(currGamepad2.left_trigger !=0 && prevGamepad2.left_trigger!=0){
                lsv_lController.runTo(-1230);
                lsv_rController.runTo(-1230);
            }

            /* HANDOFF ROUTINE:
             *
             * 1. intakeClaw -> 0.05 & outtakeClaw -> 0.15 & intakePitch -> 0.4 & outtakePitch -> 0 & horizontalSlides -> 1700 if horizontalSlides < 1700 & verticalSlides -> 0
             * 2. horizontalSlides -> 910 & intakeClaw -> 0.04
             * 3. outtakeClaw -> 0.25 & intakeClaw -> 0.03
             * 4. horizontalSlides -> 1300
             */
            if(currGamepad1.dpad_right && !prevGamepad1.dpad_right) {
                handoffStep = 1;
                lsv_lController.setup(-ports.lsv_l.getCurrentPosition());
                lsv_rController.setup(-ports.lsv_r.getCurrentPosition());
            }
            if(handoffStep == 1){
                // set claw to closed
                ports.intakeClaw.setPosition(0.85);
                // set intake claw open
                ports.outtakeClaw.setPosition(0.15);
                telemetry.addLine("set intake claw closed, set outtake claw open");
                telemetry.update();
                // I would avoid sleep() in teleop.  You have a loop running
                // and you are interrupting that loop.  Use a timer and a while
                // loop instead.  THen use state variables like handoffStep to control
                // what can run or can't run while you are waiting.
                sleep(2000);
                // bring intake claw up and over
                ports.intakePitch.setPosition(0.4);
                // bring outtake claw up and over
                ports.outtakePitchL.setPosition(0);
                ports.outtakePitchR.setPosition(1);
                telemetry.addLine("set intake claw up and over, set outtake claw up and over");
                telemetry.update();
                sleep(2000);
                // bring slides out enough so that the incoming claw doesn't bash them
                if(ports.lsh_l.getCurrentPosition() < 1700 || ports.lsh_r.getCurrentPosition() < 1700){
                    sleep(2000);
                    ports.lsh_l.setPower(1);
                    ports.lsh_r.setPower(1);
                } else {
                    sleep(2000);
                    ports.lsh_l.setPower(0);
                    ports.lsh_r.setPower(0);
                }
                // bring linear slides down
                sleep(2000);
                ports.lsv_l.setPower(lsv_lController.evaluate(-ports.lsv_l.getCurrentPosition()));
                ports.lsv_r.setPower(lsv_rController.evaluate(-ports.lsv_r.getCurrentPosition()));
                sleep(2000);
                // begin handoff setup 2
                if((ports.lsv_l.getCurrentPosition() < 20 || ports.lsv_r.getCurrentPosition() < 20) && (ports.lsh_l.getCurrentPosition() < 1700 || ports.lsh_r.getCurrentPosition() < 1700)){
                    handoffStep = 2;
                    telemetry.addLine("here now 1!");
                }
            }
//            if(handoffStep == 2){
//                telemetry.addLine("here now 2!");
//                // slightly widen intake claw so it's ready to hand the specimen off to the outtake claw
//                ports.intakeClaw.setPosition(0.04);
//                // bring the horizontal linear slides in
//                ports.lsh_l.setPower(1);
//                ports.lsh_r.setPower(1);
//                // begin step 3
//                if(ports.lsh_l.getCurrentPosition() <  940 || ports.lsh_r.getCurrentPosition() < 940){
//                    ports.lsh_l.setPower(0);
//                    ports.lsh_r.setPower(0);
//                    handoffStep = 3;
//                }
//            }
//            if(handoffStep == 3){
//                // outtake claw grabs onto the specimen
//                ports.outtakeClaw.setPosition(0.25);
//                // intake claw releases specimen
//                ports.intakeClaw.setPosition(0.03);
//                // begin handoff step 4
//                if(ports.intakeClaw.getPosition() < 0.35){
//                    handoffStep = 4;
//                }
//            }
//            if(handoffStep == 4){
//                // bring horizontal linear slides out enough
//                ports.lsh_l.setPower(1);
//                ports.lsh_r.setPower(1);
//                if(ports.lsh_l.getCurrentPosition() > 1700 || ports.lsh_r.getCurrentPosition() > 1700){
//                    ports.lsh_l.setPower(0);
//                    ports.lsh_r.setPower(0);
//                    handoffStep = 0;
//                }
//            }

            // WHAT NEEDS TO BE ADDED TO GAMEPAD 1
            // low basket set position and high basket set position
            // low chamber and high chamber set position
            // move a set distance back from the chamber to lock the specimen on the hanging bar

            // and make a gamepad 2 function for the bumper that widens the outtake claw slightly before the manual handoff to the outtake claw


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
            Telem.update(this);

            elapsedTime.reset();
        }
    }
}