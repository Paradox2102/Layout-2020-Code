/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  /**
   * Creates a new ShooterSubsystem.
   */
  TalonSRX m_shooter = new TalonSRX(Constants.k_shooter);
  TalonSRX m_shooterFollower = new TalonSRX(Constants.k_shooterFollower);

  double k_f = 0.024300;
  double k_p = 0.120000;
  double k_i = 0.001;

  int k_iRange = 100;
  int k_slot = 0;
  
  public ShooterSubsystem() {
    m_shooter.setInverted(true);
    m_shooterFollower.follow(m_shooter);
    m_shooterFollower.setInverted(true);

    m_shooter.config_kF(k_slot, k_f);
    m_shooter.config_kP(k_slot, k_p);
    m_shooter.config_kI(k_slot, k_i);
    m_shooter.config_kD(k_slot, 0);
    m_shooter.config_IntegralZone(k_slot, k_iRange);

    SmartDashboard.setDefaultNumber("shooter P", k_p);
    SmartDashboard.setDefaultNumber("shooter I", k_i);
    SmartDashboard.setDefaultNumber("Shooter F", k_f);
    SmartDashboard.setDefaultNumber("Izone", k_iRange);

    m_shooter.configOpenloopRamp(2);
    m_shooter.configClosedloopRamp(2);

    m_shooter.setSelectedSensorPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Speed", getSpeed());
    SmartDashboard.putNumber("Shooter Pos", getPos());
  }

  public void configPID(){
    k_p = SmartDashboard.getNumber("shooter P", k_p);
    k_i = SmartDashboard.getNumber("shooter I", k_i);
    k_f = SmartDashboard.getNumber("Shooter F", k_f);
    k_iRange = (int)(SmartDashboard.getNumber("Izone", k_iRange));
    
    m_shooter.config_kF(k_slot, k_f);
    m_shooter.config_kP(k_slot, k_p);
    m_shooter.config_kI(k_slot, k_i);
    m_shooter.config_kD(k_slot, 0);
    m_shooter.config_IntegralZone(k_slot, k_iRange);
  }

  public void setPower(double power){
    m_shooter.set(ControlMode.PercentOutput, power);
  }

  public void setSpeed(double speed){
    m_shooter.set(ControlMode.Velocity, speed);
  }

  public int getSpeed(){
    return m_shooter.getSelectedSensorVelocity();
  }

  public double getPos(){
    return m_shooter.getSelectedSensorPosition();
  }

  public void stop(){
    m_shooter.set(ControlMode.PercentOutput, 0);
  }
}
