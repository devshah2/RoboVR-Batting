/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import frc.robot.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.battingCommand;

/**
 * Add your docs here.
 */
public class battingSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  Spark bat=new Spark(RobotMap.motorPort);
  Encoder batEnc=new Encoder(RobotMap.encoderPorts[0], RobotMap.encoderPorts[1]);
  boolean forwardUnlim=false;
  boolean backwardUnlim=false;
  @Override
  public void initDefaultCommand() {
    
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new battingCommand());
  }

  public void init(){
    batEnc.reset();
    bat.set(0);
  }

  public void moveBat(double speed){
    if(speed>0 && batEnc.get()<RobotMap.maxValue) bat.set(speed);
    else if(speed<0 && batEnc.get()>0) bat.set(speed);
    else{
      bat.set(0);
      forwardUnlim=false;
      backwardUnlim=false;
    }
  }
  double Deadband(double value) {
    if (value >= +0.2)
      return value;
    if (value <= -0.2)
      return value;
    return 0;
  }

  public void run(){
    if(forwardUnlim) moveBat(RobotMap.speed); 
    else if(backwardUnlim) moveBat(-RobotMap.speed);
    else if(OI.stick.getRawButton(RobotMap.buttonForTakeBatToMax)) forwardUnlim=true;
    else if(OI.stick.getRawButton(RobotMap.buttonForTakeBatToMax)) backwardUnlim=true;
    else moveBat(Deadband(OI.stick.getRawAxis(RobotMap.axisForMovement)));
  }
}
