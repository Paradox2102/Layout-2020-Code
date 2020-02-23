/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.PiCamera.Logger;
import frc.robot.subsystems.DriveSubsystem;

public class TinyTurnCommand extends CommandBase {
  /**
   * Creates a new TinyTurnCommand.
   */
  DriveSubsystem m_subsystem;
  long timer;
  double k_power = 0.4;
  int k_timeOut = 500;

  public TinyTurnCommand(DriveSubsystem subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    Logger.Log("TinyTurnCommand", 2, "Constructed");

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("TinyTurnCommand", 2, "Initialized");
    timer = System.currentTimeMillis();
    m_subsystem.setPower(0, k_power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("TinyTurnCommand", 2, "Ended");
    m_subsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() > timer + k_timeOut;
  }
}
