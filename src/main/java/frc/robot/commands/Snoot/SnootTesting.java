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

public class SnootTesting extends CommandBase {
  /**
   * Creates a new SnootTesting.
   */
  private double m_startPosition = 0;
  private double m_currentPosition = 0;
  private double m_startRotations;
  private double m_currentRotations = 0;
  private double m_power;
  private SnootSubsystem m_subsystem;
  public SnootTesting(SnootSubsystem subsystem, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    m_power = power;
    addRequirements(m_subsystem);
    Logger.Log("SnootTesting", 2, "Constructed");
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Record starting position of Snoot motor and set starting rotation of Control Panel. Set Power
    m_subsystem.resetPos();
    m_startPosition = m_subsystem.getPos();
    m_startRotations = 0;
    m_currentPosition = m_startPosition;
    m_currentRotations = m_startRotations;
    

    m_subsystem.setPower(m_power);
    Logger.Log("SnootTesting", 2, "%f, m_power");
    Logger.Log("FixedRotationCommand", 2, "Initialized");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Record the position of the snoot motor and Control Panel rotations thus far. Then, place them onto Smart
    //Dashboard
    m_currentPosition = m_subsystem.getPos() - m_currentPosition;
    m_currentRotations = m_subsystem.getPosInRotations();

    SmartDashboard.putNumber("Snoot Encoder Position", m_currentPosition);
    SmartDashboard.putNumber("C.P. Rotations", m_currentRotations);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //stop snoot motor movement
    m_subsystem.stop();
    Logger.Log("SnootTesting", 2, "Ended");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
