/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Snoot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.PiCamera.Logger;
import frc.robot.subsystems.SnootSubsystem;
import frc.robot.subsystems.ThroatSubsystem;

public class FixedRotationCommand extends CommandBase {
  /**
   * Creates a new FixedRotationCommand.
   */
  private double m_startPosition = 0;
  private double m_setRotations = 0;
  private double m_power = 0;
  private double m_rotations = 0;

  private SnootSubsystem m_subsystem;
 
  public FixedRotationCommand(SnootSubsystem subsystem, double power, double rotations) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    m_power = power;
    m_rotations = rotations;
    addRequirements(m_subsystem);
    Logger.Log("FixedRotationCommand", 2, "Constructed");
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    //Get the starting position of the snoot motor, calculate it's distance goal in ticks based off of start pos
    //and takes in a set number of Control Panel Rotations. Then setPower
    m_startPosition = m_subsystem.getPosInRotations();
    m_setRotations = m_startPosition + m_rotations;
    m_subsystem.setPower(m_power);
    SmartDashboard.putNumber("Rotations C.P.", m_subsystem.getPosInRotations());

    Logger.Log("FixedRotationCommand", 2, "Initialized");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //nothing in execute, runs until goal is reached
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //stop power to motor and finish
    m_subsystem.stop();
    Logger.Log("FixedRotationCommand", 2, "Interrupted");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    //if distance goal reached
    if(m_setRotations <= m_subsystem.getPosInRotations()){
      Logger.Log("FixedRotationCommand", 2, "Finished");
      return true;
    }

    return false;
  }
}
