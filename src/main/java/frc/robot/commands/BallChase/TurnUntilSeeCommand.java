/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BallChase;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.PiCamera.PiCamera.PiCameraRegion;
import frc.lib.Camera;
import frc.lib.Logger;
import frc.lib.Camera.CameraData;
import frc.robot.subsystems.DriveSubsystem;

public class TurnUntilSeeCommand extends CommandBase {
  Camera m_camera;
  DriveSubsystem m_subsystem;
  double m_power;
  Direction k_dir;

  boolean finished = false;;

  /**
   * Creates a new TurnUntilSeeCommand.
   */
  public static enum Direction {
    LEFT, RIGHT
  }

  public TurnUntilSeeCommand(Camera camera, DriveSubsystem subsystem, Direction direction, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_camera = camera;
    m_subsystem = subsystem;
    m_power = power;
    k_dir = direction;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("TurnUntilSeeCommand", 1, "initialize");

    if (k_dir.equals(Direction.RIGHT)) {
      m_subsystem.setPower(m_power, -m_power);
    } else if (k_dir.equals(Direction.LEFT)) {
      m_subsystem.setPower(-m_power, m_power);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("TurnUntilSeeCommand", -1, "execute");
    CameraData cameraData = m_camera.createData();
    ArrayList<PiCameraRegion> regions = cameraData.ballFilter();

    if (regions.get(0) != null) {
      finished = true;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("TurnUntilSeeCommand", 1, "end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("TurnUntilSeeCommand", -1, "isFinished");
    return finished;
  }
}
