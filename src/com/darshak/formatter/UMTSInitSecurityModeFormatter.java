package com.darshak.formatter;

import static com.darshak.constants.PacketAttributeType.NW_OP_USING_UEA0;
import static com.darshak.constants.PacketAttributeType.NW_OP_USING_UEA1;
import static com.darshak.util.Utils.formatHexBytes;

import com.darshak.constants.PacketType;
import com.darshak.modal.Packet;
import com.darshak.modal.PacketAttribute;

public class UMTSInitSecurityModeFormatter extends PacketFormatter {

	@Override
	public Packet formatPacket(byte[] packetBytes) {
		String hexCode = formatHexBytes(packetBytes);
		Packet packet = new Packet(PacketType._3G_INIT_SERV_REQ, hexCode);

		byte[] encAlgoBytes = extract(packetBytes, 6, 7);
		if (encAlgoBytes[0] == (byte) 0x4a) {
			packet.addPacketAttribute(new PacketAttribute(NW_OP_USING_UEA1,
					hexCode, NW_OP_USING_UEA1.getInfo()));
		} else {
			packet.addPacketAttribute(new PacketAttribute(NW_OP_USING_UEA0,
					hexCode, NW_OP_USING_UEA0.getInfo()));
		}
		return packet;
	}
}