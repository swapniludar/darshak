package com.darshak.packetreader;

import static com.darshak.constants.PacketAttributeType.CELL_IDENTITY;
import static com.darshak.constants.PacketAttributeType.CELL_OPTIONS;
import static com.darshak.constants.PacketAttributeType.CELL_SELECTION_PARAMS;
import static com.darshak.constants.PacketAttributeType.LOC_AREA_CODE;
import static com.darshak.constants.PacketAttributeType.MOB_CNTRY_CODE;
import static com.darshak.constants.PacketAttributeType.MOB_NW_CODE;
import static com.darshak.constants.PacketAttributeType.MSCR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.darshak.constants.Constants;
import com.darshak.constants.PacketAttributeType;
import com.darshak.constants.PacketType;
import com.darshak.util.Utils;

/**
 * 
 * @author Swapnil Udar & Ravishankar Borgaonkar
 * 
 */
public class PacketConfigurator {

	private static final String LOG_TAG = PacketConfigurator.class
			.getSimpleName();

	private static final List<PacketIdentificationDetails> s3GPacketList = new ArrayList<PacketIdentificationDetails>();

	private static final List<PacketIdentificationDetails> sGSMPacketList = new ArrayList<PacketIdentificationDetails>();

	private static final List<PacketIdentificationDetails> sSilentSMSPacketList = new ArrayList<PacketIdentificationDetails>();

	private static final List<PacketIdentificationDetails> sProfilePacketList = new ArrayList<PacketIdentificationDetails>();

	static {
		// 3G
		s3GPacketList.add(initSecurityModeCodes());
		s3GPacketList.add(initAuthenticationRequestCodes());

		// GSM
		sGSMPacketList.add(gsmInitServiceRequestCode());
		sGSMPacketList.add(gsmInitCipheringModeCode());
		sGSMPacketList.add(gsmInitAuthenticationRequestCode());

		// Silent SMS
		sSilentSMSPacketList.add(initSilentSMSCodes());
		sSilentSMSPacketList.add(initPing4SilentSMSCodes());

		// Packets to create mobile network profile
		// sProfilePacketList.add(sysInfoTypeTwo());
		// sProfilePacketList.add(sysInfoTypeThree());
		// sProfilePacketList.add(sysInfoTypeSix());
		// sProfilePacketList.add(assignmentCommand());
		// sProfilePacketList.add(pagingRequestFive());
	}

	public static List<PacketIdentificationDetails> getPacketsList(
			int codeType, Context context) {
		switch (codeType) {
		case Constants.GSM:
			return sGSMPacketList;
		case Constants._3G:
			return s3GPacketList;
		case Constants.SMS:
			return sSilentSMSPacketList;
		case Constants.PROFILE_PARAMS:
			sProfilePacketList.clear();
			sProfilePacketList.add(sysInfoTypeThree(context));
			return sProfilePacketList;
		default:
			return null;
		}
	}

	private static PacketIdentificationDetails initSecurityModeCodes() {
		byte initSecurityModeBytes[] = { (byte) 0x0c, (byte) 0x20, (byte) 0xc0,
				(byte) 0x00, (byte) 0x60, (byte) 0x00, (byte) 0x4a,
				(byte) 0x30, (byte) 0x80, (byte) 0x48, (byte) 0x00,
				(byte) 0x00, (byte) 0x40, (byte) 0x00, (byte) 0x30,
				(byte) 0x00, (byte) 0xb4, (byte) 0x12, (byte) 0x3e, (byte) 0xb1 };

		List<Integer> anythingAllowedBytes = Arrays.asList(6, 7, 8, 9, 10, 11,
				12, 13, 14, 15, 16, 17, 18, 19);
		PacketIdentificationDetails packetIdentificationDetails = new PacketIdentificationDetails(
				PacketType._3G_INIT_SERV_REQ, initSecurityModeBytes,
				anythingAllowedBytes, 0, 5);

		PacketIdentificationDetails.WildByteInfo[] wildBytes = new PacketIdentificationDetails.WildByteInfo[] {};

		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo1 = packetIdentificationDetails.new ByteLevelPacketAttributeDetails(
				6, 7, new byte[] { (byte) 0x4a },
				PacketAttributeType.NW_OP_USING_UEA1);

		packetIdentificationDetails.addWildBytes(wildBytes);
		packetIdentificationDetails.addPacketAttributeDetails(indCodeInfo1);

		return packetIdentificationDetails;
	}

