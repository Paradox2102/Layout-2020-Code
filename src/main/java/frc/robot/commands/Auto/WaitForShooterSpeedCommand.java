/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Logger;
import frc.robot.subsystems.ShooterSubsystem;

public class WaitForShooterSpeedCommand extends CommandBase {
  /**
   * Creates a new WaitForShooterSpeedCommand.
   */
  ShooterSubsystem m_subsystem;
  final static int k_tolerance = 500;
  public WaitForShooterSpeedCommand(ShooterSubsystem subsystem) {
    m_subsystem = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("WaitForShooterSpeedCommand", 1 , "initialize");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("WaitForShooterSpeedCommand", -1 , "execute");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("WaitForShooterSpeedCommand", 1 , "end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("WaitForShooterSpeedCommand", -1 , "isFinished");
    return m_subsystem.getSpeed() >= m_subsystem.getSetpoint() - k_tolerance;
  }
}
