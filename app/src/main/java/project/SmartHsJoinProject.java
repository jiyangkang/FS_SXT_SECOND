package project;

import java.util.HashMap;

import nodes.Alarm;
import nodes.Ammeter;
import nodes.Fan;
import nodes.Fog;
import nodes.Gas;
import nodes.Infrared;
import nodes.Light;
import nodes.Node;
import nodes.Omnidirectional;
import nodes.Raster;
import nodes.Temperature;

/**
 *
 * Created by jiyangkang on 2016/4/11 0011.
 */
public class SmartHsJoinProject implements JoinProject{
    @Override
    public HashMap<String, Node> joinProject() {
        HashMap<String, Node> thisHash = new HashMap<>();

        Ammeter ammeter = Ammeter.getAmmeter();
        Temperature temperature = Temperature.getTemperature();
        Light light = Light.getLight();
        Omnidirectional omnidirectional = Omnidirectional.getOmnidirectional();
        Raster raster= Raster.getRaster();
        Gas gas = Gas.getGas();
        Fog fog = Fog.getFog();
        Infrared infrared = Infrared.getInfrared();
        Alarm alarm = Alarm.getAlarm();
        Fan fan = Fan.getFan();


        thisHash.put(ammeter.getName(), ammeter);
        thisHash.put(temperature.getName(), temperature);
        thisHash.put(light.getName(), light);
        thisHash.put(omnidirectional.getName(),omnidirectional);
        thisHash.put(raster.getName(), raster);
        thisHash.put(gas.getName(), gas);
        thisHash.put(fog.getName(), fog);
        thisHash.put(infrared.getName(), infrared);
        thisHash.put(alarm.getName(), alarm);
        thisHash.put(fan.getName(), fan);

        return thisHash;
    }
}