	private static PacketIdentificationDetails initAuthenticationRequestCodes() {
		byte packetBytes[] = { (byte) 0x05, (byte) 0x12, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x20, (byte) 0x10, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00 };

		List<Integer> anythingAllowedBytes = Arrays.asList(2, 3, 4, 5, 6, 7, 8,
				9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 22, 23, 24, 25, 26,
				27, 28, 29, 30, 31, 32, 33, 34, 35, 36);

		PacketIdentificationDetails packetIdentificationDetails = new PacketIdentificationDetails(
				PacketType._3G_INIT_AUTH_REQ, packetBytes,
				anythingAllowedBytes, 0, 20);

		PacketIdentificationDetails.WildByteInfo[] wildBytes = new PacketIdentificationDetails.WildByteInfo[] {};

		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo1 = packetIdentificationDetails.new ByteLevelPacketAttributeDetails(
				3, 19, PacketAttributeType.RND_NUM);

		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo2 = packetIdentificationDetails.new ByteLevelPacketAttributeDetails(
				21, 37, PacketAttributeType.AUTN_NUM);

		packetIdentificationDetails.addWildBytes(wildBytes);
		packetIdentificationDetails.addPacketAttributeDetails(indCodeInfo1,
				indCodeInfo2);

		return packetIdentificationDetails;
	}

