/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Teleop;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Intake.AmbientIntakePowerCommand;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.Throat.ThroatAtSpeedCommand;
import frc.robot.commands.Throat.ThroatPowerCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ThroatSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class FireCommand extends ParallelCommandGroup {
  /**
   * Creates a new FireCommand.
   */
  public FireCommand(ThroatSubsystem throatSubsystem, ShooterSubsystem shooterSubsystem) {
    addCommands(new ThroatPowerCommand(throatSubsystem, () -> shooterSubsystem.getSpeed(),
        () -> shooterSubsystem.getSetpoint() - 5000, 0.75, false)
    // new IntakeCommand(intakeSubsystem, 0.5)
    );
  }

  public FireCommand(ThroatSubsystem throatSubsystem, ShooterSubsystem shooterSubsystem,
      IntakeSubsystem intakeSubsystem) {
    addCommands(new ThroatPowerCommand(throatSubsystem, () -> shooterSubsystem.getSpeed(),
        () -> shooterSubsystem.getSetpoint() - 5000, 0.75, false), new IntakeCommand(intakeSubsystem, 0.85));
  }
}
