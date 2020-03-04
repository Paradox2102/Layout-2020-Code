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

public class TurnToBallsCommand extends CommandBase {
  /**
   * Creates a new TurnToBallsCommand.
   */
  DriveSubsystem m_driveSubsystem;
  Camera m_frontCamera;
  double m_power;
  double k_deadZone = 50;

  public TurnToBallsCommand(DriveSubsystem driveSubsystem, Camera frontCamera, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveSubsystem = driveSubsystem;
    m_frontCamera = frontCamera;
    m_power = power;

    addRequirements(m_driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("TurnToBallsCommand", 2, "Initialized");
    m_frontCamera.toggleLights(true);
    m_driveSubsystem.setPower(m_power, -m_power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("TurnToBallsCommand", 2, "Ended");
    m_driveSubsystem.stop();
    m_frontCamera.toggleLights(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    CameraData data = m_frontCamera.createData();

    if (data.ballFilter().size() >= 2) {
      double offset = data.ballCenterDiff(data.centerLine(), data.ballSelector(BallSide.RIGHT));
      System.out.println(offset);
      return offset < k_deadZone;
    }

    return false;
  }
}
