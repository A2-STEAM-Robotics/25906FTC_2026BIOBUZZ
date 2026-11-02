package org.firstinspires.ftc.teamcode.opmodes;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

/*
 * Drives a path built in the Pedro Pathing Visualizer (https://visualizer.pedropathing.com).
 *
 * HOW TO USE
 *   1. Build your path in the Visualizer. Set the start point to where the robot will sit.
 *   2. Click Export Code and choose "Include: Coordinates only".
 *   3. Copy the code and paste it between the PASTE START / PASTE END lines below,
 *      replacing the example that is there now.
 *   4. In pathsInOrder(), list your path methods in the order the robot should drive them.
 *   5. Put the robot on the field at the start point, facing the start heading, and run
 *      "Visualizer Auto" from the Driver Station.
 *
 * Field coordinates are in inches (0 to 144) and headings are in degrees, the same as in
 * the Visualizer.
 */
@Autonomous(name = "Visualizer Auto", group = "Pedro")
public class VisualizerAuto extends LinearOpMode {

    // ===================== PASTE START =====================
    // Everything between here and PASTE END is replaced by the Visualizer's
    // "Coordinates only" export. This example drives forward 28 inches, turning to
    // face left, then curves back to the start.

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 90);
    private final Pose path1 = poseFactory.of(56, 36, 180);
    private final Pose path2 = poseFactory.of(56, 8, 90);
    private final Pose path2Control1 = poseFactory.of(80, 22, 0);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.curve(path1, path2Control1, path2).linear(path1, path2);
    }
    // ====================== PASTE END ======================

    // The paths to drive, in order. Use the method names from your pasted code,
    // with () after each one.
    private Path[] pathsInOrder() {
        return new Path[] {
                path1(),
                path2(),
        };
    }

    // Where the robot starts. The Visualizer names this pose "start" unless you renamed
    // the start point. If you did, use that name here.
    private Pose startingPose() {
        return start;
    }

    @Override
    public void runOpMode() {
        Follower follower = Constants.create(hardwareMap);
        if (follower == null) {
            telemetry.addLine("Pedro isn't tuned yet: Constants.create() returns null.");
            telemetry.addLine("Finish the Foresight tuner and fill in Constants first.");
            telemetry.update();
            waitForStart();
            return;
        }

        Path[] paths = pathsInOrder();
        int nextPath = 0;

        follower.setPose(startingPose());
        follower.update();

        telemetry.addData("Paths to drive", paths.length);
        telemetry.addData("Start pose", startingPose());
        telemetry.addLine("Place the robot at the start pose, then press Play.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            follower.update();

            // When the robot isn't following a path, start the next one.
            // After the last path, the robot holds its final position.
            if (!follower.following() && nextPath < paths.length) {
                follower.follow(paths[nextPath]);
                nextPath++;
            }

            boolean done = nextPath >= paths.length && !follower.following();
            telemetry.addData("Path", done ? "Done" : nextPath + " of " + paths.length);
            telemetry.addData("x", "%.1f", follower.pose().x());
            telemetry.addData("y", "%.1f", follower.pose().y());
            telemetry.addData("heading (deg)", "%.1f", Math.toDegrees(follower.pose().heading()));
            telemetry.update();
        }
    }
}
