/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Teleop;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.lib.Camera;
import frc.robot.commands.Indexer.IndexCommand;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Shooter.CalibrateSpeedCommand;
import frc.robot.commands.Shooter.CaseShootingCommand;
import frc.robot.commands.Shooter.ShooterCalculateSpeedCommand;
import frc.robot.commands.Shooter.ShooterSpeedCommand;
import frc.robot.commands.Turret.TurretTrackingCommand;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.TurretSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SpinUpCommand extends ParallelCommandGroup {
  /**
   * Creates a new SpinUpCommand.
   */
  public SpinUpCommand(TurretSubsystem turretSubsystem, Camera turretCamera, ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem, double speed) {
    addCommands(new CaseShootingCommand(shooterSubsystem, turretCamera, speed), new IndexCommand(indexerSubsystem, 0.5));
  }

  public SpinUpCommand(TurretSubsystem turretSubsystem, Camera turretCamera, ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem, DoubleSupplier speed) {
    addCommands( new CalibrateSpeedCommand(shooterSubsystem, speed), new IndexCommand(indexerSubsystem, 0.5));
  }
}
