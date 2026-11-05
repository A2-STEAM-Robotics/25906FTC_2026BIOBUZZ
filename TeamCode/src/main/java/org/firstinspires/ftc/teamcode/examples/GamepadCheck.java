package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

/*
 * Shows what the robot sees from gamepad 1 and gamepad 2. This OpMode does not move any motors.
 *
 * With nobody touching a gamepad, every stick and trigger should read 0.00 and
 * "At rest" should say true. If a stick shows a number while nobody is touching it,
 * that gamepad will make the robot drive on its own. Re-plug it, or use a different gamepad.
 */
@TeleOp(name = "Gamepad Check", group = "Examples")
public class GamepadCheck extends LinearOpMode {

    @Override
    public void runOpMode() {
        telemetry.addLine("Press Play, then let go of the gamepads.");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            show("Gamepad 1", gamepad1);
            show("Gamepad 2", gamepad2);
            telemetry.update();
        }
    }

    private void show(String label, Gamepad gamepad) {
        telemetry.addLine("--- " + label + " ---");
        telemetry.addData("Type", gamepad.type);
        telemetry.addData("At rest", gamepad.atRest());
        telemetry.addData("Left stick  x / y", "%.2f / %.2f", gamepad.left_stick_x, gamepad.left_stick_y);
        telemetry.addData("Right stick x / y", "%.2f / %.2f", gamepad.right_stick_x, gamepad.right_stick_y);
        telemetry.addData("Triggers L / R", "%.2f / %.2f", gamepad.left_trigger, gamepad.right_trigger);
    }
}
