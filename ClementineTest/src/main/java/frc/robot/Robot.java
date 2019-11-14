/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private Joystick joystick;
  private Compressor compressor;
  private Solenoid[] solenoids;

  private CANSparkMax[] leftMotors;
  private CANSparkMax[] rightMotors;
  private static final int[] leftDeviceIDs = {1,2,3};
  private static final int[] rightDeviceIDs = {4,5,6};

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);


    this.joystick = new Joystick(0);
    this.compressor = new Compressor(21); // check
    this.compressor.start();
    this.leftMotors=new CANSparkMax[3];
    this.rightMotors=new CANSparkMax[3];


    this.solenoids = new Solenoid[4]; // check how many
    for(int i=0;i<this.solenoids.length;i++)
    {
      this.solenoids[i]=new Solenoid(i);
    }

    for(int i=0;i<3;i++) //check can ID's and reverses
    {

      leftMotors[i] = new CANSparkMax(leftDeviceIDs[i], MotorType.kBrushless);
      leftMotors[i].restoreFactoryDefaults();
      if(leftDeviceIDs[i]==2)
      {
        leftMotors[i].setInverted(!leftMotors[i].getInverted());
      }
      rightMotors[i] = new CANSparkMax(rightDeviceIDs[i], MotorType.kBrushless);
      rightMotors[i].restoreFactoryDefaults();      
      if(rightDeviceIDs[i]==6 ||rightDeviceIDs[i]==4)
      {
        rightMotors[i].setInverted(true);
      }

    }
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() 
  {
    double x = this.joystick.getX();
    double y = -1*this.joystick.getY();

    boolean b1 = this.joystick.getRawButton(1);

    double leftPower = (y+x)/2;
    double rightPower = (y-x)/2;

    for(CANSparkMax leftMotor:leftMotors) {leftMotor.set(leftPower);}
    for(CANSparkMax rightMotor:rightMotors) {rightMotor.set(rightPower);}
    leftMotors[1].set(leftPower);
    rightMotors[1].set(rightPower);
    if(b1) {this.solenoids[0].set(true);}
    else {this.solenoids[0].set(false);}
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
