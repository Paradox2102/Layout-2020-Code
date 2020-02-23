/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Logger;
import frc.robot.subsystems.IntakeSubsystem;

public class ActuateIntakeCommand extends CommandBase {
  /**
   * Creates a new AcutauteIntakeCommand.
   */
  IntakeSubsystem m_subsystem;
  public ActuateIntakeCommand(IntakeSubsystem intakeSubsystem) {
    m_subsystem = intakeSubsystem;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("AcutauteIntakeCommand", 1 , "initialize");
    m_subsystem.setDeploy(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("AcutauteIntakeCommand", -1 , "execute");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("AcutauteIntakeCommand", 1 , "end");
    m_subsystem.setDeploy(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("AcutauteIntakeCommand", -1 , "isFinished");
    return false;
  }
}
