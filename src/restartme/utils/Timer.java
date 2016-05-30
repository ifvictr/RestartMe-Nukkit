package restartme.utils;

import cn.nukkit.Player;
import restartme.event.plugin.PauseTimerEvent;
import restartme.event.plugin.ServerRestartEvent;
import restartme.event.plugin.SetTimeEvent;
import restartme.utils.Utils;
import restartme.RestartMe;

public class Timer{
    private RestartMe plugin;
    private int time;
    private boolean paused = false;
    public Timer(RestartMe plugin){
        this.plugin = plugin;
        this.time = plugin.getConfig().getInt("restartInterval") * 60;
    }
    public int getTime(){
        return this.time;
    }
    public void setTime(int seconds){
        this.plugin.getServer().getPluginManager().callEvent(new SetTimeEvent(this.plugin, this.getTime(), seconds));
        this.time = seconds;
    }
    public void addTime(int seconds){
        this.setTime(this.getTime() + seconds);
    }
    public void subtractTime(int seconds){
        this.setTime(this.getTime() - seconds);
    }
    public void initiateRestart(int mode){
        ServerRestartEvent event = new ServerRestartEvent(this.plugin, mode);
        this.plugin.getServer().getPluginManager().callEvent(event);
        switch(event.getMode()){
            case RestartMe.NORMAL:
                for(Player player : this.plugin.getServer().getOnlinePlayers().values()){
                    player.kick(this.plugin.getConfig().getString("quitMessage"), false);
                }
                this.plugin.getServer().getLogger().info(this.plugin.getConfig().getString("quitMessage"));
                break;
            case RestartMe.OVERLOADED:
                for(Player player : this.plugin.getServer().getOnlinePlayers().values()){
                    player.kick(this.plugin.getConfig().getString("overloadQuitMessage"), false);
                }
                this.plugin.getServer().getLogger().info(this.plugin.getConfig().getString("overloadQuitMessage"));
            break;
        }
        this.plugin.getServer().shutdown();
    }
    public boolean isPaused(){
        return this.paused == true;
    }
    public void setPaused(boolean value){
        PauseTimerEvent event = new PauseTimerEvent(this.plugin, value);
        this.plugin.getServer().getPluginManager().callEvent(event);
        this.paused = event.getValue();
    }
    public String getFormattedTime(){
        int[] time = Utils.toArray(this.getTime());
        return time[0]+" hr "+time[1]+" min "+time[2]+" sec";
    }
    public void broadcastTime(String message, String messageType){
        int[] time = Utils.toArray(this.getTime());
        String outMessage = message
            .replaceAll("\\{RESTART_FORMAT_TIME}", this.getFormattedTime())
            .replaceAll("\\{RESTART_HOUR}", Double.toString(time[0]))
            .replaceAll("\\{RESTART_MINUTE}", Double.toString(time[1]))
            .replaceAll("\\{RESTART_SECOND", Double.toString(time[2]))
            .replaceAll("\\{RESTART_TIME}", Integer.toString(this.getTime()));
        switch(messageType.toLowerCase()){
            case "chat":
                this.plugin.getServer().broadcastMessage(outMessage);
                break;
            case "popup":
                for(Player player : this.plugin.getServer().getOnlinePlayers().values()){
                    player.sendPopup(outMessage);
                }
                break;
        }
    }
}