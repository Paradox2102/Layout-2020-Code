/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Camera;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Camera;
import frc.lib.Camera.CameraData;
import frc.robot.subsystems.DriveSubsystem;

public class BallDriveCommand extends CommandBase {
  DriveSubsystem m_subsystem;
  Camera m_camera;
  double m_power;
  boolean m_seenBalls = false;
  double k_p = 0.0005;

  public BallDriveCommand(DriveSubsystem subsystem, Camera camera, double power) {
    m_subsystem = subsystem;
    m_camera = camera;
    m_power = power;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_camera.toggleLights(true);
    m_seenBalls = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    CameraData data = m_camera.createData();
    int numBalls = data.ballFilter().size();
    if (numBalls >= 2) {
      double diff = data.ballCenterDiff(data.centerLine());
      double powerDiff = Math.abs(diff) * k_p;

      // turn left
      if (diff > 0) {
        m_subsystem.setPower(m_power - powerDiff, m_power + powerDiff);
      } else {
        m_subsystem.setPower(m_power + powerDiff, m_power - powerDiff);
      }
    } else if (numBalls >= 1) {
      double diff = data.centerDiff(data.centerLine(), 0);
      double powerDiff = Math.abs(diff) * k_p;

      if (diff > 0) {
        m_subsystem.setPower(m_power - powerDiff, m_power + powerDiff);
      } else {
        m_subsystem.setPower(m_power + powerDiff, m_power - powerDiff);
      }
    }
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
    CameraData data = m_camera.createData();
    boolean canSeeMulti = data.ballFilter().size() >= 1;

    if (!m_seenBalls && canSeeMulti) {
      m_seenBalls = true;
    }

    if (m_seenBalls) {
      return !canSeeMulti;
    }
    return false;
  }
}
