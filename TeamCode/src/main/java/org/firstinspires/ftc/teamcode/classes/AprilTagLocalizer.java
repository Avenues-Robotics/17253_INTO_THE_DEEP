package org.firstinspires.ftc.teamcode.classes;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AprilTagLocalizer {
    AprilTagProcessor aprilTagProcessor;
    VisionPortal myVisionPortal;
    Telemetry dashboardTelemetry;
    List<AprilTagDetection> aprilTagDetections;

    CircularStack<Pose3D> aprilLocusStack;
    ElapsedTime timeLastDetection;

    Pose3D out;

    double xPos = 0; double yPos = 0; double zPos = 0; double pitch = 0; double yaw = 0; double roll = 0;

    public AprilTagLocalizer (LinearOpMode opMode) {
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        myVisionPortal = new VisionPortal.Builder()
                .setCamera(opMode.hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTagProcessor)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .setAutoStopLiveView(true)
                .build();
        dashboardTelemetry = FtcDashboard.getInstance().getTelemetry();

        aprilLocusStack = new CircularStack<>(5);
        timeLastDetection = new ElapsedTime();

        FtcDashboard.getInstance().startCameraStream(myVisionPortal, 30);
    }

    public Pose3D run(){
        aprilTagDetections = aprilTagProcessor.getDetections();

        for (AprilTagDetection aprilTagDetection : aprilTagDetections) {
            aprilLocusStack.push(aprilTagDetection.robotPose);
            timeLastDetection.reset();
        }
        if (timeLastDetection.seconds() >= 0.25) {
            aprilLocusStack.clear();
        }
        if (!aprilLocusStack.isEmpty()) {
            List<Pose3D> sumList = aprilLocusStack.grab();
            xPos = 0; yPos = 0; zPos = 0; pitch = 0; yaw = 0; roll = 0;
            for (int i = 0; i < sumList.size(); i++) {
                xPos += sumList.get(i).getPosition().x;
                yPos += sumList.get(i).getPosition().y;
                zPos += sumList.get(i).getPosition().z;
                pitch += sumList.get(i).getOrientation().getPitch();
                yaw += sumList.get(i).getOrientation().getYaw();
                roll += sumList.get(i).getOrientation().getRoll();
            }
            xPos /= sumList.size(); yPos /= sumList.size(); zPos /= sumList.size(); pitch /= sumList.size(); yaw /= sumList.size(); roll /= sumList.size();
        }
        out = new Pose3D(new Position(DistanceUnit.INCH, xPos, yPos, zPos, timeLastDetection.time(TimeUnit.SECONDS)), new YawPitchRollAngles(AngleUnit.DEGREES, yaw, pitch, roll, timeLastDetection.time(TimeUnit.SECONDS)));
        dashboardTelemetry.addData("April Tag Estimate", out);
        return out;
    }
}
