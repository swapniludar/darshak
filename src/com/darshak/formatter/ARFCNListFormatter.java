package com.darshak.formatter;

import com.darshak.util.Utils;

/**
 * 
 * @author Swapnil Udar
 *
 */
public class ARFCNListFormatter extends PacketFormatter {

	@Override
	public String formatBytes(byte[] extractedBytes) {
		return Utils.formatHexBytes(extractedBytes);
	}
}
