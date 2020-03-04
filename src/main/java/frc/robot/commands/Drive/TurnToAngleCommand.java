/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Logger;
import frc.robot.subsystems.DriveSubsystem;

public class TurnToAngleCommand extends CommandBase {
  DriveSubsystem m_subsystem;
  double m_angle;
  Direction k_dir;
  double m_power;
  double k_deadzone = 10;

  public static enum Direction {
    LEFT, RIGHT
  }
  public TurnToAngleCommand(DriveSubsystem driveSubsystem, double angle, Direction direction, double power) {
    m_subsystem = driveSubsystem;
    m_angle = angle;
    k_dir = direction;
    m_power = power;
    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("TurnToAngleCommand", 1 , "initialize");

    if(k_dir.equals(Direction.RIGHT)){
      m_subsystem.setPower(m_power, -m_power);
    }else if(k_dir.equals(Direction.LEFT)){
      m_subsystem.setPower(-m_power, m_power);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("TurnToAngleCommand", -1 , "execute");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("TurnToAngleCommand", 1 , "end");
    
    m_subsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("TurnToAngleCommand", -1 , "isFinished");

    return Math.abs(m_subsystem.getAngle() - m_angle) < k_deadzone;
  }
}
