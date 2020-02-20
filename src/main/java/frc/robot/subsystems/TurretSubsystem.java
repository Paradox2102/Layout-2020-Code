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

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TurretSubsystem extends SubsystemBase {
  CANSparkMax m_turret = new CANSparkMax(Constants.k_turret, MotorType.kBrushless);

  CANEncoder m_encoder = new CANEncoder(m_turret);

  CANDigitalInput m_softStop = new CANDigitalInput(m_turret, LimitSwitch.kForward, LimitSwitchPolarity.kNormallyClosed);

  public TurretSubsystem() {
    m_turret.setInverted(true);

    m_turret.setIdleMode(IdleMode.kBrake);

    m_encoder = m_turret.getEncoder();

    m_softStop.enableLimitSwitch(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setPower(double power){
    m_turret.set(power);
  }

  public void stop(){
    m_turret.set(0);
  }

  public boolean getLimit(){
    return m_softStop.get();
  }

  public double getPos(){
    return m_encoder.getPosition();
  }
}
