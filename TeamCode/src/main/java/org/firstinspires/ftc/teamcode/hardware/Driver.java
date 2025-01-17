package org.firstinspires.ftc.teamcode.hardware;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.classes.PIDController;

/*
 * USAGE GUIDE:
 *
 * The Driver class allows you to actually move to robot around using the wheels, there are two
 * functions in the driver class: drive and rotate.
 *
 * Driver.drive(a, b, c, d) can move the robot in any direction you want for any distance you want
 * at any speed you want. The first parameter (a) is the opMode. If you are calling this from the
 * opMode simply pass in "this" (without the quote marks), if you are calling this from somewhere
 * else that already had the opMode passed into it, pass "opMode" (without the quote marks). Next is
 * the speed (b), which can be anywhere from -1 to 1 and specifies how fast the robot moves, the
 * faster you go, though, the less precise you are. Third is distance in cm (c), this tells the
 * robot how far to travel at that speed, if the speed is anywhere above ~0.5 it might drift a
 * little at the end. Finally the angle you want to robot to move at (d); input any degree value and
 * that is the direction the robot will move (counterclockwise) without rotating!
 *
 * Driver.rotate(a, b, c) just like in the previous function a is the opMode, bass in this or opMode
 * depending on your situation. Next is the speed (b) that you would like it to rotate at, this is
 * again a scale from -1 to 1. Finally the number of degrees you would like to rotate (c) in the
 * clockwise direction. Remember that if you set the speed to high you might overshoot the rotation.
 */

public class Driver {

    //drive function, input speed, distance in cm, and degree angle of the movement
    public static void drive(LinearOpMode opMode, Ports ports, double speed, double cm, double degrees) {

        //Instantiate the multipliers that will control the speed of each wheel
        double frblMultiplier;
        double flbrMultiplier;

        double radians = degrees * PI / 180;

        frblMultiplier = cos(radians)-sin(radians);
        flbrMultiplier = cos(radians)+sin(radians);

        //Debug (if power level caps)
        if (speed*frblMultiplier > 1 || speed*flbrMultiplier > 1) {
            Telem.add("Drive Status", "The set speed and direction has maxed out the speed of one of the wheels. Direction and speed may not be accurate");
        }

        // Reset the tick encoders to zero
        ports.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Calculate the number of ticks based on the distance and a conversion constant
        int ticks = (int) (cm * 17.467);

        //Calculate the number of ticks required for each motor to move
        int frblTicks = (int) (ticks * frblMultiplier);
        int flbrTicks = (int) (ticks * flbrMultiplier);

        // set the target position
        ports.fr.setTargetPosition(frblTicks);
        ports.fl.setTargetPosition(flbrTicks);
        ports.br.setTargetPosition(flbrTicks);
        ports.bl.setTargetPosition(frblTicks);

        // Change the mode to spin until reaching the position
        ports.fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // make the motors run at the given speed
        ports.fr.setPower(speed * frblMultiplier);
        ports.fl.setPower(speed * flbrMultiplier);
        ports.br.setPower(speed * flbrMultiplier);
        ports.bl.setPower(speed * frblMultiplier);

        // Run while the motors are moving
        while (Math.abs(ports.fr.getTargetPosition()-ports.fr.getCurrentPosition()) >= 30 ||
                Math.abs(ports.fr.getTargetPosition()-ports.fr.getCurrentPosition()) >= 30) {

            // Update the telem data
            opMode.telemetry.addData("Running to", "Font Right and Back Left: " + frblTicks + " | Front Left and Back Right: " + flbrTicks);
            opMode.telemetry.addData("Current pos", "Front Right: " + ports.fr.getCurrentPosition() + " | Front Left: " + ports.fl.getCurrentPosition() + " | Back Right: " + ports.br.getCurrentPosition() + " | Back Left: " + ports.bl.getCurrentPosition());
            Telem.update(opMode);
        }

        Telem.remove("Drive Status");

        // Stop the motors
        ports.fr.setPower(0);
        ports.fl.setPower(0);
        ports.br.setPower(0);
        ports.bl.setPower(0);

    }

