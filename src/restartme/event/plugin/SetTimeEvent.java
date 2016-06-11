package restartme.event.plugin;

import cn.nukkit.event.plugin.PluginEvent;
import restartme.RestartMe;

public class SetTimeEvent extends PluginEvent{
    private int oldTime;
    private int newTime;
    public SetTimeEvent(RestartMe plugin, int oldTime, int newTime){
        super(plugin);
        this.oldTime = oldTime;
        this.newTime = newTime;
    }
    public int getOldTime(){
        return oldTime;
    }
    public int getNewTime(){
        return newTime;
    }
}
