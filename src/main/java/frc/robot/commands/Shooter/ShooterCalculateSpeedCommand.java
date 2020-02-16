/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Camera;
import frc.lib.Camera.CameraData;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCalculateSpeedCommand extends CommandBase {
  /**
   * Creates a new ShooterCalculateSpeedCommand.
   */
  ShooterSubsystem m_shooterSubsystem;
  Camera m_camera;
  public ShooterCalculateSpeedCommand(ShooterSubsystem shooterSubsystem, Camera camera) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_camera = camera;
    m_shooterSubsystem = shooterSubsystem;
    addRequirements(m_shooterSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    CameraData data = m_camera.createData();
    if(data.m_regions != null) {
      m_shooterSubsystem.setSpeed(m_shooterSubsystem.calculatedSpeed(data.getTargetHeight()));
      SmartDashboard.putNumber("Calculated Speed", m_shooterSubsystem.calculatedSpeed(data.getTargetHeight()));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
