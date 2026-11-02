# Next Steps: Getting Pedro Pathing to Drive Our Robot

The drivetrain and Pinpoint settings are done. Next, we finish tuning so the robot can drive paths on its own, then drive a path we draw in the Pedro Pathing Visualizer.

Work through these steps in order. Don't skip ahead, because each step depends on the one before it.

**Useful links:**
- [Team README](README.md): setup, deploying, and git
- [Pedro Pathing docs](https://pedropathing.com/)
- [Pedro Pathing Visualizer](https://visualizer.pedropathing.com/)
- Pedro tuning page: [http://192.168.43.1:10158](http://192.168.43.1:10158). This only works while you're on the robot's Wi-Fi.

---

## Step 1: Get the latest code onto the robot

1. In GitHub Desktop, click **Fetch origin**, then **Pull**. See [Working as a team with git](README.md#6-working-as-a-team-with-git) in the README.
2. Open the `25906FTC_2026BIOBUZZ` folder in Android Studio. Don't open the folder above it.
3. Connect to the robot and press **Run ▶** to deploy. See [Put code on the robot](README.md#1-get-set-up) in the README.

✅ **Check:** On the tuning page, the tuner list shows **Tests**, **Mecanum**, **Pinpoint** and **Foresight**.

---

## Step 2: Make sure the robot knows where it is

Pedro can only drive accurately if the Pinpoint position tracking is accurate.

1. Connect your laptop to the **robot's Wi-Fi**.
2. Open the tuning page at [http://192.168.43.1:10158](http://192.168.43.1:10158).
3. Choose **Tests**, then set **Test** to **Localization Test**.
4. Press Play. Drive the robot with gamepad 1 and watch the pose on the page:
   - Drive **forward**: **x** should go **up**.
   - Strafe **left**: **y** should go **up**.
   - Turn **left** (counter-clockwise): **heading** should go **up**.
5. Measure it. Drive the robot exactly 48 inches forward using a tape measure. **x** should read close to 48.

❌ **If a number goes the wrong way** or is far off, stop here and tell a mentor. The fix goes in the Pinpoint settings in [`pedro/Constants.java`](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedro/Constants.java).

---

## Step 3: Run the Foresight tuner

Foresight is the part of Pedro that steers the robot along a path. Its tuner measures how fast our robot goes, how it brakes, and how it turns.

⚠️ **The robot drives fast and on its own during this tuner.** Keep everyone's hands and feet clear. Have one person ready to press **Stop**.

1. Put the robot in the middle of a large clear area, at least **4 tiles (8 ft)** in every direction. The robot drives forward, backward, left and right, and spins in place.
2. On the tuning page, choose **Foresight**.
3. It asks for a setting three times along the way. Keep the defaults unless a mentor says otherwise:
   - First, **Distance** = 48
   - Then **Velocity** = 30
   - Later, **Distance** again = 36. This one is for the braking tests and must be at least 15.
4. The tuner runs about 10 short tests in a row. Before each test, read its description on the page, then press Play. Put the robot back in the middle of the open area whenever you need to.
5. At the end, the page shows a list of **results**: about 17 numbers with names like `maxAchievableForwardVelocity` and `heading kP`.
6. **Save every result.** Take a screenshot **and** copy the names and numbers into a text file. You don't want to run the tuner again because a number was lost.
7. Give the results to Mr. Van Loo. They'll be added to `Constants.java` so Pedro can use them.

---

## Step 4: Test path following

Do this once the Foresight results are in the code. Pull and deploy again first (Step 1).

1. On the tuning page, choose **Tests**.
2. Run each test below in this order. Leave **Distance** at **48**.

| Test | What should happen | Space needed |
|---|---|---|
| **Line Test** | Drives straight forward 48 inches, then back, over and over | 4 ft in front |
| **Curve Test** | Curves forward and to the left, then curves back | 4 ft × 4 ft, forward and to the left |
| **Hold Test** | Stays in place. Push the robot gently and it should drive back. | Small |

✅ **Good result:** The robot follows the path smoothly and ends near where it started, without wobbling or overshooting.

❌ **If it shakes, overshoots, or drifts off the path,** stop and tell a mentor. Write down what you saw.

---

## Step 5: Drive a path you draw in the Visualizer

Now the fun part: design an autonomous path and watch the robot drive it.

### Draw the path
1. Open the [Pedro Pathing Visualizer](https://visualizer.pedropathing.com/).
2. Move the **start point** to where the robot will sit on the field. Set the start heading to the direction the robot faces.
   - The field is 144 × 144 inches. Headings are in degrees: **0°** faces the +x direction and **90°** faces the +y direction.
3. Add paths. Start simple, with one or two short lines, before you try curves.
4. Press play in the Visualizer to watch the robot move along your path.

### Put it in the code
1. Click **Export Code**.
2. Set **Include** to **Coordinates only**. ⚠️ Not "Full code", because that version needs a library we don't use.
3. Copy the code.
4. In Android Studio, open [`opmodes/VisualizerAuto.java`](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes/VisualizerAuto.java).
5. Select everything **between** the `PASTE START` and `PASTE END` lines and paste your code over it.
6. Find `pathsInOrder()` and list your path methods in the order the robot should drive them:
   ```java
   return new Path[] {
           path1(),
           path2(),
           path3(),
   };
   ```
   Use the method names from your pasted code. They're the lines that start with `public Path`.
7. If you **renamed the start point** in the Visualizer, change `return start;` in `startingPose()` to the new name.

### Run it
1. Deploy (**Run ▶**).
2. Place the robot on the field **exactly** at the start point, facing the start heading. Pedro trusts that the robot starts where you said it does.
3. On the Driver Station, pick **Visualizer Auto** from the Autonomous list. Press **Init**, then **Play**.
4. Watch telemetry. It shows which path is running and the robot's x, y and heading.

❌ **If the Driver Station says "Pedro isn't tuned yet,"** Step 3 isn't finished in the code yet.

---

## Step 6: Save your work

1. Make a branch for your path, such as `auto-left-side`.
2. Commit with a clear message, such as "Add left-side auto path from Visualizer".
3. Push the branch and open a Pull Request so a mentor can review it.

See [Working as a team with git](README.md#6-working-as-a-team-with-git) for details.
