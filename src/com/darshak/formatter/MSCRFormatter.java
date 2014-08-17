package com.darshak.formatter;

/**
 * 
 * @author Swapnil Udar
 *
 */
public class MSCRFormatter extends PacketFormatter {

	protected String formatBytes(byte[] extractedBytes) {

		if (extractedBytes == null || extractedBytes.length != 1) {
			return "MSCR code should be just single byte.";
		}

		byte byt = extractedBytes[0];
		if (((byt & (byte) 0x80)) == (byte) 0x80) {
			return "MSC is Release '99 onwards";
		} else {
			return "MSC is Release '98 onwards";
		}
	}
}