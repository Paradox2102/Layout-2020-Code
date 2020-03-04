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
import frc.robot.commands.Serializer.PowerSerializeCommand;
import frc.robot.commands.Serializer.SerializeCommand;
import frc.robot.commands.Throat.ThroatMoveCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SerializerSubsystem;
import frc.robot.subsystems.ThroatSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class UnJumbleCommand extends ParallelCommandGroup {
  /**
   * Creates a new UnJumbleCommand.
   */
  public UnJumbleCommand(IntakeSubsystem intakeSubsystem, ThroatSubsystem throatSubsystem, SerializerSubsystem serializerSubsystem) {
    addCommands(
      new IntakeCommand(intakeSubsystem, -0.5), 
      new ThroatMoveCommand(throatSubsystem, -0.8), 
      new PowerSerializeCommand(serializerSubsystem, -0.5)
    );
  }
}
