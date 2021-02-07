/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Logger;
import frc.robot.PositionTracker;
import frc.robot.PositionTracker.PositionContainer;
import frc.robot.subsystems.DriveSubsystem;

public class DriveByDistanceCommand extends CommandBase {
  DriveSubsystem m_subsystem;
  PositionContainer m_pos;
  double m_distance;
  double m_power;

  /**
   * Creates a new DriveByDistanceCommand.
   */
  public DriveByDistanceCommand(DriveSubsystem subsystem, double distance, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    m_power = power;
    m_distance = distance;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("DriveByDistanceCommand", 1, "initialize");

    m_pos = m_subsystem.getPos();
    m_subsystem.setPower(m_power, m_power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("DriveByDistanceCommand", -1, "execute");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("DriveByDistanceCommand", 1, "end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("DriveByDistanceCommand", -1, "isFinished");
    return (posDistance(m_pos, m_subsystem.getPos()) > m_distance);
  }

  private double posDistance(PositionContainer pos1, PositionContainer pos2) {
    return Math.sqrt((pos1.x - pos2.x) * (pos1.x - pos2.x) + (pos1.y - pos2.y) * (pos1.y - pos2.y));
  }
}
