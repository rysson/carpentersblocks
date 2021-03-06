package carpentersblocks.tileentity;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import carpentersblocks.data.Torch;
import carpentersblocks.data.Torch.State;

public class TECarpentersTorch extends TEBase {

	@Override
	/**
	 * Torch events are handled server-side.  The client won't receive
	 * particle spawn events, and so must be handled by the client here.
	 * This simply handles spawning the big smoke particle when rain or snow
	 * lowers the torch state.
	 */
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		if (worldObj != null && worldObj.isRemote)
		{
			State existing_state = Torch.getState(this);

			super.onDataPacket(net, pkt);

			State new_state = Torch.getState(this);

			if (new_state.ordinal() > existing_state.ordinal())
			{
				double[] headCoords = Torch.getHeadCoordinates(this);
				worldObj.spawnParticle("largesmoke", headCoords[0], headCoords[1], headCoords[2], 0.0D, 0.0D, 0.0D);
			}
		}
	}

}
