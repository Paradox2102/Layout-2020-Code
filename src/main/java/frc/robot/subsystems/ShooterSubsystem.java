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
  TalonSRX m_shooterFollower = new TalonSRX(Constants.k_shooterFollower); //36000

  double k_f = Constants.m_pidTerms.k_shooterF;
  double k_p = Constants.m_pidTerms.k_shooterP;
  double k_i = Constants.m_pidTerms.k_shooterI;

  int k_iRange = Constants.m_pidTerms.k_shooterIRange;
  int k_slot = 0;

  double m_curSpeed = 0;
  boolean m_revved = false;
  
  public ShooterSubsystem() {
    m_shooter.setInverted(true);
    m_shooterFollower.follow(m_shooter);
    m_shooterFollower.setInverted(false);

    m_shooter.config_kF(k_slot, k_f);
    m_shooter.config_kP(k_slot, k_p);
    m_shooter.config_kI(k_slot, k_i);
    m_shooter.config_kD(k_slot, 0);
    m_shooter.config_IntegralZone(k_slot, k_iRange);

    SmartDashboard.setDefaultNumber("shooter P", k_p);
    SmartDashboard.setDefaultNumber("shooter I", k_i);
    SmartDashboard.setDefaultNumber("Shooter F", k_f);
    SmartDashboard.setDefaultNumber("Shooter Izone", k_iRange);

    m_shooter.configOpenloopRamp(2);
    m_shooter.configClosedloopRamp(2);

    m_shooter.setSelectedSensorPosition(0);
    m_shooter.setSensorPhase(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Speed", getSpeed());
    SmartDashboard.putNumber("Shooter Pos", getPos());
    SmartDashboard.putBoolean("Shooter Revved", isRevved());  
  }

  public void configPID(){
    k_p = SmartDashboard.getNumber("shooter P", k_p);
    k_i = SmartDashboard.getNumber("shooter I", k_i);
    k_f = SmartDashboard.getNumber("Shooter F", k_f);
    k_iRange = (int)(SmartDashboard.getNumber("Shooter Izone", k_iRange));
    
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
    m_curSpeed = speed;
    m_shooter.set(ControlMode.Velocity, speed);
    m_revved = true;
  }

  public int getSpeed(){
    return m_shooter.getSelectedSensorVelocity();
  }

  public double getPos(){
    return m_shooter.getSelectedSensorPosition();
  }

  public void stop(){
    m_curSpeed = 0;
    m_revved = false;
    m_shooter.set(ControlMode.PercentOutput, 0);
  }

  public double getSetpoint(){
    return m_curSpeed;
  }

  public double calculatedSpeed(double height){
    double speed = (3.956*height*height) - (577.432 * height) + (51611.080);

    if(speed > 40000){
      return 40000;
    }else if(speed < 30000){
      return 30000;
    }

    return speed;
  }

  public boolean isRevved(){
    return m_revved;
  }
}
