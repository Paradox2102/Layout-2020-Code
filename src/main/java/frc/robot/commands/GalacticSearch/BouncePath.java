/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.GalacticSearch;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.PiCamera.Logger;
import frc.pathfinder.Pathfinder.Waypoint;
import frc.robot.PurePursuit.PathConfigs;
import frc.robot.commands.Auto.CreatePathCommand;
import frc.robot.subsystems.DriveSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class BouncePath extends ParallelCommandGroup {
  /**
   * Creates a new pathAuto.
   */

  static final Waypoint[] k_legOne = { 
      new Waypoint(0, 3.5, Math.toRadians(90)),
      new Waypoint(-3.5, 7.5, Math.toRadians(180)),
     };
     static final Waypoint[] k_legTwo = { 
      new Waypoint(-3.5, 7.5, Math.toRadians(0), 1, 1, 0),
      new Waypoint(5, 12.5, Math.toRadians(90), 2, 6, 0),
      new Waypoint(-3.5, 15, Math.toRadians(180)),
     };
     static final Waypoint[] k_legThree = { 
      new Waypoint(-3.5, 15, Math.toRadians(0)),
      new Waypoint(4.5, 18.25, Math.toRadians(90)),
      new Waypoint(-3.5, 22.5, Math.toRadians(180)),
     };
     static final Waypoint[] k_legFour = { 
      new Waypoint(-3.5, 22.5, Math.toRadians(0)),
      new Waypoint(0, 27.5, Math.toRadians(90)),
     };
  DriveSubsystem m_driveSubsystem;

  public BouncePath(DriveSubsystem driveSubsystem) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    m_driveSubsystem = driveSubsystem;
    addCommands(new SequentialCommandGroup(
      new CreatePathCommand(driveSubsystem, k_legOne, PathConfigs.fastAccel),
      new CreatePathCommand(driveSubsystem, k_legTwo, PathConfigs.fastAccel, true, true, true),
      new CreatePathCommand(driveSubsystem, k_legThree, PathConfigs.fastAccel),
      new CreatePathCommand(driveSubsystem, k_legFour, PathConfigs.fastAccel, true, true, true)
      ));
  }

  @Override
  public void initialize() {
    super.initialize();
    m_driveSubsystem.resetAngle(90);
    m_driveSubsystem.setPos(0, 3.5);
    Logger.Log("bouncePath", 0, "initialize");
  }
}
