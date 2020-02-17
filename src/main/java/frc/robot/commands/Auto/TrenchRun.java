/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.lib.Camera;
import frc.pathfinder.Pathfinder.Waypoint;
import frc.robot.PurePursuit.PathConfigs;
import frc.robot.commands.Camera.BallDriveCommand;
import frc.robot.commands.Camera.ToggleLightsCommand;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Shooter.CaseShootingCommand;
import frc.robot.commands.Teleop.ShootCommand;
import frc.robot.commands.Throat.ThroatPowerCommand;
import frc.robot.commands.Turret.TurretTrackingCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ThroatSubsystem;
import frc.robot.subsystems.TurretSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TrenchRun extends ParallelCommandGroup {
  /**
   * Creates a new DriveToTrenchConmand.
   */
  final Waypoint[] k_backwardsTrench = {
    new Waypoint(-11, 10, Math.toRadians(90), 8.5),
    new Waypoint(-11, 25, Math.toRadians(90))
  };

  final Waypoint[] k_forwardsTrench = {
    new Waypoint(-11, 25, Math.toRadians(-90), 7),
    new Waypoint(-11, 15, Math.toRadians(-90))
  };
  
  final Waypoint[] k_2balls = {
    new Waypoint(-11, 15, Math.toRadians(90), 1, 2, 3, 2, 6),
    new Waypoint(-5, 17, Math.toRadians(40))
  };
  

  public TrenchRun(DriveSubsystem driveSubsystem, Camera turretCamera, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem, double shooterSpeed, Camera backCamera, TurretSubsystem turretSubsystem, ThroatSubsystem throatSubsystem) {
    addCommands(
        new CreatePathCommand(driveSubsystem, k_backwardsTrench, PathConfigs.fast, true, true, true), 
        new IntakeCommand(intakeSubsystem, 0.5), 
        new CaseShootingCommand(shooterSubsystem, turretCamera, shooterSpeed),
        new SequentialCommandGroup(
          new WaitForDistanceCommand(() -> driveSubsystem.getPos().x, () -> driveSubsystem.getPos().y, -10, 20),
          new WaitForShooterSpeedCommand(shooterSubsystem),
          new ParallelCommandGroup(new TurretTrackingCommand(turretSubsystem, turretCamera), new ThroatPowerCommand(throatSubsystem, () -> shooterSubsystem.getSpeed(), () -> shooterSubsystem.getSetpoint() - 1500, 0.4)),
          new WaitCommand(0.5),
          new CreatePathCommand(driveSubsystem, k_forwardsTrench, PathConfigs.fast, false, false, true),
          new WaitCommand(0.5))
      // new ToggleLightsCommand(frontCamera, true),
      // new ParallelDeadlineGroup(new WaitForBallCommand(frontCamera), new CreatePathCommand(driveSubsystem, k_2balls, PathConfigs.fast, true, false, true)),
      // new BallDriveCommand(driveSubsystem, frontCamera, -0.25)   
      );
  }
}
