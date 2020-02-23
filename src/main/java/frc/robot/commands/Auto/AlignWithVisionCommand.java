/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.PiCamera.Logger;
import frc.lib.Camera;
import frc.lib.Camera.CameraData;
import frc.robot.subsystems.DriveSubsystem;

public class AlignWithVisionCommand extends CommandBase {
  /**
   * Creates a new AlignWithVisionCommand.
   */
  DriveSubsystem m_subsystem;
  Camera m_camera;
  double m_power;
  int k_deadZone = 200;
  long m_timer;
  int k_timeOut = 500;

  public AlignWithVisionCommand(DriveSubsystem subsystem, Camera camera, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    m_camera = camera;
    m_power = power;

    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("AlignWithVisionCommand", 2, "Initialized");
    m_timer = System.currentTimeMillis();
    m_camera.toggleLights(true);
    m_subsystem.setPower(-m_power, m_power);
    //m_camera.StartPiLog();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    CameraData data = m_camera.createData();
    if (System.currentTimeMillis() > m_timer + k_timeOut) {
      // m_camera.EndPiLog();
      // m_camera.DumpFrames(100);
      Logger.Log("AlignWithVisionCommand", 2, "Timed Out");
      return true;
    } else if (data.canSee()) {
      return Math.abs(data.centerDiff(data.centerLine())) < k_deadZone;
    } else {
      return false;
    }
  }
}
