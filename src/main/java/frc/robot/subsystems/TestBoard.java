package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;

public class TestBoard extends SubsystemBase{
    private final SparkMax m_testOne;
    private final SparkMax m_testTwo;
    private final SparkMaxConfig m_testOneConfig;
    private final SparkMaxConfig m_testTwConfig;
    private final DigitalInput m_switch;
    private final DigitalInput m_sensor;
    public TestBoard(){
        m_testOne = new SparkMax(10, MotorType.kBrushed);
        m_testTwo = new SparkMax(11, MotorType.kBrushed);
    
        m_testOneConfig = new SparkMaxConfig();
        m_testTwConfig = new SparkMaxConfig();

        m_testOneConfig.smartCurrentLimit(60);
        m_testOneConfig.inverted(false);
        m_testOneConfig.idleMode(IdleMode.kBrake);

        m_testTwConfig.smartCurrentLimit(60);
        m_testTwConfig.inverted(false);
        m_testTwConfig.idleMode(IdleMode.kCoast);

        m_testOne.configure(m_testOneConfig,null,null);
        m_testTwo.configure(m_testTwConfig,null,null);

        m_switch = new DigitalInput(0);
        m_sensor = new DigitalInput(1);
    }

    public void drive(){
        if (!m_switch.get()){
            m_testOne.set(0.5);
        }else{
            m_testOne.set(0);
        }
    }
    public Command runMotor(){
        return run(()->{drive();});
    }
    public void drive2(){
        if (!m_sensor.get()){
            m_testTwo.set(0.5);
        }else{
            m_testTwo.set(0);
        }
    }
    public Command runMotor2(){
        return run(()->{drive();});
    }
    @Override 
    public void periodic(){
        SmartDashboard.putBoolean("BBrake", m_sensor.get());
    }

}

    

