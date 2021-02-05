/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BallChase;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.lib.Camera;
import frc.lib.Logger;
import frc.lib.Camera.CameraData;
import frc.robot.subsystems.DriveSubsystem;

public class PathChooserCommand extends InstantCommand {
  /**
   * Creates a new PathChooserCommand.
   */
  Camera m_camera;
  DriveSubsystem m_subsystem;

  public PathChooserCommand(Camera camera, DriveSubsystem subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_camera = camera;
    m_subsystem = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("PathChooserCommand", 1, "initialize");
    CameraData cameraData = m_camera.createData();

    if (cameraData.canSee()) {
      int positionY = cameraData.m_regions.GetRegion(0).m_bounds.m_top;
      Logger.Log("PositionY:", 1, "" + positionY);
      if (positionY > 120) {
        Logger.Log("Path Chooser", 1, "Choosing path 1");
        new closePathAuto(m_camera, m_subsystem).schedule();
      } else {
        Logger.Log("Path Chooser", 1, "Choosing Path 2");
        new farPathAuto(m_camera, m_subsystem).schedule();
      }
    }
  }
}
