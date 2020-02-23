/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Serializer;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SerializerSubsystem;

public class SerializeCommand extends CommandBase {
  SerializerSubsystem m_subsystem;
  double m_power;
  BooleanSupplier m_stop;
  DoubleSupplier m_throttle;
  BooleanSupplier m_topLimit;

  public SerializeCommand(SerializerSubsystem subsystem, double power, BooleanSupplier stop, DoubleSupplier throttle, BooleanSupplier topLimit) {
    m_subsystem = subsystem;
    m_power = power;
    m_stop = stop;
    m_throttle = throttle;
    m_topLimit = topLimit;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // if(!m_stop.getAsBoolean()){
    // m_subsystem.setPower(m_power);
    // }else{
    // m_subsystem.stop();
    // }
    if (m_throttle.getAsDouble() > 0.5 && m_topLimit.getAsBoolean()) {
      m_subsystem.setPower(m_power);
    } else {
      m_subsystem.stop();
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
