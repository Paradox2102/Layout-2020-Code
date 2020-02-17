/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase {
  IntakeSubsystem m_subsystem;
  double m_power;
  long m_startTime;
  long m_time; //in seconds
  public IntakeCommand(IntakeSubsystem subsystem, double power) {
    m_subsystem = subsystem;
    m_power = power;
    m_time = 0;

    addRequirements(m_subsystem);
  }

  public IntakeCommand(IntakeSubsystem subsystem, double power, long time) {
    m_subsystem = subsystem;
    m_power = power;
    m_time = time;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_subsystem.setPower(m_power);
    m_startTime = System.currentTimeMillis();
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
    if(m_time == 0){
      return false;
    }
    return (System.currentTimeMillis() - m_startTime) > m_time * 1000;
  }
}
