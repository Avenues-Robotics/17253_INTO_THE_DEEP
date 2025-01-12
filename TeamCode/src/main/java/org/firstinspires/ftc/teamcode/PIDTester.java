package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.classes.PIDController;

//Create an opmode class
@Config
@Autonomous(name="PID Tester")
public class PIDTester extends LinearOpMode {

    public static double Kp = 0;
    public static double Ki = 0;
    public static double Kd = 0;
    public static double Kf = 0;

    public PIDController pidController;
    public PIDController pidController2;
    public static int target;

    //Create the opmode function
    @Override
    public void runOpMode(){
        //initialize
        DcMotor slide = hardwareMap.get(DcMotor.class, "lsv_l");
        slide.setDirection(DcMotor.Direction.REVERSE);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        DcMotor slide2 = hardwareMap.get(DcMotor.class, "lsv_r");
        slide.setDirection(DcMotor.Direction.REVERSE);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        pidController = new PIDController(Kp, Ki, Kd, Kf, 0, slide);
        pidController2 = new PIDController(Kp, Ki, Kd, Kf, 0, slide2);

        Telemetry dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();

        dashboardTelemetry.addData("Position", slide.getCurrentPosition());
        dashboardTelemetry.update();

        //wait for the game to start
        waitForStart();
//        slide.setTargetPosition(100);
//
//        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        slide.setPower(0.2);

//        while(slide.isBusy()){
//            telemetry.addData("position", slide.getCurrentPosition());
//            dashboardTelemetry.addData("Power", slide.getPower());
//            telemetry.update();
//            dashboardTelemetry.update();
//        }
//        slide.setPower(0)
//        slide.setPower(.5);
//        static do
//        while(opModeIsActive()){
//            slide.setPower(1);
//        }


        pidController.setup(target-slide.getCurrentPosition());
        pidController2.setup(target-slide.getCurrentPosition());

        ElapsedTime elapsedTime = new ElapsedTime();

        while(opModeIsActive()){
            slide.setPower(pidController.evaluate(target-slide.getCurrentPosition()));
            slide2.setPower(pidController2.evaluate(target-slide2.getCurrentPosition()));
            dashboardTelemetry.addData("Position", slide.getCurrentPosition());
            dashboardTelemetry.addData("Position2", slide2.getCurrentPosition());
            dashboardTelemetry.update();
            if(slide.getCurrentPosition() == 1030){
                dashboardTelemetry.addData("Wavelength", elapsedTime);
                dashboardTelemetry.update();
                elapsedTime.reset();
            }
        }

    }
}