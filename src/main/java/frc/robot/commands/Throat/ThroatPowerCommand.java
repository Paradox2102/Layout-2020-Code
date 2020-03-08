/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Throat;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Camera;
import frc.lib.Camera.CameraData;
import frc.robot.subsystems.ThroatSubsystem;

public class ThroatPowerCommand extends CommandBase {
  /**
   * Creates a new ThroatPowerCommand.
   */
  ThroatSubsystem m_subsystem;
  double m_power;
  DoubleSupplier m_getVel;
  DoubleSupplier m_rpmSpeed;
  double k_deadZoneSpeed = 200;
  double k_deadZoneX = 0;
  Camera m_turretCamera = null;
  DoubleSupplier m_offset;
  boolean m_burst = false;

  public ThroatPowerCommand(ThroatSubsystem subsystem, DoubleSupplier getVel, DoubleSupplier rpmSpeed, double power, boolean burst) {
    m_subsystem = subsystem;
    m_power = power;
    m_getVel = getVel;
    m_rpmSpeed = rpmSpeed;
    k_deadZoneX = 0;
    m_offset = () -> 0;
    m_turretCamera = null;
    m_burst = burst;

    addRequirements(m_subsystem);
  }

  public ThroatPowerCommand(ThroatSubsystem subsystem, DoubleSupplier getVel, DoubleSupplier rpmSpeed, double power,
      double deadzone, Camera turretCamera, DoubleSupplier offset, boolean burst) {
    m_subsystem = subsystem;
    m_power = power;
    m_getVel = getVel;
    m_rpmSpeed = rpmSpeed;
    k_deadZoneX = deadzone;
    m_turretCamera = turretCamera;
    m_offset = offset;
    m_burst = burst;

    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_getVel.getAsDouble() - k_deadZoneSpeed > (m_burst ? 0 : m_rpmSpeed.getAsDouble()) && m_rpmSpeed.getAsDouble() > 0) {
      // turret position
      if (k_deadZoneX != 0) {
        CameraData data = m_turretCamera.createData();

        if (data.canSee()) {
          if (data.centerDiff(data.centerLine(), m_offset.getAsDouble()) < k_deadZoneX) {
            m_subsystem.setThroatPower(m_power);
          } else {
            // System.out.println("Turret not aligned");
            m_subsystem.stopThroatPower();
          }
        }
      } else {
        m_subsystem.setThroatPower(m_power);
      }
    } else {
      // System.out.println("Speed not Met");
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
