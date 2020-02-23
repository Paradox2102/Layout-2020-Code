/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitch;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TurretSubsystem extends SubsystemBase {
  CANSparkMax m_turret = new CANSparkMax(Constants.k_turret, MotorType.kBrushless);

  CANEncoder m_encoder = new CANEncoder(m_turret);

  CANDigitalInput m_softStop = new CANDigitalInput(m_turret, LimitSwitch.kForward, LimitSwitchPolarity.kNormallyOpen);

  double m_lastPower = 0;

  boolean m_rightStopped = false;
  boolean m_leftStopped = false;

  double k_encoderDeadzone = 5;

  public TurretSubsystem() {
    m_turret.setInverted(true);

    m_turret.setIdleMode(IdleMode.kBrake);

    m_encoder = m_turret.getEncoder();

    m_softStop.enableLimitSwitch(false);
    // m_turret.setSmartCurrentLimit(10);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Turret Vel", getVel());
    SmartDashboard.putBoolean("Turret Limit", m_softStop.get());
    SmartDashboard.putNumber("Turret Last Power", m_lastPower);
    SmartDashboard.putNumber("Turret Current", m_turret.getOutputCurrent());
    
    checkStops();

    if(m_rightStopped && m_lastPower > 0){
      stop();
    }else if(m_leftStopped && m_lastPower < 0){
      stop();
    }

    if(!m_softStop.get() && Math.abs(m_encoder.getPosition()) > k_encoderDeadzone){
      m_rightStopped = false;
      m_leftStopped = false;
    }

    if(m_softStop.get()){
      resetEncoder();
    }
  }

  public void checkStops(){
    if(m_softStop.get()){
      if(m_lastPower > 0 && !m_leftStopped){
        m_rightStopped = true;
      }else if (m_lastPower < 0 && !m_rightStopped){
        m_leftStopped = true;
      }
    }
  }

  public void setPower(double power){
    checkStops();

    if(m_rightStopped && power > 0){
      stop();
    }else if(m_leftStopped && power < 0){
      stop();
    }else{
      m_lastPower = power;
      m_turret.set(power);
    }
  }

  public void stop(){
    m_turret.set(0);
    m_lastPower = 0;
  }

  public boolean getLimit(){
    return m_softStop.get();
  }

  public double getPos(){
    return m_encoder.getPosition();
  }

  public double getVel(){
    return m_encoder.getVelocity();
  }

  public void resetEncoder(){
    m_encoder.setPosition(0);
  }
}
