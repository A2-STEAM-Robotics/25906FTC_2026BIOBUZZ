# FTC 25906 — BIOBUZZ 2026–2027

Robot code for A2 STEAM's *FIRST* Tech Challenge team 25906.

This repository is built on the official [FTC SDK 12.0](https://github.com/FIRST-Tech-Challenge/FtcRobotController). It adds:

- **goBILDA Pinpoint** odometry. The driver is built into the SDK, and this repo includes an example OpMode for it.
- **[Pedro Pathing](https://pedropathing.com/)** for autonomous path following, along with its tuners.
- **[Panels](https://panels.bylazar.com/)**, a browser dashboard for telemetry, graphs, a field view and OpMode control.

---

## 1. Get set up

**You need:**
- [Android Studio](https://developer.android.com/studio), version *Narwhal 3 Feature Drop* or later.
- A GitHub account that has been added to the A2-STEAM-Robotics organization.
- [GitHub Desktop](https://desktop.github.com/) if you aren't comfortable with command-line git.

**Clone the repository:**
1. In GitHub Desktop, choose **File → Clone repository** and pick `A2-STEAM-Robotics/25906FTC_2026BIOBUZZ`.
   - Or, from a terminal: `git clone https://github.com/A2-STEAM-Robotics/25906FTC_2026BIOBUZZ.git`
2. In Android Studio, choose **File → Open** and select the cloned folder.
3. Wait for Gradle sync to finish. The first sync downloads a lot, so it can take several minutes.

> ⚠️ **If Android Studio offers to upgrade the "Android Gradle Plugin" or Gradle, click *Don't ask again* / *Cancel*.**
> The FTC SDK pins these versions on purpose. Upgrading them breaks the build for everyone else on the team.

**Put code on the robot:**
1. Connect your computer to the Control Hub by USB-C, or join the robot's Wi-Fi.
2. In Android Studio's device menu, pick the Control Hub and press **Run ▶**.
3. On the Driver Station, choose your OpMode from the TeleOp or Autonomous list.

---

## 2. Where things live

All of our code goes in `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`:

```
teamcode/
├── examples/      Working examples to learn from (Pinpoint)
├── pedro/
│   ├── Constants.java     ← Robot settings for Pedro (filled in during tuning)
│   ├── Tuning.java        ← Registers Pedro's tuners
│   └── procedures/        Pedro's tuner code (don't edit)
└── (your OpModes)  Make a package such as opmodes/ for TeleOp and Auto
```

**Don't edit anything outside `TeamCode/`.** That covers `FtcRobotController/`, the Gradle files, and `build.dependencies.gradle`. Ask a mentor if you think a change there is needed.

---

## 3. Writing your own OpMode

The quickest way to start is to copy a sample:

1. In Android Studio's Project view, open
   `FtcRobotController/java/org.firstinspires.ftc.robotcontroller/external/samples/`.
   Good starting points:
   - `BasicOmniOpMode_Linear` — mecanum drive TeleOp
   - `RobotAutoDriveByEncoder_Linear` — simple autonomous
2. Copy the file into your folder under `teamcode/`.
3. Change the `package` line to match its new folder.
4. Give it a unique name in `@TeleOp(name = "...")` or `@Autonomous(name = "...")`.
5. Delete or comment out the `@Disabled` line. Otherwise it won't show up on the Driver Station.
6. Make sure the names in `hardwareMap.get(...)` match the names in the robot's configuration on the Driver Station.

---

## 4. Pinpoint odometry

The Pinpoint tracks the robot's X, Y and heading from two odometry pods and a built-in IMU.

1. Plug the Pinpoint into an **I2C port** on the Control Hub.
2. On the Driver Station, go to **Configure Robot**. Add an I2C device of type **goBILDA® Pinpoint Odometry Computer** and name it **`pinpoint`**.
3. Run the TeleOp **"Sensor: GoBilda Pinpoint"** (`examples/SensorGoBildaPinpoint.java`). Push the robot around and watch X, Y and heading change in telemetry.
   - The offsets in that example are for goBILDA's demo robot. Pedro's Pinpoint Tuner (below) measures the right ones for our robot.

---

## 5. Pedro Pathing

Pedro Pathing drives the robot along smooth paths in autonomous. It has to be tuned for our robot before it will work.

> ❗ `pedro/Constants.java` currently returns `null`. **Any OpMode that uses Pedro will crash until tuning is done** and Constants is filled in.

### Step 1 — Register the tuners
The tuners only appear once they are registered in `pedro/Tuning.java`, with one `@Tuner` method each. The two our robot (mecanum + Pinpoint) needs first are already registered:

```java
package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.PinpointTuner;

public class Tuning {
    @Tuner
    public static Procedure mecanum() { return new MecanumTuner(); }

    @Tuner
    public static Procedure pinpoint() { return new PinpointTuner(); }
}
```

### Step 2 — Run the tuners
1. Deploy the code to the robot.
2. Connect your laptop to the **robot's Wi-Fi**.
3. In a browser, open **http://192.168.43.1:10158**. This is Pedro's tuning page, served by the Control Hub.
4. Run the **Mecanum Tuner**, which finds the motor directions. Then run the **Pinpoint Tuner**, which finds the pod directions and offsets.
5. Each tuner prints a block of Java code at the end. **Copy that code into `pedro/Constants.java`.**

### Step 3 — Finish tuning
Once Constants has the drivetrain and Pinpoint settings, register and run:
- **`ForesightTuner`** — robot speed, braking and control coefficients.
- **`Tests`** — line, curve and hold tests to check that everything works.

Both of these take the drivetrain and localizer from Constants, so they can't run until Steps 1–2 are done. The [Pedro Pathing docs](https://pedropathing.com/) walk through each tuner in detail.

### Panels dashboard
Panels starts on its own whenever the Robot Controller app is running. You don't need to start it from an OpMode.

1. Connect your laptop to the **robot's Wi-Fi**.
2. Open **http://192.168.43.1:8001** in a browser.

If the page loads but shows nothing, check the robot's OpMode list for **Enable/Disable Panels** and make sure Panels is turned on. Pedro's tuning page (port 10158) is separate from Panels and works whether or not Panels is enabled.

---

## 6. Working as a team with git

- **Pull before you start** (GitHub Desktop: *Fetch origin → Pull*) so you have everyone's latest code.
- **Make a branch** for what you're working on, such as `teleop-arm` or `auto-left-side`.
- **Commit often**, with messages that say what changed ("Add arm lift to TeleOp", not "stuff").
- **Push your branch** and open a **Pull Request** on GitHub. A mentor or lead reviews it before it goes into `main`.
- Only merge code into `main` once it **builds and has been tested on the robot**.

---

## Getting help

- [FTC Docs](https://ftc-docs.firstinspires.org/) — official tutorials for Android Studio, configuration and sensors
- [FTC Javadoc](https://javadoc.io/doc/org.firstinspires.ftc) — reference for SDK classes
- [Pedro Pathing docs](https://pedropathing.com/)
- [Panels docs](https://panels.bylazar.com/)
- [goBILDA](https://www.gobilda.com/) — search "Pinpoint" for the product page and user guide
- [FTC SDK release notes](https://github.com/FIRST-Tech-Challenge/FtcRobotController/releases) — what changed in each SDK version
