/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SnootSubsystem extends SubsystemBase {
  CANSparkMax m_snoot = new CANSparkMax(Constants.k_snoot, MotorType.kBrushless);

  CANEncoder m_encoder = new CANEncoder(m_snoot);
  
  public SnootSubsystem() {
    m_encoder = m_snoot.getEncoder();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setPower(double power){
    m_snoot.set(power);
  }

  public void stop(){
    m_snoot.set(0);
  }

  public double getPos(){
    return m_encoder.getPosition();
  }
}
