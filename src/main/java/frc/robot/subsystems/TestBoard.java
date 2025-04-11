package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestBoard extends SubsystemBase{
    private final SparkMax m_testOne;
    private final SparkMax m_testTwo;
    private final SparkMaxConfig m_testOneConfig;
    private final SparkMaxConfig m_testTwConfig;

    private final DigitalInput m_switch;
    private final DigitalInput m_sensor;

    private final ADIS16448_IMU m_Gyro;
    private boolean isFlipped;
    private boolean useHeadingCorrection;
    private Rotation2d correctHeadingTargetHeading;
    private Timer correctHeadingTimer;
    private double correctHeadingPreviousTime;
    private double correctHeadingOffTime;
    private double heading;


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

        m_Gyro = new ADIS16448_IMU();
        isFlipped = false;
        useHeadingCorrection = true;
        correctHeadingTimer = new Timer();
        correctHeadingTimer.start();
        correctHeadingPreviousTime = 0.0;
        correctHeadingOffTime = 0.0;
        correctHeadingTargetHeading = getHeadingAsRotation2d();


    }


    
    public double getHeading(){
        heading = m_Gyro.getGyroAngleZ();
        //m_Gyro.getAngle(m_Gyro.getYawAxis()); ADIS16470 Version
        return Math.IEEEremainder(heading, 360);
    }

    public Rotation2d getHeadingAsRotation2d(){
        heading = getHeading();
        return Rotation2d.fromDegrees(heading);

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
        SmartDashboard.putNumber("gyro", m_Gyro.getGyroAngleZ());
    }

}

    

