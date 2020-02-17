/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ThroatSubsystem extends SubsystemBase {
   private TalonSRX m_throatMotor = new TalonSRX(Constants.k_throat);   

   private DigitalInput m_dioTop = new DigitalInput(Constants.k_dioTop);
   private DigitalInput m_dioBottom = new DigitalInput(Constants.k_dioBottom);
   
   private DigitalInput m_test = new DigitalInput(4);
  public ThroatSubsystem() {
    m_throatMotor.setInverted(true);
      m_throatMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void setThroatPower(double throatPower){
    m_throatMotor.set(ControlMode.PercentOutput, throatPower);
  }

  public void stopThroatPower(){
    m_throatMotor.set(ControlMode.PercentOutput, 0);
  }

  // public boolean GetOutput(){     
  //   return m_dio.get();
  // }

  public boolean GetTopBreak(){
    return !m_dioTop.get();
  }

  public boolean GetBottomBreak(){
    return !m_test.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Linebreak Top Sensor Return State", m_dioTop.get());
    SmartDashboard.putBoolean("Linebreak Bottom Sensor Return State", m_dioBottom.get());
    SmartDashboard.putBoolean("Line break test", m_test.get());
  }
}
