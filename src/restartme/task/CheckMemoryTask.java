package restartme.task;

import cn.nukkit.scheduler.PluginTask;
import restartme.utils.Utils;
import restartme.RestartMe;

public class CheckMemoryTask extends PluginTask<RestartMe>{
    private RestartMe plugin;
    public CheckMemoryTask(RestartMe plugin){
        super(plugin);
        this.plugin = plugin;
    }
    @Override
    public void onRun(int currentTick){
        if(!this.plugin.getTimer().isPaused()){
            if(Utils.isOverloaded(this.plugin.getMemoryLimit())){
                this.plugin.getTimer().initiateRestart(RestartMe.OVERLOADED);
            }
        }
    }
}