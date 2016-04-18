package project;

import java.util.HashMap;

import nodes.Alarm;
import nodes.CO2;
import nodes.Fan;
import nodes.Infrared;
import nodes.IrrigationFirst;
import nodes.IrrigationSecond;
import nodes.Light;
import nodes.Node;
import nodes.PM25In;
import nodes.Soil;
import nodes.Temperature;

/**
 *
 * Created by jiyangkang on 2016/4/11 0011.
 */
public class SmartAGJoinProject implements JoinProject {

    @Override
    public void joinProject(HashMap<String, Node> thisHash) {
        thisHash.put(Temperature.getTemperature().getName(), Temperature.getTemperature());
        thisHash.put(Light.getLight().getName(), Light.getLight());
        thisHash.put(Soil.getSoil().getName(), Soil.getSoil());
        thisHash.put(CO2.getCo2().getName(), CO2.getCo2());
        thisHash.put(PM25In.getPM25In().getName(), PM25In.getPM25In());
        thisHash.put(Infrared.getInfrared().getName(), Infrared.getInfrared());
        thisHash.put(IrrigationFirst.getIrrigationFirst().getName(),
                IrrigationFirst.getIrrigationFirst());
        thisHash.put(IrrigationSecond.getIrrigationSecond().getName(),
                IrrigationSecond.getIrrigationSecond());
        thisHash.put(Alarm.getAlarm().getName(), Alarm.getAlarm());
        thisHash.put(Fan.getFan().getName(), Fan.getFan());
    }
}
