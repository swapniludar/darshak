package com.darshak.formatter;

import java.util.ArrayList;
import java.util.List;

import com.darshak.modal.PacketAttribute;
import com.darshak.util.Utils;

/**
 * 
 * @author Swapnil Udar
 * 
 */
public class CellOptionsFormatter extends PacketFormatter {
	public List<PacketAttribute> format(byte[] extractedBytes, int kind,
			String infoText) {
		List<PacketAttribute> packetAttributes = new ArrayList<PacketAttribute>();
		if (extractedBytes == null || extractedBytes.length != 1) {
			return packetAttributes;
		}
		String hexCode = Utils.formatHexBytes(extractedBytes);
		String displayText;
		byte byt = extractedBytes[0];
		if (((byt & (byte) 0x40)) == (byte) 0x40) {
			displayText = "PWRC: True";
		} else {
			displayText = "PWRC: False";
		}
		packetAttributes.add(new PacketAttribute(kind, hexCode, displayText));

		byt = (byte) (byt & 0x0F);
		displayText = "Radio Link Timeout: " + (int) byt;
		packetAttributes.add(new PacketAttribute(kind, hexCode, displayText));

		return packetAttributes;
	}
}
