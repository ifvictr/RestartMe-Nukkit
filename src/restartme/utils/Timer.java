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
        return time;
    }
    public void setTime(int seconds){
        plugin.getServer().getPluginManager().callEvent(new SetTimeEvent(plugin, getTime(), seconds));
        this.time = seconds;
    }
    public void addTime(int seconds){
        setTime(getTime() + seconds);
    }
    public void subtractTime(int seconds){
        setTime(getTime() - seconds);
    }
    public void initiateRestart(int mode){
        ServerRestartEvent event = new ServerRestartEvent(plugin, mode);
        plugin.getServer().getPluginManager().callEvent(event);
        switch(event.getMode()){
            case RestartMe.NORMAL:
                for(Player player : plugin.getServer().getOnlinePlayers().values()){
                    player.kick(plugin.getConfig().getString("quitMessage"), false);
                }
                plugin.getServer().getLogger().info(plugin.getConfig().getString("quitMessage"));
                break;
            case RestartMe.OVERLOADED:
                for(Player player : plugin.getServer().getOnlinePlayers().values()){
                    player.kick(plugin.getConfig().getString("overloadQuitMessage"), false);
                }
                plugin.getServer().getLogger().info(plugin.getConfig().getString("overloadQuitMessage"));
            break;
        }
        plugin.getServer().shutdown();
    }
    public boolean isPaused(){
        return paused == true;
    }
    public void setPaused(boolean value){
        PauseTimerEvent event = new PauseTimerEvent(plugin, value);
        plugin.getServer().getPluginManager().callEvent(event);
        this.paused = event.getValue();
    }
    public String getFormattedTime(){
        int[] time = Utils.toArray(getTime());
        return time[0]+" hr "+time[1]+" min "+time[2]+" sec";
    }
    public void broadcastTime(String message, String messageType){
        int[] time = Utils.toArray(getTime());
        String outMessage = message
            .replaceAll("\\{RESTART_FORMAT_TIME}", getFormattedTime())
            .replaceAll("\\{RESTART_HOUR}", Double.toString(time[0]))
            .replaceAll("\\{RESTART_MINUTE}", Double.toString(time[1]))
            .replaceAll("\\{RESTART_SECOND", Double.toString(time[2]))
            .replaceAll("\\{RESTART_TIME}", Integer.toString(getTime()));
        switch(messageType.toLowerCase()){
            case "chat":
                plugin.getServer().broadcastMessage(outMessage);
                break;
            case "popup":
                for(Player player : plugin.getServer().getOnlinePlayers().values()){
                    player.sendPopup(outMessage);
                }
                break;
        }
    }
}