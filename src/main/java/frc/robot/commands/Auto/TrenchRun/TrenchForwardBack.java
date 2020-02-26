/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.TrenchRun;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.PurePursuit.PathConfigs;
import frc.robot.commands.Auto.CreatePathCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.pathfinder.Pathfinder.Waypoint;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TrenchForwardBack extends SequentialCommandGroup {
  /**
   * Creates a new TrenchForwardBack.
   */

  final static Waypoint[] k_backwardsTrench = { new Waypoint(-11, 10, Math.toRadians(90), 5),
      new Waypoint(-11, 25, Math.toRadians(90)) };

  final static Waypoint[] k_forwardsTrench = { new Waypoint(-11, 25, Math.toRadians(-90), 5),
      new Waypoint(-11, 15, Math.toRadians(-90)) };

  public TrenchForwardBack(DriveSubsystem driveSubsystem) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    addCommands(new CreatePathCommand(driveSubsystem, k_backwardsTrench, PathConfigs.fast, true, true, true),
        new CreatePathCommand(driveSubsystem, k_forwardsTrench, PathConfigs.fast));
  }
}
