package project;

import java.util.HashMap;

import nodes.Alarm;
import nodes.Ammeter;
import nodes.Fan;
import nodes.Fog;
import nodes.Gas;
import nodes.Infrared;
import nodes.Keeper;
import nodes.Light;
import nodes.Node;
import nodes.NodeInfo;
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

        thisHash.put(Ammeter.getAmmeter().getName(), Ammeter.getAmmeter());
        thisHash.put(Temperature.getTemperature().getName(), Temperature.getTemperature());
        thisHash.put(Keeper.getKeeper().getName(), Keeper.getKeeper());
        thisHash.put(Light.getLight().getName(), Light.getLight());
        thisHash.put(Omnidirectional.getOmnidirectional().getName(), Omnidirectional.getOmnidirectional());
        thisHash.put(Raster.getRaster().getName(), Raster.getRaster());
        thisHash.put(Gas.getGas().getName(), Gas.getGas());
        thisHash.put(Fog.getFog().getName(), Fog.getFog());
        thisHash.put(Infrared.getInfrared().getName(), Infrared.getInfrared());
        thisHash.put(Alarm.getAlarm().getName(), Alarm.getAlarm());
        thisHash.put(Fan.getFan().getName(), Fan.getFan());

        return thisHash;
    }
}
