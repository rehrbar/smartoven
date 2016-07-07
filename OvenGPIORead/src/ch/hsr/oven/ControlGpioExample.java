package ch.hsr.oven;

import java.util.ArrayList;
import java.util.List;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.*;
/**
 * This example code demonstrates how to perform simple state
 * control of a GPIO pin on the Raspberry Pi.  
 * 
 * @author Robert Savage
 */
public class ControlGpioExample {
    
    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("<--Pi4J--> GPIO Control Example ... started.");
        
        //GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));      
        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();
        
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16, "MyLED", PinState.HIGH);

     // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
//        final GpioPinDigitalInput buttonRight = gpio.provisionDigitalInputPin(RaspiPin.GPIO_14, PinPullResistance.PULL_DOWN);
//        final GpioPinDigitalInput buttonDown = gpio.provisionDigitalInputPin(RaspiPin.GPIO_15, PinPullResistance.PULL_DOWN);
//        final GpioPinDigitalInput buttonLeft = gpio.provisionDigitalInputPin(RaspiPin.GPIO_13, PinPullResistance.PULL_DOWN);
//        final GpioPinDigitalInput buttonUp = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_DOWN);

        
        List<GpioPinDigitalInput> buttons = new ArrayList<GpioPinDigitalInput>();
        Pin[] list = {RaspiPin.GPIO_15, RaspiPin.GPIO_04, RaspiPin.GPIO_14, RaspiPin.GPIO_13};
        for(Pin p : list)
        {
        	
        	try{
        	GpioPinDigitalInput b = gpio.provisionDigitalInputPin(p, PinPullResistance.PULL_DOWN);
        	System.out.println("Test "+b.getName());
        	b.addListener(new GpioPinListenerDigital() {
                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    // display pin state on console
                    System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
                    	pin.pulse(300, true);
                    
                }
                
            });
        	buttons.add(b);
        	} catch(Exception e){
        		}
        }
        
        // create and register gpio pin listener

//        buttonRight.addListener(new GpioPinListenerDigital() {
//            @Override
//            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//                // display pin state on console
//                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
//                	pin.pulse(300, true);
//                
//            }
//            
//        });
//        buttonDown.addListener(new GpioPinListenerDigital() {
//            @Override
//            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//                // display pin state on console
//                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
//                	pin.pulse(300, true);
//                
//            }
//            
//        });
        
        // keep program running until user aborts (CTRL-C)
        for (;;) {
            Thread.sleep(500);
        }
    }
}
