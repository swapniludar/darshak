package com.darshak.formatter;

import com.darshak.util.Utils;

public class MobileCountryCodeFormatter extends PacketFormatter {

	protected String formatBytes(byte[] extractedBytes) {
		StringBuilder mobCountryCode = new StringBuilder();
		for (int i = 0; i < extractedBytes.length; i++) {
			mobCountryCode.append(String.format("%02X ",
					Utils.swipeNibble(extractedBytes[i])));
		}
		String result = mobCountryCode.toString();
		result = result.replace(" ", "");
		mobCountryCode.setLength(0);
		// Ignore last character
		return result.substring(0, result.length() - 1);
	}
}