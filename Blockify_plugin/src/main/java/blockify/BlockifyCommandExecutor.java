package blockify;

import java.util.Collection;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class BlockifyCommandExecutor implements CommandExecutor
{
	private BlockifyPlugin plugin;
	public BlockifyCommandExecutor(BlockifyPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	public CommandResult execute(CommandSource src, CommandContext context) throws CommandException
	{
		Collection<Player> players = context.<Player>getAll("player");
		if(players.size() == 0)
		{
			//No players specified. The command should be run on self.
			if(src instanceof Player)
			{
				plugin.blockifyPlayer((Player)src);
			}
			else
			{
				plugin.getLogger().warn("No player was specified, and the command source cannot be blockified");
				CommandResult.empty();
			}
		}
		else
		{
			//Players specified. Run blockify on specified players
			for(Player player:players)
			{
				plugin.blockifyPlayer(player);
			}
		}
		return CommandResult.success();
	}

}
