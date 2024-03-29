/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  TalonSRX m_intake = new TalonSRX(Constants.k_intake);

  Solenoid m_intakeDeploy = new Solenoid(Constants.k_intakeDeploy);
  
  public IntakeSubsystem() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setPower(double power){
    setDeploy(true);
    m_intake.set(ControlMode.PercentOutput, power);
  }

  public void setAmbientPower(double power){
    m_intake.set(ControlMode.PercentOutput, power);
  }

  public void stop(){
    setDeploy(false);
    m_intake.set(ControlMode.PercentOutput, 0);
  }

  public void setDeploy(boolean deploy){
    m_intakeDeploy.set(deploy);
  }
}
