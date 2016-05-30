package restartme.task;

import cn.nukkit.scheduler.PluginTask;
import restartme.RestartMe;

public class AutoBroadcastTask extends PluginTask<RestartMe>{
    private RestartMe plugin;
    public AutoBroadcastTask(RestartMe plugin){
        super(plugin);
        this.plugin = plugin;
    }
    @Override
    public void onRun(int currentTick){
        if(!this.plugin.getTimer().isPaused()){
            if(this.plugin.getTimer().getTime() >= this.plugin.getConfig().getInt("startCountdown")){
                this.plugin.getTimer().broadcastTime(this.plugin.getConfig().getString("broadcastMessage"), this.plugin.getConfig().getString("displayType"));
            }
        }
    }
}