package com.darshak.constants;

import java.util.List;

import com.darshak.formatter.PacketFormatter;
import com.darshak.modal.PacketAttribute;

/**
 * 
 * @author Swapnil Udar & Ravishankar Borgaonkar
 *
 */
public enum PacketAttributeType {
	TMSI_NUM(0, "TMSI Number", Constants.HEX_FORMATTER),
	RND_NUM(1, "RANDOM Number", Constants.HEX_FORMATTER),
	
	A51_AVLBLE_ON_MOBILE(2, "Encryption algorithm A5/1 is available on mobile device.", Constants.NULL_FORMATTER),
	A51_NT_AVLBLE_ON_MOBILE(3, "Encryption algorithm A5/1 is not available on mobile device.", Constants.NULL_FORMATTER),	
	A52_AVLBLE_ON_MOBILE(4, "Encryption algorithm A5/2 is available on mobile device.", Constants.NULL_FORMATTER),
	A52_NT_AVLBLE_ON_MOBILE(5, "Encryption algorithm A5/2 is not available on mobile device.", Constants.NULL_FORMATTER),	
	A53_AVLBLE_ON_MOBILE(6, "Encryption algorithm A5/3 is available on mobile device.", Constants.NULL_FORMATTER),
	A53_NT_AVLBLE_ON_MOBILE(7, "Encryption algorithm A5/3 is not available on mobile device.", Constants.NULL_FORMATTER),
	
	NW_OP_REQ_START_ENC(8, "Network operator is requesting to start ciphering.", Constants.NULL_FORMATTER),
	NW_NT_OP_REQ_START_ENC(9, "Network operator is not requesting to start ciphering. Communication is insecure.", Constants.NULL_FORMATTER),
	
	NW_OP_USING_A51(10, "Network operator is using A5/1 encryption algorithm for mobile communication.", Constants.NULL_FORMATTER),
	NW_OP_USING_A52(11, "Network operator is using A5/2 encryption algorithm for mobile communication.", Constants.NULL_FORMATTER),
	NW_OP_USING_A53(12, "Network operator is using A5/3 encryption algorithm for mobile communication.", Constants.NULL_FORMATTER),
	
	NW_OP_REQ_IMEI(13, "Network operator is requesting IMEI.", Constants.NULL_FORMATTER),
	NW_OP_NT_REQ_IMEI(14, "Network operator is not requesting IMEI.", Constants.NULL_FORMATTER),	
	NW_OP_AUTHENTICATES(15, "Network operator does authentication.", Constants.NULL_FORMATTER),
	
	SS_SENDER(16, "Silent SMS Sender number", Constants.PHONE_NUM_FORMATTER),
	SS_TSMP(17, "Time at which silent SMS received", Constants.TIMESTAMP_FORMATTER),
	
	NW_OP_USING_UEA1(18, "Network operator uses uea1 algorithm for encryption.", Constants.NULL_FORMATTER),
	AUTN_NUM(19, "AUTN Number", Constants.HEX_FORMATTER),
	
	CELL_IDENTITY(20, "Cell Identity", Constants.NUMBER_FORMATTER),
	MOB_CNTRY_CODE(21, "Mobile Country Code", Constants.MOB_COUNTRY_CODE_FORMATTER), 
	MOB_NW_CODE(22, "Mobile Network Code", Constants.MOB_NETWORK_CODE_FORMATTER),
	LOC_AREA_CODE(23, "Location Area Code", Constants.NUMBER_FORMATTER),
	SINGLE_CHNL_ARFCN(24, "Single channel ARFCN", Constants.HEX_FORMATTER),	
	/*ARFCN_LIST(25, "List of ARFCNs", Constants.ARFCN_LIST_FORMATTER),*/
	MSCR(26, "MSCR", Constants.MSCR_FORMATTER),
	ASSIGNED_TMSI(27, "Assigned TMSI", Constants.HEX_FORMATTER), 
	CELL_OPTIONS(28, "Cell options", Constants.CELL_OPTIONS_FORMATTER), 
	CELL_SELECTION_PARAMS(29, "Cell selection parameters", Constants.CELL_SEL_PARAMS_FORMATTER);

	private int sTypeId;
	private String sInfo;
	private PacketFormatter sFormatter;

	private PacketAttributeType(int type, String info, PacketFormatter formatter) {
		this.sTypeId = type;
		this.sInfo = info;
		this.sFormatter = formatter;
	}
	
	public int getTypeId() {
		return sTypeId;
	}
	
	public String getInfo() {
		return sInfo;
	}
	
	public List<PacketAttribute> format(byte[] extractedBytes) {
		return sFormatter.format(extractedBytes, getTypeId(), getInfo());
	}
	
	public static PacketAttributeType getPacketAttributeType(int typeId) {		
		for (PacketAttributeType codeDisTxt : values()) {
			if (codeDisTxt.getTypeId() == typeId) {
				return codeDisTxt;
			}
		}
		throw new IllegalArgumentException("Incorrect packet attribute type id");
	}
}
