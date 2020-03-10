/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.RightBallRunCameraAlign;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.pathfinder.Pathfinder.Waypoint;
import frc.robot.PurePursuit.PathConfigs;
import frc.robot.commands.Auto.CreatePathCommand;
import frc.robot.subsystems.DriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DriveToCenter extends SequentialCommandGroup {
  /**
   * Creates a new DriveToCenter.
   */
  static final Waypoint[] k_centerDrive = { new Waypoint(10.75, 20.5, Math.toRadians(-90), 5),
      new Waypoint(2.5, 13.8, Math.toRadians(230)) };

  public DriveToCenter(DriveSubsystem driveSubsystem) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new CreatePathCommand(driveSubsystem, k_centerDrive, PathConfigs.fast, false, false, true));
  }
}
