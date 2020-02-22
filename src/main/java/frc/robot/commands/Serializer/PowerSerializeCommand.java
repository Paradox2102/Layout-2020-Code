/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Serializer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Logger;
import frc.robot.subsystems.SerializerSubsystem;

public class PowerSerializeCommand extends CommandBase {
  SerializerSubsystem m_subsystem;
  double m_power;
  public PowerSerializeCommand(SerializerSubsystem subsystem, double power) {
    m_subsystem = subsystem;
    m_power = power;
    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Logger.Log("PowerSerializeCommand", 1 , "initialize");
    m_subsystem.setPower(m_power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Logger.Log("PowerSerializeCommand", -1 , "execute");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Logger.Log("PowerSerializeCommand", 1 , "end");
    m_subsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Logger.Log("PowerSerializeCommand", -1 , "isFinished");
    return false;
  }
}
