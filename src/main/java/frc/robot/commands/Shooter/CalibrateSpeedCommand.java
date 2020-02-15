/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Shooter;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class CalibrateSpeedCommand extends CommandBase {
  /**
   * Creates a new SpeedCommand.
   */
  ShooterSubsystem m_subsystem;
  DoubleSupplier m_speed;

  public CalibrateSpeedCommand(ShooterSubsystem subsystem, DoubleSupplier speed) {
    m_subsystem = subsystem;

    m_speed = speed;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_subsystem.configPID();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = (m_speed.getAsDouble() * 10000) + 30000;

    SmartDashboard.putNumber("Ideal Speed", speed);

    if(speed < m_subsystem.getSpeed() - 1500){
      m_subsystem.stop();
    }else{
      m_subsystem.setSpeed(speed);
    }
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