	public static PacketIdentificationDetails gsmInitServiceRequestCode() {
		byte packetBytes[] = { (byte) 0x01, (byte) 0x3f, (byte) 0x35,
				(byte) 0x05, (byte) 0x24, (byte) 0x11, (byte) 0x03,
				(byte) 0x53, (byte) 0x19, (byte) 0x92, (byte) 0x05,
				(byte) 0xf4, (byte) 0xa4, (byte) 0x5c, (byte) 0xe1, (byte) 0x40 };
		List<Integer> anythingAllowedBytes = Arrays.asList(5, 7, 9, 12, 13, 14,
				15);

		PacketIdentificationDetails packetIdentificationDetails = new PacketIdentificationDetails(
				PacketType.GSM_INIT_SERV_REQ, packetBytes,
				anythingAllowedBytes, 0, 11);

		PacketIdentificationDetails.WildByteInfo[] wildBytes = {};
		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo1 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				7, 3, 1, new boolean[] { false },
				PacketAttributeType.A51_AVLBLE_ON_MOBILE);

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo2 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				7, 3, 1, new boolean[] { true },
				PacketAttributeType.A51_NT_AVLBLE_ON_MOBILE);

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo3 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				9, 1, 1, new boolean[] { true },
				PacketAttributeType.A53_AVLBLE_ON_MOBILE);

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo4 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				9, 1, 1, new boolean[] { false },
				PacketAttributeType.A53_NT_AVLBLE_ON_MOBILE);

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo5 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				9, 0, 1, new boolean[] { true },
				PacketAttributeType.A52_AVLBLE_ON_MOBILE);

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo6 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				9, 0, 1, new boolean[] { false },
				PacketAttributeType.A52_NT_AVLBLE_ON_MOBILE);

		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo7 = packetIdentificationDetails.new ByteLevelPacketAttributeDetails(
				12, 16, PacketAttributeType.TMSI_NUM);

		packetIdentificationDetails.addWildBytes(wildBytes);
		packetIdentificationDetails.addPacketAttributeDetails(indCodeInfo1,
				indCodeInfo2, indCodeInfo3, indCodeInfo4, indCodeInfo5,
				indCodeInfo6, indCodeInfo7);

		return packetIdentificationDetails;
	}

	private static PacketIdentificationDetails gsmInitCipheringModeCode() {
		byte packetBytes[] = { (byte) 0x03, (byte) 0x00, (byte) 0x0d,
				(byte) 0x06, (byte) 0x35, (byte) 0x00 };

		List<Integer> anythingAllowedBytes = Arrays.asList(1, 5);

		PacketIdentificationDetails packetIdentificationDetails = new PacketIdentificationDetails(
				PacketType.GSM_INIT_CIPHER_MODE, packetBytes,
				anythingAllowedBytes, 0, 4);

		PacketIdentificationDetails.WildByteInfo[] wildBytes = {};

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo1 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				5, 0, 1, new boolean[] { true },
				PacketAttributeType.NW_OP_REQ_START_ENC);

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo2 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				5, 0, 1, new boolean[] { false },
				PacketAttributeType.NW_NT_OP_REQ_START_ENC);

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo3 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				5, 3, 3, new boolean[] { false, false, false },
				PacketAttributeType.NW_OP_USING_A51);

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo4 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				5, 3, 3, new boolean[] { false, false, true },
				PacketAttributeType.NW_OP_USING_A52);

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo5 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				5, 3, 3, new boolean[] { false, true, false },
				PacketAttributeType.NW_OP_USING_A53);

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo6 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				5, 4, 1, new boolean[] { true },
				PacketAttributeType.NW_OP_REQ_IMEI);

		PacketIdentificationDetails.BitLevelPacketAttributeDetails indCodeInfo7 = packetIdentificationDetails.new BitLevelPacketAttributeDetails(
				5, 4, 1, new boolean[] { false },
				PacketAttributeType.NW_OP_NT_REQ_IMEI);

		packetIdentificationDetails.addWildBytes(wildBytes);
		packetIdentificationDetails.addPacketAttributeDetails(indCodeInfo1,
				indCodeInfo2, indCodeInfo3, indCodeInfo4, indCodeInfo5,
				indCodeInfo6, indCodeInfo7);

		return packetIdentificationDetails;
	}

	private static PacketIdentificationDetails gsmInitAuthenticationRequestCode() {

		byte packetBytes[] = { (byte) 0x05, (byte) 0x12, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x20 };

		List<Integer> anythingAllowedBytes = Arrays.asList(2, 3, 4, 5, 6, 7, 8,
				9, 10, 11, 12, 13, 14, 15, 16, 17, 18);

		PacketIdentificationDetails packetIdentificationDetails = new PacketIdentificationDetails(
				PacketType.GSM_INIT_AUTH_REQ, packetBytes,
				anythingAllowedBytes, 0, 19);

		PacketIdentificationDetails.WildByteInfo[] wildBytes = {};

		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo1 = packetIdentificationDetails.new ByteLevelPacketAttributeDetails(
				3, 19, PacketAttributeType.NW_OP_AUTHENTICATES);

		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo2 = packetIdentificationDetails.new ByteLevelPacketAttributeDetails(
				3, 19, PacketAttributeType.RND_NUM);

		/*
		 * PacketIdentificationDetails.ByteLevelPacketAttributeDetails
		 * indCodeInfo3 = codeInfo.new ByteLevelPacketAttributeDetails( 21, 37,
		 * PacketAttributeType.AUTN_NUM);
		 */

		packetIdentificationDetails.addWildBytes(wildBytes);
		packetIdentificationDetails.addPacketAttributeDetails(indCodeInfo1,
				indCodeInfo2/* , indCodeInfo3 */);

		return packetIdentificationDetails;
	}

	private static PacketIdentificationDetails initSilentSMSCodes() {
		byte packetBytes[] = { (byte) 0x64, (byte) 0x0d, (byte) 0x91,
				(byte) 0x94, (byte) 0x61, (byte) 0x90, (byte) 0x90,
				(byte) 0x85, (byte) 0x19, (byte) 0xf6, (byte) 0x00,
				(byte) 0x04, (byte) 0x41, (byte) 0x10, (byte) 0x82,
				(byte) 0x22, (byte) 0x21, (byte) 0x05, (byte) 0x40,
				(byte) 0x08, (byte) 0x06, (byte) 0x05, (byte) 0x04,
				(byte) 0x0b, (byte) 0x84, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

		List<Integer> anythingAllowedBytes = Arrays.asList(1, 2, 3, 4, 5, 6, 7,
				8, 9, 12, 13, 14, 15, 16, 17, 18, 27);

		PacketIdentificationDetails packetIdentificationDetails = new PacketIdentificationDetails(
				PacketType.SILENT_SMS, packetBytes, anythingAllowedBytes, 0, 19);

		PacketIdentificationDetails.WildByteInfo[] wildBytes = {};

		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo1 = packetIdentificationDetails.new ByteLevelPacketAttributeDetails(
				1, 10, PacketAttributeType.SS_SENDER);
		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo2 = packetIdentificationDetails.new ByteLevelPacketAttributeDetails(
				12, 19, PacketAttributeType.SS_TSMP);

		packetIdentificationDetails.addWildBytes(wildBytes);
		packetIdentificationDetails.addPacketAttributeDetails(indCodeInfo1,
				indCodeInfo2);
		return packetIdentificationDetails;
	}

	private static PacketIdentificationDetails initPing4SilentSMSCodes() {
		byte packetBytes[] = { (byte) 0x64, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x7c, (byte) 0x06, (byte) 0x03, (byte) 0xbe,
				(byte) 0xaf, (byte) 0x84, (byte) 0x8c, (byte) 0x96,
				(byte) 0x98, (byte) 0x31, (byte) 0x32, (byte) 0x33,
				(byte) 0x34, (byte) 0x00, (byte) 0x8d, (byte) 0x93,
				(byte) 0xbe, (byte) 0x31, (byte) 0x32, (byte) 0x33,
				(byte) 0x34, (byte) 0x00 };

		List<Integer> anythingAllowedBytes = Arrays.asList(1, 2, 3, 4, 5, 6, 7,
				8, 9, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
				26);

		PacketIdentificationDetails packetIdentificationDetails = new PacketIdentificationDetails(
				PacketType.SILENT_SMS, packetBytes, anythingAllowedBytes, 0, 19);

		PacketIdentificationDetails.WildByteInfo[] wildBytes = {};

		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo1 = packetIdentificationDetails.new ByteLevelPacketAttributeDetails(
				1, 10, PacketAttributeType.SS_SENDER);
		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo2 = packetIdentificationDetails.new ByteLevelPacketAttributeDetails(
				12, 19, PacketAttributeType.SS_TSMP);

		packetIdentificationDetails.addWildBytes(wildBytes);
		packetIdentificationDetails.addPacketAttributeDetails(indCodeInfo1,
				indCodeInfo2);
		return packetIdentificationDetails;
	}

	private static PacketIdentificationDetails assignmentCommand() {
		byte codeBytes[] = { (byte) 0x03, (byte) 0x00, (byte) 0x3d,
				(byte) 0x06, (byte) 0x2e, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x2b,
				(byte) 0x2b, (byte) 0x2b, (byte) 0x2b, (byte) 0x2b };

		List<Integer> anythingAllowedBytes = Arrays.asList(1, 5, 6, 7, 8, 9,
				10, 11, 12, 13, 14, 15, 16, 17);

		PacketIdentificationDetails codeInfo = new PacketIdentificationDetails(
				PacketType.ASGN_CMD, codeBytes, anythingAllowedBytes, 0, 22);

		PacketIdentificationDetails.WildByteInfo[] wildBytes = {};

		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo1 = codeInfo.new ByteLevelPacketAttributeDetails(
				5, 7, PacketAttributeType.SINGLE_CHNL_ARFCN);

		codeInfo.addWildBytes(wildBytes);
		codeInfo.addPacketAttributeDetails(indCodeInfo1);
		return codeInfo;
	}

	private static final byte sysInfo3Bytes[] = { (byte) 0x49, (byte) 0x06,
			(byte) 0x1b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x2b,
			(byte) 0x2b };

	private static final List<Integer> sysInfo3AnythingAllwdByts = Arrays
			.asList(3, 4, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
					19, 20);

	private static final PacketIdentificationDetails.WildByteInfo[] sysInfo3WildBytes = {};

	// Get Location area identity, Cell ID
	private static PacketIdentificationDetails sysInfoTypeThree(Context context) {
		getAllCellInfo(context);

		byte[] mobCountryCode = getMobCntryAndNetCode(context);
		sysInfo3Bytes[5] = mobCountryCode[0];
		sysInfo3Bytes[6] = mobCountryCode[1];

		sysInfo3Bytes[7] = mobCountryCode[2];

		PacketIdentificationDetails codeInfo = new PacketIdentificationDetails(
				PacketType.SYS_INFO_3, sysInfo3Bytes,
				sysInfo3AnythingAllwdByts, 0, 22);

		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo1 = codeInfo.new ByteLevelPacketAttributeDetails(
				3, 5, CELL_IDENTITY);
		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo2 = codeInfo.new ByteLevelPacketAttributeDetails(
				5, 7, MOB_CNTRY_CODE);
		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo3 = codeInfo.new ByteLevelPacketAttributeDetails(
				7, 8, MOB_NW_CODE);
		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo4 = codeInfo.new ByteLevelPacketAttributeDetails(
				8, 10, LOC_AREA_CODE);
		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo5 = codeInfo.new ByteLevelPacketAttributeDetails(
				10, 11, MSCR);
		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo6 = codeInfo.new ByteLevelPacketAttributeDetails(
				13, 14, CELL_OPTIONS);
		PacketIdentificationDetails.ByteLevelPacketAttributeDetails indCodeInfo7 = codeInfo.new ByteLevelPacketAttributeDetails(
				15, 16, CELL_SELECTION_PARAMS);

		codeInfo.addWildBytes(sysInfo3WildBytes);
		codeInfo.addPacketAttributeDetails(indCodeInfo1, indCodeInfo2,
				indCodeInfo3, indCodeInfo4, indCodeInfo5, indCodeInfo6,
				indCodeInfo7);
		return codeInfo;
	}

	private static byte[] getMobCountryCode(Context context) {
		TelephonyManager telManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String mobNetCountryCode = telManager.getNetworkCountryIso();
		Log.e(LOG_TAG, "Mobile country code from telephony manager : "
				+ mobNetCountryCode);
		return mobNetCountryCode.getBytes();
	}

	private static byte[] getMobCntryAndNetCode(Context context) {
		TelephonyManager telManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String mobNetNetworkCode = telManager.getNetworkOperator();
		Log.e(LOG_TAG, "Mobile network code from telephony manager : "
				+ mobNetNetworkCode);

		byte[] strBytes = mobNetNetworkCode.getBytes();
		strBytes[0] = (byte) (strBytes[0] << 4);
		strBytes[0] = (byte) (strBytes[0] >> 4);

		strBytes[1] = (byte) (strBytes[1] << 4);

		strBytes[2] = (byte) (strBytes[2] << 4);
		strBytes[2] = (byte) (strBytes[2] >> 4);

		strBytes[3] = (byte) (strBytes[3] << 4);
		strBytes[3] = (byte) (strBytes[3] >> 4);

		strBytes[4] = (byte) (strBytes[4] << 4);

		byte resul1 = (byte) (strBytes[0] | strBytes[1]);
		byte resul2 = (byte) (strBytes[2] | (byte) 0xF0);
		byte resul3 = (byte) (strBytes[3] | strBytes[4]);

		Log.e(LOG_TAG,
				"Mobile country code and network code "
						+ Utils.formatHexBytes(new byte[] { resul1, resul2,
								resul3 }));
		return new byte[] { resul1, resul2, resul3 };
	}

	private static void getAllCellInfo(Context context) {
		TelephonyManager telManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		List<NeighboringCellInfo> cellInfos = telManager
				.getNeighboringCellInfo();
		Log.e(LOG_TAG, "Cell Infos : " + cellInfos);
	}
}