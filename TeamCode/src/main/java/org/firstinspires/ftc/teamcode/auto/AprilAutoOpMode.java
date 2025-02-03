package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.classes.AprilTagLocalizer;
import org.firstinspires.ftc.teamcode.hardware.Driver;
import org.firstinspires.ftc.teamcode.hardware.Ports;

@Config
@Autonomous(name = "april tag auto")
public class AprilAutoOpMode extends LinearOpMode {

    Ports ports;
    Ports.Builder portsBuilder;

    AprilTagLocalizer aprilTagLocalizer;

    @Override
    public void runOpMode() {
        portsBuilder = new Ports.Builder();
        portsBuilder.wheelsActive = true;
        ports = new Ports(this, portsBuilder);

        aprilTagLocalizer = new AprilTagLocalizer(this);
        ElapsedTime elapsedTime = new ElapsedTime();

        waitForStart();

        //Driver.drive(this, ports, 1, 100, 155);
        Pose3D target = new Pose3D(new Position(DistanceUnit.INCH,-6, 35, 0, 0), new YawPitchRollAngles(AngleUnit.DEGREES, 0, 0, 0, 0));
        elapsedTime.reset();
        while(elapsedTime.seconds() > 1) {
            Driver.DriveToPosApril(this, ports, target, aprilTagLocalizer.run(), 0);
        }
    }

}
