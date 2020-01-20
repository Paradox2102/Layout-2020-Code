package frc.robot;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;

public class Sensor implements SensorData{
	private CANEncoder m_leftSparkEncoder;
	private CANEncoder m_rightSparkEncoder;
	private PigeonIMU m_gyro;
	
	public Sensor(CANEncoder leftSparkEncoder, CANEncoder rightSparkEncoder, PigeonIMU gyro) {
		m_leftSparkEncoder = leftSparkEncoder;
		m_rightSparkEncoder = rightSparkEncoder;
		
		m_gyro = gyro;
	}
	
	public double getLeftEncoderPos() {
		return m_leftSparkEncoder.getPosition();
	}
	
	public double getRightEncoderPos() {
		return m_rightSparkEncoder.getPosition();
	}
	
	public double getLeftEncoderVel() {
		return -m_leftSparkEncoder.getVelocity();
	}
	
	public double getRightEncoderVel() {
		return m_rightSparkEncoder.getVelocity();
	}
	
	public double getAngle() {
		double[] data = new double[3];
		m_gyro.getYawPitchRoll(data);
		return data[0];
	}
}
