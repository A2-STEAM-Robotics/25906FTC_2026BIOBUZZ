package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

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
 *   6. Optional: to run the intake, launcher or other mechanisms during or between paths,
 *      add code in the ROBOT ACTIONS section below.
 *
 * Field coordinates are in inches (0 to 144) and headings are in degrees, the same as in
 * the Visualizer.
 */
@Autonomous(name = "ShootFlowerPark", group = "Pedro")
public class ShootFlowerPark extends LinearOpMode {

    // ===================== PASTE START =====================
    // Everything between here and PASTE END is replaced by the Visualizer's
    // "Coordinates only" export. This example drives forward 28 inches, turning to
    // face left, then curves back to the start.
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(59.9504, 6.6492, 270);
    private final Pose point1 = poseFactory.of(19.0836, 46.8903, 180);
    private final Pose point1Control1 = poseFactory.of(27.2842, 13.4591, 0);
    private final Pose point2Start = poseFactory.of(19.0836, 46.8903, 180);
    private final Pose point2 = poseFactory.of(11.5668, 46.7347, 180);
    private final Pose point3Start = poseFactory.of(11.5668, 46.7347, 180);
    private final Pose point3 = poseFactory.of(19.4724, 46.8116, 180);
    private final Pose point4Start = poseFactory.of(19.4724, 46.8116, 180);
    private final Pose point4 = poseFactory.of(61.0632, 131.6075, 90);
    private final Pose point4Control1 = poseFactory.of(0, 106.9129, 0);
    private final Pose point4Control2 = poseFactory.of(61.6044, 105.9503, 0);
    private final Pose point5Start = poseFactory.of(61.0632, 131.6075, 90);
    private final Pose point5 = poseFactory.of(46.3559, 120.4901, 90);
    private final Pose point6Start = poseFactory.of(46.3559, 120.4901, 90);
    private final Pose point6 = poseFactory.of(46.3428, 129.5898, 90);
    private final Pose point7Start = poseFactory.of(46.3428, 129.5898, 90);
    private final Pose point7 = poseFactory.of(46.3637, 119.8282, 90);
    private final Pose point8Start = poseFactory.of(46.3637, 119.8282, 90);
    private final Pose point8 = poseFactory.of(61.1361, 131.5985, 90);
    private final Pose point9Start = poseFactory.of(61.1361, 131.5985, 90);
    private final Pose point9 = poseFactory.of(11.7749, 119.9646, 270);

    public Path path1() {
        return Paths.curve(start, point1Control1, point1).linear(start, point1);
    }

