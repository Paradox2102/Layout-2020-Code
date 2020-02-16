/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Climber;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class MoveClimberCommand extends CommandBase {
  ClimberSubsystem m_subsystem;
  DoubleSupplier m_getY;

  double k_deadZone = .1;

  public MoveClimberCommand(ClimberSubsystem subsystem, DoubleSupplier getY) {
    m_subsystem = subsystem;
    m_getY = getY;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double y = m_getY.getAsDouble();

    if(Math.abs(y) < k_deadZone){
      m_subsystem.stop();
    }else{
      m_subsystem.setPower(y);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