    //drive function, input speed, distance in cm, and degree angle of the movement
    public static void driveSlides(LinearOpMode opMode, Ports ports, double speed, double cm, double degrees, PIDController slideOne, PIDController slideTwo, int target) {

        //Instantiate the multipliers that will control the speed of each wheel
        double frblMultiplier;
        double flbrMultiplier;

        double radians = degrees * PI / 180;

        frblMultiplier = cos(radians)-sin(radians);
        flbrMultiplier = cos(radians)+sin(radians);

        //Debug (if power level caps)
        if (speed*frblMultiplier > 1 || speed*flbrMultiplier > 1) {
            Telem.add("Drive Status", "The set speed and direction has maxed out the speed of one of the wheels. Direction and speed may not be accurate");
        }

        // Reset the tick encoders to zero
        ports.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Calculate the number of ticks based on the distance and a conversion constant
        int ticks = (int) (cm * 17.467);

        //Calculate the number of ticks required for each motor to move
        int frblTicks = (int) (ticks * frblMultiplier);
        int flbrTicks = (int) (ticks * flbrMultiplier);

        // set the target position
        ports.fr.setTargetPosition(frblTicks);
        ports.fl.setTargetPosition(flbrTicks);
        ports.br.setTargetPosition(flbrTicks);
        ports.bl.setTargetPosition(frblTicks);

        // Change the mode to spin until reaching the position
        ports.fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // make the motors run at the given speed
        ports.fr.setPower(speed * frblMultiplier);
        ports.fl.setPower(speed * flbrMultiplier);
        ports.br.setPower(speed * flbrMultiplier);
        ports.bl.setPower(speed * frblMultiplier);

        slideOne.setup(target - slideOne.getSlide().getCurrentPosition());
        slideTwo.setup(target - slideTwo.getSlide().getCurrentPosition());

        // Run while the motors are moving
        while (Math.abs(ports.fr.getTargetPosition()-ports.fr.getCurrentPosition()) >= 30 ||
                Math.abs(ports.fl.getTargetPosition()-ports.fl.getCurrentPosition()) >= 30
         ) {

            slideOne.getSlide().setPower(slideOne.evaluate(target - slideOne.getSlide().getCurrentPosition()));
            slideTwo.getSlide().setPower(slideTwo.evaluate(target - slideOne.getSlide().getCurrentPosition()));

            // Update the telem data
            opMode.telemetry.addData("Slide positions", slideOne.getSlide().getCurrentPosition());
            opMode.telemetry.addData("Running to", "Font Right and Back Left: " + frblTicks + " | Front Left and Back Right: " + flbrTicks);
            opMode.telemetry.addData("Current pos", "Front Right: " + ports.fr.getCurrentPosition() + " | Front Left: " + ports.fl.getCurrentPosition() + " | Back Right: " + ports.br.getCurrentPosition() + " | Back Left: " + ports.bl.getCurrentPosition());
            Telem.update(opMode);
        }

        Telem.remove("Drive Status");

        // Stop the motors
        ports.fr.setPower(0);
        ports.fl.setPower(0);
        ports.br.setPower(0);
        ports.bl.setPower(0);

    }

    public static void rotate(LinearOpMode opMode, Ports ports, double speed, double degrees){

        // Reset the tick encoders to zero
        ports.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Convert from degrees to ticks
        int ticks = (int) (degrees*12.4);

        // set the target position
        ports.fr.setTargetPosition(-ticks);
        ports.fl.setTargetPosition(ticks);
        ports.br.setTargetPosition(-ticks);
        ports.bl.setTargetPosition(ticks);

        // Change the mode to spin until reaching the position
        ports.fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // make the motors run at the given speed
        ports.fr.setPower(-speed);
        ports.fl.setPower(-speed);
        ports.br.setPower(-speed);
        ports.bl.setPower(-speed);

        // Run while both motors are moving
        while (ports.fr.isBusy() || ports.fl.isBusy()) {
            // Update the telem data
            opMode.telemetry.addData("Running to", "Left " + ticks + " | Right: " + -ticks);
            opMode.telemetry.addData("Current pos", "Front Right: " + ports.fr.getCurrentPosition() + " | Front Left: " + ports.fl.getCurrentPosition() + " | Back Right: " + ports.br.getCurrentPosition() + " | Back Left: " + ports.bl.getCurrentPosition());
            Telem.update(opMode);
        }

        // Stop the motors
        ports.fr.setPower(0);
        ports.fl.setPower(0);
        ports.br.setPower(0);
        ports.bl.setPower(0);
    }

