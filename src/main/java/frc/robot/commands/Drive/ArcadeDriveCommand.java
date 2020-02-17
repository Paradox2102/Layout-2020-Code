/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class ArcadeDriveCommand extends CommandBase {
  /**
   * Creates a new ArcadeDriveCommand.
   */
  DriveSubsystem m_subsystem;
  DoubleSupplier m_getX;
  DoubleSupplier m_getY;
  DoubleSupplier m_getThrottle;
  public ArcadeDriveCommand(DriveSubsystem subsystem, DoubleSupplier getX, DoubleSupplier getY, DoubleSupplier getThrottle) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    m_getX = getX;
    m_getY = getY;
    m_getThrottle = getThrottle;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double x = m_getX.getAsDouble();
    double y = m_getY.getAsDouble();

    // System.out.println("Arcade Drive Execute Running");

    x = x * x * x;
    y = y * y * y;

    if(m_getThrottle.getAsDouble() > 0){
      y *= -1;
    }

    m_subsystem.setPower(y+x, y-x);
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
