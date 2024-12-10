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

    int currfr = 0;
    int prevfr = 0;
    int difffr = 0;
    int currfl = 0;
    int prevfl = 0;
    int difffl = 0;
    int currbr = 0;
    int prevbr = 0;
    int diffbr = 0;
    int currbl = 0;
    int prevbl = 0;
    int diffbl = 0;

    Ports ports;

    public MecanumLocalizer(Ports ports) {
        this.ports = ports;
        elapsedTime = new ElapsedTime();
    }

    public Pose3D loop() {
        if (elapsedTime.seconds() < 0.25) {

            prevfr = currfr;
            prevfl = currfl;
            prevbr = currbr;
            prevbl = currbl;

            currfr = ports.fr.getCurrentPosition();
            currfl = ports.fl.getCurrentPosition();
            currbr = ports.br.getCurrentPosition();
            currbl = ports.bl.getCurrentPosition();

            difffr = currfr - prevfr;
            difffl = currfl - prevfl;
            diffbr = currbr - prevbr;
            diffbl = currbl - prevbl;

            Pose3D out = new Pose3D(new Position(DistanceUnit.INCH,
                    (double) (diffbr + diffbl)/2,
                    (double) (diffbr - difffr)/2,
                    0, elapsedTime.time(TimeUnit.SECONDS)), new YawPitchRollAngles(AngleUnit.DEGREES,
                    (double) (diffbl - difffr)/2,
                    0, 0, elapsedTime.time(TimeUnit.SECONDS)));

            elapsedTime.reset();

            return out;

        } else {
            Pose3D out = new Pose3D(new Position(DistanceUnit.INCH, 0, 0, 0, elapsedTime.time(TimeUnit.SECONDS)), new YawPitchRollAngles(AngleUnit.DEGREES, 0,0,0, elapsedTime.time(TimeUnit.SECONDS)));
            elapsedTime.reset();
            return out;
        }
    }
}
