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

public class TurnByAngleCommand extends CommandBase {
  DriveSubsystem m_subsystem;
  double m_angle;
  double m_power;
  double k_deadzone = 10;

  public TurnByAngleCommand(DriveSubsystem driveSubsystem, double angle, double power) {
    m_subsystem = driveSubsystem;
    m_angle = angle;
    m_power = power;
    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("TurnByAngleCommand", 1, "initialize");
    m_subsystem.resetAngle(m_angle);

    if (m_angle > 0) {
      m_subsystem.setPower(m_power, -m_power);
    } else if (m_angle < 0) {
      m_subsystem.setPower(-m_power, m_power);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("TurnByAngleCommand", -1, "execute");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("TurnByAngleCommand", 1, "end");

    m_subsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("TurnByAngleCommand", -1, "isFinished");

    return Math.abs(m_subsystem.getAngle()) < k_deadzone;
  }
}
