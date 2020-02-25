/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Camera;
import frc.lib.Logger;
import frc.lib.Camera.BallSide;
import frc.lib.Camera.CameraData;
import frc.robot.subsystems.DriveSubsystem;

public class DriveToCenterBallsCommand extends CommandBase {
  DriveSubsystem m_driveSubsystem;
  Camera m_backCamera;
  double m_power;
  double k_p = 0.05;
  double k_deadZone = 50;

  public DriveToCenterBallsCommand(DriveSubsystem driveSubsystem, Camera backCamera, double power) {
    m_driveSubsystem = driveSubsystem;
    m_backCamera = backCamera;
    m_power = power;

    addRequirements(m_driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("DriveToCenterBallsCommand", 1 , "initialize");
    m_backCamera.toggleLights(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("DriveToCenterBallsCommand", -1 , "execute");
    CameraData data = m_backCamera.createData();

    if(data.canSeeMulti(2)){
      double offset = data.ballCenterDiff(data.centerLine(), data.ballSelector(BallSide.RIGHT));
      m_driveSubsystem.setPower(m_power - offset * k_p, m_power + offset * k_p);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("DriveToCenterBallsCommand", 1 , "end");
    m_backCamera.toggleLights(false);
    m_driveSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("DriveToCenterBallsCommand", -1 , "isFinished");
    CameraData data = m_backCamera.createData();

    if(data.canSeeMulti(2)){
      double offset = data.ballCenterDiff(data.centerLine(), data.ballSelector(BallSide.RIGHT));
      return offset < k_deadZone;
    }

    return true;
  }
}
