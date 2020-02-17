/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auto;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Logger;

public class WaitForDistanceCommand extends CommandBase {
  DoubleSupplier m_getX;
  DoubleSupplier m_getY;
  double m_targetX;
  double m_targetY;
  final static double k_tolerance = 2;
  public WaitForDistanceCommand(DoubleSupplier getX, DoubleSupplier getY, double targetX, double targetY) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_getX = getX;
    m_getY = getY;

    m_targetX = targetX;
    m_targetY = targetY;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("WaitForDistanceCommand", 1 , "initialize");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("WaitForDistanceCommand", -1 , "execute");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("WaitForDistanceCommand", 1 , "end");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("WaitForDistanceCommand", -1 , "isFinished");
    double x = m_getX.getAsDouble() - m_targetX;
    double y = m_getY.getAsDouble() - m_targetY;
    return x*x + y*y < k_tolerance*k_tolerance;
  }
}
