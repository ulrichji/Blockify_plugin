package blockify;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.inject.Inject;

@Plugin(id = "blockify", name = "Blockify", version = "0.0.1")
public class BlockifyPlugin
{
	private Logger logger;
	private ArrayList<BlockifiedPlayer> blockifiedPlayers = new ArrayList<BlockifiedPlayer>();
	@Inject
    private PluginContainer container;
	
	@Inject
	BlockifyPlugin(Logger logger)
	{
		this.logger = logger;
	}
	
	@Listener
    public void onServerStart(GameStartedServerEvent event)
	{
		CommandSpec myCommandSpec = CommandSpec.builder()
			    .description(Text.of("Blockify command"))
			    .permission("blockifyplugin.command.blockify")
			    .arguments(GenericArguments.optional(GenericArguments.player(Text.of("player"))))
			    .executor(new BlockifyCommandExecutor(this))
			    .build();
		
		Sponge.getCommandManager().register(this, myCommandSpec, "blockify");
		
		//Refresh updater
		Sponge.getScheduler().createTaskBuilder()
        .execute(new BlockifyUpdater())
        .intervalTicks(8)
        .submit(this.container);
		
        logger.info("BLockfy plugin is up running!");
    }
	
	public void blockifyPlayer(Player player)
	{
		for(BlockifiedPlayer bPlayer:blockifiedPlayers)
		{
			if(bPlayer.getPlayer().equals(player))
			{
				logger.info(player.getName() + " is already blockified");
				return;
			}
		}
		
		blockifiedPlayers.add(new BlockifiedPlayer(player));
		logger.info(player.getName()+" is blockified");
	}
	
	public void unblockifyPlayer(Player player)
	{
		for(BlockifiedPlayer bPlayer:blockifiedPlayers)
		{
			if(bPlayer.getPlayer().equals(player))
			{
				blockifiedPlayers.remove(bPlayer);
				return;
			}
		}
	}
	
	public Logger getLogger()
	{
		return logger;
	}
	
	private class BlockifyUpdater implements Runnable
	{
		public void run()
		{
			for(BlockifiedPlayer blockifiedPlayer:blockifiedPlayers)
			{
				double diffX = (Math.random() * 10) - 5;
				double diffY = (Math.random() * 10) - 5;
				double diffZ = (Math.random() * 10) - 5;
				
				Player player = blockifiedPlayer.getPlayer();
				World world = player.getWorld();
				Location<World> playerPos = player.getLocation();
				Location<World> worldLoc = new Location<World>(world,
						playerPos.getX() + diffX,
						playerPos.getY() + diffY,
						playerPos.getZ() + diffZ);
				
				worldLoc.setBlockType(BlockTypes.DIRT);
			}
		}
	}
}
