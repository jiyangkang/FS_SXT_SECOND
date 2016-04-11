package project;

import java.util.HashMap;

import nodes.Alarm;
import nodes.Fan;
import nodes.Infrared;
import nodes.IrrigationFirst;
import nodes.IrrigationSecond;
import nodes.Light;
import nodes.Node;
import nodes.PM25In;
import nodes.PM25Out;
import nodes.Soil;
import nodes.Temperature;
import nodes.WindSpeed;

/**
 *
 * Created by jiyangkang on 2016/4/11 0011.
 */
public class SmartAGJoinProject implements JoinProject {
    @Override
    public HashMap<String, Node> joinProject() {
        HashMap<String, Node> thisHash = new HashMap<>();

        Temperature temperature = Temperature.getTemperature();
        Light light = Light.getLight();
        WindSpeed windSpeed = WindSpeed.getWindSpeed();
        Soil soil = Soil.getSoil();
        PM25In pm25In = PM25In.getPM25In();
        PM25Out pm25Out = PM25Out.getPM25Out();
        Infrared infrared = Infrared.getInfrared();
        IrrigationFirst irrigationFirst = IrrigationFirst.getIrrigationFirst();
        IrrigationSecond irrigationSecond = IrrigationSecond.getIrrigationSecond();
        Alarm alarm = Alarm.getAlarm();
        Fan fan = Fan.getFan();

        thisHash.put(temperature.getName(), temperature);
        thisHash.put(light.getName(), light);
        thisHash.put(windSpeed.getName(), windSpeed);
        thisHash.put(soil.getName(), soil);
        thisHash.put(pm25In.getName(), pm25In);
        thisHash.put(pm25Out.getName(), pm25Out);
        thisHash.put(infrared.getName(), infrared);
        thisHash.put(irrigationFirst.getName(), irrigationFirst);
        thisHash.put(irrigationSecond.getName(), irrigationSecond);
        thisHash.put(alarm.getName(), alarm);
        thisHash.put(fan.getName(), fan);


        return thisHash;
    }
}
