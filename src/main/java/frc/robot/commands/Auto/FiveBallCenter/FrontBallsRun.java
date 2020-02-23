/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.FiveBallCenter;
//package frc.robot.commands.Auto.TrenchRun;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lib.Camera;
import frc.pathfinder.Pathfinder.Waypoint;
import frc.robot.PurePursuit.PathConfigs;
import frc.robot.commands.Auto.CreatePathCommand;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Shooter.ShooterSpeedCommand;
import frc.robot.commands.Teleop.SpinUpCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.TurretSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class FrontBallsRun extends ParallelDeadlineGroup {
  /**
   * Creates a new FrontBallRun.
   */
  final static Waypoint[] k_frontBalls = { new Waypoint(-1.5, 10, Math.toRadians(90), 2, 4, 3),
      new Waypoint(0.8, 20, Math.toRadians(50))/* , new Waypoint(1.92, 20.32, Math.toRadians(50)) */ };

  public FrontBallsRun(DriveSubsystem driveSubsystem) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new CreatePathCommand(driveSubsystem, k_frontBalls, PathConfigs.fast, true, true, true));
  }
}