    public static void drift(LinearOpMode opMode, Ports ports, double speed, double cm, double degrees, double rotation) {
        //Instantiate the multipliers that will control the speed of each wheel
        double frblMultiplier;
        double flbrMultiplier;

        double radians = degrees * PI / 180;

        frblMultiplier = cos(radians)-sin(radians);
        flbrMultiplier = cos(radians)+sin(radians);

        //Debug (if power level caps)
        if (speed*frblMultiplier > 1 || speed*flbrMultiplier > 1) {
            Telem.add("Drive Status", "The set speed and direction has maxed out the speed of one of the wheels. Direction and speed may not be accurate");
        }

        // Reset the tick encoders to zero
        ports.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ports.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Calculate the number of ticks based on the distance and a conversion constant
        int ticks = (int) ((cm * 17.467) + (rotation*12.4));

        //Calculate the number of ticks required for each motor to move
        int frblTicks = (int) (ticks * frblMultiplier);
        int flbrTicks = (int) (ticks * flbrMultiplier);
        //Convert from degrees to ticks

        // set the target position
        ports.fr.setTargetPosition(frblTicks);
        ports.fl.setTargetPosition(flbrTicks);
        ports.br.setTargetPosition(flbrTicks);
        ports.bl.setTargetPosition(frblTicks);

        // Change the mode to spin until reaching the position
        ports.fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ports.bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // make the motors run at the given speed
        ports.fr.setPower(speed * frblMultiplier-speed);
        ports.fl.setPower(speed * flbrMultiplier-speed);
        ports.br.setPower(speed * flbrMultiplier-speed);
        ports.bl.setPower(speed * frblMultiplier-speed);

        // Run while both motors are moving
        while (ports.fr.isBusy() || ports.fl.isBusy()) {
            // Update the telem data
            opMode.telemetry.addData("Running to", "Left " + ticks + " | Right: " + -ticks);
            opMode.telemetry.addData("Current pos", "Front Right: " + ports.fr.getCurrentPosition() + " | Front Left: " + ports.fl.getCurrentPosition() + " | Back Right: " + ports.br.getCurrentPosition() + " | Back Left: " + ports.bl.getCurrentPosition());
            Telem.update(opMode);
        }

        // Stop the motors
        ports.fr.setPower(0);
        ports.fl.setPower(0);
        ports.br.setPower(0);
        ports.bl.setPower(0);

    }

    public static void circularArc(LinearOpMode opMode, Ports ports, double speed, double target, double degrees, double sharpness) {

        //Instantiate the multipliers that will control the speed of each wheel
        double frblMultiplier;
        double flbrMultiplier;

        double radians = degrees * PI / 180;

        frblMultiplier = cos(radians)-sin(radians);
        flbrMultiplier = cos(radians)+sin(radians);

        //Debug (if power level caps)
        if (speed*frblMultiplier > 1 || speed*flbrMultiplier > 1) {
            Telem.add("Drive Status", "The set speed and direction has maxed out the speed of one of the wheels. Direction and speed may not be accurate");
        }

        double radiansTarget = target * PI / 180;
        double radiansInconstant = radians;

        // Run while the motors are moving
        while (radiansInconstant >= radiansTarget) {

            // Update the telem data
            //opMode.telemetry.addData("Running to", "Font Right and Back Left: " + frblTicks + " | Front Left and Back Right: " + flbrTicks);
            //opMode.telemetry.addData("Current pos", "Front Right: " + fr.getCurrentPosition() + " | Front Left: " + fl.getCurrentPosition() + " | Back Right: " + br.getCurrentPosition() + " | Back Left: " + bl.getCurrentPosition());
            //Telem.update(opMode);

            //Circle
            frblMultiplier = cos(radiansInconstant)-sin(radiansInconstant);
            flbrMultiplier = cos(radiansInconstant)+sin(radiansInconstant);
            radiansInconstant += sharpness;
            ports.fr.setPower(speed * frblMultiplier);
            ports.fl.setPower(speed * flbrMultiplier);
            ports.br.setPower(speed * flbrMultiplier);
            ports.bl.setPower(speed * frblMultiplier);

            // Reset the tick encoders to zero
            ports.fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ports.fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ports.br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ports.bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Change the mode to spin until reaching the position
            ports.fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ports.fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ports.br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ports.bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            ports.fr.setTargetPosition((int) (100 * frblMultiplier));
            ports.fl.setTargetPosition((int) (100 * flbrMultiplier));
            ports.br.setTargetPosition((int) (100 * flbrMultiplier));
            ports.bl.setTargetPosition((int) (100 * frblMultiplier));

            while(ports.fr.isBusy() || ports.fl.isBusy()) {
                // Update the telem data
                opMode.telemetry.addData("Current Angle", radiansInconstant);
                Telem.update(opMode);
            }
        }

        Telem.remove("Drive Status");

        // Stop the motors
        ports.fr.setPower(0);
        ports.fl.setPower(0);
        ports.br.setPower(0);
        ports.bl.setPower(0);

    }

