package com.darshak.formatter;

import com.darshak.util.Utils;

public class MobileNetworkCodeFormatter extends PacketFormatter {

	@Override
	public String formatBytes(byte[] extractedBytes) {
		StringBuilder mobileNetworkCode = new StringBuilder();
		for (int i = 0; i < extractedBytes.length; i++) {
			mobileNetworkCode.append(String.format("%02X ",
					Utils.swipeNibble(extractedBytes[i])));
		}
		return mobileNetworkCode.toString();
	}
}
