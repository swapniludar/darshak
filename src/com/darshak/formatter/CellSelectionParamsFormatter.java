package com.darshak.formatter;

import com.darshak.util.Utils;

/**
 * 
 * @author Swapnil Udar
 *
 */
public class CellSelectionParamsFormatter extends PacketFormatter {

	protected String formatBytes(byte[] extractedBytes) {
		if (extractedBytes == null || extractedBytes.length != 1) {
			return "Something wrong.";
		}
		// On 6 LS Bits are required.
		byte byt = (byte) (0x3F & extractedBytes[0]);
		return "RXLEV-ACCESS-MIN: "
				+ Utils.formatHexBytes(new byte[] { byt });
	}
}
