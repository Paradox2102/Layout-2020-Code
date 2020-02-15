/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Teleop;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Indexer.IndexCommand;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Serializer.SerializeCommand;
import frc.robot.commands.Shooter.PowerCommand;
import frc.robot.commands.Throat.ThroatPowerCommand;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SerializerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ThroatSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootAllCommand extends SequentialCommandGroup {
  /**
   * Creates a new ShootAllCommand.
   */
  public ShootAllCommand(ThroatSubsystem throatSubsystem, ShooterSubsystem shooterSubsystem, SerializerSubsystem serializerSubsystem, IndexerSubsystem indexerSubsystem, IntakeSubsystem intakeSubsystem,DoubleSupplier getThrottle) {
    // addCommands(new ParallelDeadlineGroup(new IndexCommand(indexerSubsystem, 0.75), new ThroatPowerCommand(throatSubsystem, 0.5), new SerializeCommand(serializerSubsystem, 0.25, () -> throatSubsystem.GetTopBreak()), new PowerCommand(shooterSubsystem, getThrottle), new IntakeCommand(intakeSubsystem, 0.5)));
  }
}
