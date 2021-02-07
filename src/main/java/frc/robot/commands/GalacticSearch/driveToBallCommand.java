/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.GalacticSearch;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.PiCamera.PiCamera.PiCameraRegion;
import frc.lib.Camera;
import frc.lib.Logger;
import frc.lib.Camera.CameraData;
import frc.robot.subsystems.DriveSubsystem;

public class driveToBallCommand extends CommandBase {

  Camera m_camera;
  DriveSubsystem m_subsystem;

  double k_turningFactor = 0.0004;
  double m_power;
  int lowerBound = 450;

  boolean finished;

  /**
   * Creates a new driveToBallCommand.
   */
  public driveToBallCommand(Camera camera, DriveSubsystem subsystem, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_camera = camera;
    m_subsystem = subsystem;
    m_power = -power;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("driveToBallCommand", 1, "initialize");
    finished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("driveToBallCommand", -1, "execute");
    CameraData cameraData = m_camera.createData();

    if (cameraData.canSee()) {
      ArrayList<PiCameraRegion> regions = cameraData.ballFilter();
      if (!regions.isEmpty()) {
        // PiCameraRegion region = regions.get(0);
        PiCameraRegion region = cameraData.findClosestRegion();
        double centerDiff = cameraData.centerDiff(cameraData.centerLine(), 0.0, region);

        m_subsystem.setPower(m_power - (k_turningFactor * centerDiff), m_power + (k_turningFactor * centerDiff));

        if (region.m_bounds.m_bottom >= lowerBound) {
          finished = true;
          Logger.Log("finish", 1, "" + finished);
        }
      } else {
        m_subsystem.setPower(0, 0);
      }
    } else {
      m_subsystem.setPower(0, 0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("driveToBallCommand", 1, "end");
    // m_subsystem.setPower(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("driveToBallCommand", -1, "isFinished");
    return finished;
  }
}
