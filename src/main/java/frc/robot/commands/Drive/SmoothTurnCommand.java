/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Logger;
import frc.robot.subsystems.DriveSubsystem;

public class SmoothTurnCommand extends CommandBase {
  DriveSubsystem m_subsystem;
  double m_angle;
  double m_power;
  double k_deadzone = 10;

  double m_turnPower;
  double m_turnFactor;

  /**
   * Creates a new SmoothTurnCommand.
   */
  public SmoothTurnCommand(DriveSubsystem driveSubsystem, double angle, double turnPower, double turnFactor) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = driveSubsystem;
    m_angle = angle;
    m_turnPower = turnPower;
    m_turnFactor = turnFactor;
    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("SmoothTurnCommand", 1, "initialize");
    m_subsystem.resetAngle(m_angle);

    if (m_angle > 0) {
      m_subsystem.setPower(m_turnPower + m_turnFactor, m_turnPower - m_turnFactor);
    } else if (m_angle < 0) {
      m_subsystem.setPower(m_turnPower - m_turnFactor, m_turnPower + m_turnFactor);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("SmoothTurnCommand", -1, "execute");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("SmoothTurnCommand", 1, "end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("SmoothTurnCommand", -1, "isFinished");
    return Math.abs(m_subsystem.getAngle()) < k_deadzone;
  }
}
