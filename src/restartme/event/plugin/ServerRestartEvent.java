package restartme.event.plugin;

import cn.nukkit.event.plugin.PluginEvent;
import restartme.RestartMe;

public class ServerRestartEvent extends PluginEvent{
    private int mode;
    public ServerRestartEvent(RestartMe plugin, int mode){
        super(plugin);
        this.mode = mode;
    }
    public int getMode(){
        return this.mode;
    }
    public void setMode(int mode){
        this.mode = mode;
    }
}
