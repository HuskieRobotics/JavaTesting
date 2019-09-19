package frc.robot;

public class DriveStateMachine
{
    private int state;
    /*
     * States:
     *  0 - joystick
     *  1 - vision
     *  2 - line following
     *  3 - turning
     */

    public DriveStateMachine()
    {
        state = 0;
    }

    public double[] getMotorPower(double joystickX, double joystickY, boolean[] buttons)
    {
        /**
         * temporary buttons?
         *  0 - joystick override
         *  1 - vision
         *  2 (while in vision) - line following
         *  3 - turning
         */

        double[] powers = {0,0}; // left then right
        switch(state)
        {
            case 1: // vision
                powers[0] = 0.7; // go forwards, temporary
                powers[1] = 0.7;
                if (!buttons[0]) state = 0;
                else if (buttons[2]) state = 2;
                break;
            case 2: // line following
                powers[0] = -0.7; // go backwards, temporary
                powers[1] = 0.7;
                if(!buttons[2]) state = 1;
                break;
            case 3: // turning
                powers[0] = -0.7; // turn left, temporary
                powers[1] = 0.7;
                if(!buttons[3]) state = 0;
                break;
            default: // joysticks
                double left = (joystickY-joystickX)/2;
                double right = (joystickY+joystickX)/2;
                powers[0] = left;
                powers[1] = right;
                if(buttons[1]) state = 1;
        }
        return powers;
    }
}