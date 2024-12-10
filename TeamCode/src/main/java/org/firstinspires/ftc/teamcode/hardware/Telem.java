package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.HashMap;
import java.util.Map;

/* USAGE GUIDE:
 * The purpose of this class is to introduce the capability to include data in the telemetry output
 * for more than just one update. This is useful when you would like to, for example, include a
 * status (such as "sample in hand") while the robot is moving (when it will likely be constantly
 * updating telemetry).
 *
 * To add something to the permanent telemetry list, call telem.add(a, b), imagine it is exactly
 * like telemetry.addData() but with a different name. So a is the value and it will be printed
 * followed by ": " and then b. Unlike in telemetry.addData() both a and b must be strings. If
 * something is already on the list with that name (a), it will be replaced.
 *
 * To remove something from the permanent telemetry list, call telem.remove(a), where a is the name
 * of the thing you would like to remove. a must be a string.
 *
 * WHENEVER you wish to update the telemetry, instead of using telemetry.update(), use
 * telem.update(). Otherwise it will be updated without the permanent list. IMPORTANT: IF YOU WISH
 * TO INCLUDE DATA THAT WILL CHANGE EVERY UPDATE, SIMPLY USE telemetry.addData(). OTHERWISE YOU
 * WILL BE MAKING THE ROBOT RUN MUCH MORE SLOWELY, REDUCING OUR POSITION ACCURACY AND GIVING THE
 * OPPONENT A LEG UP.
 */

public class Telem {

    // Creates a map, made out of a list of pairs of string, the first string is the key and the
    // second is the value. This is searchable by the value. It will be printed as telem data.
    public static Map<String, String> permanentTelems = new HashMap<String, String>();

    // Call as telem.add("", "")
    public static void add(String name, String value) {
        // Adds new data to the map, if the map already has something with the same key, it will
        // simply replace the value with the new value.
        permanentTelems.put(name, value);
    }

    // Call as telem.remove("", "")
    public static void remove(String name) {
        // Removes any data only with the key
        permanentTelems.remove(name);
    }

    // Call as telem.update("", "") and include any temp before calling
    public static void update(LinearOpMode opMode) {
        // Goes through every pair in the list and adds them to the telemetry output before
        // updating.
        for (Map.Entry<String, String> me: permanentTelems.entrySet()) {
            opMode.telemetry.addData(me.getKey(), me.getValue());
        }
        opMode.telemetry.update();
    }
}
