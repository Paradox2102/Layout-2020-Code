/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto.TrenchRun;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lib.Camera;
import frc.pathfinder.Pathfinder.Waypoint;
import frc.robot.PurePursuit.PathConfigs;
import frc.robot.commands.Auto.CreatePathCommand;
import frc.robot.commands.Auto.WaitForDistanceCommand;
import frc.robot.commands.Auto.WaitForShooterSpeedCommand;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Teleop.FireCommand;
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
public class BackTrenchRunCommand extends ParallelDeadlineGroup {
  /**
   * Creates a new BackTrenchRunCommand.
   */
  
  final static Waypoint[] k_backwardsTrench = { 
      new Waypoint(-11, 10, Math.toRadians(90), 5),
      new Waypoint(-11, 25, Math.toRadians(90))
  };

  final static double k_firingX = -11;
  final static double k_firingY = 20;

  public BackTrenchRunCommand(DriveSubsystem driveSubsystem, IntakeSubsystem intakeSubsystem, 
                                ShooterSubsystem shooterSubsystem, TurretSubsystem turretSubsystem, ThroatSubsystem throatSubsystem,
                                DoubleSupplier getX, DoubleSupplier getY, Camera camera) {

    super(
      new CreatePathCommand(driveSubsystem, k_backwardsTrench, PathConfigs.fast, true, true, true),
      new SequentialCommandGroup(
        new WaitForDistanceCommand(getX, getY, k_firingX, k_firingY),
        new WaitForShooterSpeedCommand(shooterSubsystem),
        new ParallelCommandGroup(
          new TurretTrackingCommand(turretSubsystem, camera),
          new FireCommand(throatSubsystem, shooterSubsystem, intakeSubsystem)
        )
      )
    );
  }
}
