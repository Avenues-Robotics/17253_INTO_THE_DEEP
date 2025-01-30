package org.firstinspires.ftc.teamcode.classes;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.hardware.Ports;

import java.util.concurrent.TimeUnit;

public class MecanumLocalizer {

    ElapsedTime elapsedTime;

    int currHoriz;
    int prevHoriz;
    double diffHoriz;
    int currVerti;
    int prevVerti;
    double diffVerti;

    Ports ports;

    public MecanumLocalizer(Ports ports) {
        this.ports = ports;
        elapsedTime = new ElapsedTime();
    }

    public Pose3D loop() {
        if (elapsedTime.seconds() < 0.25) {

            prevHoriz = currHoriz;
            prevVerti = currVerti;

            currHoriz = ports.fl.getCurrentPosition();
            currVerti = ports.fr.getCurrentPosition();

            diffHoriz = (currHoriz - prevHoriz)*elapsedTime.seconds();
            diffVerti = (currVerti - prevVerti)*elapsedTime.seconds();

            Pose3D out = new Pose3D(new Position(DistanceUnit.INCH,
                    diffVerti, diffHoriz,
                    0, 0), new YawPitchRollAngles(AngleUnit.DEGREES, 0, 0, 0, 0));

            elapsedTime.reset();

            return out;

        } else {
            Pose3D out = new Pose3D(new Position(DistanceUnit.INCH, 0, 0, 0, elapsedTime.time(TimeUnit.SECONDS)), new YawPitchRollAngles(AngleUnit.DEGREES, 0,0,0, elapsedTime.time(TimeUnit.SECONDS)));
            elapsedTime.reset();
            return out;
        }
    }
}
