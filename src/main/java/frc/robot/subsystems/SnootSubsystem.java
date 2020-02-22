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
  
  //measures and constants
  private double k_ticksFootSpark = 1;
  private double k_snootWheelRadius = 1.9375; 
  private double k_ticksToRotations = 1;

  public SnootSubsystem() {
    m_encoder = m_snoot.getEncoder();
    m_snoot.setSmartCurrentLimit(25);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private double feetToTicks(double feet) {
    return feet * k_ticksFootSpark;
  }

  public void setPower(double power){
    m_snoot.set(power);
  }

  // //takes in a certain number of desired control panel rotations (as a real number) and translates it into 
  // //ticks for the motor attatched to the snoot wheel of certain circumfrance
  // public double rotationsToTicks(double rotations){
  //   return rotations*k_rotationsToTicks;
  // }

  public void stop(){
    m_snoot.set(0);
  }

  //return position in number of 
  public double getPos(){
    return m_encoder.getPosition();
  }

  public double getPosInRotations(){
    return m_encoder.getPosition() * k_ticksToRotations;
  }

  public void resetPos(){
    m_encoder.setPosition(0);
  }
}
