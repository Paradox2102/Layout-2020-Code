/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Throat;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ThroatSubsystem;

public class ThroatPowerCommand extends CommandBase {
  /**
   * Creates a new ThroatPowerCommand.
   */
  ThroatSubsystem m_subsystem;
  double m_power;
  DoubleSupplier m_getVel;
  double m_rpmSpeed;
  double k_deadZone = 200;
  public ThroatPowerCommand(ThroatSubsystem subsystem, DoubleSupplier getVel, double rpmSpeed, double power) {
    m_subsystem = subsystem;
    m_power = power;
    m_getVel = getVel;
    m_rpmSpeed = rpmSpeed;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_getVel.getAsDouble() - k_deadZone > m_rpmSpeed){
      m_subsystem.setThroatPower(m_power);
    }else{
      m_subsystem.stopThroatPower();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.stopThroatPower();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
