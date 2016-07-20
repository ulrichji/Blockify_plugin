package blockify;

import org.spongepowered.api.entity.living.player.Player;

public class BlockifiedPlayer
{
	private Player player;
	private long lastTickBlock;
	
	public BlockifiedPlayer(Player player)
	{
		this.player = player;
		lastTickBlock = 0;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public long getTick()
	{
		return lastTickBlock;
	}
	
	public void blockify(long tick)
	{
		this.lastTickBlock = tick;
	}
}
