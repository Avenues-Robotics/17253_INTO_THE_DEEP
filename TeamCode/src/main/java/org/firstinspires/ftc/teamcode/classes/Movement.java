package org.firstinspires.ftc.teamcode.classes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class Movement {

    static double rotations;



    public static void left(double distance, Telemetry telemetry, DcMotor motorBackLeft, DcMotor motorBackRight, DcMotor motorFrontLeft, DcMotor motorFrontRight) {
        reset_encoders(motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
        rotations = distance * 21.74;
        // SET TARGET POSITION
        motorFrontRight.setTargetPosition((int) rotations);
        motorFrontLeft.setTargetPosition((int) -rotations);
        motorBackLeft.setTargetPosition((int) rotations);
        motorBackRight.setTargetPosition((int) -rotations);
        // RUN TO POSITION
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // POWER (left slide)
        ((DcMotorEx) motorFrontRight).setVelocity(1000);
        ((DcMotorEx) motorFrontLeft).setVelocity(-1000);
        ((DcMotorEx) motorBackLeft).setVelocity(1000);
        ((DcMotorEx) motorBackRight).setVelocity(-1000);
        while (motorBackLeft.isBusy() && motorBackRight.isBusy() && motorFrontLeft.isBusy() && motorFrontRight.isBusy()) {
            telemetry.addData("function", "left");
            motor_telemetry(telemetry, motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
            telemetry.update();
        }
    }

    /**
     * Describe this function...
     */
    public static void forward(int distance, Telemetry telemetry, DcMotor motorBackLeft, DcMotor motorBackRight, DcMotor motorFrontLeft, DcMotor motorFrontRight) {
        reset_encoders(motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
        rotations = distance * 21.74;
        // SET TARGET POSITION
        motorFrontRight.setTargetPosition((int) rotations);
        motorFrontLeft.setTargetPosition((int) rotations);
        motorBackLeft.setTargetPosition((int) rotations);
        motorBackRight.setTargetPosition((int) rotations);
        // RUN TO POSITION
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // POWER (left slide)
        ((DcMotorEx) motorFrontRight).setVelocity(1000);
        ((DcMotorEx) motorFrontLeft).setVelocity(1000);
        ((DcMotorEx) motorBackLeft).setVelocity(1000);
        ((DcMotorEx) motorBackRight).setVelocity(1000);
        while (motorBackLeft.isBusy() && motorBackRight.isBusy() && motorFrontLeft.isBusy() && motorFrontRight.isBusy()) {
            telemetry.addData("function", "forward...");
            motor_telemetry(telemetry, motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
            telemetry.update();
        }
    }


    /**
     * Describe this function...
     */
    public static void backward(double distance, Telemetry telemetry, DcMotor motorBackLeft, DcMotor motorBackRight, DcMotor motorFrontLeft, DcMotor motorFrontRight) {
        reset_encoders(motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
        rotations = distance * 21.74;
        // SET TARGET POSITION
        motorFrontRight.setTargetPosition((int) -rotations);
        motorFrontLeft.setTargetPosition((int) -rotations);
        motorBackLeft.setTargetPosition((int) -rotations);
        motorBackRight.setTargetPosition((int) -rotations);
        // RUN TO POSITION
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // POWER (left slide)
        ((DcMotorEx) motorFrontRight).setVelocity(-1000);
        ((DcMotorEx) motorFrontLeft).setVelocity(-1000);
        ((DcMotorEx) motorBackLeft).setVelocity(-1000);
        ((DcMotorEx) motorBackRight).setVelocity(-1000);
        while (motorBackLeft.isBusy() && motorBackRight.isBusy() && motorFrontLeft.isBusy() && motorFrontRight.isBusy()) {
            telemetry.addData("function", "backward");
            motor_telemetry(telemetry, motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
            telemetry.update();
        }
    }

    /**
     * Describe this function...
     */
    public static void right(double distance, Telemetry telemetry, DcMotor motorBackLeft, DcMotor motorBackRight, DcMotor motorFrontLeft, DcMotor motorFrontRight) {
        reset_encoders(motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
        rotations = distance * 21.74;
        // SET TARGET POSITION
        motorFrontRight.setTargetPosition((int) -rotations);
        motorFrontLeft.setTargetPosition((int) rotations);
        motorBackLeft.setTargetPosition((int) -rotations);
        motorBackRight.setTargetPosition((int) rotations);
        // RUN TO POSITION
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // POWER (left slide)
        ((DcMotorEx) motorFrontRight).setVelocity(-1000);
        ((DcMotorEx) motorFrontLeft).setVelocity(1000);
        ((DcMotorEx) motorBackLeft).setVelocity(-1000);
        ((DcMotorEx) motorBackRight).setVelocity(1000);
        while (motorBackLeft.isBusy() && motorBackRight.isBusy() && motorFrontLeft.isBusy() && motorFrontRight.isBusy()) {
            telemetry.addData("function", "right");
            motor_telemetry(telemetry, motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
            telemetry.update();
        }
    }

    /**
     * Describe this function...
     */
    public static void reset_encoders(DcMotor motorBackLeft, DcMotor motorBackRight, DcMotor motorFrontLeft, DcMotor motorFrontRight) {
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public static void reset_linear_encoders(DcMotor motorLinearSlideLeft, DcMotor motorLinearSlideRight) {
        motorLinearSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLinearSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    /**
     * Describe this function...
     */
    public static void motor_telemetry(Telemetry telemetry, DcMotor motorBackLeft, DcMotor motorBackRight, DcMotor motorFrontLeft, DcMotor motorFrontRight) {
        telemetry.addData("back left pos", motorBackLeft.getCurrentPosition());
        telemetry.addData("back right pos", motorBackRight.getCurrentPosition());
        telemetry.addData("front left pos", motorFrontLeft.getCurrentPosition());
        telemetry.addData("front right pos", motorFrontRight.getCurrentPosition());
        telemetry.update();
    }

    //rotation right
    public static void rotationRight(double degrees, Telemetry telemetry, DcMotor motorBackLeft, DcMotor motorBackRight, DcMotor motorFrontLeft, DcMotor motorFrontRight) {
        reset_encoders(motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
        rotations = degrees * 14;
        // SET TARGET POSITION
        motorFrontRight.setTargetPosition((int) -rotations);
        motorFrontLeft.setTargetPosition((int) rotations);
        motorBackLeft.setTargetPosition((int) rotations);
        motorBackRight.setTargetPosition((int) -rotations);
        // RUN TO POSITION
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // POWER (left slide)
        ((DcMotorEx) motorFrontRight).setVelocity(-1000);
        ((DcMotorEx) motorFrontLeft).setVelocity(1000);
        ((DcMotorEx) motorBackLeft).setVelocity(1000);
        ((DcMotorEx) motorBackRight).setVelocity(-1000);
        while (motorBackLeft.isBusy() && motorBackRight.isBusy() && motorFrontLeft.isBusy() && motorFrontRight.isBusy()) {
            telemetry.addData("function", "rotation right");
            motor_telemetry(telemetry, motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
            telemetry.update();
        }
    }

    public static void rotationLeft(double degrees, Telemetry telemetry, DcMotor motorBackLeft, DcMotor motorBackRight, DcMotor motorFrontLeft, DcMotor motorFrontRight) {
        reset_encoders(motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
        rotations = degrees * 14;
        // SET TARGET POSITION
        motorFrontRight.setTargetPosition((int) rotations);
        motorFrontLeft.setTargetPosition((int) -rotations);
        motorBackLeft.setTargetPosition((int) -rotations);
        motorBackRight.setTargetPosition((int) rotations);
        // RUN TO POSITION
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // POWER (left slide)
        ((DcMotorEx) motorFrontRight).setVelocity(1000);
        ((DcMotorEx) motorFrontLeft).setVelocity(-1000);
        ((DcMotorEx) motorBackLeft).setVelocity(-1000);
        ((DcMotorEx) motorBackRight).setVelocity(1000);
        while (motorBackLeft.isBusy() && motorBackRight.isBusy() && motorFrontLeft.isBusy() && motorFrontRight.isBusy()) {
            telemetry.addData("function", "rotation right");
            motor_telemetry(telemetry, motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
            telemetry.update();
        }
    }

    public static void linearSlidesH(int ticks, Telemetry telemetry, DcMotor motorLinearSlideLeftH, DcMotor motorLinearSlideRightH){
        //reset_linear_encoders(motorLinearSlideLeft, motorLinearSlideRight);
        //SET TARGET POSITION
        motorLinearSlideLeftH.setTargetPosition((int)ticks);
        motorLinearSlideRightH.setTargetPosition((int)ticks);
        //RUN TO POSITION
        motorLinearSlideLeftH.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLinearSlideRightH.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //POWER
        ((DcMotorEx) motorLinearSlideLeftH).setPower(1);
        ((DcMotorEx) motorLinearSlideRightH).setPower(-1);
    }

    public static void linearSlidesV(int ticks, Telemetry telemetry, DcMotor motorLinearSlideLeftV, DcMotor motorLinearSlideRightV){
        //reset_linear_encoders(motorLinearSlideLeft, motorLinearSlideRight);
        //SET TARGET POSITION
        motorLinearSlideLeftV.setTargetPosition((int)ticks);
        motorLinearSlideRightV.setTargetPosition((int)ticks);
        //RUN TO POSITION
        motorLinearSlideLeftV.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLinearSlideRightV.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //POWER
        ((DcMotorEx) motorLinearSlideLeftV).setPower(1);
        ((DcMotorEx) motorLinearSlideRightV).setPower(-1);
    }

    public static void hang( Telemetry telemetry, DcMotor motorLinearSlideLeft, DcMotor motorLinearSlideRight){
        //reset_linear_encoders(motorLinearSlideLeft, motorLinearSlideRight);
        //SET TARGET POSITION
//        motorLinearSlideLeft.setTargetPosition(0);
//        motorLinearSlideRight.setTargetPosition(0);
        //RUN TO POSITION
//        motorLinearSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorLinearSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorLinearSlideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLinearSlideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //POWER
        ((DcMotorEx) motorLinearSlideLeft).setPower(-1);
        ((DcMotorEx) motorLinearSlideRight).setPower(-1);
    }

    public static void distance(Telemetry telemetry, DistanceSensor dsensor){
        double value = dsensor.getDistance(DistanceUnit.CM);
        telemetry.addData( "Distance:", value);
        telemetry.update();
    }

}