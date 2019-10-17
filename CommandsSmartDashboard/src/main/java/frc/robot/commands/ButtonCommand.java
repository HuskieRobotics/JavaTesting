package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;


public class ButtonCommand extends Command{


    public void initialize()
    {
    }

    public void execute()
    {
        Robot.joystickDrive = false;
        for(int i = 0;i<Robot.leftTalons.length;i++)
        {
      Robot.leftTalons[i].set(ControlMode.PercentOutput,1);
      Robot.rightTalons[i].set(ControlMode.PercentOutput,1);
        }
    }

    public boolean isFinished()
    {
        return !Robot.joystick.getRawButton(3);
    }

    public void end()
    {
        Robot.joystickDrive = true;
    }
}