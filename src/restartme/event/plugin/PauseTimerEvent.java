package restartme.event.plugin;

import cn.nukkit.event.plugin.PluginEvent;
import restartme.RestartMe;

public class PauseTimerEvent extends PluginEvent{
    private boolean value;
    public PauseTimerEvent(RestartMe plugin, boolean value){
        super(plugin);
        this.value = value;
    }
    public boolean getValue(){
        return value;
    }
    public void setValue(boolean value){
        this.value = value;
    }
}