    public static void claw(Ports ports, double position) {
        ports.intakeClaw.getController().setServoPosition(0, position);
    }

    public static void linearSlidesHUp(Ports ports, double power, double maxExtension) {
        if (ports.lsh_r.getCurrentPosition()<maxExtension && ports.lsh_l.getCurrentPosition()<maxExtension){
            ports.lsh_r.setPower(power);
            ports.lsh_l.setPower(power);
        }
        else {
            ports.lsh_r.setPower(0);
            ports.lsh_l.setPower(0);
        }
    }

    public static void linearSlidesHDown(Ports ports, double power, double maxExtension) {
        if (ports.lsh_r.getCurrentPosition()>maxExtension && ports.lsh_l.getCurrentPosition()>maxExtension){
            ports.lsh_r.setPower(power);
            ports.lsh_l.setPower(power);
        }
        else {
            ports.lsh_r.setPower(0);
            ports.lsh_l.setPower(0);
        }
    }

    public static void linearSlidesVUp(Ports ports, double power, double maxExtension) {
        if (ports.lsv_r.getCurrentPosition()<maxExtension && ports.lsv_l.getCurrentPosition()<maxExtension){
            ports.lsv_r.setPower(power);
            ports.lsv_l.setPower(power);
        }
        else {
            ports.lsv_r.setPower(0);
            ports.lsv_l.setPower(0);
        }
    }

    public static void linearSlidesVDown(Ports ports, double power, double maxExtension) {
        if (ports.lsv_r.getCurrentPosition()>maxExtension && ports.lsv_l.getCurrentPosition()>maxExtension){
            ports.lsv_r.setPower(power);
            ports.lsv_l.setPower(power);
        }
        else {
            ports.lsv_r.setPower(0);
            ports.lsv_l.setPower(0);
        }
    }

    public static void Transfer(LinearOpMode opMode, Ports ports, PIDController lsv_l, PIDController lsv_r, PIDController lsh_l, PIDController lsh_r) {
        ElapsedTime elapsedTime = new ElapsedTime();
        ports.intakePitch.setPosition(0.4);
        ports.outtakeClaw.setPosition(0);
        lsv_l.setup(-lsv_l.getSlide().getCurrentPosition());
        lsv_r.setup(-lsv_l.getSlide().getCurrentPosition());
        while(lsv_l.getSlide().getCurrentPosition() > 10) {
            lsv_l.getSlide().setPower(lsv_l.evaluate(-lsv_l.getSlide().getCurrentPosition())); // error: between 0 and current pos
            lsv_r.getSlide().setPower(lsv_r.evaluate(-lsv_l.getSlide().getCurrentPosition()));

            ports.fr.setPower(0);
            ports.br.setPower(0);
            ports.fl.setPower(0);
            ports.bl.setPower(0);
        }


        lsh_l.setup(60-lsh_l.getSlide().getCurrentPosition());
        lsh_r.setup(60-lsh_r.getSlide().getCurrentPosition());

        while(Math.abs(lsh_l.getSlide().getCurrentPosition()-60) > 10 || Math.abs(lsh_r.getSlide().getCurrentPosition()-60) > 10) {
            lsh_l.getSlide().setPower(lsh_l.evaluate(60-lsh_l.getSlide().getCurrentPosition()));
            lsh_r.getSlide().setPower(lsh_r.evaluate(60-lsh_r.getSlide().getCurrentPosition()));
        }

        ports.outtakeClaw.setPosition(1);

        elapsedTime.reset();
        while (elapsedTime.seconds() < 0.3) {}
        ports.intakeClaw.setPosition(0.1);


        lsh_l.setup(200-lsh_l.getSlide().getCurrentPosition());
        lsh_r.setup(200-lsh_r.getSlide().getCurrentPosition());
        while(Math.abs(lsh_l.getSlide().getCurrentPosition()-200) < 10 || Math.abs(lsh_r.getSlide().getCurrentPosition()-200) < 10) {
            lsh_l.getSlide().setPower(lsh_l.evaluate(200-lsh_l.getSlide().getCurrentPosition()));
            lsh_r.getSlide().setPower(lsh_r.evaluate(200-lsh_r.getSlide().getCurrentPosition()));
        }
    }
}