/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Camera;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.PiCamera.Logger;
import frc.lib.Camera;
import frc.lib.Camera.BallSide;
import frc.lib.Camera.CameraData;
import frc.robot.subsystems.DriveSubsystem;

public class BallDriveCommand extends CommandBase {
  DriveSubsystem m_subsystem;
  Camera m_camera;
  double m_power;
  boolean m_seenBalls = false;
  double k_p = 0.0005;
  BallSide k_ballSide;
  boolean m_reversed;

  public BallDriveCommand(DriveSubsystem subsystem, Camera camera, double power, BallSide ballSide, boolean reversed) {
    m_subsystem = subsystem;
    m_camera = camera;
    m_power = power;
    k_ballSide = ballSide;
    m_reversed = reversed;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_camera.toggleLights(true);
    m_seenBalls = false;
    Logger.Log("BallDriveCommand", 1, "Init");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    CameraData data = m_camera.createData();
    int numBalls = data.ballFilter().size();
    double powerDiff;

    System.out.println(numBalls);
    if (numBalls >= 2) {
      double diff = data.ballCenterDiff(data.centerLine(), data.ballSelector(k_ballSide));
      powerDiff = Math.abs(diff) * k_p;

      
    } else if (numBalls >= 1) {
      double diff = data.centerDiff(data.centerLine(), 0, data.ballFilter().get(0));
      powerDiff = Math.abs(diff) * k_p;

      // turn left
      if(!m_reversed){
        if (diff > 0) {
          m_subsystem.setPower(m_power - powerDiff, m_power + powerDiff);
        } else {
          m_subsystem.setPower(m_power + powerDiff, m_power - powerDiff);
        }
      }else{
        if (diff < 0) {
          m_subsystem.setPower(-m_power - powerDiff, -m_power + powerDiff);
        } else {
          m_subsystem.setPower(-m_power + powerDiff, -m_power - powerDiff);
        }
      }
    }

    // turn left
    if(!m_reversed){
      if (diff > 0) {
        m_subsystem.setPower(m_power - powerDiff, m_power + powerDiff);
      } else {
        m_subsystem.setPower(m_power + powerDiff, m_power - powerDiff);
      }
    }else{
      if (diff < 0) {
        m_subsystem.setPower(-m_power - powerDiff, -m_power + powerDiff);
      } else {
        m_subsystem.setPower(-m_power + powerDiff, -m_power - powerDiff);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
    m_camera.toggleLights(false);
    Logger.Log("BallDriveCommand", 1, "End");
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
