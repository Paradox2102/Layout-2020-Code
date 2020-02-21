/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Camera;
import frc.lib.Camera.CameraData;
import frc.robot.subsystems.ShooterSubsystem;

public class CaseShootingCommand extends CommandBase {
  ShooterSubsystem m_subsystem;
  Camera m_camera;
  double m_speed;

  public CaseShootingCommand(ShooterSubsystem subsystem, Camera camera, double speed) {
    m_subsystem = subsystem;
    m_camera = camera;
    m_speed = speed;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_camera.toggleLights(true);
    m_subsystem.setSpeed(m_speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    CameraData data = m_camera.createData();

    if (data.canSee() && data.getTargetWidth() > 50) {
      double speed = m_subsystem.calculatedSpeed(data.getTargetHeight());

      m_speed = speed;
    }
    SmartDashboard.putNumber("Calculated Speed", m_speed);
    m_subsystem.setSpeed(m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
    m_camera.toggleLights(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
