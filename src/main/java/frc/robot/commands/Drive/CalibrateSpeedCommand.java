/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class CalibrateSpeedCommand extends CommandBase {
  /**
   * Creates a new SpeedCommand.
   */
  DriveSubsystem m_subsystem;
  double m_speed;
  public CalibrateSpeedCommand(DriveSubsystem subsystem, double speed) {
    m_subsystem = subsystem;
    m_speed = speed;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_subsystem.configPID();
    m_subsystem.setSpeed(m_speed, m_speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystem.setSpeed(m_speed, m_speed);

    SmartDashboard.putNumber("Left Vel", m_subsystem.getLeftVel());
    SmartDashboard.putNumber("Right Vel", m_subsystem.getRightVel());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
