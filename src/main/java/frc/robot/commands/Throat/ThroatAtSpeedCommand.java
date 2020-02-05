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

public class ThroatAtSpeedCommand extends CommandBase {
  /**
   * Creates a new ThroatAtSpeedCommand.
   */
   private ThroatSubsystem m_throatSubsystem;
   private double m_power = 0;
  public ThroatAtSpeedCommand(ThroatSubsystem throatSubsystem, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    Logger.Log("ThroatAtSpeedCommand", 3, String.format("ThroatAtSpeedCommand: %f", power));
    
    m_power = power;
    m_throatSubsystem = throatSubsystem;
    addRequirements(m_throatSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("ThroatAtSpeedCommand", 2, "initialize()");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_throatSubsystem.GetTopBreak() && m_throatSubsystem.GetBottomBreak()){
      m_throatSubsystem.setThroatPower(m_power);
    } else {
      m_throatSubsystem.stopThroatPower();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_throatSubsystem.stopThroatPower();
    Logger.Log("ThroatAtSpeedCommand", 3, "end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
