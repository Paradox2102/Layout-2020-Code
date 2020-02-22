/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Throat;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Logger;
import frc.robot.subsystems.ThroatSubsystem;

public class ThroatMoveCommand extends CommandBase {
  ThroatSubsystem m_subsystem;
  double m_power;
  public ThroatMoveCommand(ThroatSubsystem throatSubsystem, double power) {
    m_subsystem = throatSubsystem;
    m_power = power;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("ThroatMoveCommand", 1 , "initialize");
    m_subsystem.setThroatPower(m_power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("ThroatMoveCommand", -1 , "execute");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("ThroatMoveCommand", 1 , "end");
    m_subsystem.stopThroatPower();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("ThroatMoveCommand", -1 , "isFinished");
    return false;
  }
}
