/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.GalacticSearch;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.pathfinder.Pathfinder.Waypoint;
import frc.robot.PurePursuit.PathConfigs;
import frc.robot.commands.Auto.CreatePathCommand;
import frc.robot.subsystems.DriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class pathAuto extends ParallelCommandGroup {
  /**
   * Creates a new pathAuto.
   */

  static final Waypoint[] k_drive6ft = { 
      new Waypoint(0, 0, Math.toRadians(90), 1, 13, 0),
      new Waypoint(-5, 15, Math.toRadians(90)), 
      new Waypoint(0.5, 22.5, Math.toRadians(90), 4, 4, 0), 
      new Waypoint(-5, 22.5, Math.toRadians(-90)),
      new Waypoint(0, 15, Math.toRadians(-90), 12, 6, 0), 
      new Waypoint(-6.5, -2.5, Math.toRadians(-90)) };
  DriveSubsystem m_driveSubsystem;

  public pathAuto(DriveSubsystem driveSubsystem) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    m_driveSubsystem = driveSubsystem;
    addCommands(new SequentialCommandGroup(new CreatePathCommand(driveSubsystem, k_drive6ft, PathConfigs.fastAccel)));
  }

  @Override
  public void initialize() {
    super.initialize();
    m_driveSubsystem.resetAngle(90);
  }
}
