# Building Auton Routines with Pedro Pathing

Updated Nov 6, 2026

You can draw a path on a picture of the field, copy it into our code, and watch the robot drive it by itself. This guide shows you how, one step at a time.

## Before you start

You need a laptop with Android Studio and our team code, plus the robot with a charged battery.

- [ ] Your laptop has the latest team code. In GitHub Desktop, click **Fetch origin**, then **Pull**.
- [ ] Android Studio has the `25906FTC_2026BIOBUZZ` folder open.
- [ ] The robot is on, and you have a clear space on the field to test.

**Words to know**

| Word | What it means |
| --- | --- |
| Path | One move the robot makes, from one point to the next. A full auton is several paths in a row. |
| Pose | Where the robot is (x and y, in inches) and which way it faces (heading, in degrees). |
| Heading | The direction the robot faces. 0° faces the +x direction and 90° faces the +y direction. |

## Part 1: Draw your path

1. Open the [Pedro Pathing Visualizer](https://visualizer.pedropathing.com/).
2. Drag the **start point** to where the robot will sit at the start of the match.
3. Set the start **heading** to the way the robot will face.
4. Add a path to your first stop. Start simple: one or two short straight lines.
5. For each path, choose how the robot turns while it drives:
   - **Tangent:** the robot faces the way it is driving.
   - **Linear:** the robot turns smoothly from one heading to another.
   - **Constant:** the robot keeps facing the same way the whole time.
6. Add more paths until your auton is done.
7. Press the **play** button in the Visualizer to watch a pretend robot drive your path. Make sure it doesn't run off the field or into anything.
8. Write down the **start point's x, y and heading**. You need them to set up the robot later.

**Tip:** Draw your first auton on an empty part of the field. Add more paths once that works.

## Part 2: Put the path in the code

You copy the path out of the Visualizer and paste it into our auton program, `VisualizerAuto.java`.

1. In the Visualizer, click **Export Code**.
2. Set **Include** to **Coordinates only**. Do not pick "Full code": it needs a library our robot doesn't have.
3. Click copy to copy the code.
4. In Android Studio, open `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes/VisualizerAuto.java`.
5. Find the two lines that say `PASTE START` and `PASTE END`.
6. Select everything **between** those two lines, and paste your code over it. Leave the two lines themselves alone.
7. Look at your pasted code. Find every line that starts with `public Path`. Each one is a path, and its name is the word before `()`, like `path1`.
8. Scroll down to `pathsInOrder()`. List every path there, in the order the robot should drive them. Each one ends with `(),` like this:

```java
return new Path[] {
        path1(),
        path2(),
        path3(),
};
```

9. Count them. If you drew 3 paths, there must be 3 lines here. **A path you forget to list is skipped**, and the robot just stops.
10. Did you rename the start point in the Visualizer? Then find `return start;` in `startingPose()` and change `start` to the new name. If you didn't rename it, skip this step.
11. Look for red underlines in Android Studio. If you see any, check that you pasted between the right lines and that every path in the list has `(),` after it.

## Part 3: Run it on the robot

The robot drives itself in this part, so keep everyone clear of the field and choose one person to press **Stop** if anything goes wrong.

1. Put the code on the robot. Connect to the robot (USB-C or the robot's Wi-Fi), pick the Control Hub in Android Studio, and press **Run ▶**.
2. Place the robot **exactly** on the start point you wrote down, facing the start heading. The robot believes it starts where the code says, so if it starts 6 inches off, it ends up 6 inches off.
3. On the Driver Station, open the **Autonomous** list and choose **Visualizer Auto**.
4. Press **Init**. Check the screen: **Paths to drive** should match how many paths you drew.
5. Press **Play** and watch. The screen shows which path is running and where the robot thinks it is (x, y and heading).
6. When it says **Path: Done**, the robot holds its last spot. Press **Stop**.
7. Did it go where you wanted? If not, change your path in the Visualizer and do Part 2 again.

## Part 4: Save your work

Once your auton works, save it on its own branch so you don't change anyone else's code. These steps use GitHub Desktop, and you need internet, so switch back from the robot's Wi-Fi first.

1. Click **Current Branch**, then **New Branch**. Give it a name that says what it does, like `auto-left-side`.
2. In the list of changed files, check that `VisualizerAuto.java` is there.
3. At the bottom left, type a short message that says what you did, like `Add left-side auto path`. Then click **Commit**.
4. Click **Publish branch**.
5. Click **Create Pull Request**. A mentor will look it over before it goes into the main code.

**Want more than one auton?** Ask a mentor to help you copy `VisualizerAuto.java` into a new file with its own name, like `LeftSideAuto`. That way, each auton shows up on the Driver Station by itself.

## Part 5: Make the robot do things along the way

A real auton does more than drive. It might run the intake while it drives to the game pieces, then stop and shoot before it moves on. You add that code in the **ROBOT ACTIONS** section of `VisualizerAuto.java`, just below `pathsInOrder()`.

Your paths are numbered **1, 2, 3...** in the order you listed them in `pathsInOrder()`. Each spot below gets the path number, so you can choose which path your code goes with.

| Spot in the code | When it runs | Good for |
| --- | --- | --- |
| `setUpMechanisms()` | Once, when you press **Init** | Finding your motors and servos with `hardwareMap` |
| `whenPathStarts(pathNumber)` | Once, right as a path starts | Turning something on, like the intake |
| `whilePathRuns(pathNumber)` | Over and over while a path drives | Quick checks, like stopping the intake when a sensor sees a game piece |
| `whenPathEnds(pathNumber)` | Once, after a path ends and before the next one starts | Shooting, dropping, or anything the robot does while it stands still |

**Example:** run the intake during path 2, then shoot for 2 seconds after path 3.

1. Under **Your motors and servos**, add a line for each mechanism. Remove the `//` at the start of the line.
2. In `setUpMechanisms()`, connect each one to its name from the robot's configuration on the Driver Station. The name in quotes must match exactly.
3. In `whenPathStarts`, turn the intake on when path 2 starts.
4. In `whenPathEnds`, turn the intake off after path 2. After path 3, spin up the launcher, wait 2 seconds, then turn it off.

When you're done, it looks like this:

```java
private DcMotor intake;
private DcMotorEx launcher;

private void setUpMechanisms() {
    intake = hardwareMap.get(DcMotor.class, "intake");
    launcher = hardwareMap.get(DcMotorEx.class, "launcher");
}

private void whenPathStarts(int pathNumber) {
    if (pathNumber == 2) {
        intake.setPower(1);
    }
}

private void whilePathRuns(int pathNumber) {
}

private void whenPathEnds(int pathNumber) {
    if (pathNumber == 2) {
        intake.setPower(0);
    }
    if (pathNumber == 3) {
        launcher.setVelocity(1250);
        holdFor(2.0);
        launcher.setVelocity(0);
    }
}
```

**Two rules:**

- **To wait, use `holdFor(seconds)`, never `sleep()`.** `holdFor` keeps the robot holding its spot while it waits. With `sleep()`, the robot stops paying attention and can drift.
- **Keep `whilePathRuns` quick.** No waiting and no loops there. It runs many times every second while the robot drives, and slow code makes the robot miss its path.

Test one action at a time. Get the intake working, then add the shot.

## If something goes wrong

If the robot does something unsafe, press **Stop** first, then use this table.

| What happens | What to try |
| --- | --- |
| The robot stops before the last path | A path is missing from `pathsInOrder()`. Make sure every `public Path` has a line there (Part 2, step 8). |
| Every point is a few inches off | The robot didn't start exactly on the start point. Measure carefully and line it up again. |
| The robot drives the right shape but in the wrong direction | The robot started facing the wrong way. Check the start heading. |
| Red underlines in Android Studio | The paste went in the wrong spot, or a `(),` is missing from the list. Compare your code with Part 2. |
| **Visualizer Auto** isn't in the Autonomous list | The new code isn't on the robot. Press **Run ▶** again and wait for it to finish. |
| Android Studio can't find the robot | Join the robot's Wi-Fi, or try a different USB-C cable. Ask a mentor if it still doesn't show up. |
| The robot wobbles or misses the path badly | Don't change the tuning yourself. Tell a mentor what you saw. |

Still stuck? Ask a mentor, and bring your laptop so they can see your code.
