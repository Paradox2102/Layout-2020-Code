/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Add your docs here.
 */
public class DecreaseTrimTrigger extends Trigger {
  private final GenericHID m_input;

	public DecreaseTrimTrigger(final GenericHID input) {
		m_input = input;
  }
  
  @Override
  public boolean get() {
    return m_input.getPOV() == 180;
  }
}