    public Path path2() {
        return Paths.line(point2Start, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.line(point3Start, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return Paths.curve(point4Start, point4Control1, point4Control2, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return Paths.line(point5Start, point5).linear(point5Start, point5);
    }

    public Path path6() {
        return Paths.line(point6Start, point6).linear(point6Start, point6);
    }

    public Path path7() {
        return Paths.line(point7Start, point7).linear(point7Start, point7);
    }

    public Path path8() {
        return Paths.line(point8Start, point8).linear(point8Start, point8);
    }

    public Path path9() {
        return Paths.line(point9Start, point9).linear(point9Start, point9);
    }
    // ====================== PASTE END ======================

    // The paths to drive, in order. Use the method names from your pasted code,
    // with () after each one. Every "public Path" method above needs a line here,
    // or the robot will skip it.
    private Path[] pathsInOrder() {
        return new Path[] {
                path1(),
                path2(),
                path3(),
                path4(),
                path5(),
                path6(),
                path7(),
                path8(),
                path9()

        };
    }

    // Where the robot starts. The Visualizer names
    // this pose "start" unless you renamed
    // the start point. If you did, use that name here.
    private Pose startingPose() {
        return start;
    }

    // ================= ROBOT ACTIONS =================
    // Put your intake, launcher and other mechanism code in these methods.
    // Paths are numbered 1, 2, 3... in the order listed in pathsInOrder().

    // Your motors and servos. Example:
    // private DcMotor intake;
    // private DcMotorEx launcher;

    // Runs once during Init. Set up your motors and servos here.
    private DcMotorEx launcher;
    private DcMotor indexer1;
    private DcMotor indexer2;
    public final int LAUNCHER_TARGET_VELOCITY = 1250; //2678 RPM
    public final int LAUNCHER_MIN_VELOCITY = 1215;


    // Runs once during Init. Set up your motors and servos here.
    private void setUpMechanisms() {
        // Example:
        // intake = hardwareMap.get(DcMotor.class, "intake");
        launcher=hardwareMap.get(DcMotorEx.class, "launcher");
        indexer1=hardwareMap.get(DcMotor.class, "windmillmotor");
        indexer2=hardwareMap.get(DcMotor.class, "windmillmotor2");
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));

    }

    // Runs once, right as a path starts. Good for turning things on.
    private void whenPathStarts(int pathNumber) {

        // Example: run the intake while path 2 drives.
        // if (pathNumber == 2) {
        //     intake.setPower(1);
        // }
    }

    private void shoot(double length){
        launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
        while(opModeIsActive() && launcher.getVelocity()<LAUNCHER_MIN_VELOCITY){
            idle();
        }
        ElapsedTime timer = new ElapsedTime();
        while(opModeIsActive() && timer.seconds() < length) {
           if(launcher.getVelocity()<LAUNCHER_MIN_VELOCITY){
               indexer1.setPower(0);
               indexer2.setPower(0);
           }else{indexer1.setPower(-1);
               indexer2.setPower(-1);

           }
        }

        indexer1.setPower(0);
        indexer2.setPower(0);
        launcher.setVelocity(0);
    }

    // Runs over and over while a path is driving. Keep it quick: no sleep() here.
    private void whilePathRuns(int pathNumber) {
    }

    // Runs once, after a path finishes and before the next one starts.
    // The robot holds its spot while this runs. To wait, use holdFor(seconds), not sleep().
    private void whenPathEnds(int pathNumber) {
        // Example: stop the intake after path 2, then shoot for 2 seconds after path 3.
        // if (pathNumber == 2) {
        //     intake.setPower(0);
        // }
        // if (pathNumber == 3) {
        //     launcher.setVelocity(1250);
        //     holdFor(2.0);
        //     launcher.setVelocity(0);
        // }
    }
    // =============== END ROBOT ACTIONS ===============

    private Follower follower;

    // Waits for a number of seconds while the robot keeps holding its position.
    private void holdFor(double seconds) {
        ElapsedTime timer = new ElapsedTime();
        while (opModeIsActive() && timer.seconds() < seconds) {
            follower.update();
        }
    }

    @Override
    public void runOpMode() {
        follower = Constants.create(hardwareMap);
        if (follower == null) {
            telemetry.addLine("Pedro isn't tuned yet: Constants.create() returns null.");
            telemetry.addLine("Finish the Foresight tuner and fill in Constants first.");
            telemetry.update();
            waitForStart();
            return;
        }

        setUpMechanisms();

        Path[] paths = pathsInOrder();

        follower.setPose(startingPose());
        follower.update();

        telemetry.addData("Paths to drive", paths.length);
        telemetry.addData("Start pose", startingPose());
        telemetry.addLine("Place the robot at the start pose, then press Play.");
        telemetry.update();

        waitForStart();
        shoot(4.0);
        // Drive each path in order, running the robot actions along the way.
        for (int i = 0; i < paths.length && opModeIsActive(); i++) {
            int pathNumber = i + 1;
            follower.follow(paths[i]);
            whenPathStarts(pathNumber);

            while (opModeIsActive() && follower.following()) {
                follower.update();
                whilePathRuns(pathNumber);
                showStatus("Path " + pathNumber + " of " + paths.length);
            }

            whenPathEnds(pathNumber);
        }

        // All paths are done. Keep holding the final position until Stop.
        while (opModeIsActive()) {
            follower.update();
            showStatus("Done");
        }
    }

    private void showStatus(String status) {
        telemetry.addData("Path", status);
        telemetry.addData("x", "%.1f", follower.pose().x());
        telemetry.addData("y", "%.1f", follower.pose().y());
        telemetry.addData("heading (deg)", "%.1f", Math.toDegrees(follower.pose().heading()));
        telemetry.update();
    }
}
