package net.h31ix.anticheat.event;

import net.h31ix.anticheat.manage.AnimationManager;
import net.h31ix.anticheat.Anticheat;
import net.h31ix.anticheat.PlayerTracker;
import net.h31ix.anticheat.manage.VehicleManager;
import net.h31ix.anticheat.checks.LengthCheck;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class PlayerListener implements Listener {
    Anticheat plugin;
    AnimationManager am;
    VehicleManager vm;
    PlayerTracker tracker;
     
    public PlayerListener(Anticheat plugin)
    {
        this.plugin = plugin;
        this.am = plugin.am;
        this.vm = plugin.vm;
        this.tracker = plugin.tracker;
    }
    
    @EventHandler
    public void onPlayerAnimation(PlayerAnimationEvent event)
    {
        if(event.getAnimationType() == PlayerAnimationType.ARM_SWING)
        {
            am.logAnimation(event.getPlayer());
        }
    }
    
    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event)
    {
        if(event.getEntered() instanceof Player)
        {
            vm.logEnter((Player)event.getEntered());
        }
    }    
    
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        if(!plugin.lagged)
        {        
            plugin.cm.addChat(event.getPlayer());
        }        
    }
    
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event)
    {
        if(!plugin.lagged)
        {        
            plugin.cm.addChat(event.getPlayer());
        }
    }
    
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
        plugin.cm.clear(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        if(!plugin.lagged)
        {
            Player player = event.getPlayer();
            LengthCheck c = new LengthCheck(event.getFrom(), event.getTo());
            double xd = c.getXDifference();
            double zd = c.getZDifference();
            double yd = c.getYDifference();
            Block p1 = player.getLocation().getWorld().getBlockAt(player.getLocation());
            if(p1.isLiquid())
            {
                if (player.getVehicle() != null)
                {
                    if(xd > 2.0D || zd > 2.0D)
                    {
                        tracker.increaseLevel(player);
                        plugin.log(player.getName()+" is using a boat too fast! XSpeed="+xd+" ZSpeed="+zd);
                        event.setTo(event.getFrom().clone());
                    }
                }                
                else if(xd > 0.19D || zd > 0.19D)
                {
                    if(!player.isSprinting() && !player.isFlying())
                    {
                        tracker.increaseLevel(player);
                        plugin.log(player.getName()+" is walking too fast in water! XSpeed="+xd+" ZSpeed="+zd);
                        event.setTo(event.getFrom().clone());
                    }  
                }
                else
                {
                    if(xd > 0.3D || zd > 0.3D)
                    {
                        tracker.increaseLevel(player);
                        plugin.log(player.getName()+" is flying/sprinting too fast in water! XSpeed="+xd+" ZSpeed="+zd);
                        event.setTo(event.getFrom().clone());
                    }
                }                
            }
            else
            {
                if (player.getVehicle() != null)
                {
                    if(!vm.isEntering(player))
                    {
                        if(xd > 0.41D || zd > 0.41D)
                        {
                            tracker.increaseLevel(player);
                            plugin.log(player.getName()+" is using a vehicle too fast! XSpeed="+xd+" ZSpeed="+zd);
                            event.setTo(event.getFrom().clone());
                        }
                    }
                }               
                else if(player.isSneaking())
                {
                    if(xd > 0.17D || zd > 0.17D)
                    {
                        tracker.increaseLevel(player);
                        plugin.log(player.getName()+" is sneaking too fast! XSpeed="+xd+" ZSpeed="+zd);
                        event.setTo(event.getFrom().clone());
                        player.setSneaking(false);
                    }
                    else
                    {
                        tracker.decreaseLevel(player);
                    }
                }
                else if(xd > 0.32D || zd > 0.32D)
                {
                    if(!player.isSprinting() && !player.isFlying())
                    {
                        tracker.increaseLevel(player);
                        plugin.log(player.getName()+" is walking too fast! XSpeed="+xd+" ZSpeed="+zd);
                        event.setTo(event.getFrom().clone());
                    }              
                    else
                    {
                        if(xd > 0.62D || zd > 0.62D)
                        {
                            tracker.increaseLevel(player);
                            plugin.log(player.getName()+" is flying/sprinting too fast! XSpeed="+xd+" ZSpeed="+zd);
                            event.setTo(event.getFrom().clone());
                        }
                        else
                        {
                            tracker.decreaseLevel(player);
                        }
                    }
                }
                if(event.getFrom().getY() < event.getTo().getY())
                {
                    if(yd > 1D)
                    {
                        tracker.increaseLevel(player);
                        plugin.log(player.getName()+" is ascending too fast! YSpeed="+yd);
                        event.setTo(event.getFrom().clone());
                    }
                    else
                    {
                        tracker.decreaseLevel(player);
                    }
                } 
            }
        }
    } 
}