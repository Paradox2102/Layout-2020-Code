package frc.robot;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;

public class Sensor implements SensorData{
	private CANEncoder m_leftSparkEncoder;
	private CANEncoder m_rightSparkEncoder;
	private PigeonIMU m_gyro;
	private double k_ticksFoot;
	
	public Sensor(CANEncoder leftSparkEncoder, CANEncoder rightSparkEncoder, PigeonIMU gyro, double ticksFoot) {
		m_leftSparkEncoder = leftSparkEncoder;
		m_rightSparkEncoder = rightSparkEncoder;
		
		m_gyro = gyro;

		k_ticksFoot = ticksFoot;
	}
	
	public double getLeftEncoderPos() {
		return ticksToFeet(m_leftSparkEncoder.getPosition());
	}
	
	public double getRightEncoderPos() {
		return ticksToFeet(m_rightSparkEncoder.getPosition());
	}
	
	public double getLeftEncoderVel() {
		return -ticksToFeet(m_leftSparkEncoder.getVelocity()/60.0);
	}
	
	public double getRightEncoderVel() {
		return ticksToFeet(m_rightSparkEncoder.getVelocity()/60.0);
	}
	
	public double getAngle() {
		double[] data = new double[3];
		m_gyro.getYawPitchRoll(data);
		return data[0];
	}
		
	private double ticksToFeet(double ticks) {
		return ticks / k_ticksFoot;
	}
}
