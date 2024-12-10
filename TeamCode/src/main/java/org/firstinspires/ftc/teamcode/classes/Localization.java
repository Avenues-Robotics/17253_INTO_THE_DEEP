package org.firstinspires.ftc.teamcode.classes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.hardware.Ports;

public class Localization {

    Pose3D robotPosition;
    Ports ports;
    AprilTagLocalizer aprilTagLocalizer;
    MecanumLocalizer mecanumLocalizer;

    Pose3D mecanumVelocityEstimate;
    Pose3D mecanumPositionEstimate;
    Pose3D aprilTagEstimate;

    Position aprilTagPos;
    YawPitchRollAngles aprilTagRot;

    double radianYaw;

    public Localization(LinearOpMode opMode, Ports ports, Pose3D startingPosition) {
        robotPosition = startingPosition;
        this.ports = ports;
        aprilTagLocalizer = new AprilTagLocalizer(opMode);
        mecanumLocalizer = new MecanumLocalizer(ports);
    }

    public Pose3D getRobotPosition(){
        return robotPosition;
    }

    public void loop() {
//        aprilTagEstimate = aprilTagLocalizer.run();
//        aprilTagPos = aprilTagEstimate.getPosition();
//        aprilTagRot = aprilTagEstimate.getOrientation();

        mecanumVelocityEstimate = mecanumLocalizer.loop();

//        if (!(aprilTagPos.x == 0 && aprilTagPos.y == 0 && aprilTagPos.z == 0 && aprilTagRot.getRoll() == 0)){
//            robotPosition = new Pose3D(new Position(DistanceUnit.INCH,
//                    aprilTagPos.x,
//                    aprilTagPos.y,
//                    0, aprilTagPos.acquisitionTime), new YawPitchRollAngles(AngleUnit.DEGREES,
//                    aprilTagRot.getYaw(),
//                    0, 0, aprilTagRot.getAcquisitionTime()));
//            mecanumPositionEstimate = robotPosition;
//        } else {
            mecanumPositionEstimate = new Pose3D(robotPosition.getPosition(), new YawPitchRollAngles(AngleUnit.DEGREES, robotPosition.getOrientation().getYaw() + mecanumVelocityEstimate.getOrientation().getYaw()*mecanumVelocityEstimate.getOrientation().getAcquisitionTime(), 0, 0, 0));
            radianYaw = Math.toRadians(-mecanumPositionEstimate.getOrientation().getYaw());
            mecanumPositionEstimate = new Pose3D(new Position(DistanceUnit.INCH,
                    robotPosition.getPosition().x+(mecanumVelocityEstimate.getPosition().x*Math.cos(radianYaw)-mecanumVelocityEstimate.getPosition().y*Math.sin(radianYaw))*mecanumVelocityEstimate.getOrientation().getAcquisitionTime(),
                    robotPosition.getPosition().y+(mecanumVelocityEstimate.getPosition().x*Math.sin(radianYaw)+mecanumVelocityEstimate.getPosition().y*Math.cos(radianYaw))*mecanumVelocityEstimate.getOrientation().getAcquisitionTime(),
                    0, 0), robotPosition.getOrientation());
            robotPosition = mecanumPositionEstimate;
//        }
    }
}
